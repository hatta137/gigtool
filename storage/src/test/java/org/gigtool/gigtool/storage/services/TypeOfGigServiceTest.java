package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.GigCreate;
import org.gigtool.gigtool.storage.services.model.GigResponse;
import org.gigtool.gigtool.storage.services.model.TypeOfGigCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfGigResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class TypeOfGigServiceTest {


    @Autowired
    private TypeOfGigService typeOfGigService;
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private GigService gigService;

    private TypeOfGigCreate typeOfGigToSave;

    private ResponseEntity<TypeOfGigResponse> savedTypeOfGig;

    private UUID savedTypeOfGigId;

    @BeforeEach
    private void setup() {
        typeOfGigToSave = testUtils.getRandomTypeOfGigCreate();
        savedTypeOfGig = typeOfGigService.addTypeOfGig(typeOfGigToSave);
        savedTypeOfGigId = Objects.requireNonNull(savedTypeOfGig.getBody().getId());
    }

    @Test
    public void testAddTypeOfGig() {
        assertEquals(savedTypeOfGig.getBody().getName(), typeOfGigToSave.getName());
        assertEquals(savedTypeOfGig.getBody().getDescription(), typeOfGigToSave.getDescription());

        // Negative Test: Try adding a TypeOfGig with missing information
        TypeOfGigCreate incompleteTypeOfGig = new TypeOfGigCreate(
                "name",
                null
        );

        ResponseEntity<TypeOfGigResponse> negativeResult = typeOfGigService.addTypeOfGig(incompleteTypeOfGig);

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetAllTypesOfGig() {
        TypeOfGigCreate typeOfGigToSave1 = testUtils.getRandomTypeOfGigCreate();
        TypeOfGigCreate typeOfGigToSave2 = testUtils.getRandomTypeOfGigCreate();

        ResponseEntity<TypeOfGigResponse> savedTypeOfGig1 = typeOfGigService.addTypeOfGig(typeOfGigToSave1);
        ResponseEntity<TypeOfGigResponse> savedTypeOfGig2 = typeOfGigService.addTypeOfGig(typeOfGigToSave2);

        ResponseEntity<List<TypeOfGigResponse>> savedTypeOfGigList = typeOfGigService.getAllTypesOfGig();

        assertNotNull(savedTypeOfGigList);
        assertFalse(Objects.requireNonNull(savedTypeOfGigList.getBody()).isEmpty());
        assertEquals(3, savedTypeOfGigList.getBody().size());

        assertEquals(typeOfGigToSave1.getName(), savedTypeOfGigList.getBody().get(1).getName());
        assertEquals(typeOfGigToSave1.getDescription(), savedTypeOfGigList.getBody().get(1).getDescription());

        assertEquals(typeOfGigToSave2.getName(), savedTypeOfGigList.getBody().get(2).getName());
        assertEquals(typeOfGigToSave2.getDescription(), savedTypeOfGigList.getBody().get(2).getDescription());
    }

    @Test
    public void testGetTypeOfGigById() {
        // Positive test
        ResponseEntity<TypeOfGigResponse> typeOfGigInDatabaseById = typeOfGigService.getTypeOfGigById(savedTypeOfGigId);

        assertEquals(Objects.requireNonNull(typeOfGigInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedTypeOfGig.getBody()).getId());
        assertEquals(typeOfGigInDatabaseById.getBody().getName(), savedTypeOfGig.getBody().getName());
        assertEquals(typeOfGigInDatabaseById.getBody().getDescription(), savedTypeOfGig.getBody().getDescription());

        // Negative test
        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedTypeOfGigId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<TypeOfGigResponse> falseTypeOfGigInDatabaseId = typeOfGigService.getTypeOfGigById(randomUUID);

        assertNull(falseTypeOfGigInDatabaseId.getBody());
    }

    @Test
    public void testUpdateTypeOfGig() {

        // Positive test
        ResponseEntity<TypeOfGigResponse> typeOfGigBeforeUpdate = savedTypeOfGig;

        TypeOfGigCreate updateForTypeOfGig = new TypeOfGigCreate(
                "newName",
                "newDescription"
        );

        ResponseEntity<TypeOfGigResponse> updatedTypeOfGig = typeOfGigService.updateTypeOfGig(savedTypeOfGigId, updateForTypeOfGig);

        assertEquals(typeOfGigBeforeUpdate.getBody().getId(), Objects.requireNonNull(updatedTypeOfGig.getBody()).getId());
        assertEquals(updatedTypeOfGig.getBody().getName(), "newName");
        assertEquals(updatedTypeOfGig.getBody().getDescription(), "newDescription");

        // Negative test: Try to add TypOfGig without description
        TypeOfGigCreate updateForTypeOfGigFalse = new TypeOfGigCreate(
                "newName",
                null
        );

        ResponseEntity<TypeOfGigResponse> updatedTypeOfGigFalse = typeOfGigService.updateTypeOfGig(savedTypeOfGigId, updateForTypeOfGigFalse);

        assertTrue(updatedTypeOfGigFalse.getStatusCode().is4xxClientError());

        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedTypeOfGigId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<String> existingTypeOfGigFalse = typeOfGigService.deleteTypeOfGig(randomUUID);

        assertTrue(existingTypeOfGigFalse.getStatusCode().is4xxClientError());

        //negative existing Type is empty
        ResponseEntity<TypeOfGigResponse> negativeResult = typeOfGigService.updateTypeOfGig( UUID.randomUUID(), updateForTypeOfGig);
        assertTrue(negativeResult.getStatusCode().is4xxClientError());
    }

    @Test
    public void testDeleteTypeOfGig() {
        ResponseEntity<String> deletedTypeOfGig = typeOfGigService.deleteTypeOfGig(savedTypeOfGigId);

        assertTrue(deletedTypeOfGig.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testDeleteTypeOfGigWithNullId() {
        ResponseEntity<String> deleteResult = typeOfGigService.deleteTypeOfGig(null);

        assertTrue(deleteResult.getStatusCode().is4xxClientError());
        assertEquals("No ID", deleteResult.getBody());

        ResponseEntity<TypeOfGigResponse> typeOfGig = testUtils.getRandomTypeOfGigResponse();
        GigCreate gigCreate = testUtils.getRandomGigCreate();
        gigCreate.setName("name");
        gigCreate.setTypeOfGig(typeOfGig.getBody().getId());
        gigCreate.setAddress(testUtils.getRandomAddressResponse().getBody().getId());
        gigCreate.setDescription("descr");
        gigCreate.setStartTime(LocalDateTime.now());
        gigCreate.setEndTime(LocalDateTime.now());

        ResponseEntity<GigResponse> savedGig = gigService.addGig(gigCreate);

        ResponseEntity<String> negativeResult = typeOfGigService.deleteTypeOfGig(typeOfGig.getBody().getId());
        assertTrue(negativeResult.getStatusCode().is4xxClientError());



    }

}
