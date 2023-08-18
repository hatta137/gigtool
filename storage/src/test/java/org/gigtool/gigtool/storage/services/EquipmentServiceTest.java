package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.EquipmentCreate;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EquipmentServiceTest {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private TypeOfEquipmentService typeOfEquipmentService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private TypeOfLocationService typeOfLocationService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private TestUtils testUtils;


    private EquipmentCreate equipmentToSave;
    private ResponseEntity<EquipmentResponse> savedEquipment;
    private UUID savedEquipmentId;
    @BeforeEach
    public void setup() {
        equipmentToSave = testUtils.getRandomEquipmentCreate();
        savedEquipment = equipmentService.addEquipment(equipmentToSave);
        savedEquipmentId = savedEquipment.getBody().getId();
    }


    @Test
    @Transactional
    public void testAddEquipment() {

        assertEquals(savedEquipment.getBody().getName(),        equipmentToSave.getName());
        assertEquals(savedEquipment.getBody().getDescription(), equipmentToSave.getDescription());
        assertEquals(savedEquipment.getBody().getTypeOfEquipmentResponse().getId(), equipmentToSave.getTypeOfEquipmentId());
        assertEquals(savedEquipment.getBody().getWeight(),      equipmentToSave.getWeight());
        assertEquals(savedEquipment.getBody().getLength(),      equipmentToSave.getLength());
        assertEquals(savedEquipment.getBody().getWidth(),       equipmentToSave.getWidth());
        assertEquals(savedEquipment.getBody().getHeight(),      equipmentToSave.getHeight());
        assertEquals(savedEquipment.getBody().getDateOfPurchase(), equipmentToSave.getDateOfPurchase());
        assertEquals(savedEquipment.getBody().getLocationResponse().getId(), equipmentToSave.getLocationId());
        assertEquals(savedEquipment.getBody().getDescription(), equipmentToSave.getDescription());

        // Negative Test: Try adding equipment with missing information
        EquipmentCreate incompleteEquipment = new EquipmentCreate(
                "name",
                "null",
                null,
                0, 1, 2, 3,
                LocalDate.now(),
                null,
                100.0f
        );

        ResponseEntity<EquipmentResponse> negativeResult = equipmentService.addEquipment(incompleteEquipment);

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetAllEquipment() {
        EquipmentCreate equipmentToSave1 = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentToSave2 = testUtils.getRandomEquipmentCreate();

        ResponseEntity<EquipmentResponse> savedEquipment1 = equipmentService.addEquipment(equipmentToSave1);
        ResponseEntity<EquipmentResponse> savedEquipment2 = equipmentService.addEquipment(equipmentToSave2);

        ResponseEntity<List<EquipmentResponse>> savedEquipmentList = equipmentService.getAllEquipment();

        assertNotNull(savedEquipmentList);
        assertFalse(savedEquipmentList.getBody().isEmpty());
        assertEquals(3, savedEquipmentList.getBody().size());

        assertEquals(equipmentToSave1.getName(),                savedEquipmentList.getBody().get(1).getName());
        assertEquals(equipmentToSave1.getDescription(),         savedEquipmentList.getBody().get(1).getDescription());
        assertEquals(equipmentToSave1.getTypeOfEquipmentId(),   savedEquipmentList.getBody().get(1).getTypeOfEquipmentResponse().getId());
        assertEquals(equipmentToSave1.getWeight(),              savedEquipmentList.getBody().get(1).getWeight());
        assertEquals(equipmentToSave1.getLength(),              savedEquipmentList.getBody().get(1).getLength());
        assertEquals(equipmentToSave1.getWidth(),               savedEquipmentList.getBody().get(1).getWidth());
        assertEquals(equipmentToSave1.getHeight(),              savedEquipmentList.getBody().get(1).getHeight());
        assertEquals(equipmentToSave1.getDateOfPurchase(),      savedEquipmentList.getBody().get(1).getDateOfPurchase());
        assertEquals(equipmentToSave1.getLocationId(),          savedEquipmentList.getBody().get(1).getLocationResponse().getId());
        assertEquals(equipmentToSave1.getPrice(),               savedEquipmentList.getBody().get(1).getPrice());


        assertEquals(equipmentToSave2.getName(),                savedEquipmentList.getBody().get(2).getName());
        assertEquals(equipmentToSave2.getDescription(),         savedEquipmentList.getBody().get(2).getDescription());
        assertEquals(equipmentToSave2.getTypeOfEquipmentId(),   savedEquipmentList.getBody().get(2).getTypeOfEquipmentResponse().getId());
        assertEquals(equipmentToSave2.getWeight(),              savedEquipmentList.getBody().get(2).getWeight());
        assertEquals(equipmentToSave2.getLength(),              savedEquipmentList.getBody().get(2).getLength());
        assertEquals(equipmentToSave2.getWidth(),               savedEquipmentList.getBody().get(2).getWidth());
        assertEquals(equipmentToSave2.getHeight(),              savedEquipmentList.getBody().get(2).getHeight());
        assertEquals(equipmentToSave2.getDateOfPurchase(),      savedEquipmentList.getBody().get(2).getDateOfPurchase());
        assertEquals(equipmentToSave2.getLocationId(),          savedEquipmentList.getBody().get(2).getLocationResponse().getId());
        assertEquals(equipmentToSave2.getPrice(),               savedEquipmentList.getBody().get(2).getPrice());
    }

    @Test
    public void testGetEquipmentById() {
        // Positive test
        ResponseEntity<EquipmentResponse> equipmentInDatabaseById = equipmentService.getEquipmentById( savedEquipmentId );

        assertEquals(equipmentInDatabaseById.getBody().getId(),             savedEquipmentId);
        assertEquals(equipmentInDatabaseById.getBody().getName(),           savedEquipment.getBody().getName());
        assertEquals(equipmentInDatabaseById.getBody().getDescription(),    savedEquipment.getBody().getDescription());
        assertEquals(equipmentInDatabaseById.getBody().getTypeOfEquipmentResponse().getId(),    savedEquipment.getBody().getTypeOfEquipmentResponse().getId());
        assertEquals(equipmentInDatabaseById.getBody().getWeight(),               savedEquipment.getBody().getWeight());
        assertEquals(equipmentInDatabaseById.getBody().getLength(),               savedEquipment.getBody().getLength());
        assertEquals(equipmentInDatabaseById.getBody().getWidth(),                savedEquipment.getBody().getWidth());
        assertEquals(equipmentInDatabaseById.getBody().getHeight(),               savedEquipment.getBody().getHeight());
        assertEquals(equipmentInDatabaseById.getBody().getDateOfPurchase(),       savedEquipment.getBody().getDateOfPurchase());
        assertEquals(equipmentInDatabaseById.getBody().getLocationResponse().getId(),           savedEquipment.getBody().getLocationResponse().getId());
        assertEquals(equipmentInDatabaseById.getBody().getPrice(),                savedEquipment.getBody().getPrice());

        // Negative test
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID.equals(savedEquipmentId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<EquipmentResponse> falseEquipmentInDatabaseById = equipmentService.getEquipmentById(randomUUID);

        assertNull(falseEquipmentInDatabaseById.getBody());
    }

    @Test
    @Transactional
    public void testUpdateEquipment() {
        EquipmentCreate updateForEquipment = new EquipmentCreate("newName");

        ResponseEntity<EquipmentResponse> equipmentBeforeUpdate = savedEquipment;

        ResponseEntity<EquipmentResponse> updatedEquipment = equipmentService.updateEquipment(savedEquipmentId, updateForEquipment);

        assertEquals(updatedEquipment.getBody().getName(),              updateForEquipment.getName());

        assertEquals(updatedEquipment.getBody().getDescription(),                       equipmentBeforeUpdate.getBody().getDescription());
        assertEquals(updatedEquipment.getBody().getTypeOfEquipmentResponse().getId(),   equipmentBeforeUpdate.getBody().getTypeOfEquipmentResponse().getId());
        assertEquals(updatedEquipment.getBody().getWeight(),                            equipmentBeforeUpdate.getBody().getWeight());
        assertEquals(updatedEquipment.getBody().getLength(),                            equipmentBeforeUpdate.getBody().getLength());
        assertEquals(updatedEquipment.getBody().getWidth(),                             equipmentBeforeUpdate.getBody().getWidth());
        assertEquals(updatedEquipment.getBody().getHeight(),                            equipmentBeforeUpdate.getBody().getHeight());
        assertEquals(updatedEquipment.getBody().getDateOfPurchase(),                    equipmentBeforeUpdate.getBody().getDateOfPurchase());
        assertEquals(updatedEquipment.getBody().getLocationResponse().getId(),          equipmentBeforeUpdate.getBody().getLocationResponse().getId());
        assertEquals(updatedEquipment.getBody().getPrice(),                             equipmentBeforeUpdate.getBody().getPrice());

        assertEquals(updatedEquipment.getBody().getName(), "newName");
    }

    @Test
    public void testDeleteEquipment() {

        ResponseEntity<EquipmentResponse> deletedEquipment = equipmentService.deleteEquipment( savedEquipmentId );

        assertNull(deletedEquipment.getBody());

        UUID randomUUID = UUID.randomUUID();
        while (randomUUID == savedEquipmentId) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<EquipmentResponse> negativeResult = equipmentService.deleteEquipment( randomUUID );

        assertTrue(negativeResult.getStatusCode().is4xxClientError());
    }

    @Test
    public void testGetAllEquipmentByTypeOfEquipment () {

        ResponseEntity<EquipmentResponse> savedEquipment1 = equipmentService.addEquipment(testUtils.getRandomEquipmentCreate());
        ResponseEntity<EquipmentResponse> savedEquipment2 = equipmentService.addEquipment(testUtils.getRandomEquipmentCreate());

        UUID savedEquipment1Id = savedEquipment1.getBody().getId();
        UUID savedEquipment2Id = savedEquipment2.getBody().getId();

        UUID typeOfEquipmentId = testUtils.getRandomTypeOfEquipmentResponse().getBody().getId();

        EquipmentCreate updateForEquipment = new EquipmentCreate(
                "name",
                "",
                typeOfEquipmentId,
                0,0,0,0,
                null,
                null,
                0.0f);


        ResponseEntity<EquipmentResponse> updatedEquipment1 = equipmentService.updateEquipment(savedEquipment1Id, updateForEquipment);
        ResponseEntity<EquipmentResponse> updatedEquipment2 = equipmentService.updateEquipment(savedEquipment2Id, updateForEquipment);

        ResponseEntity<List<EquipmentResponse>> listOfEquipmentByTypeOfEquipment = equipmentService.getAllEquipmentByTypeOfEquipment( typeOfEquipmentId );

        assertEquals(listOfEquipmentByTypeOfEquipment.getBody().get(0).getTypeOfEquipmentResponse().getId(), typeOfEquipmentId);
        assertEquals(listOfEquipmentByTypeOfEquipment.getBody().get(1).getTypeOfEquipmentResponse().getId(), typeOfEquipmentId);

    }

    @Test
    @Transactional
    public void testGetAllEquipmentByLocation() {

        ResponseEntity<EquipmentResponse> savedEquipment1 = equipmentService.addEquipment(testUtils.getRandomEquipmentCreate());
        ResponseEntity<EquipmentResponse> savedEquipment2 = equipmentService.addEquipment(testUtils.getRandomEquipmentCreate());

        UUID savedEquipment1Id = savedEquipment1.getBody().getId();
        UUID savedEquipment2Id = savedEquipment2.getBody().getId();

        UUID locationId = testUtils.getRandomLocationResponse().getBody().getId();

        EquipmentCreate updateForEquipment = new EquipmentCreate(
                "name",
                "description",
                testUtils.getRandomTypeOfEquipmentResponse().getBody().getId(),
                1,1,1,1,
                LocalDate.now(),
                locationId,
                0.0f);


        ResponseEntity<EquipmentResponse> updatedEquipment1 = equipmentService.updateEquipment(savedEquipment1Id, updateForEquipment);
        ResponseEntity<EquipmentResponse> updatedEquipment2 = equipmentService.updateEquipment(savedEquipment2Id, updateForEquipment);

        ResponseEntity<List<EquipmentResponse>> listOfEquipmentByLocation = equipmentService.getAllEquipmentByLocation( locationId );

        assertEquals(listOfEquipmentByLocation.getBody().get(0).getLocationResponse().getId(), locationId);
        assertEquals(listOfEquipmentByLocation.getBody().get(1).getLocationResponse().getId(), locationId);
    }
}
