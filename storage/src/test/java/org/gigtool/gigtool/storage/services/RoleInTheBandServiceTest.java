package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.BandResponse;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandCreate;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RoleInTheBandServiceTest {

    @Autowired
    private RoleInTheBandService roleInTheBandService;
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private BandService bandService;


    private RoleInTheBandCreate roleInTheBandToSave;

    private ResponseEntity<RoleInTheBandResponse> savedRoleInTheBand;

    private UUID savedRoleInTheBandId;

    @BeforeEach
    public void setup() {
        roleInTheBandToSave = testUtils.getRandomRoleInTheBandCreate();
        savedRoleInTheBand = roleInTheBandService.addRoleInTheBand( roleInTheBandToSave );
        savedRoleInTheBandId = Objects.requireNonNull(savedRoleInTheBand.getBody().getId());
    }

    @Test
    @Transactional
    public void testAddRoleInTheBand() {

        assertEquals(savedRoleInTheBand.getBody().getName(),        roleInTheBandToSave.getName());
        assertEquals(savedRoleInTheBand.getBody().getDescription(), roleInTheBandToSave.getDescription());

        //Negative test: Try adding a RoleInTheBand with missing information
        RoleInTheBandCreate incompleteRoleInTheBand = new RoleInTheBandCreate(
                "name",
                null
        );

        ResponseEntity<RoleInTheBandResponse> negativeResult = roleInTheBandService.addRoleInTheBand( incompleteRoleInTheBand );

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetAllRolesInTheBand() {

        RoleInTheBandCreate roleInTheBandToSave1 = testUtils.getRandomRoleInTheBandCreate();
        RoleInTheBandCreate roleInTheBandToSave2 = testUtils.getRandomRoleInTheBandCreate();

        ResponseEntity<RoleInTheBandResponse> savedRoleInTheBand1 = roleInTheBandService.addRoleInTheBand( roleInTheBandToSave1 );
        ResponseEntity<RoleInTheBandResponse> savedRoleInTheBand2 = roleInTheBandService.addRoleInTheBand( roleInTheBandToSave2 );

        ResponseEntity<List<RoleInTheBandResponse>> savedRoleInTheBandList = roleInTheBandService.getAllRolesInTheBand();

        assertNotNull(savedRoleInTheBandList);
        assertFalse(Objects.requireNonNull( savedRoleInTheBandList.getBody()).isEmpty());

        assertEquals(3, savedRoleInTheBandList.getBody().size());

        assertEquals(roleInTheBandToSave1.getName(),        savedRoleInTheBandList.getBody().get(1).getName());
        assertEquals(roleInTheBandToSave1.getDescription(), savedRoleInTheBandList.getBody().get(1).getDescription());

        assertEquals(roleInTheBandToSave2.getName(),        savedRoleInTheBandList.getBody().get(2).getName());
        assertEquals(roleInTheBandToSave2.getDescription(), savedRoleInTheBandList.getBody().get(2).getDescription());
    }

    @Test
    public void testGetRoleInTheBandById() {

        // Positive test
        ResponseEntity<RoleInTheBandResponse> roleInTheBandInDatabaseById = roleInTheBandService.getRoleInTheBandById( savedRoleInTheBandId );

        assertEquals(Objects.requireNonNull(roleInTheBandInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedRoleInTheBand.getBody()).getId());
        assertEquals(roleInTheBandInDatabaseById.getBody().getName(),           savedRoleInTheBand.getBody().getName());
        assertEquals(roleInTheBandInDatabaseById.getBody().getDescription(),    savedRoleInTheBand.getBody().getDescription());

        // Negative test
        UUID randomUUID = UUID.randomUUID();

        while( randomUUID == savedRoleInTheBandId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<RoleInTheBandResponse> falseRoleInTheBandInDatabaseId = roleInTheBandService.getRoleInTheBandById( randomUUID );

        assertNull(falseRoleInTheBandInDatabaseId.getBody());
    }

    @Test
    public void testUpdateRoleInTheBand() {

        // Positive test
        ResponseEntity<RoleInTheBandResponse> roleInTheBandBeforeUpdate = savedRoleInTheBand;

        RoleInTheBandCreate updateForRoleInTheBand = new RoleInTheBandCreate(
                "name",
                "description"
        );

        ResponseEntity<RoleInTheBandResponse> updatedRoleInTheBand = roleInTheBandService.updateRoleInTheBand(savedRoleInTheBandId, updateForRoleInTheBand);

        assertEquals(roleInTheBandBeforeUpdate.getBody().getId(),          Objects.requireNonNull(updatedRoleInTheBand.getBody()).getId());

        assertEquals(updatedRoleInTheBand.getBody().getName(), "name");
        assertEquals(updatedRoleInTheBand.getBody().getDescription(), "description");

        // Negative test
        RoleInTheBandCreate updateForRoleInTheBandFalse = new RoleInTheBandCreate(
                "newName",
                null
        );

        ResponseEntity<RoleInTheBandResponse> updatedRoleInTheBandFalse = roleInTheBandService.updateRoleInTheBand(savedRoleInTheBandId, updateForRoleInTheBandFalse);

        assertTrue(updatedRoleInTheBandFalse.getStatusCode().is4xxClientError());

        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedRoleInTheBandId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<String> existingRoleInTheBandFalse = roleInTheBandService.deleteRoleInTheBand( randomUUID );

        assertTrue(existingRoleInTheBandFalse.getStatusCode().is4xxClientError());

        //negative existing role is empty
        ResponseEntity<RoleInTheBandResponse> existingRoleEmpty = roleInTheBandService.updateRoleInTheBand( randomUUID, updateForRoleInTheBand );

        assertTrue(existingRoleEmpty.getStatusCode().is4xxClientError());
    }

    @Test
    public void testDeleteRoleInTheBand() {

        ResponseEntity<String> deletedRoleInTheBand = roleInTheBandService.deleteRoleInTheBand( savedRoleInTheBandId );

        assertTrue(deletedRoleInTheBand.getStatusCode().is2xxSuccessful());

        //negative rolieId = null
        ResponseEntity<String> negativeResult = roleInTheBandService.deleteRoleInTheBand( null );

        assertTrue(negativeResult.getStatusCode().is4xxClientError());

        //negative role is part of a band
        ResponseEntity<RoleInTheBandResponse> newRole = testUtils.getRandomRoleInTheBandResponse();
        ResponseEntity<BandResponse> band = testUtils.getRandomBandResponse();
        ResponseEntity<BandResponse> bandResponseResponseEntity = bandService.addRoleToBand(band.getBody().getId(), newRole.getBody().getId());

        ResponseEntity<String> negativeResult2 = roleInTheBandService.deleteRoleInTheBand( newRole.getBody().getId() );

        assertTrue(negativeResult2.getStatusCode().is4xxClientError());
    }


}
