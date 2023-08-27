package org.gigtool.gigtool.storage.services.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentCreate {

    private String name;
    private String description;
    private UUID typeOfEquipmentId;
    private int weight;
    private int length;
    private int width;
    private int height;
    private LocalDate dateOfPurchase;
    private UUID locationId;
    private float price;

    public EquipmentCreate( String name ) {
        this.name = name;
    }

    public EquipmentCreate( UUID typeOfEquipmentId ) {
        this.typeOfEquipmentId = typeOfEquipmentId;
    }

    public EquipmentCreate( int width ) {
        this.width = width;
    }
}
