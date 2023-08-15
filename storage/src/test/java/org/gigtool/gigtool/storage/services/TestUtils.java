package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationCreate;

import java.util.Random;
import java.util.UUID;

public class TestUtils {

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
