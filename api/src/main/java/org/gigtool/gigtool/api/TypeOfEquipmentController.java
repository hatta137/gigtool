package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.TypeOfEquipmentService;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class represents the RESTful API controller for managing types of equipment.
 */
@RestController
@RequestMapping("/typeOfEquipment")
public class TypeOfEquipmentController {

    public final TypeOfEquipmentService typeOfEquipmentService;

    public TypeOfEquipmentController( TypeOfEquipmentService typeOfEquipmentService ) {
        this.typeOfEquipmentService = typeOfEquipmentService;
    }

    /**
     * Adds a new type of equipment to the system.
     *
     * @param newTypeOfEquipment The type of equipment data to be added.
     * @return A ResponseEntity containing the created type of equipment response.
     */
    @PostMapping()
    public ResponseEntity<TypeOfEquipmentResponse> addTypeOfEquipment ( @RequestBody TypeOfEquipmentCreate newTypeOfEquipment ) {
        return this.typeOfEquipmentService.addTypeOfEquipment( newTypeOfEquipment );
    }

    /**
     * Retrieves a list of all types of equipment.
     *
     * @return A ResponseEntity containing a list of type of equipment responses.
     */
    @GetMapping()
    public ResponseEntity<List<TypeOfEquipmentResponse>> getAllTypeOfEquipment() {
        return this.typeOfEquipmentService.getAllTypeOfEquipment();
    }

    /**
     * Retrieves a type of equipment by its unique identifier.
     *
     * @param id The unique identifier of the type of equipment to retrieve.
     * @return A ResponseEntity containing the type of equipment response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfEquipmentResponse> getTypeOfEquipmentById( @PathVariable UUID id ) {
        return this.typeOfEquipmentService.getTypeOfEquipmentById( id );
    }

    /**
     * Updates an existing type of equipment with new data.
     *
     * @param id                 The unique identifier of the type of equipment to update.
     * @param typeOfEquipmentCreate The updated type of equipment data.
     * @return A ResponseEntity containing the updated type of equipment response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfEquipmentResponse> updateTypeOfEquipment( @PathVariable UUID id, @RequestBody TypeOfEquipmentCreate typeOfEquipmentCreate ) {
        return this.typeOfEquipmentService.updateTypeOfEquipment( id, typeOfEquipmentCreate );
    }

    /**
     * Deletes a type of equipment by its unique identifier.
     *
     * @param id The unique identifier of the type of equipment to delete.
     * @return A ResponseEntity containing the deleted type of equipment response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeOfEquipment( @PathVariable UUID id ) {
        return this.typeOfEquipmentService.deleteTypeOfEquipment( id );
    }
}
