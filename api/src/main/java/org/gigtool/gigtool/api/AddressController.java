package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.AddressService;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class represents the RESTful API controller for managing addresses.
 * @author Hendrik Lendeckel
 */
@RestController
@RequestMapping("/addresses")
public class AddressController {

    public final AddressService addressService;
    public AddressController( AddressService addressService ) {
        this.addressService = addressService;
    }

    /**
     * Adds a new address to the system.
     *
     * @param newAddress The address data to be added.
     * @return A ResponseEntity containing the created address response.
     */
    @PostMapping
    public ResponseEntity<AddressResponse> addAddress( @RequestBody AddressCreate newAddress ) {
        return this.addressService.addNewAddress( newAddress );
    }

    /**
     * Retrieves a list of all addresses.
     *
     * @return A ResponseEntity containing a list of address responses.
     */
    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddress() {
        return this.addressService.getAllAddress();
    }

    /**
     * Retrieves an address by its unique identifier.
     *
     * @param id The unique identifier of the address to retrieve.
     * @return A ResponseEntity containing the address response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById( @PathVariable UUID id ) {
        return this.addressService.getAddressById( id );
    }

    /**
     * Updates an existing address with new data.
     *
     * @param id           The unique identifier of the address to update.
     * @param addressCreate The updated address data.
     * @return A ResponseEntity containing the updated address response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress( @PathVariable UUID id, @RequestBody AddressCreate addressCreate ) {
        return this.addressService.updateAddress( id, addressCreate );
    }

    /**
     * Deletes an address by its unique identifier.
     *
     * @param id The unique identifier of the address to delete.
     * @return A ResponseEntity containing the deleted address response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress( @PathVariable UUID id ) {
        return this.addressService.deleteAddress( id );
    }
}
