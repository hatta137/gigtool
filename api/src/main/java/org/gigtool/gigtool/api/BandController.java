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

    private final BandService bandService;

    public BandController( BandService bandService ){
        this.bandService = bandService;
    }

    @PostMapping
    public ResponseEntity<BandResponse> addBand( @RequestBody BandCreate bandCreate ){
        return this.bandService.addBand(bandCreate);
    }

    @GetMapping
    public ResponseEntity<List<BandResponse>> allBands() {
        return bandService.getAllBands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandResponse> getBandById( @PathVariable UUID id ) {
        return bandService.getBandById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BandResponse> updateBand( @PathVariable UUID id, @RequestBody BandCreate bandRequest ) {
        return bandService.updateBand(id, bandRequest);
    }

    @PutMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<BandResponse> addEquipment( @PathVariable UUID id, @PathVariable UUID eqID ) {
        return bandService.addEquipmentToBand(id, eqID);
    }

    @DeleteMapping("/{id}/equipment/{eqID}")
    public ResponseEntity<BandResponse> deleteEquipment( @PathVariable UUID id, @PathVariable UUID eqID ) {
        return bandService.deleteEquipmentFromBand(id, eqID);
    }

    @PutMapping("/{id}/roleintheband/{roleId}")
    public ResponseEntity<BandResponse> addRole( @PathVariable UUID id, @PathVariable UUID roleId ) {
        return bandService.addRoleToBand(id, roleId);
    }

    @DeleteMapping("/{id}/roleintheband/{roleId}")
    public ResponseEntity<BandResponse> deleteRole( @PathVariable UUID id, @PathVariable UUID roleId ) {
        return bandService.deleteRoleFromBand(id, roleId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBand( @PathVariable UUID id ) {
        return bandService.deleteBand(id);
    }
}
