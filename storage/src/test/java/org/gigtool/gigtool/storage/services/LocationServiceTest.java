package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


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

    private TypeOfLocationCreate typeOfLocationToSave;
    private AddressCreate addressToSave;

    ResponseEntity<TypeOfLocationResponse> savedTypeOfLocation;
    ResponseEntity<AddressResponse> savedAddress;

    private LocationCreate locationToSave;

    @BeforeEach
    @Transactional
    public void setup() {

        TestUtils testUtils = new TestUtils(addressService, typeOfLocationService);

        locationToSave = new LocationCreate(
                testUtils.getRandomTypeOfLocationResponse().getBody().getId(),
                testUtils.getRandomAddressResponse().getBody().getId()
        );

/*        typeOfLocationToSave = TestUtils.getRandomTypeOfLocationCreate();
        addressToSave = TestUtils.getRandomAddressCreate();

        savedTypeOfLocation =  typeOfLocationService.addTypeOfLocation( typeOfLocationToSave );
        savedAddress = addressService.addNewAddress( addressToSave );

        locationToSave = new LocationCreate(
                savedTypeOfLocation.getBody().getId(),
                savedAddress.getBody().getId()
        );*/

    }

    @Test
    @Transactional
    public void testAddLocation() {


        assertNotNull(locationToSave);

        ResponseEntity<LocationResponse> savedLocation = locationService.addLocation( locationToSave );

        assertEquals(savedLocation.getBody().getAddressResponse().getId(), locationToSave.getAddressId());
        System.out.println(savedLocation.getBody());

    }

    @Test
    public void getAllLocation() {

    }

    @Test
    public void getLocationById() {

    }

    @Test
    public void updateLocation() {

    }

    @Test
    public void deleteLocation() {

    }
}
