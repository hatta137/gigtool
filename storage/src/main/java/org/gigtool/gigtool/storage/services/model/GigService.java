package org.gigtool.gigtool.storage.services.model;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GigService {

    private final GigRepository gigRepository;
    private final AddressRepository addressRepository;
    private final TypeOfGigRepository typeOfGigRepository;
    private final BandRepository bandRepository;

    private final EquipmentRepository equipmentRepository;

    public GigService (GigRepository gigRepository, AddressRepository addressRepository, TypeOfGigRepository typeOfGigRepository, BandRepository bandRepository, EquipmentRepository equipmentRepository) {
        this.gigRepository = gigRepository;
        this.addressRepository = addressRepository;
        this.typeOfGigRepository = typeOfGigRepository;
        this.bandRepository = bandRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public ResponseEntity<GigResponse> addGig(GigCreate gigCreate){
        if (gigCreate.getName() == null || gigCreate.getStartTime() == null || gigCreate.getEndTime() == null ||
                gigCreate.getDescription() == null || gigCreate.getAddress() == null ||
                gigCreate.getTypeOfGig() == null || gigCreate.getBand() == null) {
            return ResponseEntity.badRequest().build();
        }

        Gig gig = new Gig();

        Optional<Address> addressOptional = addressRepository.findById(gigCreate.getAddress());

        if (addressOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Address address = addressOptional.get();

        Optional<TypeOfGig> typeOfGigOptional = typeOfGigRepository.findById(gigCreate.getTypeOfGig());

        if (typeOfGigOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfGig typeOfGig = typeOfGigOptional.get();

        Optional<Band> bandOptional = bandRepository.findById(gigCreate.getBand());

        if (bandOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Band band = bandOptional.get();

        gig.setAddress(address);
        gig.setTypeOfGig(typeOfGig);
        gig.setBand(band);
        gig.setName(gigCreate.getName());
        gig.setStartTime(gigCreate.getStartTime());
        gig.setEndTime(gigCreate.getEndTime());
        gig.setDescription(gigCreate.getDescription());

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

        if (gig.getEquipmentList().contains(equipment)) {
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



}
