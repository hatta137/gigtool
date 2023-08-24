package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Genre;
import org.gigtool.gigtool.storage.model.RoleInTheBand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BandResponse {
    private UUID id;
    private String name;
    private Genre genre;
    private List<RoleInTheBand> listOfRole;
    private List<Equipment> equipmentList;

    public BandResponse (Band band){
        this.id = band.getId();
        this.name = band.getName();
        this.genre = band.getGenre();
        this.listOfRole = band.getListOfRole();
        this.equipmentList = band.getEquipmentList();
    }
/*
alternative nur ids?
    TODO welche variante?
    private UUID id;
    private String name;
    private UUID genre;
    private List<UUID> listOfRole;
    private List<UUID> equipmentList;

    public BandResponse (Band band){
        this.id = band.getId();
        this.name = band.getName();
        this.genre = band.getGenre().getId();
        this.listOfRole = band.getListOfRole().stream()
                .map(RoleInTheBand::getId)
                .toList();
        this.equipmentList = band.getEquipmentList().stream()
                .map(Equipment::getId)
                .toList();
    }*/
}
