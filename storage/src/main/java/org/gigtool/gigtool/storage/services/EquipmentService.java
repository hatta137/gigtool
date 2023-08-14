package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.repositories.EquipmentRepository;
import org.gigtool.gigtool.storage.repositories.LocationRepository;
import org.gigtool.gigtool.storage.repositories.TypeOfEquipmentRepository;
import org.gigtool.gigtool.storage.services.model.EquipmentCreate;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final TypeOfEquipmentRepository typeOfEquipmentRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository, TypeOfEquipmentRepository typeOfEquipmentRepository, LocationRepository locationRepository) {
        this.equipmentRepository = equipmentRepository;
        this.typeOfEquipmentRepository = typeOfEquipmentRepository;
        this.locationRepository = locationRepository;
    }

    //TODO @Hendrik Fehlermeldung Check
    @Transactional
    public ResponseEntity<EquipmentResponse> addEquipment( EquipmentCreate equipmentCreate ) {

        TypeOfEquipment typeOfEquipment = typeOfEquipmentRepository.findById(equipmentCreate.getTypeOfEquipmentId())
                .orElseThrow(() -> new IllegalArgumentException("Type of equipment not found"));

        Location location = locationRepository.findById(equipmentCreate.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));

        if (equipmentCreate.getName() == null || equipmentCreate.getDescription() == null ||
                equipmentCreate.getTypeOfEquipmentId() == null || equipmentCreate.getLocationId() == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        if (equipmentCreate.getWeight() <= 0 || equipmentCreate.getLength() <= 0 ||
                equipmentCreate.getWidth() <= 0 || equipmentCreate.getHeight() <= 0 ||
                equipmentCreate.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Equipment equipment = new Equipment(
                equipmentCreate.getName(),
                equipmentCreate.getDescription(),
                typeOfEquipment,
                equipmentCreate.getWeight(),
                equipmentCreate.getLength(),
                equipmentCreate.getWidth(),
                equipmentCreate.getHeight(),
                equipmentCreate.getDateOfPurchase(),
                location,
                equipmentCreate.getPrice()
        );

        Equipment savedEquipment = equipmentRepository.save(equipment);

        //TODO TypeOfEquipment Aktualisieren

        return ResponseEntity.accepted().body( new EquipmentResponse( savedEquipment ) );
    }


    public ResponseEntity<List<EquipmentResponse>> getAllEquipment() {

        List<Equipment> equipmentList = equipmentRepository.findAll();

        List<EquipmentResponse> responseList = equipmentList
                .stream()
                .map(EquipmentResponse::new)
                .toList();

        return ResponseEntity.status(200).body( responseList );
    }


    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByTypeOfEquipment( UUID typeOfEquipmentId ) {

        List<Equipment> equipmentList = equipmentRepository.findByTypeOfEquipmentId(typeOfEquipmentId);

        List<EquipmentResponse> responseList = equipmentList
                .stream().map(EquipmentResponse::new).toList();

        return ResponseEntity.status(200).body( responseList );
    }
}

















