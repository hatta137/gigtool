package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.TypeOfLocationService;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/typeOfLocation")
public class TypeOfLocationController {

    public final TypeOfLocationService typeOfLocationService;

    public TypeOfLocationController(TypeOfLocationService typeOfLocationService) {
        this.typeOfLocationService = typeOfLocationService;
    }

    @PostMapping
    public ResponseEntity<TypeOfLocationResponse> addTypeOfLocation( @RequestBody TypeOfLocationCreate typeOfLocationCreate ) {
        return this.typeOfLocationService.addTypeOfLocation( typeOfLocationCreate );
    }

    @GetMapping
    public ResponseEntity<List<TypeOfLocationResponse>> getAllTypeOfLocation() {
        return this.typeOfLocationService.getAllTypeOfLocation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfLocationResponse> getTypeOfLocationById( @PathVariable UUID id ) {
        return this.typeOfLocationService.getTypeOfLocationById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeOfLocationResponse> updateTypeOfLocation(@PathVariable UUID id, @RequestBody TypeOfLocationCreate typeOfLocationCreate) {
        return this.typeOfLocationService.updateTypeOfLocation(id, typeOfLocationCreate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TypeOfLocationResponse> deleteTypeOfLocation(@PathVariable UUID id) {
        return this.typeOfLocationService.deleteTypeOfLocation(id);
    }


}
