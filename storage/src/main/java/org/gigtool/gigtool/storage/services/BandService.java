package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.Genre;
import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.gigtool.gigtool.storage.repositories.BandRepository;
import org.gigtool.gigtool.storage.repositories.GenreRepository;
import org.gigtool.gigtool.storage.repositories.RoleInTheBandRepository;
import org.gigtool.gigtool.storage.services.model.BandCreate;
import org.gigtool.gigtool.storage.services.model.BandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BandService {

    private final BandRepository bandRepository;
    private final GenreRepository genreRepository;
    private final RoleInTheBandRepository roleInTheBandRepository;

    public BandService(BandRepository bandRepository, GenreRepository genreRepository, RoleInTheBandRepository roleInTheBandRepository) {
        this.bandRepository = bandRepository;
        this.genreRepository = genreRepository;
        this.roleInTheBandRepository = roleInTheBandRepository;
    }

    public ResponseEntity<BandResponse> createBand(BandCreate bandCreate){

        Band band = new Band();

        Optional<Genre> genre = genreRepository.findById(bandCreate.getGenre());

        if(genre.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Genre existingGenre = genre.get();

        Optional<RoleInTheBand> role = roleInTheBandRepository.findById(bandCreate.getMainRoleInTheBand());

        if(role.isEmpty()){
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

/*
    public ResponseEntity<BandResponse> addEquipment(UUID id, Equipment equipment) {

        if (equipment == null) {
            return ResponseEntity.badRequest().build();
        }

        //TODO @Dario Inventory gibts nicht mehr, da Datenbank
        //checken ob es das equipmentb allgemein schon gibt in der equipment tabbelle
*//*        if (!Inventory.getInstance().isEquipmentInInventory(equipment.getId())) {

            Inventory.getInstance().getEquipmentList().addEquipment(equipment);
        }*//*

        this.equipmentList.add(equipment);

        return this.equipmentList;
    }

    public ArrayList<Equipment> deleteEquipment(Equipment equipment){

        if (equipment == null) {

            throw new IllegalArgumentException("Equipment cannot be null.");
        }

        this.equipmentList.remove(equipment);

        return this.equipmentList;
    }

    public ArrayList<RoleInTheBand> addRoleInTheBand(RoleInTheBand roleInTheBand){

        this.listOfRole.add(roleInTheBand);

        return this.listOfRole;
    }

    public Optional<RoleInTheBand> getRoleInTheBand(int index){

        if (index >= 0 && index < listOfRole.size()){

            return Optional.of(this.listOfRole.get(index));
        }
        return Optional.empty();
    }

    public boolean deleteRoleInTheBand(RoleInTheBand roleInTheBand){

        if (this.listOfRole.isEmpty()){
            throw new NoSuchElementException("No listOfRole existing");
        }

        if (roleInTheBand == null){
            throw new NoSuchElementException("No such RoleInTheBand existing");
        }

        Iterator<RoleInTheBand> iterator = this.listOfRole.iterator();

        while (iterator.hasNext()) {

            RoleInTheBand role = iterator.next();

            if (roleInTheBand.getName().equals(role.getName())){

                iterator.remove();

                return true;
            }
        }
        return false;
    }*/


}
