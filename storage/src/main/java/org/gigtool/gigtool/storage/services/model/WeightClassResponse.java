package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.WeightClass;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeightClassResponse {
    private UUID id;
    private String name;
    private String description;
    private int weightStart;
    private int duration;
    private ArrayList<Equipment> equipments;

    public WeightClassResponse( WeightClass weightClass ) {
        id = weightClass.getId();
        name = weightClass.getName();
        description = weightClass.getDescription();
        weightStart = weightClass.getWeightStart();
        duration = weightClass.getDuration();
        equipments = new ArrayList<>();
    }
}
