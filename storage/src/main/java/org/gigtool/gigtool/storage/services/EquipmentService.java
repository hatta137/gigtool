package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.repositories.EquipmentRepository;
import org.gigtool.gigtool.storage.repositories.LocationRepository;
import org.gigtool.gigtool.storage.repositories.TypeOfEquipmentRepository;
import org.gigtool.gigtool.storage.services.model.AddressCreate;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.gigtool.gigtool.storage.services.model.EquipmentCreate;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
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

        TypeOfEquipment typeOfEquipment = typeOfEquipmentRepository.findById( equipmentCreate.getTypeOfEquipmentId() )
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

        Equipment savedEquipment = equipmentRepository.save( equipment );

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


    public ResponseEntity<EquipmentResponse> getEquipmentById( UUID id ) {

        Optional<Equipment> foundEquipment = equipmentRepository.findById( id );

        if (foundEquipment.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.accepted().body( new EquipmentResponse( foundEquipment.get() ));
    }


    public ResponseEntity<EquipmentResponse> updateEquipment( UUID id, EquipmentCreate equipmentCreate ) {

        Optional<Equipment> existingEquipment = equipmentRepository.findById( id );

        if (existingEquipment.isEmpty())
            throw new RuntimeException( "Address not found with id: " + id );

        Equipment equipmentToUpdate = existingEquipment.get();

        if ( equipmentCreate.getName() != null ) {
            equipmentToUpdate.setName(equipmentCreate.getName());
        }
        if ( equipmentCreate.getDescription() != null ) {
            equipmentToUpdate.setDescription(equipmentCreate.getDescription());
        } //TODO isPresent Check
        if ( equipmentCreate.getTypeOfEquipmentId() != null ) {
            equipmentToUpdate.setTypeOfEquipment( typeOfEquipmentRepository.findById(equipmentCreate.getTypeOfEquipmentId()).get());
        }
        if ( equipmentCreate.getWeight() > 0 ) {
            equipmentToUpdate.setWeight(equipmentCreate.getWeight());
        }
        if ( equipmentCreate.getLength() > 0 ) {
            equipmentToUpdate.setLength(equipmentCreate.getLength());
        }
        if ( equipmentCreate.getWidth() > 0 ) {
            equipmentToUpdate.setWidth(equipmentCreate.getWidth());
        }
        if ( equipmentCreate.getHeight() > 0 ) {
            equipmentToUpdate.setHeight(equipmentCreate.getHeight());
        }
        if ( equipmentCreate.getDateOfPurchase() != null ) {
            equipmentToUpdate.setDateOfPurchase(equipmentCreate.getDateOfPurchase());
        } // TODO isPresent cheeck
        if ( equipmentCreate.getLocationId() != null ) {
            equipmentToUpdate.setLocation( locationRepository.findById(equipmentCreate.getLocationId()).get());
        }
        if ( equipmentCreate.getPrice() > 0 ) {
            equipmentToUpdate.setPrice(equipmentCreate.getPrice());
        }

        Equipment savedEquipment = equipmentRepository.saveAndFlush( equipmentToUpdate );

        return ResponseEntity.ok().body( new EquipmentResponse( savedEquipment ));
    }

    // TODO @Hendrik Test ob Beziehungen aufgelöst werden nach löschen (TypeOfEquipment und Location)
    public ResponseEntity<EquipmentResponse> deleteEquipment( UUID id ) {

        Optional<Equipment> foundEquiupment = equipmentRepository.findById( id );

        if (foundEquiupment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Equipment equipmentToDelete = foundEquiupment.get();

        equipmentRepository.delete(equipmentToDelete);

        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByTypeOfEquipment( UUID typeOfEquipmentId ) {

        List<Equipment> equipmentList = equipmentRepository.findByTypeOfEquipmentId( typeOfEquipmentId );

        List<EquipmentResponse> responseList = equipmentList
                .stream().map(EquipmentResponse::new).toList();

        return ResponseEntity.status(200).body( responseList );
    }


    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByLocation( UUID locationId ) {

        List<Equipment> equipmentList = equipmentRepository.findByLocationId( locationId );

        List<EquipmentResponse> responseList = equipmentList
                .stream().map(EquipmentResponse::new).toList();

        return ResponseEntity.status(200).body( responseList );
    }
}

















