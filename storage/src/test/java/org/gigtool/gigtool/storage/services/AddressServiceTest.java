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
        addressToSave = TestUtils.getRandomAddressCreate();
        savedAddress = addressService.addNewAddress( addressToSave );
        savedAddressId = Objects.requireNonNull(savedAddress.getBody()).getId();
    }

    @Test
    public void testAddAddress() {

        assertEquals(addressToSave.getStreet(), addressService.getAddressById( savedAddressId ).getBody().getStreet());

        assertEquals(savedAddress.getBody().getCity(),          addressToSave.getCity());
        assertEquals(savedAddress.getBody().getCountry(),       addressToSave.getCountry());
        assertEquals(savedAddress.getBody().getZipCode(),       addressToSave.getZipCode());
        assertEquals(savedAddress.getBody().getHouseNumber(),   addressToSave.getHouseNumber());
        assertEquals(savedAddress.getBody().getStreet(),        addressToSave.getStreet());

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

        AddressCreate addressToSave1 = TestUtils.getRandomAddressCreate();
        AddressCreate addressToSave2 = TestUtils.getRandomAddressCreate();


        ResponseEntity<AddressResponse> savedAddress1 = addressService.addNewAddress( addressToSave1 );
        ResponseEntity<AddressResponse> savedAddress2 = addressService.addNewAddress( addressToSave2 );

        ResponseEntity<List<AddressResponse>> savedAddressList = addressService.getAllAddress();

        assertNotNull(savedAddressList);
        assertFalse(Objects.requireNonNull(savedAddressList.getBody()).isEmpty());

        assertEquals(3, savedAddressList.getBody().size());

        assertEquals(addressToSave1.getStreet(),    savedAddressList.getBody().get(1).getStreet());
        assertEquals(addressToSave1.getCity(),      savedAddressList.getBody().get(1).getCity());
        assertEquals(addressToSave1.getZipCode(),   savedAddressList.getBody().get(1).getZipCode());
        assertEquals(addressToSave1.getCountry(),   savedAddressList.getBody().get(1).getCountry());

        assertEquals(addressToSave2.getStreet(),    savedAddressList.getBody().get(2).getStreet());
        assertEquals(addressToSave2.getCity(),      savedAddressList.getBody().get(2).getCity());
        assertEquals(addressToSave2.getZipCode(),   savedAddressList.getBody().get(2).getZipCode());
        assertEquals(addressToSave2.getCountry(),   savedAddressList.getBody().get(2).getCountry());
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

        while( randomUUID == savedAddressId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<AddressResponse> falseAddressInDatabaseById = addressService.getAddressById(randomUUID);

        assertNull(falseAddressInDatabaseById.getBody());
    }

    @Test
    public void testUpdateAddress() {

        AddressCreate updateForAddress = new AddressCreate(
                12,
                "newStreet",
                null,
                null,
                null
        );

        ResponseEntity<AddressResponse> updatedAddress = addressService.updateAddress(savedAddressId, updateForAddress);

        assertEquals(updatedAddress.getBody().getId(),          updatedAddress.getBody().getId());
        assertEquals(updatedAddress.getBody().getHouseNumber(), updatedAddress.getBody().getHouseNumber());
        assertEquals(updatedAddress.getBody().getStreet(),      updatedAddress.getBody().getStreet());
        assertEquals(updatedAddress.getBody().getZipCode(),     updatedAddress.getBody().getZipCode());
        assertEquals(updatedAddress.getBody().getCountry(),     updatedAddress.getBody().getCountry());
        assertEquals(updatedAddress.getBody().getCity(),        updatedAddress.getBody().getCity());

        assertEquals(updatedAddress.getBody().getHouseNumber(), 12);
        assertEquals(updatedAddress.getBody().getStreet(), "newStreet");
        assertEquals(updatedAddress.getBody().getZipCode(),     savedAddress.getBody().getZipCode());
        assertEquals(updatedAddress.getBody().getCountry(),     savedAddress.getBody().getCountry());
        assertEquals(updatedAddress.getBody().getCity(),        savedAddress.getBody().getCity());
    }

    @Test
    public void testDeleteAddress() {

        ResponseEntity<AddressResponse> deletedAddress = addressService.deleteAddress( savedAddressId );

        assertNull(deletedAddress.getBody());

    }
}
