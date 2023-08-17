package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.LocationService;
import org.gigtool.gigtool.storage.services.model.LocationCreate;
import org.gigtool.gigtool.storage.services.model.LocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class represents the RESTful API controller for managing locations.
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    public final LocationService locationService;
    public LocationController( LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Adds a new location to the system.
     *
     * @param locationCreate The location data to be added.
     * @return A ResponseEntity containing the created location response.
     */
    @PostMapping
    public ResponseEntity<LocationResponse> addLocation( @RequestBody LocationCreate locationCreate ) {
        return this.locationService.addLocation( locationCreate );
    }

    /**
     * Retrieves a list of all locations.
     *
     * @return A ResponseEntity containing a list of location responses.
     */
    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocation() {
        return this.locationService.getAllLocation();
    }

    /**
     * Retrieves a location by its unique identifier.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return A ResponseEntity containing the location response.
     */

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocationById( @PathVariable UUID id ) {
        return this.locationService.getLocationById( id );
    }

    /**
     * Updates an existing location with new data.
     *
     * @param id             The unique identifier of the location to update.
     * @param locationCreate The updated location data.
     * @return A ResponseEntity containing the updated location response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation( @PathVariable UUID id, @RequestBody LocationCreate locationCreate ) {
        return this.locationService.updateLocation( id, locationCreate );
    }

    /**
     * Deletes a location by its unique identifier.
     *
     * @param id The unique identifier of the location to delete.
     * @return A ResponseEntity containing the deleted location response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<LocationResponse> deleteLocation( @PathVariable UUID id ) {
        return this.locationService.deleteLocation( id );
    }
}
