package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.services.EquipmentService;
import org.gigtool.gigtool.storage.services.TypeOfEquipmentService;
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


    @PostMapping("/typeOfEquipment")
    public ResponseEntity<TypeOfEquipmentResponse> addTypeOfEquipment (@RequestBody TypeOfEquipmentCreate newTypeOfEquipment ) {
        return this.typeOfEquipmentService.addTypeOfEquipment( newTypeOfEquipment );
    }

    @GetMapping("/typeOfEquipment")
    public ResponseEntity<List<TypeOfEquipmentResponse>> getAllTypeOfEquipment() {
        return this.typeOfEquipmentService.getAllTypeOfEquipment();
    }

    @GetMapping("/typeOfEquipment/{id}")
    public ResponseEntity<TypeOfEquipmentResponse> getTypeOfEquipmentById(@PathVariable UUID id) {
        return this.typeOfEquipmentService.getTypeOfEquipmentById(id);
    }

    @PutMapping("/typeOfEquipment/{id}")
    public ResponseEntity<TypeOfEquipmentResponse> updateTypeOfEquipment(@PathVariable UUID id, @RequestBody TypeOfEquipmentCreate typeOfEquipmentCreate) {
        return this.typeOfEquipmentService.updateTypeOfEquipment(id, typeOfEquipmentCreate);
    }
}
