package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TimetableServiceTest {

    @Autowired
    private TimetableService timetableService;
    @Autowired
    private GigService gigService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private TestUtils testUtils;



    @BeforeEach
    public void setup() {
    }

    @Test
    @Transactional
    public void testGetAll() {
        GigCreate gig = testUtils.getRandomGigCreate();
        ResponseEntity<GigResponse> savedGig = gigService.addGig(gig);

        GigCreate anotherGig = testUtils.getRandomGigCreate();
        ResponseEntity<GigResponse> savedAnotherGig = gigService.addGig(anotherGig);

        ResponseEntity<List<TimetableResponse>> allGigs = timetableService.getAll();

        assertTrue(allGigs.getStatusCode().is2xxSuccessful());

        assertEquals(allGigs.getBody().get(0).getId(), savedGig.getBody().getId());
        assertEquals(allGigs.getBody().get(1).getId(), savedAnotherGig.getBody().getId());
    }
    @Test
    @Transactional
    public void testGetFilteredHappenings() {
        GigCreate kirmes = testUtils.getRandomGigCreate();
        kirmes.setName("Kirmes");
        kirmes.setDescription("Kirmes in Erfurt");
        ResponseEntity<GigResponse> savedKirmes = gigService.addGig(kirmes);

        GigCreate oktoberfest = testUtils.getRandomGigCreate();
        oktoberfest.setName("Oktoberfest");
        oktoberfest.setDescription("Oktoberfest in Erfurt");
        ResponseEntity<GigResponse> savedOktoberfest = gigService.addGig(oktoberfest);

        GigCreate kirmes2 = testUtils.getRandomGigCreate();
        kirmes2.setName("Kirmes");
        kirmes2.setDescription("Kirmes in BSA");
        ResponseEntity<GigResponse> savedKirmes2 = gigService.addGig(kirmes2);

        ResponseEntity<List<TimetableResponse>> filteredHappenings = timetableService.getFilteredHappenings("Kirmes", null);

        assertTrue(filteredHappenings.getStatusCode().is2xxSuccessful());

        assertEquals(filteredHappenings.getBody().get(0).getName(), savedKirmes.getBody().getName());
        assertEquals(filteredHappenings.getBody().get(1).getName(), savedKirmes2.getBody().getName());
    }

    @Test
    @Transactional
    public void testGetTotalValuesOfHappening() {
        EquipmentCreate equipmentCreate = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentCreate2 = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentCreate3 = testUtils.getRandomEquipmentCreate();

        equipmentCreate.setPrice(100.0f);
        equipmentCreate.setWeight(110);
        equipmentCreate.setWidth(10);
        equipmentCreate.setHeight(10);
        equipmentCreate.setLength(10);

        equipmentCreate2.setPrice(100.0f);
        equipmentCreate2.setWeight(110);
        equipmentCreate2.setWidth(10);
        equipmentCreate2.setHeight(10);
        equipmentCreate2.setLength(10);

        equipmentCreate3.setPrice(100.0f);
        equipmentCreate3.setWeight(110);
        equipmentCreate3.setWidth(10);
        equipmentCreate3.setHeight(10);
        equipmentCreate3.setLength(10);

        ResponseEntity<EquipmentResponse> savedEquipment = equipmentService.addEquipment(equipmentCreate);
        ResponseEntity<EquipmentResponse> savedEquipment2 = equipmentService.addEquipment(equipmentCreate2);
        ResponseEntity<EquipmentResponse> savedEquipment3 = equipmentService.addEquipment(equipmentCreate3);

        GigCreate kirmes = testUtils.getRandomGigCreate();
        kirmes.setName("Kirmes");
        kirmes.setDescription("Kirmes in Erfurt");
        ResponseEntity<GigResponse> savedKirmes = gigService.addGig(kirmes);

        gigService.addEquipmentToGig(savedKirmes.getBody().getId(), savedEquipment.getBody().getId());
        gigService.addEquipmentToGig(savedKirmes.getBody().getId(), savedEquipment2.getBody().getId());
        gigService.addEquipmentToGig(savedKirmes.getBody().getId(), savedEquipment3.getBody().getId());

        ResponseEntity<CalcResponse> calc = timetableService.getTotalValuesOfHappening(savedKirmes.getBody().getId());

        assertTrue(calc.getStatusCode().is2xxSuccessful());

        assertEquals(calc.getBody().getTotalCosts(), 300);
        assertEquals(calc.getBody().getTotalWeight(), 330);
        assertEquals(calc.getBody().getTotalCuboidVolume(),3000);

        //negative happening not in db
        ResponseEntity<CalcResponse> calcNoHappening = timetableService.getTotalValuesOfHappening(UUID.randomUUID());
        assertTrue(calcNoHappening.getStatusCode().is4xxClientError());

        //Positive rental
        EquipmentCreate equipmentRCreate = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentRCreate2 = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentRCreate3 = testUtils.getRandomEquipmentCreate();

        equipmentRCreate.setPrice(100.0f);
        equipmentRCreate.setWeight(110);
        equipmentRCreate.setWidth(10);
        equipmentRCreate.setHeight(10);
        equipmentRCreate.setLength(10);

        equipmentRCreate2.setPrice(100.0f);
        equipmentRCreate2.setWeight(110);
        equipmentRCreate2.setWidth(10);
        equipmentRCreate2.setHeight(10);
        equipmentRCreate2.setLength(10);

        equipmentRCreate3.setPrice(100.0f);
        equipmentRCreate3.setWeight(110);
        equipmentRCreate3.setWidth(10);
        equipmentRCreate3.setHeight(10);
        equipmentRCreate3.setLength(10);

        ResponseEntity<EquipmentResponse> savedREquipment =  equipmentService.addEquipment(equipmentRCreate);
        ResponseEntity<EquipmentResponse> savedREquipment2 = equipmentService.addEquipment(equipmentRCreate2);
        ResponseEntity<EquipmentResponse> savedREquipment3 = equipmentService.addEquipment(equipmentRCreate3);

        RentalCreate rental = testUtils.getRandomRentalCreate();
        rental.setName("Kirmes");
        ResponseEntity<RentalResponse> savedRental = rentalService.addRental(rental);

        rentalService.addEquipmentToRental(savedRental.getBody().getId(), savedREquipment.getBody().getId());
        rentalService.addEquipmentToRental(savedRental.getBody().getId(), savedREquipment2.getBody().getId());
        rentalService.addEquipmentToRental(savedRental.getBody().getId(), savedREquipment3.getBody().getId());

        ResponseEntity<CalcResponse> calcRental = timetableService.getTotalValuesOfHappening(savedRental.getBody().getId());

        assertTrue(calcRental.getStatusCode().is2xxSuccessful());

        assertEquals(calcRental.getBody().getTotalCosts(), 300);
        assertEquals(calcRental.getBody().getTotalWeight(), 330);
        assertEquals(calcRental.getBody().getTotalCuboidVolume(),3000);
    }

    @Test
    @Transactional
    public void testGetLocationsOfEquipmentFromHappening() {
        EquipmentCreate equipmentCreate = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentCreate2 = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentCreate3 = testUtils.getRandomEquipmentCreate();

        ResponseEntity<LocationResponse> location = testUtils.getRandomLocationResponse();
        ResponseEntity<LocationResponse> location2 = testUtils.getRandomLocationResponse();
        ResponseEntity<LocationResponse> location3 = testUtils.getRandomLocationResponse();

        equipmentCreate.setLocationId(location.getBody().getId());
        equipmentCreate2.setLocationId(location2.getBody().getId());
        equipmentCreate3.setLocationId(location3.getBody().getId());

        ResponseEntity<EquipmentResponse> savedEquipment = equipmentService.addEquipment(equipmentCreate);
        ResponseEntity<EquipmentResponse> savedEquipment2 = equipmentService.addEquipment(equipmentCreate2);
        ResponseEntity<EquipmentResponse> savedEquipment3 = equipmentService.addEquipment(equipmentCreate3);

        GigCreate kirmes = testUtils.getRandomGigCreate();
        kirmes.setName("Kirmes");
        kirmes.setDescription("Kirmes in Erfurt");
        ResponseEntity<GigResponse> savedKirmes = gigService.addGig(kirmes);

        gigService.addEquipmentToGig(savedKirmes.getBody().getId(), savedEquipment.getBody().getId());
        gigService.addEquipmentToGig(savedKirmes.getBody().getId(), savedEquipment2.getBody().getId());
        gigService.addEquipmentToGig(savedKirmes.getBody().getId(), savedEquipment3.getBody().getId());

        ResponseEntity<List<WhereismyequipmentResponse>> calc = timetableService.getLocationsOfEquipmentFromHappening(savedKirmes.getBody().getId());

        assertTrue(calc.getStatusCode().is2xxSuccessful());

        assertEquals(calc.getBody().get(0).getLocationID(), equipmentCreate.getLocationId());
        assertEquals(calc.getBody().get(1).getLocationID(), equipmentCreate2.getLocationId());
        assertEquals(calc.getBody().get(2).getLocationID(), equipmentCreate3.getLocationId());

        //negative happening not in db
        ResponseEntity<List<WhereismyequipmentResponse>> calcNoHappening = timetableService.getLocationsOfEquipmentFromHappening(UUID.randomUUID());
        assertTrue(calcNoHappening.getStatusCode().is4xxClientError());

        //Positive rental
        EquipmentCreate equipmentRCreate = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentRCreate2 = testUtils.getRandomEquipmentCreate();
        EquipmentCreate equipmentRCreate3 = testUtils.getRandomEquipmentCreate();

        equipmentRCreate.setLocationId(location.getBody().getId());
        equipmentRCreate2.setLocationId(location2.getBody().getId());
        equipmentRCreate3.setLocationId(location3.getBody().getId());

        ResponseEntity<EquipmentResponse> savedREquipment =  equipmentService.addEquipment(equipmentRCreate);
        ResponseEntity<EquipmentResponse> savedREquipment2 = equipmentService.addEquipment(equipmentRCreate2);
        ResponseEntity<EquipmentResponse> savedREquipment3 = equipmentService.addEquipment(equipmentRCreate3);

        RentalCreate rental = testUtils.getRandomRentalCreate();
        rental.setName("Kirmes");
        ResponseEntity<RentalResponse> savedRental = rentalService.addRental(rental);

        rentalService.addEquipmentToRental(savedRental.getBody().getId(), savedREquipment.getBody().getId());
        rentalService.addEquipmentToRental(savedRental.getBody().getId(), savedREquipment2.getBody().getId());
        rentalService.addEquipmentToRental(savedRental.getBody().getId(), savedREquipment3.getBody().getId());

        ResponseEntity<List<WhereismyequipmentResponse>> calcRental = timetableService.getLocationsOfEquipmentFromHappening(savedRental.getBody().getId());

        assertTrue(calcRental.getStatusCode().is2xxSuccessful());
    }
}
