package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.*;
import org.gigtool.gigtool.storage.services.model.BandCreate;
import org.gigtool.gigtool.storage.services.model.BandResponse;
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
public class BandService {

    private final BandRepository bandRepository;
    private final GenreRepository genreRepository;
    private final RoleInTheBandRepository roleInTheBandRepository;
    private final EquipmentRepository equipmentRepository;
    private final HappeningRepository happeningRepository;
    private final GigRepository gigRepository;

    public BandService(BandRepository bandRepository, GenreRepository genreRepository, RoleInTheBandRepository roleInTheBandRepository, EquipmentRepository equipmentRepository, HappeningRepository happeningRepository, GigRepository gigRepository) {
        this.bandRepository = bandRepository;
        this.genreRepository = genreRepository;
        this.roleInTheBandRepository = roleInTheBandRepository;
        this.equipmentRepository = equipmentRepository;
        this.happeningRepository = happeningRepository;
        this.gigRepository = gigRepository;
    }

    private boolean equiptmentIsUseSameTimeLikeBandGigs(Band band, Equipment equipment){
        List<Gig> bandGigs = gigRepository.findGigsByBand(band);
        for (Gig bandGig: bandGigs) {
            List<Happening> overlappingHappenings = happeningRepository.findOverlappingHappeningsWithEquipment(bandGig.getStartTime(), bandGig.getEndTime(), equipment);
            if(!overlappingHappenings.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<BandResponse> addBand(BandCreate bandCreate){

        if (bandCreate.getName() == null || bandCreate.getGenre() == null || bandCreate.getMainRoleInTheBand() == null) {
            return ResponseEntity.badRequest().build();
        }

        Band band = new Band();

        Optional<Genre> genre = genreRepository.findById(bandCreate.getGenre());

        if(genre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Genre existingGenre = genre.get();

        Optional<RoleInTheBand> role = roleInTheBandRepository.findById(bandCreate.getMainRoleInTheBand());

        if(role.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RoleInTheBand existingRole = role.get();

        List<RoleInTheBand> roleInTheBandList = new ArrayList<>();
        roleInTheBandList.add(existingRole);

        band.setGenre(existingGenre);
        band.setListOfRole(roleInTheBandList);
        band.setName(bandCreate.getName());
        band.setEquipmentList(new ArrayList<>());

        Band savedBand = bandRepository.saveAndFlush(band);

        return ResponseEntity.ok(new BandResponse(savedBand));
    }

    public ResponseEntity<List<BandResponse>> getAllBands() {

        List<Band> bandList = bandRepository.findAll();

        List<BandResponse> responseList = bandList.stream()
                .map(BandResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<BandResponse> getBandById(UUID bandId) {
        Optional<Band> bandOptional = bandRepository.findById(bandId);

        return bandOptional.map(band -> ResponseEntity.ok(new BandResponse(band)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<BandResponse> addEquipmentToBand(UUID bandId, UUID equipmentId) {

        if ((equipmentId == null) || (bandId == null)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Band> existingBand = bandRepository.findById(bandId);
        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);

        if(existingEquipment.isEmpty() || existingBand.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Band band = existingBand.get();
        Equipment equipment = existingEquipment.get();

        if (band.getEquipmentList().contains(equipment) || this.equiptmentIsUseSameTimeLikeBandGigs(band, equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        band.getEquipmentList().add(equipment);

        Band savedBand = bandRepository.saveAndFlush(band);

        return ResponseEntity.ok(new BandResponse(savedBand));

    }

    public ResponseEntity<BandResponse> updateBand(UUID bandId, BandCreate bandRequest) {

        if ((bandId == null)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Band> existingBand = bandRepository.findById(bandId);

        if (existingBand.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Band updatedBand = existingBand.get();

        if (bandRequest.getName() != null) {
            updatedBand.setName(bandRequest.getName());
        }

        if (bandRequest.getGenre() != null) {

            Optional<Genre> existingGenre = genreRepository.findById(bandRequest.getGenre());

            if (existingGenre.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            updatedBand.setGenre(existingGenre.get());
        }

        Band savedBand = bandRepository.saveAndFlush(updatedBand);

        return ResponseEntity.ok(new BandResponse(savedBand));
    }

    public ResponseEntity<BandResponse> deleteEquipmentFromBand(UUID bandId, UUID equipmentId) {

        if ((equipmentId == null) || (bandId == null)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Band> existingBand = bandRepository.findById(bandId);

        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);

        if(existingEquipment.isEmpty() || existingBand.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Band band = existingBand.get();
        Equipment equipment = existingEquipment.get();

        if (!band.getEquipmentList().contains(equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        band.getEquipmentList().remove(equipment);

        Band savedBand = bandRepository.saveAndFlush(band);

        return ResponseEntity.ok(new BandResponse(savedBand));

    }

    public ResponseEntity<BandResponse> addRoleToBand(UUID bandId, UUID roleId) {

        if ((roleId == null) || (bandId == null)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Band> existingBand = bandRepository.findById(bandId);

        Optional<RoleInTheBand> existingRole = roleInTheBandRepository.findById(roleId);

        if(existingRole.isEmpty() || existingBand.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Band band = existingBand.get();
        RoleInTheBand role = existingRole.get();

        if (band.getListOfRole().contains(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        band.getListOfRole().add(role);

        Band savedBand = bandRepository.saveAndFlush(band);

        return ResponseEntity.ok(new BandResponse(savedBand));

    }

    public ResponseEntity<BandResponse> deleteRoleFromBand(UUID bandId, UUID roleId) {

        if ((roleId == null) || (bandId == null)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Band> existingBand = bandRepository.findById(bandId);

        Optional<RoleInTheBand> existingRole = roleInTheBandRepository.findById(roleId);

        if(existingRole.isEmpty() || existingBand.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Band band = existingBand.get();
        RoleInTheBand role = existingRole.get();

        if (!band.getListOfRole().contains(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        band.getListOfRole().remove(role);

        Band savedBand = bandRepository.saveAndFlush(band);

        return ResponseEntity.ok(new BandResponse(savedBand));

    }


    public ResponseEntity<String> deleteBand(UUID bandId) {

        if (bandId == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<Band> existingBand = bandRepository.findById(bandId);

        if (existingBand.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int gigsForBand = bandRepository.countGigsForBand(bandId);

        if (gigsForBand > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Band has a relation to Gigs. You cannot delete this Band!");
        }

        bandRepository.delete(existingBand.get());

        return ResponseEntity.ok("Band is deleted");
    }

}
