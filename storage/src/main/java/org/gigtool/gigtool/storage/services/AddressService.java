package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ResponseEntity<AddressResponse> addNewAddress(AddressCreate addressCreate) {

        if (addressCreate.getStreet() == null || addressCreate.getCity() == null ||
                addressCreate.getZipCode() == null || addressCreate.getCountry() == null) {

            // If any required information is missing, return a bad request response
            return ResponseEntity.badRequest().build();
        }

        Address address = new Address(
                addressCreate.getStreet(),
                addressCreate.getCity(),
                addressCreate.getZipCode(),
                addressCreate.getCountry(),
                addressCreate.getHouseNumber()
        );

        Address savedAddress = addressRepository.saveAndFlush( address );

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

    public ResponseEntity<AddressResponse> updateAddress( UUID id, AddressCreate addressCreate ) {

        Optional<Address> existingAddress = addressRepository.findById( id );

        if (existingAddress.isEmpty())
            throw new RuntimeException( "Address not found with id: " + id );

        Address addressToUpdate = existingAddress.get();

        if ( addressCreate.getStreet() != null ) {
            addressToUpdate.setStreet(addressCreate.getStreet());
        }
        if ( addressCreate.getZipCode() != null ) {
            addressToUpdate.setZipCode(addressCreate.getZipCode());
        }
        if ( addressCreate.getCity() != null ) {
            addressToUpdate.setCity(addressCreate.getCity());
        }
        if ( addressCreate.getCountry() != null ) {
            addressToUpdate.setCountry(addressCreate.getCountry());
        }

        Address savedAddress = addressRepository.saveAndFlush( addressToUpdate );

        return ResponseEntity.ok().body( new AddressResponse( savedAddress ));
    }

    public ResponseEntity<AddressResponse> deleteAddress( UUID id ) {

        Optional<Address> foundAddress = addressRepository.findById(id);

        if (foundAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Address addressToDelete = foundAddress.get();

        addressRepository.delete(addressToDelete);

        return ResponseEntity.accepted().build();
    }
}
























