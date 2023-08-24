package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.BandCreate;
import org.gigtool.gigtool.storage.services.model.BandResponse;
import org.gigtool.gigtool.storage.services.model.GenreResponse;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
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
    public void testGetBandById() {
        // Positive test
        ResponseEntity<BandResponse> bandInDatabaseById = bandService.getBandById(savedBandId);

        assertEquals(bandInDatabaseById.getBody().getId(), savedBandId);
        assertEquals(bandInDatabaseById.getBody().getName(), savedBand.getBody().getName());
        assertEquals(bandInDatabaseById.getBody().getGenre(), savedBand.getBody().getGenre());
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
    }

    @Test
    public void testDeleteBand() {
        ResponseEntity<String> deletedBand = bandService.deleteBand(savedBandId);

        assertTrue(deletedBand.getStatusCode().is2xxSuccessful());

        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedBandId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<String> negativeResult = bandService.deleteBand(randomUUID);

        assertTrue(negativeResult.getStatusCode().is4xxClientError());
    }
}