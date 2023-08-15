package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;
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

    @BeforeEach
    public void setup() {

    }

    @Test
    @Transactional
    public void testAddLocation() {


        ResponseEntity<TypeOfLocationResponse>  typeOfLocation =    typeOfLocationService.addTypeOfLocation( TestUtils.getRandomTypeOfLocationCreate() );
        ResponseEntity<AddressResponse> address =           addressService.addNewAddress( TestUtils.getRandomAddressCreate() );

        LocationCreate locationToSave = new LocationCreate(
                typeOfLocation.getBody().getId(),
                address.getBody().getId()
        );

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
