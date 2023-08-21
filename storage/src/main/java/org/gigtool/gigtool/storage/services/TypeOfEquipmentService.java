package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.repositories.TypeOfEquipmentRepository;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * @author Hendrik Lendeckel
 * Service class for managing type of equipment-related operations.
 */
@Service
public class TypeOfEquipmentService {

    public final TypeOfEquipmentRepository typeOfEquipmentRepository;

    public TypeOfEquipmentService( TypeOfEquipmentRepository typeOfEquipmentRepository ) {
        this.typeOfEquipmentRepository = typeOfEquipmentRepository;
    }

    /**
     * Adds a new type of equipment.
     *
     * @param typeOfEquipmentCreate The details of the type of equipment to be added.
     * @return A ResponseEntity containing the added type of equipment response or a bad request if input is invalid.
     */
    public ResponseEntity<TypeOfEquipmentResponse> addTypeOfEquipment( TypeOfEquipmentCreate typeOfEquipmentCreate ) {

        if (typeOfEquipmentCreate.getName() == null || typeOfEquipmentCreate.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        TypeOfEquipment typeOfEquipment = new TypeOfEquipment(
                typeOfEquipmentCreate.getName(),
                typeOfEquipmentCreate.getDescription()
        );

        TypeOfEquipment savedTypeOfEquipment = typeOfEquipmentRepository.saveAndFlush( typeOfEquipment );

        return ResponseEntity.accepted().body( new TypeOfEquipmentResponse( savedTypeOfEquipment ));
    }

    /**
     * Retrieves a list of all types of equipment.
     *
     * @return A ResponseEntity containing a list of type of equipment responses.
     */
    public ResponseEntity<List<TypeOfEquipmentResponse>> getAllTypeOfEquipment() {

        List<TypeOfEquipment> typeOfEquipmentList = typeOfEquipmentRepository.findAll();

        List<TypeOfEquipmentResponse> responseList = typeOfEquipmentList
                .stream()
                .map(TypeOfEquipmentResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(200).body( responseList );
    }

    /**
     * Retrieves type of equipment by its ID.
     *
     * @param id The ID of the type of equipment to retrieve.
     * @return A ResponseEntity containing the type of equipment response or not found if the type of equipment doesn't exist.
     */
    public ResponseEntity<TypeOfEquipmentResponse> getTypeOfEquipmentById( UUID id ) {

        Optional<TypeOfEquipment> foundTypeOfEquipment = typeOfEquipmentRepository.findById( id );

        if (foundTypeOfEquipment.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.accepted().body( new TypeOfEquipmentResponse( foundTypeOfEquipment.get() ));
    }

    /**
     * Updates an existing type of equipment.
     *
     * @param id                     The ID of the type of equipment to update.
     * @param typeOfEquipmentCreate The updated type of equipment details.
     * @return A ResponseEntity containing the updated type of equipment response or not found if the type of equipment doesn't exist.
     */
    public ResponseEntity<TypeOfEquipmentResponse> updateTypeOfEquipment( UUID id, TypeOfEquipmentCreate typeOfEquipmentCreate ) {

        if (typeOfEquipmentCreate.getName() == null || typeOfEquipmentCreate.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<TypeOfEquipment> existingTypeOfEquipment = typeOfEquipmentRepository.findById( id );

        if (existingTypeOfEquipment.isEmpty())
            return ResponseEntity.notFound().build();

        TypeOfEquipment typeOfEquipmentToUpdate = existingTypeOfEquipment.get();

        typeOfEquipmentToUpdate.setName(typeOfEquipmentCreate.getName());

        typeOfEquipmentToUpdate.setDescription(typeOfEquipmentCreate.getDescription());


        TypeOfEquipment savedTypeOfEquipment = typeOfEquipmentRepository.saveAndFlush( typeOfEquipmentToUpdate );

        return ResponseEntity.ok().body( new TypeOfEquipmentResponse( savedTypeOfEquipment ));
    }

    /**
     * Deletes an existing type of equipment.
     *
     * @param id The ID of the type of equipment to delete.
     * @return A ResponseEntity indicating the success of the deletion or not found if the type of equipment doesn't exist.
     */
    public ResponseEntity<TypeOfEquipmentResponse> deleteTypeOfEquipment( UUID id ) {

        Optional<TypeOfEquipment> foundTypeOfEquipment = typeOfEquipmentRepository.findById( id );

        if (foundTypeOfEquipment.isEmpty())
            return ResponseEntity.notFound().build();

        TypeOfEquipment typeOfEquipmentToDelete = foundTypeOfEquipment.get();

        typeOfEquipmentRepository.delete(typeOfEquipmentToDelete);

        return ResponseEntity.accepted().build();
    }
}
