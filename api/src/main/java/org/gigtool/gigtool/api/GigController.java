package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.model.GigCreate;
import org.gigtool.gigtool.storage.services.model.GigResponse;
import org.gigtool.gigtool.storage.services.model.GigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gig")
public class GigController {

    private final GigService gigService;

    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    @PostMapping
    public ResponseEntity<GigResponse> addGig(@RequestBody GigCreate gigCreate) {
        return this.gigService.addGig(gigCreate);
    }

    @GetMapping
    public ResponseEntity<List<GigResponse>> allGigs() {
        return gigService.getAllGigs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GigResponse> getGigById(@PathVariable UUID id) {
        return gigService.getGigById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GigResponse> updateGig(@PathVariable UUID id, @RequestBody GigCreate gigRequest) {
        return gigService.updateGig(id, gigRequest);
    }

    @PutMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<GigResponse> addEquipment(@PathVariable UUID id, @PathVariable UUID eqID) {
        return gigService.addEquipmentToGig(id, eqID);
    }

    @DeleteMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<GigResponse> deleteEquipment(@PathVariable UUID id, @PathVariable UUID eqID) {
        return gigService.deleteEquipmentFromGig(id, eqID);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGig(@PathVariable UUID id) {
        return gigService.deleteGig(id);
    }
}
