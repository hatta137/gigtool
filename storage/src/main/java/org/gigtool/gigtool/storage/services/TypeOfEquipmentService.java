package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.repositories.TypeOfEquipmentRepository;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TypeOfEquipmentService {

    public final TypeOfEquipmentRepository typeOfEquipmentRepository;

    public TypeOfEquipmentService(TypeOfEquipmentRepository typeOfEquipmentRepository) {
        this.typeOfEquipmentRepository = typeOfEquipmentRepository;
    }

    public ResponseEntity<TypeOfEquipmentResponse> addTypeOfEquipment(TypeOfEquipmentCreate typeOfEquipmentCreate) {

        if (typeOfEquipmentCreate.getName() == null || typeOfEquipmentCreate.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        TypeOfEquipment typeOfEquipment = new TypeOfEquipment(
                typeOfEquipmentCreate.getName(),
                typeOfEquipmentCreate.getDescription()
        );

        TypeOfEquipment savedTypeOfEquipment = typeOfEquipmentRepository.saveAndFlush(typeOfEquipment);

        return ResponseEntity.accepted().body( new TypeOfEquipmentResponse( savedTypeOfEquipment ));
    };

    public ResponseEntity<List<TypeOfEquipmentResponse>> getAllTypeOfEquipment() {

        List<TypeOfEquipment> typeOfEquipmentList = typeOfEquipmentRepository.findAll();

        List<TypeOfEquipmentResponse> responseList = typeOfEquipmentList
                .stream()
                .map(TypeOfEquipmentResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(200).body( responseList );
    }

    public ResponseEntity<TypeOfEquipmentResponse> getTypeOfEquipmentById(UUID id) {

        Optional<TypeOfEquipment> foundTypeOfEquipment = typeOfEquipmentRepository.findById(id);

        if (foundTypeOfEquipment.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.accepted().body( new TypeOfEquipmentResponse( foundTypeOfEquipment.get() ));
    }

    public ResponseEntity<TypeOfEquipmentResponse> updateTypeOfEquipment(UUID id, TypeOfEquipmentCreate typeOfEquipmentCreate) {

        Optional<TypeOfEquipment> existingTypeOfEquipment = typeOfEquipmentRepository.findById(id);

        if (existingTypeOfEquipment.isEmpty()) {
            throw new RuntimeException("TypeOfEquipment not found with id: " + id);
        }

        TypeOfEquipment typeOfEquipmentToUpdate = existingTypeOfEquipment.get();

        if (typeOfEquipmentCreate.getName() != null) {
            typeOfEquipmentToUpdate.setName(typeOfEquipmentCreate.getName());
        }
        if (typeOfEquipmentCreate.getDescription() != null) {
            typeOfEquipmentToUpdate.setDescription(typeOfEquipmentCreate.getDescription());
        }

        TypeOfEquipment savedTypeOfEquipment = typeOfEquipmentRepository.saveAndFlush( typeOfEquipmentToUpdate );

        return ResponseEntity.ok().body( new TypeOfEquipmentResponse( savedTypeOfEquipment ));
    }
}
