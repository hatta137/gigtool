package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.TypeOfGig;
import org.gigtool.gigtool.storage.repositories.TypeOfGigRepository;
import org.gigtool.gigtool.storage.services.model.TypeOfGigCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfGigResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing gig-related operations in the application
 * @author Dario
 */
@Service
public class TypeOfGigService {

    private final TypeOfGigRepository typeOfGigRepository;

    public TypeOfGigService(TypeOfGigRepository typeOfGigRepository) {
        this.typeOfGigRepository = typeOfGigRepository;
    }

    /**
     * Adds a new type of gig to the system.
     *
     * @param typeOfGigCreate The details of the new type of gig to be created.
     * @return A response entity containing the created type of gig or a bad request if input is invalid.
     */
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

    /**
     * Retrieves a list of all types of gigs available in the system.
     *
     * @return A response entity containing a list of type of gig responses.
     */
    public ResponseEntity<List<TypeOfGigResponse>> getAllTypesOfGig() {
        List<TypeOfGig> typeOfGigList = typeOfGigRepository.findAll();

        List<TypeOfGigResponse> responseList = typeOfGigList.stream()
                .map(TypeOfGigResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    /**
     * Retrieves a specific type of gig by its unique identifier.
     *
     * @param typeId The unique identifier of the type of gig to retrieve.
     * @return A response entity containing the requested type of gig.
     */
    public ResponseEntity<TypeOfGigResponse> getTypeOfGigById(UUID typeId) {
        Optional<TypeOfGig> typeOptional = typeOfGigRepository.findById(typeId);

        return typeOptional.map(typeOfGig -> ResponseEntity.ok(new TypeOfGigResponse(typeOfGig)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates the details of a specific type of gig.
     *
     * @param typeId      The unique identifier of the type of gig to be updated.
     * @param typeRequest The updated details of the type of gig.
     * @return A response entity containing the updated type of gig response or not found if the type of gig doesn't exist.
     */
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

    /**
     * Deletes a specific type of gig from the system.
     *
     * @param typeId The unique identifier of the type of gig to be deleted.
     * @return A response entity indicating the success or failure of the deletion or not found if the type of gig doesn't exist.
     */
    public ResponseEntity<String> deleteTypeOfGig(UUID typeId) {
        if (typeId == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<TypeOfGig> existingType = typeOfGigRepository.findById(typeId);

        if (existingType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int gigsWithTypeOfGig = typeOfGigRepository.countBandsWithGenre(typeId);

        if(gigsWithTypeOfGig > 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("TypeOfGig has a relation to a Gig. You cannot delete this TypeOfGig!");
        }

        typeOfGigRepository.delete(existingType.get());

        return ResponseEntity.ok("TypeOfGig is deleted");
    }
}
