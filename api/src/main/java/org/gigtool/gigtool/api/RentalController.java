package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.model.RentalCreate;
import org.gigtool.gigtool.storage.services.model.RentalResponse;
import org.gigtool.gigtool.storage.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalResponse> addRental( @RequestBody RentalCreate rentalCreate ) {
        return this.rentalService.addRental( rentalCreate );
    }

    @GetMapping
    public ResponseEntity<List<RentalResponse>> allRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById( @PathVariable UUID id ) {
        return rentalService.getRentalById( id );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental( @PathVariable UUID id, @RequestBody RentalCreate rentalRequest ) {
        return rentalService.updateRental( id, rentalRequest );
    }

    @PutMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<RentalResponse> addEquipmentToRental( @PathVariable UUID id, @PathVariable UUID eqID ) {
        return rentalService.addEquipmentToRental( id, eqID );
    }

    @DeleteMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<RentalResponse> deleteEquipmentFromRental( @PathVariable UUID id, @PathVariable UUID eqID ) {
        return rentalService.deleteEquipmentFromRental( id, eqID );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRental( @PathVariable UUID id ) {
        return rentalService.deleteRental( id );
    }
}
