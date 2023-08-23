package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Component
public class TestUtils {

    @Autowired
    private AddressService addressService;
    @Autowired
    private TypeOfLocationService typeOfLocationService;
    @Autowired
    private TypeOfEquipmentService typeOfEquipmentService;
    @Autowired
    private LocationService locationService;


    public ResponseEntity<AddressResponse> getRandomAddressResponse() {

        AddressCreate addressToSave = getRandomAddressCreate();

        return addressService.addNewAddress(addressToSave);
    }

    public ResponseEntity<TypeOfLocationResponse> getRandomTypeOfLocationResponse() {

        TypeOfLocationCreate typeOfLocationToSave = getRandomTypeOfLocationCreate();

        return typeOfLocationService.addTypeOfLocation( typeOfLocationToSave );
    }

    public TypeOfEquipmentCreate getRandomTypeOfEquipmentCreate() {
        return new TypeOfEquipmentCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public ResponseEntity<TypeOfEquipmentResponse> getRandomTypeOfEquipmentResponse() {

        return typeOfEquipmentService.addTypeOfEquipment( getRandomTypeOfEquipmentCreate() );
    }

    public LocationCreate getRandomLocationCreate() {
        return new LocationCreate(
                getRandomAddressResponse().getBody().getId(),
                getRandomTypeOfLocationResponse().getBody().getId()
        );
    }

    public ResponseEntity<LocationResponse> getRandomLocationResponse() {
        return locationService.addLocation( getRandomLocationCreate() );
    }

    public AddressCreate getRandomAddressCreate() {
        Random random = new Random();
        return new AddressCreate(
                random.nextInt(100),
                UUID.randomUUID() + "street",
                UUID.randomUUID() + "zipcode",
                UUID.randomUUID() + "country",
                UUID.randomUUID() + "city"
        );
    }

    public TypeOfLocationCreate getRandomTypeOfLocationCreate() {
        return new TypeOfLocationCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public EquipmentCreate getRandomEquipmentCreate() {
        Random random = new Random();

        return new EquipmentCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description",
                getRandomTypeOfEquipmentResponse().getBody().getId(),
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100),
                LocalDate.now(),
                getRandomLocationResponse().getBody().getId(),
                random.nextFloat(100)
        );
    }
}
