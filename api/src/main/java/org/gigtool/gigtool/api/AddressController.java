package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.AddressService;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    //TODO ResponseEntity<ListOfAddressResponse>
    @GetMapping("allAddresses")
    public ResponseEntity<List<AddressResponse>> addresses() {
        return this.addressService.getAllAddresses();
    }

}
