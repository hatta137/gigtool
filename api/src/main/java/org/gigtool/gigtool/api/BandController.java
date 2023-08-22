package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.BandService;
import org.gigtool.gigtool.storage.services.model.BandCreate;
import org.gigtool.gigtool.storage.services.model.BandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gig/band")
public class BandController {

    public final BandService bandService;

    public BandController (BandService bandService){
        this.bandService = bandService;
    }

    @PostMapping
    public ResponseEntity<BandResponse> addBand(@RequestBody BandCreate bandCreate){
        return this.bandService.addBand(bandCreate);
    }

    @GetMapping
    public ResponseEntity<List<BandResponse>> allBands() {
        return bandService.getAllBands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandResponse> getBandById(@PathVariable UUID id) {
        return bandService.getBandById(id);
    }

    @PutMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<BandResponse> addEquipment(@PathVariable UUID id, @PathVariable UUID eqID){
        return bandService.addEquipment(id, eqID);
    }

    @DeleteMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<BandResponse> deleteEquipment(@PathVariable UUID id, @PathVariable UUID eqID){
        return bandService.deleteEquipment(id, eqID);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBand(@PathVariable UUID id) {
        return bandService.deleteBand(id);
    }
}
