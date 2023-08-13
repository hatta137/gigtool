package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.model.WeightClassList;
import org.gigtool.gigtool.storage.services.WeightClassListService;
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
    public final WeightClassListService weightClassListService;

    public WeightClassController( WeightClassService weightClassService, WeightClassListService weightClassListService) {
        this.weightClassService = weightClassService;
        this.weightClassListService = weightClassListService;
    }

    @PostMapping
    public ResponseEntity<WeightClassResponse> addWeightClass(@RequestBody WeightClassCreate weightClassCreate ) {
        return this.weightClassService.addWeightClass( weightClassCreate );
    }

    @GetMapping
    public ResponseEntity<List<WeightClassResponse>> getAllWeightClasses() {
        return this.weightClassService.getAllWeightClass();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeightClassResponse> getWeightClassById( @PathVariable UUID id ) {
        return this.weightClassService.getWeightClassById( id );
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeightClassResponse> updateWeightClass( @PathVariable UUID id, @RequestBody WeightClassCreate weightClassCreate ) {
        return this.weightClassService.updateWeightClass( id, weightClassCreate );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WeightClassResponse> deleteWeightClass( @PathVariable UUID id ) {
        return this.weightClassService.deleteWeightClass( id );
    }

    @GetMapping("/weightClassList")
    public ResponseEntity<WeightClassList> getWeightClassList() {
        return this.weightClassListService.getWeightClassList();
    }
}
