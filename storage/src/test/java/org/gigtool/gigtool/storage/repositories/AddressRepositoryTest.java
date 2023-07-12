package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.Gig;
import org.gigtool.gigtool.storage.model.Happening;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSave() {
        Address address = getRandomAddress();
        assertNotNull(address);

        Address savedAddress = addressRepository.saveAndFlush(address);
        assertNotNull(savedAddress);
        assertEquals(savedAddress.getId(), address.getId());
        assertEquals(savedAddress.getStreet(), address.getStreet());
        assertEquals(savedAddress.getCity(), address.getCity());
        assertEquals(savedAddress.getZipCode(), address.getZipCode());
        assertEquals(savedAddress.getCountry(), address.getCountry());
    }

    public static Address getRandomAddress() {
        UUID newID = UUID.randomUUID();
        String street = newID + "Street";
        String city = newID + "City";
        String zipCode = newID + "ZipCode";
        String country = newID + "Country";
        int houseNumber = 1234;
        return new Address(street, city, zipCode, country, houseNumber);
    }
}
