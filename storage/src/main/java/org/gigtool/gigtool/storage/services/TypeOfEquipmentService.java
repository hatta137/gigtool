package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.repositories.TypeOfEquipmentRepository;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfEquipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeOfEquipmentService {

    public final TypeOfEquipmentRepository typeOfEquipmentRepository;

    public TypeOfEquipmentService(TypeOfEquipmentRepository typeOfEquipmentRepository) {
        this.typeOfEquipmentRepository = typeOfEquipmentRepository;
    }

    public ResponseEntity<TypeOfEquipmentResponse> addTypeOfEquipment(TypeOfEquipmentCreate typeOfEquipmentCreate) {

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
}
