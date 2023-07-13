package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.AddressService;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/addresses")
public class AddressController {

    public final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/asas")
    public String hello(@RequestParam(value = "name") String name) {
        return "Hello " + name;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(
            @RequestBody AddressCreate newAddress
            ) {
        return this.addressService.addNewAddress( newAddress );
    }

    //TODO ResponseEntity<ListOfAddressResponse>

}
