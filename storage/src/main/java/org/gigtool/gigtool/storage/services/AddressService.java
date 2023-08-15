package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Service class for managing addresses in the application.
 * @author Hendrik Lendeckel
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    /**
     * Constructs an instance of the AddressService.
     *
     * @param addressRepository The repository for accessing address data.
     */
    public AddressService( AddressRepository addressRepository ) {
        this.addressRepository = addressRepository;
    }

    /**
     * Adds a new address to the system.
     *
     * @param addressCreate The information for creating the new address.
     * @return A response entity indicating the success or failure of the operation.
     */
    @Transactional
    public ResponseEntity<AddressResponse> addNewAddress( AddressCreate addressCreate ) {

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

    /**
     * Retrieves a list of all addresses in the system.
     *
     * @return A response entity containing a list of address responses.
     */
    public ResponseEntity<List<AddressResponse>> getAllAddress() {

        List<Address> addressesList = addressRepository.findAll();

        List<AddressResponse> responseList = addressesList
                .stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(200).body( responseList );
    }

    /**
     * Retrieves a specific address by its unique identifier.
     *
     * @param id The unique identifier of the address to retrieve.
     * @return A response entity containing the retrieved address response.
     */
    public ResponseEntity<AddressResponse> getAddressById( UUID id ) {

        Optional<Address> foundAddress = addressRepository.findById( id );

        if (foundAddress.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.accepted().body( new AddressResponse( foundAddress.get() ));
    }

    /**
     * Updates an existing address with new information.
     *
     * @param id            The unique identifier of the address to update.
     * @param addressCreate The updated information for the address.
     * @return A response entity containing the updated address response.
     * @throws RuntimeException If the address with the specified ID is not found.
     */
    @Transactional
    public ResponseEntity<AddressResponse> updateAddress( UUID id, AddressCreate addressCreate ) {

        Optional<Address> existingAddress = addressRepository.findById( id );

        if (existingAddress.isEmpty())
            return ResponseEntity.notFound().build();

        Address addressToUpdate = existingAddress.get();

        if ( addressCreate.getHouseNumber() >= 0) {
            addressToUpdate.setHouseNumber( addressCreate.getHouseNumber() );
        }
        if ( addressCreate.getStreet() != null ) {
            addressToUpdate.setStreet( addressCreate.getStreet() );
        }
        if ( addressCreate.getZipCode() != null ) {
            addressToUpdate.setZipCode( addressCreate.getZipCode() );
        }
        if ( addressCreate.getCity() != null ) {
            addressToUpdate.setCity( addressCreate.getCity() );
        }
        if ( addressCreate.getCountry() != null ) {
            addressToUpdate.setCountry( addressCreate.getCountry() );
        }

        Address savedAddress = addressRepository.saveAndFlush( addressToUpdate );

        return ResponseEntity.ok().body( new AddressResponse( savedAddress ));
    }

    /**
     * Deletes an existing address from the system.
     *
     * @param id The unique identifier of the address to delete.
     * @return A response entity indicating the success or failure of the deletion operation.
     */
    public ResponseEntity<AddressResponse> deleteAddress( UUID id ) {

        Optional<Address> foundAddress = addressRepository.findById( id );

        if (foundAddress.isEmpty())
            return ResponseEntity.notFound().build();


        Address addressToDelete = foundAddress.get();

        addressRepository.delete(addressToDelete);

        return ResponseEntity.accepted().build();
    }
}
























