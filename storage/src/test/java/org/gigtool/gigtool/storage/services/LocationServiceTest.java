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
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;
    @Autowired
    private TypeOfLocationService typeOfLocationService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private TypeOfEquipmentService typeOfEquipmentService;
    @Autowired
    private TestUtils testUtils;



    private LocationCreate locationToSave;
    private ResponseEntity<LocationResponse> savedLocation;
    private UUID savedLocationId;

    LocationCreate updateForLocation;



    @BeforeEach
    @Transactional
    public void setup() {
        locationToSave = testUtils.getRandomLocationCreate();
        savedLocation = locationService.addLocation( locationToSave );
        savedLocationId = savedLocation.getBody().getId();
        updateForLocation = testUtils.getRandomLocationCreate();
    }

    @Test
    @Transactional
    public void testAddLocation() {

        assertEquals(locationToSave.getAddressId(), savedLocation.getBody().getAddressResponse().getId());
        assertEquals(locationToSave.getTypeOfLocationId(), savedLocation.getBody().getTypeOfLocationResponse().getId());

        LocationCreate incompleteLocation = new LocationCreate(
                null,
                null
        );

        ResponseEntity<LocationResponse> negativeResult = locationService.addLocation( incompleteLocation );

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetAllLocation() {

        LocationCreate locationToSave1 = testUtils.getRandomLocationCreate();
        LocationCreate locationToSave2 = testUtils.getRandomLocationCreate();

        ResponseEntity<LocationResponse> savedLocation1 = locationService.addLocation(locationToSave1);
        ResponseEntity<LocationResponse> savedLocation2 = locationService.addLocation(locationToSave2);

        ResponseEntity<List<LocationResponse>> savedLocationList = locationService.getAllLocation();

        assertNotNull(savedLocationList);
        assertFalse(Objects.requireNonNull(savedLocationList.getBody()).isEmpty());

        assertEquals(3, savedLocationList.getBody().size());

        assertEquals(locationToSave1.getTypeOfLocationId(), savedLocationList.getBody().get(1).getTypeOfLocationResponse().getId());
        assertEquals(locationToSave1.getAddressId(), savedLocationList.getBody().get(1).getAddressResponse().getId());

        assertEquals(locationToSave2.getTypeOfLocationId(), savedLocationList.getBody().get(2).getTypeOfLocationResponse().getId());
        assertEquals(locationToSave2.getAddressId(), savedLocationList.getBody().get(2).getAddressResponse().getId());

    }

    @Test
    public void testGetLocationById() {

        // positiver Test
        ResponseEntity<LocationResponse> locationInDatabaseById = locationService.getLocationById(savedLocationId);

        assertEquals(Objects.requireNonNull(locationInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedLocation.getBody()).getId());
        assertEquals(locationInDatabaseById.getBody().getTypeOfLocationResponse().getId(), savedLocation.getBody().getTypeOfLocationResponse().getId());
        assertEquals(locationInDatabaseById.getBody().getAddressResponse().getId(), savedLocation.getBody().getAddressResponse().getId());

        // negative Test
        UUID randomUUID = UUID.randomUUID();

        while (randomUUID.equals(savedLocationId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<LocationResponse> falseLocationInDatabaseById = locationService.getLocationById(randomUUID);

        assertNull(falseLocationInDatabaseById.getBody());
    }

    @Test
    public void testUpdateLocation() {

        ResponseEntity<LocationResponse> updatedLocation = locationService.updateLocation(savedLocationId, updateForLocation);

        assertEquals(updatedLocation.getBody().getTypeOfLocationResponse().getId(), updateForLocation.getTypeOfLocationId());
        assertEquals(updatedLocation.getBody().getAddressResponse().getId(),        updateForLocation.getAddressId());


        assertEquals(updatedLocation.getBody().getTypeOfLocationResponse().getId(), updateForLocation.getTypeOfLocationId());
        assertEquals(updatedLocation.getBody().getAddressResponse().getId(),        updateForLocation.getAddressId());

    }

    @Test
    public void testDeleteLocation() {

        ResponseEntity<LocationResponse> deletedLocation = locationService.deleteLocation( savedLocationId );

        assertNull(deletedLocation.getBody());
    }
}
