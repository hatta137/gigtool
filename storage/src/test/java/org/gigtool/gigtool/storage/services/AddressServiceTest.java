package org.gigtool.gigtool.storage.services;


import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.UUID;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressServiceTest {



    @Autowired
    private AddressService addressService;

    private AddressCreate addressToSave;
    private ResponseEntity<AddressResponse> savedAddress;
    @BeforeEach
    public void setup() {
        addressToSave = getRandomAddressCreate();
        savedAddress = addressService.addNewAddress( addressToSave );
    }

    @Test
    public void testAddAddress() {

        // Positive Test
        UUID savedId = Objects.requireNonNull(savedAddress.getBody()).getId();

        assertNotNull(savedId);

        assertEquals(addressToSave.getStreet(), Objects.requireNonNull(addressService.getAddressById(savedId).getBody()).getStreet());

        assertEquals(savedAddress.getBody().getCity(), addressToSave.getCity());
        assertEquals(savedAddress.getBody().getCountry(), addressToSave.getCountry());
        assertEquals(savedAddress.getBody().getZipCode(), addressToSave.getZipCode());
        assertEquals(savedAddress.getBody().getHouseNumber(), addressToSave.getHouseNumber());
        assertEquals(savedAddress.getBody().getStreet(), addressToSave.getStreet());

        // Negative Test: Try adding an address with missing information
        AddressCreate incompleteAddress = new AddressCreate(
                123, // house number
                null, // street
                null, // zip code
                null, // country
                null  // city
        );

        ResponseEntity<AddressResponse> negativeResult = addressService.addNewAddress( incompleteAddress );

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());

        // Testing getAddressById
        ResponseEntity<AddressResponse> addressInDatabase = addressService.getAddressById(savedId);

        assertEquals(Objects.requireNonNull(addressInDatabase.getBody()).getId(), savedAddress.getBody().getId());
        assertEquals(addressInDatabase.getBody().getCity(), savedAddress.getBody().getCity());
        assertEquals(addressInDatabase.getBody().getStreet(), savedAddress.getBody().getStreet());
        assertEquals(addressInDatabase.getBody().getCountry(), savedAddress.getBody().getCountry());
        assertEquals(addressInDatabase.getBody().getZipCode(), savedAddress.getBody().getZipCode());
    }

    @Test
    public void testGetAllAddresses() {

    }

    @Test
    public void testGetAddressById() {

    }

    @Test
    public void testDeleteAddress() {

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
