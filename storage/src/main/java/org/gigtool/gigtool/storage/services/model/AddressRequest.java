package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Address;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private int houseNumber;
    private String street;
    private String zipCode;
    private String country;
    private String city;

    public AddressRequest(Address address) {

        houseNumber = address.getHouseNumber();
        street = address.getStreet();
        zipCode = address.getZipCode();
        country = address.getCountry();
        city = address.getCity();
    }
}
