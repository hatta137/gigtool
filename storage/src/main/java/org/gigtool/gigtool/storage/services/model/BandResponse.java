package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Band;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BandResponse {
    private UUID id;
    private String name;
    private GenreResponse genre;
    private List<RoleInTheBandResponse> listOfRole;
    private List<EquipmentResponse> equipmentList;

    public BandResponse (Band band) {
        this.id = band.getId();
        this.name = band.getName();
        this.genre = new GenreResponse(band.getGenre());
        this.listOfRole = band.getListOfRole().stream().map(RoleInTheBandResponse::new).toList();
        this.equipmentList = band.getEquipmentList().stream().map(EquipmentResponse::new).toList();
    }

}
