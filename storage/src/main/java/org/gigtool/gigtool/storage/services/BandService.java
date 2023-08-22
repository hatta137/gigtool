package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Genre;
import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.gigtool.gigtool.storage.repositories.BandRepository;
import org.gigtool.gigtool.storage.repositories.EquipmentRepository;
import org.gigtool.gigtool.storage.repositories.GenreRepository;
import org.gigtool.gigtool.storage.repositories.RoleInTheBandRepository;
import org.gigtool.gigtool.storage.services.model.BandCreate;
import org.gigtool.gigtool.storage.services.model.BandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BandService {

    private final BandRepository bandRepository;
    private final GenreRepository genreRepository;
    private final RoleInTheBandRepository roleInTheBandRepository;

    private final EquipmentRepository equipmentRepository;

    public BandService(BandRepository bandRepository, GenreRepository genreRepository, RoleInTheBandRepository roleInTheBandRepository, EquipmentRepository equipmentRepository) {
        this.bandRepository = bandRepository;
        this.genreRepository = genreRepository;
        this.roleInTheBandRepository = roleInTheBandRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public ResponseEntity<BandResponse> addBand(BandCreate bandCreate){

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

    public ResponseEntity<BandResponse> addEquipment(UUID bandId, UUID equipmentId) {

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

        if (band.getEquipmentList().contains(equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        band.getEquipmentList().add(equipment);

        Band savedBand = bandRepository.saveAndFlush(band);

        return ResponseEntity.ok(new BandResponse(savedBand));

    }

    public ResponseEntity<BandResponse> deleteEquipment(UUID bandId, UUID equipmentId) {

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

    public ResponseEntity<BandResponse> addRole(UUID bandId, UUID roleId) {

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

    public ResponseEntity<BandResponse> deleteRole(UUID bandId, UUID roleId) {

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
