package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.TypeOfGigService;
import org.gigtool.gigtool.storage.services.model.TypeOfGigCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfGigResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gig/typeofgig")
public class TypeOfGigController {

    private final TypeOfGigService typeOfGigService;

    public TypeOfGigController(TypeOfGigService typeOfGigService) {
        this.typeOfGigService = typeOfGigService;
    }

    @PostMapping
    public ResponseEntity<TypeOfGigResponse> addTypeOfGig(@RequestBody TypeOfGigCreate typeOfGigCreate) {
        return typeOfGigService.addTypeOfGig(typeOfGigCreate);
    }

    @GetMapping
    public ResponseEntity<List<TypeOfGigResponse>> allTypesOfGig() {
        return typeOfGigService.getAllTypesOfGig();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfGigResponse> getTypeOfGigByID(@PathVariable UUID id) {
        return typeOfGigService.getTypeOfGigById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeOfGigResponse> updateTypeOfGig(@PathVariable UUID id, @RequestBody TypeOfGigCreate typeRequest) {
        return typeOfGigService.updateTypeOfGig(id, typeRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeOfGig(@PathVariable UUID id) {
        return typeOfGigService.deleteTypeOfGig(id);
    }
}
