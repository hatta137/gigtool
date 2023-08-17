package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.TypeOfGig;
import org.gigtool.gigtool.storage.repositories.TypeOfGigRepository;
import org.gigtool.gigtool.storage.services.model.TypeOfGigCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfGigResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TypeOfGigService {

    private final TypeOfGigRepository typeOfGigRepository;

    public TypeOfGigService(TypeOfGigRepository typeOfGigRepository) {
        this.typeOfGigRepository = typeOfGigRepository;
    }

    public ResponseEntity<TypeOfGigResponse> addTypeOfGig(TypeOfGigCreate typeOfGigCreate) {
        if (typeOfGigCreate.getName() == null || typeOfGigCreate.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        TypeOfGig newTypeOfGig = new TypeOfGig();
        newTypeOfGig.setName(typeOfGigCreate.getName());
        newTypeOfGig.setDescription(typeOfGigCreate.getDescription());

        TypeOfGig savedTypeOfGig = typeOfGigRepository.saveAndFlush(newTypeOfGig);

        return ResponseEntity.ok(new TypeOfGigResponse(savedTypeOfGig));
    }

    public ResponseEntity<List<TypeOfGigResponse>> getAllTypesOfGig() {
        List<TypeOfGig> typeOfGigList = typeOfGigRepository.findAll();

        List<TypeOfGigResponse> responseList = typeOfGigList.stream()
                .map(TypeOfGigResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<TypeOfGigResponse> getTypeOfGigById(UUID typeId) {
        Optional<TypeOfGig> typeOptional = typeOfGigRepository.findById(typeId);

        return typeOptional.map(typeOfGig -> ResponseEntity.ok(new TypeOfGigResponse(typeOfGig)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<TypeOfGigResponse> updateTypeOfGig(UUID typeId, TypeOfGigCreate typeRequest) {

        if ((typeId == null) || (typeRequest.getName() == null) || (typeRequest.getDescription() == null) ) {
            return ResponseEntity.badRequest().build();
        }

        Optional<TypeOfGig> existingType = typeOfGigRepository.findById(typeId);

        if (existingType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfGig updatedType = existingType.get();

        updatedType.setName(typeRequest.getName());
        updatedType.setDescription(typeRequest.getDescription());

        TypeOfGig savedType = typeOfGigRepository.saveAndFlush(updatedType);

        return ResponseEntity.ok(new TypeOfGigResponse(savedType));
    }

    public ResponseEntity<String> deleteTypeOfGig(UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<TypeOfGig> existingType = typeOfGigRepository.findById(id);

        if (existingType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        typeOfGigRepository.delete(existingType.get());

        return ResponseEntity.ok("TypeOfGig is deleted");
    }
}
