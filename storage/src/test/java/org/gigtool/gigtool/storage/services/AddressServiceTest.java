package org.gigtool.gigtool.storage.services;
import org.gigtool.gigtool.storage.services.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;
    @Autowired
    private TestUtils testUtils;

    @Autowired
    private LocationService locationService;

    private AddressCreate addressToSave;
    private ResponseEntity<AddressResponse> savedAddress;
    private UUID savedAddressId;

    @BeforeEach
    public void setup() {
        addressToSave = testUtils.getRandomAddressCreate();
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

        AddressCreate addressToSave1 = testUtils.getRandomAddressCreate();
        AddressCreate addressToSave2 = testUtils.getRandomAddressCreate();


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

        // positive Test
        ResponseEntity<AddressResponse> addressInDatabaseById = addressService.getAddressById(savedAddressId);

        assertEquals(Objects.requireNonNull(addressInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedAddress.getBody()).getId());
        assertEquals(addressInDatabaseById.getBody().getCity(), savedAddress.getBody().getCity());
        assertEquals(addressInDatabaseById.getBody().getStreet(), savedAddress.getBody().getStreet());
        assertEquals(addressInDatabaseById.getBody().getCountry(), savedAddress.getBody().getCountry());
        assertEquals(addressInDatabaseById.getBody().getZipCode(), savedAddress.getBody().getZipCode());

        //negative Test
        UUID randomUUID = UUID.randomUUID();

        while( randomUUID == savedAddressId ) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<AddressResponse> falseAddressInDatabaseById = addressService.getAddressById(randomUUID);

        assertNull(falseAddressInDatabaseById.getBody());
    }

    @Test
    public void testUpdateAddress() {

        //positive
        ResponseEntity<AddressResponse> addressBeforeUpdate = savedAddress;

        AddressCreate updateForAddress = new AddressCreate(
                12,
                "newStreet",
                null,
                null,
                null
        );

        ResponseEntity<AddressResponse> updatedAddress = addressService.updateAddress(savedAddressId, updateForAddress);

        assertEquals(addressBeforeUpdate.getBody().getId(),          updatedAddress.getBody().getId());
        assertEquals(addressBeforeUpdate.getBody().getZipCode(),     updatedAddress.getBody().getZipCode());
        assertEquals(addressBeforeUpdate.getBody().getCountry(),     updatedAddress.getBody().getCountry());
        assertEquals(addressBeforeUpdate.getBody().getCity(),        updatedAddress.getBody().getCity());

        assertEquals(updatedAddress.getBody().getHouseNumber(), 12);
        assertEquals(updatedAddress.getBody().getStreet(), "newStreet");

        AddressCreate updateForAddress2 = new AddressCreate(
                0,
                null,
                "zipCode",
                "country",
                "city"
        );

        ResponseEntity<AddressResponse> updatedAddress2 = addressService.updateAddress(savedAddressId, updateForAddress2);

        assertEquals(updatedAddress2.getBody().getCity(), "city");
        assertEquals(updatedAddress2.getBody().getZipCode(), "zipCode");
        assertEquals(updatedAddress2.getBody().getCountry(), "country");

        assertEquals(addressBeforeUpdate.getBody().getId(),          updatedAddress2.getBody().getId());

        //negative
        UUID randomUUID = UUID.randomUUID();
        while (randomUUID == addressBeforeUpdate.getBody().getId()) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<AddressResponse> updatedAddressFalse = addressService.updateAddress(randomUUID, updateForAddress);

        assertTrue(updatedAddressFalse.getStatusCode().is4xxClientError());
    }

    @Test
    public void testDeleteAddress() {

        //positive
        ResponseEntity<AddressResponse> deletedAddress = addressService.deleteAddress( savedAddressId );

        assertNull(deletedAddress.getBody());

        //negative
        UUID randomUUID = UUID.randomUUID();
        while (randomUUID == savedAddressId) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<AddressResponse> deletedAddressFalse = addressService.deleteAddress( randomUUID );

        assertTrue(deletedAddressFalse.getStatusCode().is4xxClientError());

        ResponseEntity<LocationResponse> locationResponse = testUtils.getRandomLocationResponse();
        AddressResponse addressResponse = locationResponse.getBody().getAddressResponse();

        ResponseEntity<AddressResponse> deletedAddressFalse2 = addressService.deleteAddress( addressResponse.getId() );

        assertTrue(deletedAddressFalse2.getStatusCode().is4xxClientError());
    }
}
