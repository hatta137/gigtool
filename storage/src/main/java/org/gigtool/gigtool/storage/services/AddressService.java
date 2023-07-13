package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    public ResponseEntity<List<AddressResponse>> getAllAddresses() {

        List<Address> addressesList = addressRepository.findAll();

        List<AddressResponse> responseList = addressesList
                .stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(200).body( responseList );
    }
}
