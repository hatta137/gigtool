package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.Happening;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private UUID Id;
    private int houseNumber;
    private String street;
    private String zipCode;
    private String country;
    private String city;
    private ArrayList<Happening> happenings;

    public AddressResponse(Address address) {
        Id = address.getId();
        houseNumber = address.getHouseNumber();
        street = address.getStreet();
        zipCode = address.getZipCode();
        country = address.getCountry();
        city = address.getCity();
        happenings = new ArrayList<>();
    }
}
