package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;


@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<AddressResponse> addNewAddress(AddressCreate addressCreate) {

        Address address = new Address(
                addressCreate.getStreet(),
                addressCreate.getCity(),
                addressCreate.getZipCode(),
                addressCreate.getCountry(),
                addressCreate.getHouseNumber()
        );

        Address savedAddress = addressRepository.saveAndFlush(address);

        return ResponseEntity.accepted().body( new AddressResponse( savedAddress ));
    }
}
