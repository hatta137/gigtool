package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.*;
import org.gigtool.gigtool.storage.services.model.RentalCreate;
import org.gigtool.gigtool.storage.services.model.RentalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final AddressRepository addressRepository;
    private final EquipmentRepository equipmentRepository;
    private final HappeningRepository happeningRepository;

    public RentalService(RentalRepository rentalRepository, AddressRepository addressRepository, EquipmentRepository equipmentRepository, HappeningRepository happeningRepository) {
        this.rentalRepository = rentalRepository;
        this.addressRepository = addressRepository;
        this.equipmentRepository = equipmentRepository;
        this.happeningRepository = happeningRepository;
    }



    private boolean equipmentIsOverlapping(LocalDateTime startTime, LocalDateTime endTime, Equipment equipment) {
        List<Happening> overlappingHappenings = happeningRepository.findOverlappingHappeningsWithEquipment(startTime, endTime, equipment);
        return !overlappingHappenings.isEmpty();
    }
    
    private boolean equiptmentlistIsOverlapping( LocalDateTime startTime, LocalDateTime endTime, Rental rental) {
        for (Equipment equipment: rental.getEquipmentList()) {
            List<Happening> overlappingHappenings = happeningRepository.findOverlappingHappeningsWithEquipment(startTime, endTime, equipment);
            System.out.println(equipment);
            if(!overlappingHappenings.isEmpty()){
                System.out.println(overlappingHappenings);
                if(!(overlappingHappenings.size() == 1 && overlappingHappenings.contains(rental))){
                    return true;
                }
            }
        }
        return false;
    }

    public ResponseEntity<RentalResponse> addRental(RentalCreate rentalCreate) {
        if (rentalCreate.getName() == null || rentalCreate.getStartTime() == null || rentalCreate.getEndTime() == null ||
                rentalCreate.getDescription() == null || rentalCreate.getAddress() == null ||
                rentalCreate.getResponsiblePerson() == null) {
            return ResponseEntity.badRequest().build();
        }


        Rental rental = new Rental();

        Optional<Address> addressOptional = addressRepository.findById(rentalCreate.getAddress());

        if (addressOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Address address = addressOptional.get();

        rental.setAddress(address);
        rental.setName(rentalCreate.getName());
        rental.setStartTime(rentalCreate.getStartTime());
        rental.setEndTime(rentalCreate.getEndTime());
        rental.setDescription(rentalCreate.getDescription());
        rental.setResponsiblePerson(rentalCreate.getResponsiblePerson());
        rental.setEquipmentList(new ArrayList<>());

        Rental savedRental = rentalRepository.saveAndFlush(rental);

        return ResponseEntity.ok(new RentalResponse(savedRental));
    }

    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        List<Rental> rentalList = rentalRepository.findAll();

        List<RentalResponse> responseList = rentalList.stream()
                .map(RentalResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<RentalResponse> getRentalById(UUID rentalId) {
        Optional<Rental> rentalOptional = rentalRepository.findById(rentalId);

        return rentalOptional.map(rental -> ResponseEntity.ok(new RentalResponse(rental)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<RentalResponse> updateRental(UUID rentalId, RentalCreate rentalRequest) {
        if (rentalId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Rental> existingRental = rentalRepository.findById(rentalId);

        if (existingRental.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Rental updatedRental = existingRental.get();

        if (rentalRequest.getName() != null) {
            updatedRental.setName(rentalRequest.getName());
        }

        if (rentalRequest.getStartTime() != null && rentalRequest.getEndTime() == null) {
            if (equiptmentlistIsOverlapping(rentalRequest.getStartTime(), updatedRental.getEndTime(), updatedRental)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            updatedRental.setStartTime(rentalRequest.getStartTime());
        }

        if (rentalRequest.getEndTime() != null && rentalRequest.getStartTime() == null) {
            if (equiptmentlistIsOverlapping(updatedRental.getStartTime(), rentalRequest.getEndTime(), updatedRental)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            updatedRental.setEndTime(rentalRequest.getEndTime());
        }

        if (rentalRequest.getStartTime() != null && rentalRequest.getEndTime() != null) {
            if (equiptmentlistIsOverlapping(rentalRequest.getStartTime(), rentalRequest.getEndTime(), updatedRental)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            updatedRental.setStartTime(rentalRequest.getStartTime());
            updatedRental.setEndTime(rentalRequest.getEndTime());
        }

        if (rentalRequest.getDescription() != null) {
            updatedRental.setDescription(rentalRequest.getDescription());
        }

        if (rentalRequest.getAddress() != null) {
            Optional<Address> existingAddress = addressRepository.findById(rentalRequest.getAddress());
            if (existingAddress.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            updatedRental.setAddress(existingAddress.get());
        }

        if (rentalRequest.getResponsiblePerson() != null) {
            updatedRental.setResponsiblePerson(rentalRequest.getResponsiblePerson());
        }

        Rental savedRental = rentalRepository.saveAndFlush(updatedRental);

        return ResponseEntity.ok(new RentalResponse(savedRental));
    }

    public ResponseEntity<RentalResponse> addEquipmentToRental(UUID rentalId, UUID equipmentId) {
        if (rentalId == null || equipmentId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Rental> existingRental = rentalRepository.findById(rentalId);
        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);

        if (existingRental.isEmpty() || existingEquipment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Rental rental = existingRental.get();
        Equipment equipment = existingEquipment.get();

        if (rental.getEquipmentList().contains(equipment) || equipmentIsOverlapping(rental.getStartTime(), rental.getEndTime(), equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        rental.getEquipmentList().add(equipment);

        Rental savedRental = rentalRepository.saveAndFlush(rental);

        return ResponseEntity.ok(new RentalResponse(savedRental));
    }

    public ResponseEntity<RentalResponse> deleteEquipmentFromRental(UUID rentalId, UUID equipmentId) {
        if (rentalId == null || equipmentId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Rental> existingRental = rentalRepository.findById(rentalId);
        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);

        if (existingRental.isEmpty() || existingEquipment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Rental rental = existingRental.get();
        Equipment equipment = existingEquipment.get();

        if (!rental.getEquipmentList().contains(equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        rental.getEquipmentList().remove(equipment);

        Rental savedRental = rentalRepository.saveAndFlush(rental);

        return ResponseEntity.ok(new RentalResponse(savedRental));
    }

    public ResponseEntity<String> deleteRental(UUID rentalId) {
        if (rentalId == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<Rental> existingRental = rentalRepository.findById(rentalId);

        if (existingRental.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        rentalRepository.delete(existingRental.get());

        return ResponseEntity.ok("Rental is deleted");
    }


}
