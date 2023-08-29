package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.TypeOfLocationService;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class represents the RESTful API controller for managing types of locations.
 */
@RestController
@RequestMapping("/typeOfLocation")
public class TypeOfLocationController {

    public final TypeOfLocationService typeOfLocationService;

    public TypeOfLocationController( TypeOfLocationService typeOfLocationService ) {
        this.typeOfLocationService = typeOfLocationService;
    }

    /**
     * Adds a new type of location to the system.
     *
     * @param typeOfLocationCreate The type of location data to be added.
     * @return A ResponseEntity containing the created type of location response.
     */
    @PostMapping
    public ResponseEntity<TypeOfLocationResponse> addTypeOfLocation( @RequestBody TypeOfLocationCreate typeOfLocationCreate ) {
        return this.typeOfLocationService.addTypeOfLocation( typeOfLocationCreate );
    }

    /**
     * Retrieves a list of all types of locations.
     *
     * @return A ResponseEntity containing a list of type of location responses.
     */
    @GetMapping
    public ResponseEntity<List<TypeOfLocationResponse>> getAllTypeOfLocation() {
        return this.typeOfLocationService.getAllTypeOfLocation();
    }

    /**
     * Retrieves a type of location by its unique identifier.
     *
     * @param id The unique identifier of the type of location to retrieve.
     * @return A ResponseEntity containing the type of location response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfLocationResponse> getTypeOfLocationById( @PathVariable UUID id ) {
        return this.typeOfLocationService.getTypeOfLocationById( id );
    }

    /**
     * Updates an existing type of location with new data.
     *
     * @param id                      The unique identifier of the type of location to update.
     * @param typeOfLocationCreate The updated type of location data.
     * @return A ResponseEntity containing the updated type of location response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfLocationResponse> updateTypeOfLocation( @PathVariable UUID id, @RequestBody TypeOfLocationCreate typeOfLocationCreate ) {
        return this.typeOfLocationService.updateTypeOfLocation( id, typeOfLocationCreate );
    }

    /**
     * Deletes a type of location by its unique identifier.
     *
     * @param id The unique identifier of the type of location to delete.
     * @return A ResponseEntity containing the deleted type of location response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeOfLocation( @PathVariable UUID id ) {
        return this.typeOfLocationService.deleteTypeOfLocation( id );
    }
}
