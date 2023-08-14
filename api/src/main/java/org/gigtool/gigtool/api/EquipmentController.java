package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.services.EquipmentService;
import org.gigtool.gigtool.storage.services.TypeOfEquipmentService;
import org.gigtool.gigtool.storage.services.model.EquipmentCreate;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentResponse;
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

    @GetMapping("/byType/{typeOfEquipmentId}")
    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByTypeOfEquipment(@PathVariable UUID typeOfEquipmentId) {
        return this.equipmentService.getAllEquipmentByTypeOfEquipment(typeOfEquipmentId);
    }
}
