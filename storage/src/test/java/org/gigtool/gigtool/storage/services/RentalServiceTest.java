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
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class RentalServiceTest {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private TestUtils testUtils;

    private RentalCreate rentalToSave;
    private ResponseEntity<RentalResponse> savedRental;
    private UUID savedRentalId;

    @BeforeEach
    public void setup() {
        rentalToSave = testUtils.getRandomRentalCreate();
        savedRental = rentalService.addRental(rentalToSave);
        savedRentalId = savedRental.getBody().getId();
    }

    @Test
    @Transactional
    public void testAddRental() {


        //negative name == null
        rentalToSave.setName( null );
        ResponseEntity<RentalResponse> rentalNameNull = rentalService.addRental( rentalToSave );

        assertTrue(rentalNameNull.getStatusCode().is4xxClientError());

        //negative  add == not in db
        rentalToSave.setName( "name" );
        rentalToSave.setAddress( UUID.randomUUID() );
        ResponseEntity<RentalResponse> rentalRandAdd = rentalService.addRental( rentalToSave );

        assertTrue(rentalRandAdd.getStatusCode().is4xxClientError());
    }

    @Test
    @Transactional
    public void testGetAllRentals() {
        RentalCreate rentalToSave1 = testUtils.getRandomRentalCreate();
        RentalCreate rentalToSave2 = testUtils.getRandomRentalCreate();

        ResponseEntity<RentalResponse> savedGig1 = rentalService.addRental(rentalToSave1);
        ResponseEntity<RentalResponse> savedGig2 = rentalService.addRental(rentalToSave2);

        ResponseEntity<List<RentalResponse>> savedRentals = rentalService.getAllRentals();

        assertEquals(rentalToSave1.getName(), savedRentals.getBody().get(1).getName());
        assertEquals(rentalToSave2.getName(), savedRentals.getBody().get(2).getName());
    }

    @Test
    @Transactional
    public void testGetRentalById() {

        ResponseEntity<RentalResponse> gigInDatabaseById = rentalService.getRentalById( savedRentalId );

        assertEquals(gigInDatabaseById.getBody().getName(), savedRental.getBody().getName());

        //negative
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID == savedRentalId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<RentalResponse> rentalNotInDatabase = rentalService.getRentalById( randomUUID );
        assertTrue(rentalNotInDatabase.getStatusCode().is4xxClientError());
    }

    @Test
    @Transactional
    public void testUpdateRental() {
        RentalCreate updateForRental = new RentalCreate();
        updateForRental.setName( "update" );
        updateForRental.setDescription( "update" );
        updateForRental.setAddress( testUtils.getRandomAddressResponse().getBody().getId() );
        updateForRental.setStartTime( LocalDateTime.now() );
        updateForRental.setEndTime( LocalDateTime.of(2024, 1, 1, 15, 30, 0) );

        ResponseEntity<RentalResponse> updatedRental = rentalService.updateRental( savedRentalId, updateForRental);
        assertEquals(updatedRental.getBody().getName(), "update");
        assertEquals(updatedRental.getBody().getDescription(), "update");

        //negative gig not in database
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID == savedRentalId ) {
            randomUUID = UUID.randomUUID();
        }
        ResponseEntity<RentalResponse> RentalNotInDb = rentalService.updateRental( randomUUID, updateForRental);
        assertTrue(RentalNotInDb.getStatusCode().is4xxClientError());

        //negative gigId == null
        ResponseEntity<RentalResponse> RentalIdNull = rentalService.updateRental( null, updateForRental);
        assertTrue(RentalIdNull.getStatusCode().is4xxClientError());

        //negative overlapping time
        rentalToSave.setStartTime( LocalDateTime.now() );

/*        ResponseEntity<RentalResponse> rentalTime = rentalService.addRental( rentalToSave );
        ResponseEntity<RentalResponse> rentalTimeUpdate = rentalService.updateRental(rentalTime.getBody().getId(), updateForRental);

        assertTrue(rentalTimeUpdate.getStatusCode().is4xxClientError());*/
    }

    @Test
    @Transactional
    public void testAddEquipmentToRental() {
        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();
        ResponseEntity<RentalResponse> rentalWithEquipment = rentalService.addEquipmentToRental( savedRentalId, equipment.getBody().getId());

        assertTrue(rentalWithEquipment.getStatusCode().is2xxSuccessful());
        assertEquals(rentalWithEquipment.getBody().getEquipmentList().get(0).getId(), equipment.getBody().getId());

        //negative equipmentId ==  null
        ResponseEntity<RentalResponse> rentalEquNull = rentalService.addEquipmentToRental( savedRentalId, null);
        assertFalse(rentalEquNull.getStatusCode().is2xxSuccessful());

        //negative equipment already part of equipmentList
        ResponseEntity<RentalResponse> alreadyPartOf = rentalService.addEquipmentToRental( savedRentalId, equipment.getBody().getId());
        assertFalse(alreadyPartOf.getStatusCode().is2xxSuccessful());

        //rental is empty
        ResponseEntity<EquipmentResponse> equipment2 = testUtils.getRandomEquipmentResponse();
        ResponseEntity<RentalResponse> rentalEmpty = rentalService.addEquipmentToRental( UUID.randomUUID(), equipment2.getBody().getId());
        assertFalse(rentalEmpty.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void testDeleteEquipmentFromRental() {

        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();
        ResponseEntity<RentalResponse> rentalWithEquipment = rentalService.addEquipmentToRental( savedRentalId, equipment.getBody().getId());

        assertTrue(rentalWithEquipment.getStatusCode().is2xxSuccessful());

        assertEquals(rentalWithEquipment.getBody().getEquipmentList().size(), 1);

        ResponseEntity<RentalResponse> deletedFromRental = rentalService.deleteEquipmentFromRental( savedRentalId, equipment.getBody().getId());

        assertTrue(deletedFromRental.getStatusCode().is2xxSuccessful());

        assertEquals(deletedFromRental.getBody().getEquipmentList().size(), 0);

        //negativ equipmentId == null
        ResponseEntity<RentalResponse> rentalEquNull = rentalService.deleteEquipmentFromRental( rentalWithEquipment.getBody().getId(), null);
        assertTrue(rentalEquNull.getStatusCode().is4xxClientError());

        //negative non existing equipment
        ResponseEntity<RentalResponse> rentalEquNonExist = rentalService.deleteEquipmentFromRental( rentalWithEquipment.getBody().getId(), UUID.randomUUID());
        assertTrue(rentalEquNonExist.getStatusCode().is4xxClientError());

        //negative equipmentList not containing equipment
        ResponseEntity<EquipmentResponse> equipment2 = testUtils.getRandomEquipmentResponse();
        ResponseEntity<RentalResponse> rentalWith2Equ = rentalService.addEquipmentToRental( savedRentalId, equipment2.getBody().getId());

        ResponseEntity<EquipmentResponse> equipment3 = testUtils.getRandomEquipmentResponse();

        ResponseEntity<RentalResponse> deleteNotConEqu = rentalService.deleteEquipmentFromRental( rentalWith2Equ.getBody().getId(), equipment3.getBody().getId());
        assertTrue(deleteNotConEqu.getStatusCode().is4xxClientError());
    }

    @Test
    @Transactional
    public void testDeleteRental() {

        ResponseEntity<String> deletedRental = rentalService.deleteRental(savedRentalId);

        assertTrue(deletedRental.getStatusCode().is2xxSuccessful());

        assertTrue(deletedRental.getBody().contains("Rental is deleted"));

        //negative rentalId == null
        ResponseEntity<String> deletedRentalNull = rentalService.deleteRental(null);
        assertTrue(deletedRentalNull.getStatusCode().is4xxClientError());
        assertTrue(deletedRentalNull.getBody().contains("No ID"));

        //negative rentalId not in Database
        ResponseEntity<String> deletedRentalNotInDataBase = rentalService.deleteRental(UUID.randomUUID());
        assertTrue(deletedRentalNotInDataBase.getStatusCode().is4xxClientError());
    }
}
