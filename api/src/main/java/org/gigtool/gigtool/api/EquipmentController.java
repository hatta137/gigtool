package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.EquipmentService;
import org.gigtool.gigtool.storage.services.TypeOfEquipmentService;
import org.gigtool.gigtool.storage.services.model.EquipmentCreate;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class represents the RESTful API controller for managing equipment.
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    public final EquipmentService equipmentService;
    public final TypeOfEquipmentService typeOfEquipmentService;

    public EquipmentController(EquipmentService equipmentService, TypeOfEquipmentService typeOfEquipmentService) {
        this.equipmentService = equipmentService;
        this.typeOfEquipmentService = typeOfEquipmentService;
    }

    /**
     * Adds a new equipment to the system.
     *
     * @param equipmentCreate The equipment data to be added.
     * @return A ResponseEntity containing the result of the equipment addition.
     */
    @PostMapping
    public ResponseEntity<?> addEquipment( @RequestBody EquipmentCreate equipmentCreate ) {
        return this.equipmentService.addEquipment( equipmentCreate );
    }

    /**
     * Retrieves a list of all equipment.
     *
     * @return A ResponseEntity containing a list of equipment responses.
     */
    @GetMapping
    public ResponseEntity<List<EquipmentResponse>> getAllEquipment() {
        return this.equipmentService.getAllEquipment();
    }

    /**
     * Retrieves equipment by its unique identifier.
     *
     * @param id The unique identifier of the equipment to retrieve.
     * @return A ResponseEntity containing the equipment response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponse> getEquipmentById( @PathVariable UUID id ) {
        return this.equipmentService.getEquipmentById( id );
    }

    /**
     * Updates existing equipment with new data.
     *
     * @param id              The unique identifier of the equipment to update.
     * @param equipmentCreate The updated equipment data.
     * @return A ResponseEntity containing the updated equipment response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponse> updateEquipment( @PathVariable UUID id, @RequestBody EquipmentCreate equipmentCreate ) {
        return this.equipmentService.updateEquipment( id, equipmentCreate );
    }

    /**
     * Deletes equipment by its unique identifier.
     *
     * @param id The unique identifier of the equipment to delete.
     * @return A ResponseEntity containing the result of the equipment deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<EquipmentResponse> deleteEquipment( @PathVariable UUID id ) {
        return this.equipmentService.deleteEquipment( id );
    }

    /**
     * Retrieves a list of equipment by a specific type of equipment.
     *
     * @param typeOfEquipmentId The unique identifier of the type of equipment.
     * @return A ResponseEntity containing a list of equipment responses.
     */
    @GetMapping("/byType/{typeOfEquipmentId}")
    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByTypeOfEquipment( @PathVariable UUID typeOfEquipmentId)  {
        return this.equipmentService.getAllEquipmentByTypeOfEquipment( typeOfEquipmentId );
    }

    /**
     * Retrieves a list of equipment by a specific location.
     *
     * @param locationId The unique identifier of the location.
     * @return A ResponseEntity containing a list of equipment responses.
     */
    @GetMapping("/byLocation/{locationId}")
    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByLocation( @PathVariable UUID locationId ) {
        return this.equipmentService.getAllEquipmentByLocation( locationId );
    }
}
