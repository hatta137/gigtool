package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.gigtool.gigtool.storage.services.model.AddressRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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


    public ResponseEntity<AddressResponse> getAddressById(UUID id) {

        Optional<Address> foundAddress = addressRepository.findById(id);

        if (foundAddress.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.accepted().body( new AddressResponse( foundAddress.get() ));
    }

    public ResponseEntity<AddressResponse> updateAddress(UUID id, AddressRequest addressRequest) {
        Optional<Address> existingAddress = addressRepository.findById(id);

        if (existingAddress.isEmpty()) {
            // Hier können Sie auch eine spezielle Exception werfen oder andere Fehlerbehandlung durchführen
            throw new RuntimeException("Address not found with id: " + id);
        }

        Address addressToUpdate = existingAddress.get();

        if (addressRequest.getStreet() != null) {
            addressToUpdate.setStreet(addressRequest.getStreet());
        }
        if (addressRequest.getZipCode() != null) {
            addressToUpdate.setZipCode(addressRequest.getZipCode());
        }
        if (addressRequest.getCity() != null) {
            addressToUpdate.setCity(addressRequest.getCity());
        }
        if (addressRequest.getCountry() != null) {
            addressToUpdate.setCountry(addressRequest.getCountry());
        }

        Address savedAddress = addressRepository.saveAndFlush(addressToUpdate);

        return ResponseEntity.ok().body( new AddressResponse(( savedAddress)));
    }

    public ResponseEntity<AddressResponse> deleteAddress(UUID id) {
        Optional<Address> foundAddress = addressRepository.findById(id);

        if (foundAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Address addressToDelete = foundAddress.get();

        addressRepository.delete(addressToDelete);

        return ResponseEntity.accepted().build();
    }
}
