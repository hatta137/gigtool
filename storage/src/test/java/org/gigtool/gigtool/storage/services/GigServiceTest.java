package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
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
    private RentalService rentalService;
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
        updateForGig.setStartTime( LocalDateTime.of(2023, 6, 1, 15, 30, 0) );
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
        gigToSave.setStartTime( LocalDateTime.of(2023, 4, 1, 15, 30, 0) );
        gigToSave.setEndTime( LocalDateTime.of(2023, 5, 1, 15, 30, 0) );

        ResponseEntity<GigResponse> gigTime = gigService.addGig( gigToSave );

        assertTrue(gigTime.getStatusCode().is2xxSuccessful());
        ResponseEntity<GigResponse> gigTimeUpdate = gigService.updateGig(gigTime.getBody().getId(), updateForGig);

        assertTrue(gigTimeUpdate.getStatusCode().is4xxClientError());


        //overlapping equipment booking
        RentalCreate overlappingRentalCreate = new RentalCreate();


        overlappingRentalCreate.setName("overlapping");
        overlappingRentalCreate.setDescription("overlapping");
        overlappingRentalCreate.setAddress( testUtils.getRandomAddressResponse().getBody().getId() );
        overlappingRentalCreate.setResponsiblePerson("overlappingPerson");
        overlappingRentalCreate.setStartTime( LocalDateTime.of(2023, 1, 1, 15, 30, 0) );
        overlappingRentalCreate.setEndTime( LocalDateTime.of(2023, 3, 1, 15, 30, 0) );

        ResponseEntity<RentalResponse> overlappingRental = rentalService.addRental(overlappingRentalCreate);

        assertTrue(overlappingRental.getStatusCode().is2xxSuccessful());

        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();

        overlappingRental = rentalService.addEquipmentToRental(overlappingRental.getBody().getId(), equipment.getBody().getId());


        assertTrue(overlappingRental.getBody().getEquipmentList().stream()
                .anyMatch(equipmentIterator -> equipmentIterator.getId().equals(equipment.getBody().getId())));

        ResponseEntity<GigResponse> gigWithEquipment = gigService.addEquipmentToGig(savedGigId, equipment.getBody().getId());

        assertTrue(gigWithEquipment.getBody().getEquipmentList().stream()
                .anyMatch(equipmentIterator -> equipmentIterator.getId().equals(equipment.getBody().getId())));

        updateForGig = new GigCreate();

        updateForGig.setStartTime( LocalDateTime.of(2023, 1, 1, 15, 30, 0) );
        updateForGig.setEndTime( LocalDateTime.of(2023, 3, 1, 15, 30, 0) );

        ResponseEntity<GigResponse> gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

        //update Time test
        updateForGig = new GigCreate();

        updateForGig.setStartTime( LocalDateTime.of(2023, 6, 1, 15, 30, 0) );

        gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is2xxSuccessful());

        updateForGig = new GigCreate();

        updateForGig.setEndTime( LocalDateTime.of(2024, 3, 1, 15, 30, 0) );

        gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is2xxSuccessful());

        //negative updateTimetest
        updateForGig = new GigCreate();

        updateForGig.setStartTime( LocalDateTime.of(2023, 1, 1, 15, 30, 0) );

        gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

        updateForGig = new GigCreate();

        updateForGig.setEndTime( LocalDateTime.of(2023, 3, 1, 15, 30, 0) );

        gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

        //negativ test typeOfGig / Band / Address

        //typeOfGig
        updateForGig = new GigCreate();

        updateForGig.setTypeOfGig(UUID.randomUUID());

        gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

        //Band
        updateForGig = new GigCreate();

        updateForGig.setBand(UUID.randomUUID());

        gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

        //Address
        updateForGig = new GigCreate();

        updateForGig.setAddress(UUID.randomUUID());

        gigTimeUpdateWithEquipment = gigService.updateGig(savedGigId, updateForGig);

        assertTrue(gigTimeUpdateWithEquipment.getStatusCode().is4xxClientError());






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
    public void testAddEquipmentToGig() {

        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();
        ResponseEntity<GigResponse> gigWithEquipment = gigService.addEquipmentToGig( savedGigId, equipment.getBody().getId());

        assertTrue(gigWithEquipment.getStatusCode().is2xxSuccessful());
        assertEquals(gigWithEquipment.getBody().getEquipmentList().get(0).getId(), equipment.getBody().getId());

        //negative equipmentId ==  null
        ResponseEntity<GigResponse> gigEquNull = gigService.addEquipmentToGig( savedGigId, null);
        assertFalse(gigEquNull.getStatusCode().is2xxSuccessful());

        //negative equipment already part of equipmentList
        ResponseEntity<GigResponse> alreadyPartOf = gigService.addEquipmentToGig( savedGigId, equipment.getBody().getId());
        assertFalse(alreadyPartOf.getStatusCode().is2xxSuccessful());

        //gig is empty
        ResponseEntity<EquipmentResponse> equipment2 = testUtils.getRandomEquipmentResponse();
        ResponseEntity<GigResponse> gigEmpty = gigService.addEquipmentToGig( UUID.randomUUID(), equipment2.getBody().getId());
        assertFalse(gigEmpty.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testDeleteEquipmentFromGig() {

        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();
        ResponseEntity<GigResponse> gigWithEquipment = gigService.addEquipmentToGig( savedGigId, equipment.getBody().getId());

        assertTrue(gigWithEquipment.getStatusCode().is2xxSuccessful());

        assertEquals(gigWithEquipment.getBody().getEquipmentList().size(), 1);

        ResponseEntity<GigResponse> deletedFromGig = gigService.deleteEquipmentFromGig( savedGigId, equipment.getBody().getId());

        assertTrue(deletedFromGig.getStatusCode().is2xxSuccessful());

        assertEquals(deletedFromGig.getBody().getEquipmentList().size(), 0);

        //negativ equipmentId == null
        ResponseEntity<GigResponse> gigEquNull = gigService.deleteEquipmentFromGig( gigWithEquipment.getBody().getId(), null);
        assertTrue(gigEquNull.getStatusCode().is4xxClientError());

        //negative non existing equipment
        ResponseEntity<GigResponse> gigEquNonExist = gigService.deleteEquipmentFromGig( gigWithEquipment.getBody().getId(), UUID.randomUUID());
        assertTrue(gigEquNonExist.getStatusCode().is4xxClientError());

        //negative equipmentList not containing equipment
        ResponseEntity<EquipmentResponse> equipment2 = testUtils.getRandomEquipmentResponse();
        ResponseEntity<GigResponse> gigWith2Equ = gigService.addEquipmentToGig( savedGigId, equipment2.getBody().getId());

        ResponseEntity<EquipmentResponse> equipment3 = testUtils.getRandomEquipmentResponse();

        ResponseEntity<GigResponse> deleteNotConEqu = gigService.deleteEquipmentFromGig( savedGigId, equipment3.getBody().getId());
        assertTrue(gigEquNonExist.getStatusCode().is4xxClientError());
    }

    @Test
    public void testDeleteGig() {

        ResponseEntity<String> deletedGig = gigService.deleteGig(savedGigId);

        assertTrue(deletedGig.getStatusCode().is2xxSuccessful());

        assertTrue(deletedGig.getBody().contains("Gig is deleted"));

        //negative gigId == null
        ResponseEntity<String> deletedGigNull = gigService.deleteGig(null);
        assertTrue(deletedGigNull.getStatusCode().is4xxClientError());
        assertTrue(deletedGigNull.getBody().contains("No ID"));

        //negative gigId not in Database
        ResponseEntity<String> deletedGigNotInDataBase = gigService.deleteGig(UUID.randomUUID());
        assertTrue(deletedGigNotInDataBase.getStatusCode().is4xxClientError());
    }
}
