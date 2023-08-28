package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.*;
import org.gigtool.gigtool.storage.services.model.EquipmentCreate;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Hendrik Lendeckel
 * Service class for managing equipment-related operations.
 */
@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final TypeOfEquipmentRepository typeOfEquipmentRepository;
    private final LocationRepository locationRepository;
    private final HappeningRepository happeningRepository;
    private final BandRepository bandRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository,
                            TypeOfEquipmentRepository typeOfEquipmentRepository,
                            LocationRepository locationRepository,
                            HappeningRepository happeningRepository,
                            BandRepository bandRepository) {
        this.equipmentRepository = equipmentRepository;
        this.typeOfEquipmentRepository = typeOfEquipmentRepository;
        this.locationRepository = locationRepository;
        this.happeningRepository = happeningRepository;
        this.bandRepository = bandRepository;
    }

    /**
     * Adds a new equipment.
     *
     * @param equipmentCreate The details of the equipment to be added.
     * @return A ResponseEntity containing the added equipment response or a bad request if input is invalid.
     */
    @Transactional
    public ResponseEntity<EquipmentResponse> addEquipment( EquipmentCreate equipmentCreate ) {

        if (equipmentCreate.getName() == null || equipmentCreate.getDescription() == null ||
                equipmentCreate.getTypeOfEquipmentId() == null || equipmentCreate.getLocationId() == null ||
                equipmentCreate.getWeight() <= 0 || equipmentCreate.getLength() <= 0 ||
                equipmentCreate.getWidth() <= 0 || equipmentCreate.getHeight() <= 0 ||
                equipmentCreate.getPrice() <= 0) {

            return ResponseEntity.badRequest().build();
        }

        Optional<TypeOfEquipment> typeOfEquipment = typeOfEquipmentRepository.findById( equipmentCreate.getTypeOfEquipmentId() );

        Optional<Location> location = locationRepository.findById(equipmentCreate.getLocationId());

        if (typeOfEquipment.isEmpty() || location.isEmpty())
            return ResponseEntity.notFound().build();

        Equipment equipment = new Equipment(
                equipmentCreate.getName(),
                equipmentCreate.getDescription(),
                typeOfEquipment.get(),
                equipmentCreate.getWeight(),
                equipmentCreate.getLength(),
                equipmentCreate.getWidth(),
                equipmentCreate.getHeight(),
                equipmentCreate.getDateOfPurchase(),
                location.get(),
                equipmentCreate.getPrice()
        );

        Equipment savedEquipment = equipmentRepository.save( equipment );

        return ResponseEntity.accepted().body( new EquipmentResponse( savedEquipment ) );
    }

    /**
     * Retrieves a list of all equipment.
     *
     * @return A ResponseEntity containing a list of equipment responses.
     */
    public ResponseEntity<List<EquipmentResponse>> getAllEquipment() {

        List<Equipment> equipmentList = equipmentRepository.findAll();

        List<EquipmentResponse> responseList = equipmentList
                .stream()
                .map(EquipmentResponse::new)
                .toList();

        return ResponseEntity.status(200).body( responseList );
    }

    /**
     * Retrieves equipment by its ID.
     *
     * @param id The ID of the equipment to retrieve.
     * @return A ResponseEntity containing the equipment response or not found if the equipment doesn't exist.
     */
    public ResponseEntity<EquipmentResponse> getEquipmentById( UUID id ) {

        Optional<Equipment> foundEquipment = equipmentRepository.findById( id );

        if (foundEquipment.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.accepted().body( new EquipmentResponse( foundEquipment.get() ));
    }

    /**
     * Updates an existing equipment.
     *
     * @param id               The ID of the equipment to update.
     * @param equipmentCreate The updated equipment details.
     * @return A ResponseEntity containing the updated equipment response or not found if the equipment doesn't exist.
     */
    public ResponseEntity<EquipmentResponse> updateEquipment( UUID id, EquipmentCreate equipmentCreate ) {

        Optional<Equipment> existingEquipment = equipmentRepository.findById( id );

        if (existingEquipment.isEmpty())
            return ResponseEntity.notFound().build();

        Equipment equipmentToUpdate = existingEquipment.get();

        UUID typeOfEquipmentId = equipmentCreate.getTypeOfEquipmentId();
        UUID locationId = equipmentCreate.getLocationId();


        if ( equipmentCreate.getName() != null ) {
            equipmentToUpdate.setName(equipmentCreate.getName());
        }
        if ( equipmentCreate.getDescription() != null ) {
            equipmentToUpdate.setDescription(equipmentCreate.getDescription());
        }
        if (typeOfEquipmentId != null) {
            Optional<TypeOfEquipment> typeOfEquipment = typeOfEquipmentRepository.findById(typeOfEquipmentId);
            if (typeOfEquipment.isPresent()) {
                equipmentToUpdate.setTypeOfEquipment(typeOfEquipment.get());
            }
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
        }
        if (locationId != null) {
            Optional<Location> location = locationRepository.findById(locationId);
            if (location.isPresent()) {
                equipmentToUpdate.setLocation(location.get());
            }
        }
        if ( equipmentCreate.getPrice() > 0.0f ) {
            equipmentToUpdate.setPrice(equipmentCreate.getPrice());
        }

        Equipment savedEquipment = equipmentRepository.saveAndFlush( equipmentToUpdate );

        return ResponseEntity.ok().body( new EquipmentResponse( savedEquipment ));
    }

    /**
     * Deletes an existing equipment.
     *
     * @param id The ID of the equipment to delete.
     * @return A ResponseEntity indicating the success of the deletion or not found if the equipment doesn't exist.
     */
    public ResponseEntity<EquipmentResponse> deleteEquipment( UUID id ) {

        Optional<Equipment> foundEquipment = equipmentRepository.findById( id );

        if (foundEquipment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Happening> happeningWithEquipment = happeningRepository.findByEquipmentId( id );
        List<Band> bandWithEquipment = bandRepository.findByEquipmentId( id );

        if (!happeningWithEquipment.isEmpty() || !bandWithEquipment.isEmpty())
            return ResponseEntity.badRequest().build();

        Equipment equipmentToDelete = foundEquipment.get();

        equipmentRepository.delete(equipmentToDelete);

        return ResponseEntity.accepted().build();
    }

    /**
     * Retrieves a list of equipment by a specific type of equipment.
     *
     * @param typeOfEquipmentId The ID of the type of equipment.
     * @return A ResponseEntity containing a list of equipment responses or not found if no equipment exists for the given type.
     */
    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByTypeOfEquipment( UUID typeOfEquipmentId ) {

        List<Equipment> equipmentList = equipmentRepository.findByTypeOfEquipmentId( typeOfEquipmentId );

        if (equipmentList.isEmpty())
            return ResponseEntity.notFound().build();

        List<EquipmentResponse> responseList = equipmentList
                .stream().map(EquipmentResponse::new).toList();

        return ResponseEntity.status(200).body( responseList );
    }

    /**
     * Retrieves a list of equipment by a specific location.
     *
     * @param locationId The ID of the location.
     * @return A ResponseEntity containing a list of equipment responses or not found if no equipment exists for the given location.
     */
    public ResponseEntity<List<EquipmentResponse>> getAllEquipmentByLocation( UUID locationId ) {

        List<Equipment> equipmentList = equipmentRepository.findByLocationId( locationId );

        if (equipmentList.isEmpty())
            return ResponseEntity.notFound().build();

        List<EquipmentResponse> responseList = equipmentList
                .stream().map(EquipmentResponse::new).toList();

        return ResponseEntity.status(200).body( responseList );
    }
}