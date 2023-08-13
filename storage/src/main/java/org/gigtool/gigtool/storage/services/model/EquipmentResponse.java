package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.gigtool.gigtool.storage.model.WeightClass;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentResponse {

    private UUID id;
    private String name;
    private String description;
    private TypeOfEquipmentResponse typeOfEquipmentResponse;
    private int weight;
    private WeightClass weightClass;
    private int length;
    private int width;
    private int height;
    private LocalDate dateOfPurchase;
    private LocationResponse locationResponse;
    private float price;

    //TODO @Hendrik fertigstellen. unterbrochen wegen weightclass und weightclass List
    public EquipmentResponse( Equipment equipment ) {
        id = equipment.getId();
        name = equipment.getName();
        description = equipment.getDescription();
        typeOfEquipmentResponse = new TypeOfEquipmentResponse( equipment.getTypeOfEquipment() );
        weight = equipment.getWeight();


    }
}
