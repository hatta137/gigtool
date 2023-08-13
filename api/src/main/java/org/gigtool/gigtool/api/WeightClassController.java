package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.WeightClassService;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.gigtool.gigtool.storage.services.model.WeightClassCreate;
import org.gigtool.gigtool.storage.services.model.WeightClassResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/weightClass")
public class WeightClassController {

    public final WeightClassService weightClassService;

    public WeightClassController( WeightClassService weightClassService) {
        this.weightClassService = weightClassService;
    }

    @PostMapping
    public ResponseEntity<WeightClassResponse> addAddress(@RequestBody WeightClassCreate weightClassCreate ) {
        return this.weightClassService.addWeightClass( weightClassCreate );
    }

    @GetMapping
    public ResponseEntity<List<WeightClassResponse>> addresses() {
        return this.weightClassService.getAllWeightClass();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeightClassResponse> addressById( @PathVariable UUID id ) {
        return this.weightClassService.getWeightClassById( id );
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeightClassResponse> updateAddress( @PathVariable UUID id, @RequestBody WeightClassCreate weightClassCreate ) {
        return this.weightClassService.updateWeightClass( id, weightClassCreate );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WeightClassResponse> deleteAddress( @PathVariable UUID id ) {
        return this.weightClassService.deleteWeightClass( id );
    }
}
