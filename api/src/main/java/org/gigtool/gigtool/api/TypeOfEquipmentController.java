package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.TypeOfEquipmentService;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/typeOfEquipment")
public class TypeOfEquipmentController {

    public final TypeOfEquipmentService typeOfEquipmentService;

    public TypeOfEquipmentController( TypeOfEquipmentService typeOfEquipmentService) {
        this.typeOfEquipmentService = typeOfEquipmentService;
    }

    @PostMapping()
    public ResponseEntity<TypeOfEquipmentResponse> addTypeOfEquipment (@RequestBody TypeOfEquipmentCreate newTypeOfEquipment ) {
        return this.typeOfEquipmentService.addTypeOfEquipment( newTypeOfEquipment );
    }

    @GetMapping()
    public ResponseEntity<List<TypeOfEquipmentResponse>> getAllTypeOfEquipment() {
        return this.typeOfEquipmentService.getAllTypeOfEquipment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfEquipmentResponse> getTypeOfEquipmentById(@PathVariable UUID id) {
        return this.typeOfEquipmentService.getTypeOfEquipmentById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeOfEquipmentResponse> updateTypeOfEquipment(@PathVariable UUID id, @RequestBody TypeOfEquipmentCreate typeOfEquipmentCreate) {
        return this.typeOfEquipmentService.updateTypeOfEquipment(id, typeOfEquipmentCreate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TypeOfEquipmentResponse> deleteTypeOfEquipment(@PathVariable UUID id) {
        return this.typeOfEquipmentService.deleteTypeOfEquipment(id);
    }
}
