package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Transactional
public class TestUtils {

    private static AddressService addressService;
    private static TypeOfLocationService typeOfLocationService;
    private static TypeOfEquipmentService typeOfEquipmentService;

    public TestUtils(AddressService addressService, TypeOfLocationService typeOfLocationService, TypeOfEquipmentService typeOfEquipmentService) {
        TestUtils.addressService = addressService;
        TestUtils.typeOfLocationService = typeOfLocationService;
        TestUtils.typeOfEquipmentService = typeOfEquipmentService;
    }

    public static ResponseEntity<AddressResponse> getRandomAddressResponse() {

        AddressCreate addressToSave = getRandomAddressCreate();

        return addressService.addNewAddress(addressToSave);
    }

    public static ResponseEntity<TypeOfLocationResponse> getRandomTypeOfLocationResponse() {

        TypeOfLocationCreate typeOfLocationToSave = getRandomTypeOfLocationCreate();

        return typeOfLocationService.addTypeOfLocation( typeOfLocationToSave );
    }

    public static ResponseEntity<TypeOfEquipmentResponse> getRandomTypeOfEquipmentResponse() {

        return typeOfEquipmentService.addTypeOfEquipment( getRandomTypeOfEquipmentCreate() );
    }

    public static LocationCreate getRandomLocationCreate() {
        return new LocationCreate(
                getRandomAddressResponse().getBody().getId(),
                getRandomTypeOfLocationResponse().getBody().getId()
        );
    }

    public static AddressCreate getRandomAddressCreate() {
        Random random = new Random();
        return new AddressCreate(
                random.nextInt(100),
                UUID.randomUUID() + "street",
                UUID.randomUUID() + "zipcode",
                UUID.randomUUID() + "country",
                UUID.randomUUID() + "city"
        );
    }

    public static TypeOfLocationCreate getRandomTypeOfLocationCreate() {
        return new TypeOfLocationCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public static TypeOfEquipmentCreate getRandomTypeOfEquipmentCreate() {
        return new TypeOfEquipmentCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public static EquipmentCreate getRandomEquipmentCreate() {
        Random random = new Random();
        return new EquipmentCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description",
                getRandomTypeOfEquipmentResponse().getBody().getId(),
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100),
                LocalDate.of(2020, 2, 2),
                getRandomTypeOfLocationResponse().getBody().getId(),
                random.nextFloat(100)
        );
    }
}
