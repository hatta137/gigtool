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
public class AddressCreate {
    private int houseNumber;
    private String street;
    private String zipCode;
    private String country;
    private String city;

}

