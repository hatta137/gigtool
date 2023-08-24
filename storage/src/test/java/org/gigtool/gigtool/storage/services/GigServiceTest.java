package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.gigtool.gigtool.storage.services.model.GigCreate;
import org.gigtool.gigtool.storage.services.model.GigResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GigServiceTest {

    @Autowired
    private GigService gigService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private TestUtils testUtils;


    private GigCreate gigToSave;
    private ResponseEntity<GigResponse> savedGig;
    private UUID savedGigId;

    @BeforeEach
    public void setup() {
        gigToSave = testUtils.getRandomGigCreate();
        savedGig = gigService.addGig(gigToSave);
        savedGigId = savedGig.getBody().getId();
    }

    @Test
    @Transactional
    public void testAddGig() {

        //positive Test
        assertEquals(savedGig.getBody().getName(),                  gigToSave.getName());
        assertEquals(savedGig.getBody().getDescription(),           gigToSave.getDescription());
        assertEquals(savedGig.getBody().getTypeOfGig().getId(),     gigToSave.getTypeOfGig());
        assertEquals(savedGig.getBody().getBand().getId(),          gigToSave.getBand());
        assertEquals(savedGig.getBody().getAddress().getId(),       gigToSave.getAddress());
        assertEquals(savedGig.getBody().getStartTime(),             gigToSave.getStartTime());
        assertEquals(savedGig.getBody().getEndTime(),               gigToSave.getEndTime());

        //negative Test Gig without a name
        GigCreate gig = testUtils.getRandomGigCreate();
        gig.setName(null);

        ResponseEntity<GigResponse> savedGigWithoutName = gigService.addGig( gig );
        assertTrue(savedGigWithoutName.getStatusCode().is4xxClientError());

        //negative overlapping with another gig
        gig.setName("name");
        gig.setStartTime( savedGig.getBody().getStartTime() );
        ResponseEntity<GigResponse> gigWithOverlappingTime = gigService.addGig( gig );
        assertTrue(gigWithOverlappingTime.getStatusCode().is4xxClientError());

        //negative address is empty
        GigCreate gigWithoutExistingAddress = testUtils.getRandomGigCreate();
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID == gigWithoutExistingAddress.getAddress() ) {
            randomUUID = UUID.randomUUID();
        }

        gigWithoutExistingAddress.setAddress( randomUUID );
        ResponseEntity<GigResponse> savedGigWithoutAddress = gigService.addGig( gigWithoutExistingAddress );
        assertTrue(savedGigWithoutAddress.getStatusCode().is4xxClientError());
    }

    @Test
    public void testGetAllGigs() {

        GigCreate gigToSave1 = testUtils.getRandomGigCreate();
        GigCreate gigToSave2 = testUtils.getRandomGigCreate();

        ResponseEntity<GigResponse> savedGig1 = gigService.addGig(gigToSave1);
        ResponseEntity<GigResponse> savedGig2 = gigService.addGig(gigToSave2);

        ResponseEntity<List<GigResponse>> savedGigs = gigService.getAllGigs();

        assertEquals(gigToSave1.getName(), savedGigs.getBody().get(1).getName());
        assertEquals(gigToSave2.getName(), savedGigs.getBody().get(2).getName());
    }

    @Test
    public void testGetGigById() {

        ResponseEntity<GigResponse> gigInDatabaseById = gigService.getGigById( savedGigId );

        assertEquals(gigInDatabaseById.getBody().getName(), savedGig.getBody().getName());

        //negative
        GigCreate gigWithoutExistingAddress = testUtils.getRandomGigCreate();
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID == savedGigId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<GigResponse> gigNotInDatabase = gigService.getGigById( randomUUID );
        assertTrue(gigNotInDatabase.getStatusCode().is4xxClientError());
    }

    @Test
    public void testUpdateGig() {

        GigCreate updateForGig = new GigCreate();
        updateForGig.setName( "update" );
        updateForGig.setDescription( "update" );
        updateForGig.setAddress( testUtils.getRandomAddressResponse().getBody().getId() );
        updateForGig.setTypeOfGig( testUtils.getRandomTypeOfGigResponse().getBody().getId() );
        updateForGig.setBand( testUtils.getRandomBandResponse().getBody().getId() );
        updateForGig.setStartTime( LocalDateTime.now() );
        updateForGig.setEndTime( LocalDateTime.of(2024, 1, 1, 15, 30, 0) );

        ResponseEntity<GigResponse> updatedGig = gigService.updateGig( savedGigId, updateForGig);
        assertEquals(updatedGig.getBody().getName(), "update");
        assertEquals(updatedGig.getBody().getDescription(), "update");

        //negative gig not in database
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID == savedGigId ) {
            randomUUID = UUID.randomUUID();
        }
        ResponseEntity<GigResponse> gigNotInDb = gigService.updateGig( randomUUID, updateForGig);
        assertTrue(gigNotInDb.getStatusCode().is4xxClientError());

        //negative gigId == null
        ResponseEntity<GigResponse> gigIdNull = gigService.updateGig( null, updateForGig);
        assertTrue(gigIdNull.getStatusCode().is4xxClientError());

        //negative overlapping time
        gigToSave.setStartTime( LocalDateTime.now() );

        ResponseEntity<GigResponse> gigTime = gigService.addGig( gigToSave );
        ResponseEntity<GigResponse> gigTimeUpdate = gigService.updateGig(gigTime.getBody().getId(), updateForGig);

        assertTrue(gigTimeUpdate.getStatusCode().is4xxClientError());

        //negative attributes not found in db
/*        GigCreate noAddressInDb = updateForGig;
        noAddressInDb.setAddress( UUID.randomUUID() );
        ResponseEntity<GigResponse> newGig1 = gigService.addGig( testUtils.getRandomGigCreate() );
        ResponseEntity<GigResponse> noAddress = gigService.updateGig( newGig1.getBody().getId(), noAddressInDb );
        assertTrue(noAddress.getStatusCode().is4xxClientError());

        GigCreate noTypeOfGig = updateForGig;
        noAddressInDb.setTypeOfGig( UUID.randomUUID() );
        ResponseEntity<GigResponse> newGig2 = gigService.addGig( testUtils.getRandomGigCreate() );
        ResponseEntity<GigResponse> noToG = gigService.updateGig( newGig2.getBody().getId(), noTypeOfGig );
        assertTrue(noToG.getStatusCode().is4xxClientError());

        GigCreate noBand = updateForGig;
        noAddressInDb.setBand( UUID.randomUUID() );
        ResponseEntity<GigResponse> newGig3 = gigService.addGig( testUtils.getRandomGigCreate() );
        ResponseEntity<GigResponse> noBandInDb = gigService.updateGig( newGig3.getBody().getId(), noBand );
        assertTrue(noBandInDb.getStatusCode().is4xxClientError());*/

    }

    @Test
    @Transactional
    public void testAddAndDeleteEquipmentToGig() {

        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();
        ResponseEntity<GigResponse> gigWithEquipment = gigService.addEquipmentToGig( savedGigId, equipment.getBody().getId());

        assertTrue(gigWithEquipment.getStatusCode().is2xxSuccessful());
        assertEquals(gigWithEquipment.getBody().getEquipmentList().get(0).getId(), equipment.getBody().getId());

        //negative equipmentId ==  null
        ResponseEntity<GigResponse> gigEquNull = gigService.addEquipmentToGig( savedGigId, null);
        assertFalse(gigEquNull.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void testDeleteGig() {

    }
}
