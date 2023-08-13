package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.LocationService;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.gigtool.gigtool.storage.services.model.LocationCreate;
import org.gigtool.gigtool.storage.services.model.LocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/location")
public class LocationController {

    public final LocationService locationService;

    public LocationController( LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationResponse> addLocation( @RequestBody LocationCreate locationCreate ) {
        return this.locationService.addLocation( locationCreate );
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocation() {
        return this.locationService.getAllLocation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocationById( @PathVariable UUID id ) {
        return this.locationService.getLocationById( id );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation( @PathVariable UUID id, @RequestBody LocationCreate locationCreate ) {
        return this.locationService.updateLocation( id, locationCreate );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationResponse> deleteLocation( @PathVariable UUID id ) {
        return this.locationService.deleteLocation( id );
    }
}
