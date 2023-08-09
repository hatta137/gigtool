package org.gigtool.gigtool.storage.services;


import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void shouldAddNewAddress() {

        AddressCreate addressToSave = getRandomAddressCreate();

        ResponseEntity<AddressResponse> result = addressService.addNewAddress( addressToSave );

        // Positive Test
        UUID savedId = result.getBody().getId();

        assertNotNull(savedId);

        assertEquals(addressToSave.getStreet(), addressService.getAddressById(savedId).getBody().getStreet());

        assertEquals(result.getBody().getCity(), addressToSave.getCity());
        assertEquals(result.getBody().getCountry(), addressToSave.getCountry());
        assertEquals(result.getBody().getZipCode(), addressToSave.getZipCode());
        assertEquals(result.getBody().getHouseNumber(), addressToSave.getHouseNumber());
        assertEquals(result.getBody().getStreet(), addressToSave.getStreet());

        // Negative Test: Try adding an address with missing information
        AddressCreate incompleteAddress = new AddressCreate(
                123, // house number
                null, // street
                null, // zip code
                null, // country
                null  // city
        );

        ResponseEntity<AddressResponse> negativeResult = addressService.addNewAddress(incompleteAddress);

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());

        // Testing getAddressById
        ResponseEntity<AddressResponse> addressInDatabase = addressService.getAddressById(savedId);

        assertEquals(addressInDatabase.getBody().getId(), result.getBody().getId());
        assertEquals(addressInDatabase.getBody().getCity(), result.getBody().getCity());
        assertEquals(addressInDatabase.getBody().getStreet(), result.getBody().getStreet());
        assertEquals(addressInDatabase.getBody().getCountry(), result.getBody().getCountry());
        assertEquals(addressInDatabase.getBody().getZipCode(), result.getBody().getZipCode());
        assertEquals(addressInDatabase.getBody().getHappenings(), result.getBody().getHappenings());
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
}
