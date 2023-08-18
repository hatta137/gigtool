package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentResponse;
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
public class TypeOfEquipmentServiceTest {

    @Autowired
    private TypeOfEquipmentService typeOfEquipmentService;
    @Autowired
    private TestUtils testUtils;

    private TypeOfEquipmentCreate typeOfEquipmentToSave;
    private ResponseEntity<TypeOfEquipmentResponse> savedTypeOfEquipment;
    private UUID savedTypeOfEquipmentId;
    @BeforeEach
    public void setup() {
        typeOfEquipmentToSave = testUtils.getRandomTypeOfEquipmentCreate();
        savedTypeOfEquipment = typeOfEquipmentService.addTypeOfEquipment( typeOfEquipmentToSave );
        savedTypeOfEquipmentId = Objects.requireNonNull(savedTypeOfEquipment.getBody()).getId();
    }

    @Test
    @Transactional
    public void testAddTypeOfEquipment() {

        assertEquals(savedTypeOfEquipment.getBody().getName(),       typeOfEquipmentToSave.getName());
        assertEquals(savedTypeOfEquipment.getBody().getDescription(),typeOfEquipmentToSave.getDescription());

        // Negative Test: Try adding an address with missing information
        TypeOfEquipmentCreate incompleteTypeOfLocation = new TypeOfEquipmentCreate(
                "name",
                null
        );

        ResponseEntity<TypeOfEquipmentResponse> negativeResult = typeOfEquipmentService.addTypeOfEquipment( incompleteTypeOfLocation );

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetAllTypeOfEquipment() {

        TypeOfEquipmentCreate typeOfEquipmentToSave1 = testUtils.getRandomTypeOfEquipmentCreate();
        TypeOfEquipmentCreate typeOfEquipmentToSave2 = testUtils.getRandomTypeOfEquipmentCreate();


        ResponseEntity<TypeOfEquipmentResponse> savedTypeOfEquipmentToSave1 = typeOfEquipmentService.addTypeOfEquipment( typeOfEquipmentToSave1 );
        ResponseEntity<TypeOfEquipmentResponse> savedTypeOfEquipmentToSave2 = typeOfEquipmentService.addTypeOfEquipment( typeOfEquipmentToSave2 );

        ResponseEntity<List<TypeOfEquipmentResponse>> savedTypeOfLocationList = typeOfEquipmentService.getAllTypeOfEquipment();

        assertNotNull(savedTypeOfLocationList);
        assertFalse(Objects.requireNonNull(savedTypeOfLocationList.getBody()).isEmpty());

        assertEquals(3, savedTypeOfLocationList.getBody().size());

        assertEquals(typeOfEquipmentToSave1.getName(),           savedTypeOfLocationList.getBody().get(1).getName());
        assertEquals(typeOfEquipmentToSave1.getDescription(),    savedTypeOfLocationList.getBody().get(1).getDescription());

        assertEquals(typeOfEquipmentToSave2.getName(),           savedTypeOfLocationList.getBody().get(2).getName());
        assertEquals(typeOfEquipmentToSave2.getDescription(),    savedTypeOfLocationList.getBody().get(2).getDescription());
    }

    @Test
    public void testGetTypeOfEquipmentById() {

        // positive Test
        ResponseEntity<TypeOfEquipmentResponse> typeOfEquipmentInDatabaseById = typeOfEquipmentService.getTypeOfEquipmentById(savedTypeOfEquipmentId);

        assertEquals(Objects.requireNonNull(typeOfEquipmentInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedTypeOfEquipment.getBody()).getId());
        assertEquals(typeOfEquipmentInDatabaseById.getBody().getName(),          savedTypeOfEquipment.getBody().getName());
        assertEquals(typeOfEquipmentInDatabaseById.getBody().getDescription(),   savedTypeOfEquipment.getBody().getDescription());

        //negative Test
        UUID randomUUID = UUID.randomUUID();

        while( randomUUID == savedTypeOfEquipmentId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<TypeOfEquipmentResponse> falseTypeOfEquipmentInDatabaseById = typeOfEquipmentService.getTypeOfEquipmentById( randomUUID );

        assertNull(falseTypeOfEquipmentInDatabaseById.getBody());
    }

    @Test
    public void testUpdateTypeOfEquipment() {

        ResponseEntity<TypeOfEquipmentResponse> typeOfEquipmentBeforeUpdate = savedTypeOfEquipment;

        TypeOfEquipmentCreate updateForTypeOfEquipment = new TypeOfEquipmentCreate(
                "name",
                "description"
        );

        ResponseEntity<TypeOfEquipmentResponse> updatedTypeOfEquipment = typeOfEquipmentService.updateTypeOfEquipment(savedTypeOfEquipmentId, updateForTypeOfEquipment);

        assertEquals(typeOfEquipmentBeforeUpdate.getBody().getId(),          Objects.requireNonNull(updatedTypeOfEquipment.getBody()).getId());

        assertEquals(updatedTypeOfEquipment.getBody().getName(), "name");
        assertEquals(updatedTypeOfEquipment.getBody().getDescription(), "description");
    }

    @Test
    public void testDeleteTypeOfEquipment() {

        ResponseEntity<TypeOfEquipmentResponse> deletedTypeOfEquipment = typeOfEquipmentService.deleteTypeOfEquipment( savedTypeOfEquipmentId );

        assertNull(deletedTypeOfEquipment.getBody());
    }
}
