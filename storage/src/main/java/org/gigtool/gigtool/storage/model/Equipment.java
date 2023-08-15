package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/***
 * Author: Hendrik Lendeckel
 * This class represents the equipment with all necessary properties.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private TypeOfEquipment typeOfEquipment;
    private int weight;
    private int length;
    private int width;
    private int height;
    private LocalDate dateOfPurchase;
    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;
    private float price;


    /**
     *
     * @param name Name of the equipment
     * @param description of the equipment
     * @param typeOfEquipment which type it is
     * @param weight of the equipment
     * @param dateOfPurchase of the equipment
     * @param location of the equipment
     * @param price of the equipment
     */

    public Equipment(
            String name,
            String description,
            TypeOfEquipment typeOfEquipment,
            int weight, int length, int width, int height,
            LocalDate dateOfPurchase,
            Location location,
            float price) {

        this.name = name;
        this.description = description;
        this.typeOfEquipment = typeOfEquipment;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.dateOfPurchase = dateOfPurchase;
        this.location = location;
        this.price = price;
    }


    // TODO @Hendrik To Happening Service getTotalVolume
    public int getCuboidVolume(){
        return this.height * this.length * this.width;
    }
}
