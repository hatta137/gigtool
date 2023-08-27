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
public class RentalServiceTest {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private GigService gigService;
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

        //negative starttime after endtime
        RentalCreate rentalTimeCollapsCreate = testUtils.getRandomRentalCreate();
        rentalTimeCollapsCreate.setStartTime(LocalDateTime.of(2023, 3, 1, 15, 30, 0));
        rentalTimeCollapsCreate.setEndTime(LocalDateTime.of(2023, 1, 1, 15, 30, 0));
        ResponseEntity<RentalResponse> rentalTimeCollaps = rentalService.addRental( rentalTimeCollapsCreate );


        assertTrue(rentalTimeCollaps.getStatusCode().is4xxClientError());

        RentalCreate rentalToSaveNoName = testUtils.getRandomRentalCreate();
        //negative name == null
        rentalToSaveNoName.setName( null );
        ResponseEntity<RentalResponse> rentalNameNull = rentalService.addRental( rentalToSaveNoName );

        assertTrue(rentalNameNull.getStatusCode().is4xxClientError());


        RentalCreate rentalToSaveNoValidAddress = testUtils.getRandomRentalCreate();
        //negative  add == not in db
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID == savedRental.getBody().getAddress().getId() ) {
            randomUUID = UUID.randomUUID();
        }
        rentalToSaveNoValidAddress.setName( "name" );
        rentalToSaveNoValidAddress.setAddress( randomUUID );
        ResponseEntity<RentalResponse> rentalRandAdd = rentalService.addRental( rentalToSaveNoValidAddress );

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
    public void testUpdateRental() {
        RentalCreate updateForRental = new RentalCreate();
        updateForRental.setName( "update" );
        updateForRental.setDescription( "update" );
        updateForRental.setAddress( testUtils.getRandomAddressResponse().getBody().getId() );
        updateForRental.setStartTime( LocalDateTime.of(2023, 6, 1, 15, 30, 0));
        updateForRental.setEndTime( LocalDateTime.of(2024, 1, 1, 15, 30, 0) );

        ResponseEntity<RentalResponse> updatedRental = rentalService.updateRental( savedRentalId, updateForRental);
        assertEquals(updatedRental.getBody().getName(), "update");
        assertEquals(updatedRental.getBody().getDescription(), "update");

        //negative rental not in database
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID == savedRentalId ) {
            randomUUID = UUID.randomUUID();
        }
        ResponseEntity<RentalResponse> rentalNotInDb = rentalService.updateRental( randomUUID, updateForRental);
        assertTrue(rentalNotInDb.getStatusCode().is4xxClientError());

        //negative rentalId == null
        ResponseEntity<RentalResponse> rentalIdNull = rentalService.updateRental( null, updateForRental);
        assertTrue(rentalIdNull.getStatusCode().is4xxClientError());

//overlapping equipment booking
        GigCreate overlappingGigCreate = new GigCreate();

        overlappingGigCreate.setName("overlapping");
        overlappingGigCreate.setDescription("overlapping");
        overlappingGigCreate.setAddress(testUtils.getRandomAddressResponse().getBody().getId());
        overlappingGigCreate.setBand(testUtils.getRandomBandResponse().getBody().getId());
        overlappingGigCreate.setTypeOfGig(testUtils.getRandomTypeOfGigResponse().getBody().getId());
        overlappingGigCreate.setStartTime(LocalDateTime.of(2023, 1, 1, 15, 30, 0));
        overlappingGigCreate.setEndTime(LocalDateTime.of(2023, 3, 1, 15, 30, 0));

        ResponseEntity<GigResponse> overlappingGig = gigService.addGig(overlappingGigCreate);

        assertTrue(overlappingGig.getStatusCode().is2xxSuccessful());

        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();

        overlappingGig = gigService.addEquipmentToGig(overlappingGig.getBody().getId(), equipment.getBody().getId());

        assertTrue(overlappingGig.getBody().getEquipmentList().stream()
                .anyMatch(equipmentIterator -> equipmentIterator.getId().equals(equipment.getBody().getId())));

        ResponseEntity<RentalResponse> rentalWithEquipment = rentalService.addEquipmentToRental(savedRentalId, equipment.getBody().getId());

        assertTrue(rentalWithEquipment.getBody().getEquipmentList().stream()
                .anyMatch(equipmentIterator -> equipmentIterator.getId().equals(equipment.getBody().getId())));

        updateForRental = new RentalCreate();

        updateForRental.setStartTime(LocalDateTime.of(2023, 1, 1, 15, 30, 0));
        updateForRental.setEndTime(LocalDateTime.of(2023, 3, 1, 15, 30, 0));

        ResponseEntity<RentalResponse> rentalTimeUpdateWithEquipment = rentalService.updateRental(savedRentalId, updateForRental);

        assertTrue(rentalTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

//update Time test
        updateForRental = new RentalCreate();

        updateForRental.setStartTime(LocalDateTime.of(2023, 6, 1, 15, 30, 0));

        rentalTimeUpdateWithEquipment = rentalService.updateRental(savedRentalId, updateForRental);

        assertTrue(rentalTimeUpdateWithEquipment.getStatusCode().is2xxSuccessful());

        updateForRental = new RentalCreate();

        updateForRental.setEndTime(LocalDateTime.of(2024, 3, 1, 15, 30, 0));

        rentalTimeUpdateWithEquipment = rentalService.updateRental(savedRentalId, updateForRental);

        assertTrue(rentalTimeUpdateWithEquipment.getStatusCode().is2xxSuccessful());

//negative updateTimetest


        updateForRental = new RentalCreate();

        updateForRental.setStartTime(LocalDateTime.of(2023, 1, 1, 15, 30, 0));

        rentalTimeUpdateWithEquipment = rentalService.updateRental(savedRentalId, updateForRental);

        assertTrue(rentalTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

        updateForRental = new RentalCreate();

        updateForRental.setEndTime(LocalDateTime.of(2023, 3, 1, 15, 30, 0));

        rentalTimeUpdateWithEquipment = rentalService.updateRental(savedRentalId, updateForRental);

        assertTrue(rentalTimeUpdateWithEquipment.getStatusCode().is4xxClientError());

        //negativ test Address
        updateForRental = new RentalCreate();

        updateForRental.setAddress(UUID.randomUUID());

        rentalTimeUpdateWithEquipment = rentalService.updateRental(savedRentalId, updateForRental);

        assertTrue(rentalTimeUpdateWithEquipment.getStatusCode().is4xxClientError());


        //update Time start and end
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 25, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 25, 12, 0);
        RentalCreate rental = testUtils.getRandomRentalCreate();
        rental.setStartTime( startTime );
        rental.setEndTime( endTime );

        ResponseEntity<RentalResponse> rentalTimeUpdateStartEnd = rentalService.updateRental( savedRentalId, rental);
        assertTrue(rentalTimeUpdateStartEnd.getStatusCode().is2xxSuccessful());

        RentalCreate rental2 = testUtils.getRandomRentalCreate();
        rental2.setStartTime( startTime );
        ResponseEntity<RentalResponse> rentalTimeUpdateStart = rentalService.updateRental( savedRentalId, rental2);
        assertTrue(rentalTimeUpdateStart.getStatusCode().is2xxSuccessful());
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

    @Test
    @Transactional
    public void testEquipmentIsOverlapping() {
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 25, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 25, 12, 0);
        ResponseEntity<EquipmentResponse> equipment = testUtils.getRandomEquipmentResponse();
        ResponseEntity<EquipmentResponse> equipment2 = testUtils.getRandomEquipmentResponse();

        RentalCreate rental = testUtils.getRandomRentalCreate();
        rental.setStartTime( startTime );
        rental.setEndTime( endTime );

        ResponseEntity<RentalResponse> rentalInDb = rentalService.addRental( rental );
        ResponseEntity<RentalResponse> rentalWithEquipment = rentalService.addEquipmentToRental( rentalInDb.getBody().getId(), equipment.getBody().getId() );

        ResponseEntity<RentalResponse> negativeResult = rentalService.addEquipmentToRental( rentalInDb.getBody().getId(), equipment2.getBody().getId() );

        assertTrue(negativeResult.getStatusCode().is2xxSuccessful());

        //equipmentList overlapping
        LocalDateTime startTime2 = LocalDateTime.of(2023, 8, 26, 10, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2023, 8, 26, 12, 0);
        ResponseEntity<EquipmentResponse> equipment3 = testUtils.getRandomEquipmentResponse();

        ResponseEntity<RentalResponse> rentalWithEquipmentList = rentalService.addEquipmentToRental( rentalInDb.getBody().getId(), equipment3.getBody().getId() );
        ResponseEntity<RentalResponse> negativeResultList = rentalService.addEquipmentToRental( rentalInDb.getBody().getId(), equipment2.getBody().getId() );

        assertTrue(negativeResult.getStatusCode().is2xxSuccessful());
    }
}
