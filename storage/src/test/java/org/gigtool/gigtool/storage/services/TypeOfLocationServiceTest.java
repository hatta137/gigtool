package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
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
public class TypeOfLocationServiceTest {

    @Autowired
    private TypeOfLocationService typeOfLocationService;
    @Autowired
    private TestUtils testUtils;


    private TypeOfLocationCreate typeOfLocationToSave;
    private ResponseEntity<TypeOfLocationResponse> savedTypeOfLocation;
    private UUID savedTypeOfLocationId;
    @BeforeEach
    public void setup() {
        typeOfLocationToSave = testUtils.getRandomTypeOfLocationCreate();
        savedTypeOfLocation = typeOfLocationService.addTypeOfLocation( typeOfLocationToSave );
        savedTypeOfLocationId = Objects.requireNonNull(savedTypeOfLocation.getBody()).getId();
    }

    @Test
    @Transactional
    public void testAddTypeOfLocation() {

        assertEquals(Objects.requireNonNull(savedTypeOfLocation.getBody()).getName(),       typeOfLocationToSave.getName());
        assertEquals(savedTypeOfLocation.getBody().getDescription(),typeOfLocationToSave.getDescription());

        // Negative Test: Try adding an address with missing information
        TypeOfLocationCreate incompleteTypeOfLocation = new TypeOfLocationCreate(
                "name",
                null
        );

        ResponseEntity<TypeOfLocationResponse> negativeResult = typeOfLocationService.addTypeOfLocation( incompleteTypeOfLocation );

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetAllTypeOfLocation() {

        TypeOfLocationCreate typeOfLocationToSave1 = testUtils.getRandomTypeOfLocationCreate();
        TypeOfLocationCreate typeOfLocationToSave2 = testUtils.getRandomTypeOfLocationCreate();


        ResponseEntity<TypeOfLocationResponse> savedTypeOfLocationToSave1 = typeOfLocationService.addTypeOfLocation( typeOfLocationToSave1 );
        ResponseEntity<TypeOfLocationResponse> savedTypeOfLocationToSave2 = typeOfLocationService.addTypeOfLocation( typeOfLocationToSave2 );

        ResponseEntity<List<TypeOfLocationResponse>> savedTypeOfLocationList = typeOfLocationService.getAllTypeOfLocation();

        assertNotNull(savedTypeOfLocationList);
        assertFalse(Objects.requireNonNull(savedTypeOfLocationList.getBody()).isEmpty());

        assertEquals(3, savedTypeOfLocationList.getBody().size());

        assertEquals(typeOfLocationToSave1.getName(),           savedTypeOfLocationList.getBody().get(1).getName());
        assertEquals(typeOfLocationToSave1.getDescription(),    savedTypeOfLocationList.getBody().get(1).getDescription());

        assertEquals(typeOfLocationToSave2.getName(),           savedTypeOfLocationList.getBody().get(2).getName());
        assertEquals(typeOfLocationToSave2.getDescription(),    savedTypeOfLocationList.getBody().get(2).getDescription());
    }

    @Test
    public void testGetTypeOfLocationById() {

        // positive Test
        ResponseEntity<TypeOfLocationResponse> typeOfLocationInDatabaseById = typeOfLocationService.getTypeOfLocationById(savedTypeOfLocationId);

        assertEquals(Objects.requireNonNull(typeOfLocationInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedTypeOfLocation.getBody()).getId());
        assertEquals(typeOfLocationInDatabaseById.getBody().getName(),          savedTypeOfLocation.getBody().getName());
        assertEquals(typeOfLocationInDatabaseById.getBody().getDescription(),   savedTypeOfLocation.getBody().getDescription());

        //negative Test
        UUID randomUUID = UUID.randomUUID();

        while( randomUUID == savedTypeOfLocationId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<TypeOfLocationResponse> falseTypeOfLocationInDatabaseById = typeOfLocationService.getTypeOfLocationById(randomUUID);

        assertNull(falseTypeOfLocationInDatabaseById.getBody());
    }

    @Test
    @Transactional
    public void testUpdateTypeOfLocation() {

        ResponseEntity<TypeOfLocationResponse> typeOfLocationBeforeUpdate = savedTypeOfLocation;

        TypeOfLocationCreate updateForTypeOfLocation = new TypeOfLocationCreate(
                "name",
                "description"
        );

        //positiv
        ResponseEntity<TypeOfLocationResponse> updatedTypeOfLocation = typeOfLocationService.updateTypeOfLocation(savedTypeOfLocationId, updateForTypeOfLocation);

        assertEquals(Objects.requireNonNull(typeOfLocationBeforeUpdate.getBody()).getId(), Objects.requireNonNull(updatedTypeOfLocation.getBody()).getId());

        assertEquals(updatedTypeOfLocation.getBody().getName(), "name");
        assertEquals(updatedTypeOfLocation.getBody().getDescription(), "description");

        //negative
        UUID randomUUID = UUID.randomUUID();
        while (randomUUID == typeOfLocationBeforeUpdate.getBody().getId()) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<TypeOfLocationResponse> updatedTypeOfLocationFalse = typeOfLocationService.updateTypeOfLocation(randomUUID, updateForTypeOfLocation);

        assertTrue(updatedTypeOfLocationFalse.getStatusCode().is4xxClientError());

    }

    @Test
    public void testDeleteTypeOfLocation() {

        //positive
        ResponseEntity<TypeOfLocationResponse> deletedTypeOfLocation = typeOfLocationService.deleteTypeOfLocation( savedTypeOfLocationId );

        assertNull(deletedTypeOfLocation.getBody());

        //negative
        UUID randomUUID = UUID.randomUUID();
        while (randomUUID == savedTypeOfLocationId) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<TypeOfLocationResponse> deletedTypeOfLocationFalse = typeOfLocationService.deleteTypeOfLocation( randomUUID );

        assertTrue(deletedTypeOfLocationFalse.getStatusCode().is4xxClientError());
    }
}
