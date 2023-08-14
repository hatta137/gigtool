package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.EquipmentService;
import org.gigtool.gigtool.storage.services.TypeOfEquipmentService;
import org.gigtool.gigtool.storage.services.model.EquipmentCreate;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    public final EquipmentService equipmentService;

    public final TypeOfEquipmentService typeOfEquipmentService;

    public EquipmentController(EquipmentService equipmentService, TypeOfEquipmentService typeOfEquipmentService) {
        this.equipmentService = equipmentService;
        this.typeOfEquipmentService = typeOfEquipmentService;
    }

    @PostMapping
    public ResponseEntity<EquipmentResponse> addEquipment( @RequestBody EquipmentCreate equipmentCreate ) {
        return this.equipmentService.addEquipment( equipmentCreate );
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponse>> getAllEquipment() {
        return this.equipmentService.getAllEquipment();
    }


    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponse> getEquipmentById( @PathVariable UUID id ) {
        return this.equipmentService.getEquipmentById( id );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponse> updateEquipment( @PathVariable UUID id, @RequestBody EquipmentCreate equipmentCreate ) {
        return this.equipmentService.updateEquipment( id, equipmentCreate );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EquipmentResponse> deleteEquipment( @PathVariable UUID id ) {
        return this.equipmentService.deleteEquipment( id );
    }


    @GetMapping("/byType/{typeOfEquipmentId}")
    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByTypeOfEquipment( @PathVariable UUID typeOfEquipmentId)  {
        return this.equipmentService.getAllEquipmentByTypeOfEquipment( typeOfEquipmentId );
    }


    @GetMapping("/byLocation/{locationId}")
    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByLocation( @PathVariable UUID locationId ) {
        return this.equipmentService.getAllEquipmentByLocation( locationId );
    }
}
