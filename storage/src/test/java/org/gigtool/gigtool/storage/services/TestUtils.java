package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Transactional
public class TestUtils {

    private static AddressService addressService;
    private static TypeOfLocationService typeOfLocationService;

    public TestUtils(AddressService addressService, TypeOfLocationService typeOfLocationService) {
        TestUtils.addressService = addressService;
        TestUtils.typeOfLocationService = typeOfLocationService;
    }
    public static ResponseEntity<AddressResponse> getRandomAddressResponse() {

        AddressCreate addressToSave = getRandomAddressCreate();

        return addressService.addNewAddress(addressToSave);
    }

    public static ResponseEntity<TypeOfLocationResponse> getRandomTypeOfLocationResponse() {

        TypeOfLocationCreate typeOfLocationToSave = getRandomTypeOfLocationCreate();

        return typeOfLocationService.addTypeOfLocation( typeOfLocationToSave );
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
}
