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

        assertEquals(savedTypeOfLocation.getBody().getName(),       typeOfLocationToSave.getName());
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
    public void testGetAddressById() {

        // positiv Test
        ResponseEntity<TypeOfLocationResponse> typeOfLocationInDatabaseById = typeOfLocationService.getTypeOfLocationById(savedTypeOfLocationId);

        assertEquals(Objects.requireNonNull(typeOfLocationInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedTypeOfLocation.getBody()).getId());
        assertEquals(typeOfLocationInDatabaseById.getBody().getName(),          savedTypeOfLocation.getBody().getName());
        assertEquals(typeOfLocationInDatabaseById.getBody().getDescription(),   savedTypeOfLocation.getBody().getDescription());

        //negativ Test
        UUID randomUUID = UUID.randomUUID();

        while( randomUUID == savedTypeOfLocationId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<TypeOfLocationResponse> falseTypeOfLocationInDatabaseById = typeOfLocationService.getTypeOfLocationById(randomUUID);

        assertNull(falseTypeOfLocationInDatabaseById.getBody());
    }

    @Test
    public void testUpdateAddress() {

        TypeOfLocationCreate updateForTypeOfLocation = new TypeOfLocationCreate(
                "name",
                null
        );

        ResponseEntity<TypeOfLocationResponse> updatedTypeOfLocation = typeOfLocationService.updateTypeOfLocation(savedTypeOfLocationId, updateForTypeOfLocation);

        assertEquals(updatedTypeOfLocation.getBody().getId(), Objects.requireNonNull(updatedTypeOfLocation.getBody()).getId());
        assertEquals(updatedTypeOfLocation.getBody().getName(), Objects.requireNonNull(updatedTypeOfLocation.getBody()).getName());
        assertEquals(updatedTypeOfLocation.getBody().getDescription(), Objects.requireNonNull(updatedTypeOfLocation.getBody()).getDescription());

        assertEquals(updatedTypeOfLocation.getBody().getName(), "name");
        assertEquals(updatedTypeOfLocation.getBody().getDescription(), savedTypeOfLocation.getBody().getDescription());
    }

    @Test
    public void testDeleteAddress() {

        ResponseEntity<TypeOfLocationResponse> deletedTypeOfLocation = typeOfLocationService.deleteTypeOfLocation( savedTypeOfLocationId );

        assertNull(deletedTypeOfLocation.getBody());

    }
}
