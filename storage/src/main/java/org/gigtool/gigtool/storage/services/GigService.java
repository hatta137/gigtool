package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.*;
import org.gigtool.gigtool.storage.services.model.GigCreate;
import org.gigtool.gigtool.storage.services.model.GigResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
//TODO @Max Comments
@Service
public class GigService {

    private final GigRepository gigRepository;
    private final AddressRepository addressRepository;
    private final TypeOfGigRepository typeOfGigRepository;
    private final BandRepository bandRepository;
    private final EquipmentRepository equipmentRepository;
    private final HappeningRepository happeningRepository;

    public GigService (GigRepository gigRepository, AddressRepository addressRepository, TypeOfGigRepository typeOfGigRepository, BandRepository bandRepository, EquipmentRepository equipmentRepository, HappeningRepository happeningRepository) {
        this.gigRepository = gigRepository;
        this.addressRepository = addressRepository;
        this.typeOfGigRepository = typeOfGigRepository;
        this.bandRepository = bandRepository;
        this.equipmentRepository = equipmentRepository;
        this.happeningRepository = happeningRepository;
    }

    private boolean gigIsOverlapping(LocalDateTime startTime, LocalDateTime endTime) {
        List<Gig> overlappingGigs = gigRepository.findOverlappingGigs(startTime, endTime);
        return !overlappingGigs.isEmpty();
    }

    private boolean gigIsOverlapping(LocalDateTime startTime, LocalDateTime endTime, Gig excludedGig){
        List<Gig> overlappingGigs = gigRepository.findOverlappingGigs(startTime, endTime);

        if (overlappingGigs.isEmpty()) {
            return false;
        }

        return !(overlappingGigs.size() == 1 && overlappingGigs.contains(excludedGig));
    }

    private boolean equipmentIsOverlapping(LocalDateTime startTime, LocalDateTime endTime, Equipment equipment){
        List<Happening> overlappingHappenings = happeningRepository.findOverlappingHappeningsWithEquipment(startTime, endTime, equipment);
        return !overlappingHappenings.isEmpty();
    }

    private boolean equiptmentlistIsOverlapping( LocalDateTime startTime, LocalDateTime endTime, Gig gig) {
        for (Equipment equipment: gig.getEquipmentList()) {
            List<Happening> overlappingHappenings = happeningRepository.findOverlappingHappeningsWithEquipment(startTime, endTime, equipment);
            if(!overlappingHappenings.isEmpty()){
                if(!(overlappingHappenings.size() == 1 && overlappingHappenings.contains(gig))){
                    return true;
                }
            }
        }
        return false;
    }

    public ResponseEntity<GigResponse> addGig(GigCreate gigCreate){
        if (gigCreate.getName() == null || gigCreate.getStartTime() == null || gigCreate.getEndTime() == null ||
                gigCreate.getDescription() == null || gigCreate.getAddress() == null ||
                gigCreate.getTypeOfGig() == null || gigCreate.getBand() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (gigIsOverlapping(gigCreate.getStartTime(), gigCreate.getEndTime()))
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Gig gig = new Gig();

        Optional<Address> addressOptional = addressRepository.findById(gigCreate.getAddress());
        Optional<TypeOfGig> typeOfGigOptional = typeOfGigRepository.findById(gigCreate.getTypeOfGig());
        Optional<Band> bandOptional = bandRepository.findById(gigCreate.getBand());

        if (addressOptional.isEmpty() || typeOfGigOptional.isEmpty() || bandOptional.isEmpty())
            return ResponseEntity.notFound().build();

        Address address = addressOptional.get();
        TypeOfGig typeOfGig = typeOfGigOptional.get();
        Band band = bandOptional.get();

        gig.setAddress(address);
        gig.setTypeOfGig(typeOfGig);
        gig.setBand(band);
        gig.setName(gigCreate.getName());
        gig.setStartTime(gigCreate.getStartTime());
        gig.setEndTime(gigCreate.getEndTime());
        gig.setDescription(gigCreate.getDescription());
        gig.setEquipmentList(new ArrayList<>());

        Gig savedGig = gigRepository.saveAndFlush(gig);

        return ResponseEntity.ok(new GigResponse(savedGig));
    }

    public ResponseEntity<List<GigResponse>> getAllGigs() {
        List<Gig> gigList = gigRepository.findAll();

        List<GigResponse> responseList = gigList.stream()
                .map(GigResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<GigResponse> getGigById(UUID gigId) {
        Optional<Gig> gigOptional = gigRepository.findById(gigId);

        return gigOptional.map(gig -> ResponseEntity.ok(new GigResponse(gig)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<GigResponse> updateGig(UUID gigId, GigCreate gigRequest) {
        if (gigId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Gig> existingGig = gigRepository.findById(gigId);

        if (existingGig.isEmpty())
            return ResponseEntity.notFound().build();

        Gig updatedGig = existingGig.get();

        if (gigRequest.getName() != null)
            updatedGig.setName(gigRequest.getName());

        if (gigRequest.getStartTime() != null && gigRequest.getEndTime() == null) {
            if (gigIsOverlapping(gigRequest.getStartTime(), updatedGig.getEndTime(), updatedGig) || equiptmentlistIsOverlapping(gigRequest.getStartTime(), updatedGig.getEndTime(), updatedGig)){
                return ResponseEntity.badRequest().build();
            }
            updatedGig.setStartTime(gigRequest.getStartTime());
        }

        if (gigRequest.getEndTime() != null && gigRequest.getStartTime() == null) {
            if (gigIsOverlapping(updatedGig.getStartTime(), gigRequest.getEndTime(), updatedGig) || equiptmentlistIsOverlapping(updatedGig.getStartTime(), gigRequest.getEndTime(), updatedGig)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            updatedGig.setEndTime(gigRequest.getEndTime());
        }

        if (gigRequest.getStartTime() != null && gigRequest.getEndTime() != null) {
            if (gigIsOverlapping(gigRequest.getStartTime(), gigRequest.getEndTime(), updatedGig) || equiptmentlistIsOverlapping(gigRequest.getStartTime(), gigRequest.getEndTime(), updatedGig)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            updatedGig.setStartTime(gigRequest.getStartTime());
            updatedGig.setEndTime(gigRequest.getEndTime());
        }

        if (gigRequest.getDescription() != null)
            updatedGig.setDescription(gigRequest.getDescription());


        if (gigRequest.getAddress() != null) {
            Optional<Address> existingAddress = addressRepository.findById(gigRequest.getAddress());
            if (existingAddress.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            updatedGig.setAddress(existingAddress.get());
        }

        if (gigRequest.getTypeOfGig() != null) {
            Optional<TypeOfGig> existingTypeOfGig = typeOfGigRepository.findById(gigRequest.getTypeOfGig());
            if (existingTypeOfGig.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            updatedGig.setTypeOfGig(existingTypeOfGig.get());
        }

        if (gigRequest.getBand() != null) {
            Optional<Band> existingBand = bandRepository.findById(gigRequest.getBand());
            if (existingBand.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            updatedGig.setBand(existingBand.get());
        }

        Gig savedGig = gigRepository.saveAndFlush(updatedGig);

        return ResponseEntity.ok(new GigResponse(savedGig));
    }


    public ResponseEntity<GigResponse> addEquipmentToGig(UUID gigId, UUID equipmentId) {

        if (gigId == null || equipmentId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Gig> existingGig = gigRepository.findById(gigId);
        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);

        if (existingGig.isEmpty() || existingEquipment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Gig gig = existingGig.get();
        Equipment equipment = existingEquipment.get();

        if (gig.getEquipmentList().contains(equipment) || gig.getBand().getEquipmentList().contains(equipment) || equipmentIsOverlapping(gig.getStartTime(), gig.getEndTime(), equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        gig.getEquipmentList().add(equipment);

        Gig savedGig = gigRepository.saveAndFlush(gig);

        return ResponseEntity.ok(new GigResponse(savedGig));
    }

    public ResponseEntity<GigResponse> deleteEquipmentFromGig(UUID gigId, UUID equipmentId) {
        if (gigId == null || equipmentId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Gig> existingGig = gigRepository.findById(gigId);
        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);

        if (existingGig.isEmpty() || existingEquipment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Gig gig = existingGig.get();
        Equipment equipment = existingEquipment.get();

        if (!gig.getEquipmentList().contains(equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        gig.getEquipmentList().remove(equipment);

        Gig savedGig = gigRepository.saveAndFlush(gig);

        return ResponseEntity.ok(new GigResponse(savedGig));
    }

    public ResponseEntity<String> deleteGig(UUID gigId) {
        if (gigId == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<Gig> existingGig = gigRepository.findById(gigId);

        if (existingGig.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        gigRepository.delete(existingGig.get());

        return ResponseEntity.ok("Gig is deleted");
    }
}
