package org.gigtool.gigtool.storage.services;


import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
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
    private UUID savedAddressId;
    @BeforeEach
    public void setup() {
        addressToSave = getRandomAddressCreate();
        savedAddress = addressService.addNewAddress( addressToSave );
        savedAddressId = Objects.requireNonNull(savedAddress.getBody()).getId();
    }

    @Test
    public void testAddAddress() {

        assertEquals(addressToSave.getStreet(), Objects.requireNonNull(addressService.getAddressById( savedAddressId ).getBody()).getStreet());

        assertEquals(Objects.requireNonNull(savedAddress.getBody()).getCity(), addressToSave.getCity());
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
    }

    @Test
    public void testGetAllAddresses() {

        AddressCreate addressToSave1 = getRandomAddressCreate();
        AddressCreate addressToSave2 = getRandomAddressCreate();
        AddressCreate addressToSave3 = getRandomAddressCreate();
        AddressCreate addressToSave4 = getRandomAddressCreate();

        ResponseEntity<AddressResponse> savedAddress1 = addressService.addNewAddress( addressToSave1 );
        ResponseEntity<AddressResponse> savedAddress2 = addressService.addNewAddress( addressToSave2 );
        ResponseEntity<AddressResponse> savedAddress3 = addressService.addNewAddress( addressToSave3 );
        ResponseEntity<AddressResponse> savedAddress4 = addressService.addNewAddress( addressToSave4 );

        ResponseEntity<List<AddressResponse>> savedAddressList = addressService.getAllAddress();

        assertNotNull(savedAddressList);
        assertFalse(Objects.requireNonNull(savedAddressList.getBody()).isEmpty());

        assertEquals(5, savedAddressList.getBody().size());

        assertEquals(addressToSave1.getStreet(), savedAddressList.getBody().get(1).getStreet());
        assertEquals(addressToSave1.getCity(), savedAddressList.getBody().get(1).getCity());
        assertEquals(addressToSave1.getZipCode(), savedAddressList.getBody().get(1).getZipCode());
        assertEquals(addressToSave1.getCountry(), savedAddressList.getBody().get(1).getCountry());
    }

    @Test
    public void testGetAddressById() {

        // positiv Test
        ResponseEntity<AddressResponse> addressInDatabaseById = addressService.getAddressById(savedAddressId);

        assertEquals(Objects.requireNonNull(addressInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedAddress.getBody()).getId());
        assertEquals(addressInDatabaseById.getBody().getCity(), savedAddress.getBody().getCity());
        assertEquals(addressInDatabaseById.getBody().getStreet(), savedAddress.getBody().getStreet());
        assertEquals(addressInDatabaseById.getBody().getCountry(), savedAddress.getBody().getCountry());
        assertEquals(addressInDatabaseById.getBody().getZipCode(), savedAddress.getBody().getZipCode());

        //negativ Test
        UUID randomUUID = UUID.randomUUID();

        while( randomUUID == savedAddressId) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<AddressResponse> falseAddressInDatabaseById = addressService.getAddressById(randomUUID);

        assertNull(falseAddressInDatabaseById.getBody());
    }

    @Test //TODO @ Hendrik
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
