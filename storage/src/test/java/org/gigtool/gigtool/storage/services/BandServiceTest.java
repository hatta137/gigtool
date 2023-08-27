package org.gigtool.gigtool.storage.services;

import org.apache.coyote.Response;
import org.gigtool.gigtool.storage.repositories.BandRepository;
import org.gigtool.gigtool.storage.services.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BandServiceTest {

    @Autowired
    private BandService bandService;
    @Autowired
    private GigService gigService;
    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private RentalService rentalService;

    @Autowired
    private TestUtils testUtils;

    private BandCreate bandToSave;
    private ResponseEntity<BandResponse> savedBand;
    private UUID savedBandId;

    @BeforeEach
    public void setup() {
        bandToSave = testUtils.getRandomBandCreate();
        savedBand = bandService.addBand(bandToSave);
        savedBandId = savedBand.getBody().getId();
    }

    @Test
    @Transactional
    public void testAddBand() {

        assertEquals(savedBand.getBody().getName(), bandToSave.getName());
        assertEquals(savedBand.getBody().getGenre().getId(), bandToSave.getGenre());
        assertEquals(savedBand.getBody().getListOfRole().get(0).getId(), bandToSave.getMainRoleInTheBand());

        // Negative Test: Try adding a band with missing information
        BandCreate incompleteBand = new BandCreate(
                "name",
                null,
                null
        );

        ResponseEntity<BandResponse> negativeResult = bandService.addBand(incompleteBand);

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());

        UUID randomUUID = UUID.randomUUID();

        while (randomUUID.equals(savedBandId)) {
            randomUUID = UUID.randomUUID();
        }

        BandCreate incompleteBand2 = new BandCreate(
                "name",
                randomUUID,
                null
        );

        ResponseEntity<BandResponse> negativeResult2 = bandService.addBand(incompleteBand2);

        assertTrue(negativeResult2.getStatusCode().is4xxClientError());


        BandCreate bandCreate = testUtils.getRandomBandCreate();
        bandCreate.setGenre(UUID.randomUUID());
        negativeResult = bandService.addBand(bandCreate);
        assertTrue(negativeResult.getStatusCode().is4xxClientError());

        bandCreate = testUtils.getRandomBandCreate();
        bandCreate.setMainRoleInTheBand(UUID.randomUUID());
        negativeResult = bandService.addBand(bandCreate);
        assertTrue(negativeResult.getStatusCode().is4xxClientError());

    }

    @Test
    public void testGetAllBands() {
        BandCreate bandToSave1 = testUtils.getRandomBandCreate();
        BandCreate bandToSave2 = testUtils.getRandomBandCreate();

        ResponseEntity<BandResponse> savedBand1 = bandService.addBand(bandToSave1);
        ResponseEntity<BandResponse> savedBand2 = bandService.addBand(bandToSave2);

        ResponseEntity<List<BandResponse>> savedBandList = bandService.getAllBands();

        assertNotNull(savedBandList);
        assertFalse(savedBandList.getBody().isEmpty());
        assertEquals(3, savedBandList.getBody().size());

        assertEquals(bandToSave1.getName(), savedBandList.getBody().get(1).getName());
        assertEquals(bandToSave1.getGenre(), savedBandList.getBody().get(1).getGenre().getId());
        assertEquals(bandToSave1.getMainRoleInTheBand(), savedBandList.getBody().get(1).getListOfRole().get(0).getId());

        assertEquals(bandToSave2.getName(), savedBandList.getBody().get(2).getName());
        assertEquals(bandToSave2.getGenre(), savedBandList.getBody().get(2).getGenre().getId());
        assertEquals(bandToSave2.getMainRoleInTheBand(), savedBandList.getBody().get(2).getListOfRole().get(0).getId());
    }

    @Test
    public void testAddEquipmentToBand() {
        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();

        ResponseEntity<BandResponse> bandWithEquipment = bandService.addEquipmentToBand(savedBandId, equipment.getBody().getId());

        assertTrue(bandWithEquipment.getBody().getEquipmentList().stream()
                .anyMatch(equipmentIterator -> equipmentIterator.getId().equals(equipment.getBody().getId())));

        bandWithEquipment = bandService.addEquipmentToBand(savedBandId, equipment.getBody().getId());

        assertTrue(bandWithEquipment.getStatusCode().is4xxClientError());

        UUID randomUUID = UUID.randomUUID();

        while(randomUUID == equipment.getBody().getId()) {
            randomUUID = UUID.randomUUID();
        }

        bandWithEquipment = bandService.addEquipmentToBand(savedBandId, randomUUID);

        assertTrue(bandWithEquipment.getStatusCode().is4xxClientError());

        ResponseEntity<EquipmentResponse> equipment2 = testUtils.getRandomEquipmentResponse();
        GigCreate gigCreate = testUtils.getRandomGigCreate();
        gigCreate.setBand(savedBandId);
        ResponseEntity<GigResponse> gig = gigService.addGig(gigCreate);

        gigService.addEquipmentToGig(gig.getBody().getId(), equipment2.getBody().getId());

        bandWithEquipment = bandService.addEquipmentToBand(savedBandId, equipment2.getBody().getId());

        assertTrue(bandWithEquipment.getBody().getEquipmentList().stream()
                .anyMatch(equipmentIterator -> equipmentIterator.getId().equals(equipment2.getBody().getId())));

        ResponseEntity<EquipmentResponse> equipment3 = testUtils.getRandomEquipmentResponse();
        RentalCreate rentalCreate = testUtils.getRandomRentalCreate();
        rentalCreate.setStartTime(gigCreate.getStartTime());
        rentalCreate.setEndTime(gigCreate.getEndTime());
        ResponseEntity<RentalResponse> rental = rentalService.addRental(rentalCreate);
        rentalService.addEquipmentToRental(rental.getBody().getId(), equipment3.getBody().getId());

        bandWithEquipment = bandService.addEquipmentToBand(savedBandId, equipment3.getBody().getId());

        assertTrue(bandWithEquipment.getStatusCode().is4xxClientError());

        bandWithEquipment = bandService.addEquipmentToBand(savedBandId, null);

        assertTrue(bandWithEquipment.getStatusCode().is4xxClientError());



        assertTrue(gigService.deleteGig(gig.getBody().getId()).getStatusCode().is2xxSuccessful());


    }

    @Test
    public void testAddRoleInTheBand() {
        ResponseEntity<RoleInTheBandResponse> role = testUtils.getRandomRoleInTheBandResponse();
        ResponseEntity<BandResponse> bandWithRole = bandService.addRoleToBand(savedBandId, role.getBody().getId());
        assertTrue(bandWithRole.getStatusCode().is2xxSuccessful());

        bandWithRole = bandService.addRoleToBand(savedBandId, null);

        assertTrue(bandWithRole.getStatusCode().is4xxClientError());

        bandWithRole = bandService.addRoleToBand(savedBandId, UUID.randomUUID());

        assertTrue(bandWithRole.getStatusCode().is4xxClientError());

        bandWithRole = bandService.addRoleToBand(UUID.randomUUID(), role.getBody().getId());

        assertTrue(bandWithRole.getStatusCode().is4xxClientError());
    }

    @Test
    public void testGetBandById() {
        // Positive test
        ResponseEntity<BandResponse> bandInDatabaseById = bandService.getBandById(savedBandId);

        assertEquals(bandInDatabaseById.getBody().getId(), savedBandId);
        assertEquals(bandInDatabaseById.getBody().getName(), savedBand.getBody().getName());
        assertEquals(bandInDatabaseById.getBody().getGenre().getId(), savedBand.getBody().getGenre().getId());
        assertEquals(bandInDatabaseById.getBody().getListOfRole().get(0).getId(), savedBand.getBody().getListOfRole().get(0).getId());

        // Negative test
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID.equals(savedBandId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<BandResponse> falseBandInDatabaseById = bandService.getBandById(randomUUID);

        assertNull(falseBandInDatabaseById.getBody());
    }

    @Test
    @Transactional
    public void testUpdateBand() {

        BandCreate updateForBand = new BandCreate(
                "newName",
                testUtils.getRandomGenreResponse().getBody().getId(),
                null
        );

        ResponseEntity<BandResponse> bandBeforeUpdate = savedBand;

        ResponseEntity<BandResponse> updatedBand = bandService.updateBand(savedBandId, updateForBand);

        assertEquals(updatedBand.getBody().getName(), updateForBand.getName());
        assertEquals(updatedBand.getBody().getGenre().getId(), updateForBand.getGenre());


        // Negative test
        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedBandId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<BandResponse> falseBandUpdate = bandService.updateBand(randomUUID, updateForBand);

        assertTrue(falseBandUpdate.getStatusCode().is4xxClientError());

        falseBandUpdate = bandService.updateBand(null, updateForBand);

        assertTrue(falseBandUpdate.getStatusCode().is4xxClientError());

        BandCreate falseUpdateForBand = testUtils.getRandomBandCreate();
        falseUpdateForBand.setGenre(UUID.randomUUID());

        falseBandUpdate = bandService.updateBand(savedBandId, falseUpdateForBand);

        assertTrue(falseBandUpdate.getStatusCode().is4xxClientError());
    }

    @Test
    public void testDeleteBand() {

        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedBandId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<String> negativeResult = bandService.deleteBand(randomUUID);

        assertTrue(negativeResult.getStatusCode().is4xxClientError());

        negativeResult = bandService.deleteBand(null);
        assertTrue(negativeResult.getStatusCode().is4xxClientError());

        GigCreate gigCreate = testUtils.getRandomGigCreate();
        gigCreate.setBand(savedBandId);
        ResponseEntity<GigResponse> gig = gigService.addGig(gigCreate);

        negativeResult = bandService.deleteBand(savedBandId);
        assertTrue(negativeResult.getStatusCode().is4xxClientError());

        gigService.deleteGig(gig.getBody().getId());
        ResponseEntity<String> deletedBand = bandService.deleteBand(savedBandId);

        assertTrue(deletedBand.getStatusCode().is2xxSuccessful());




    }
}