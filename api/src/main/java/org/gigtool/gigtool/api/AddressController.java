package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.AddressService;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressRequest;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/addresses")
public class AddressController {

    public final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress( @RequestBody AddressCreate newAddress ) {
        return this.addressService.addNewAddress( newAddress );
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> addresses() {
        return this.addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> addressById( @PathVariable UUID id ) {
        return this.addressService.getAddressById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable UUID id, @RequestBody AddressCreate addressCreate) {
        return this.addressService.updateAddress(id, addressCreate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressResponse> deleteAddress(@PathVariable UUID id) {
        return this.addressService.deleteAddress(id);
    }
}
