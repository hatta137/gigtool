package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.services.Calc;

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

    private int equipmentID;
    private String name;
    private String description;
    @ManyToOne
    private TypeOfEquipment typeOfEquipment;
    private int weight;
    @ManyToOne
    private WeightClass weightClass;
    @ManyToOne
    private Dimension dimension;
    private LocalDate dateOfPurchase;
    @ManyToOne
    private Location location;
    private float price;


    /**
     *
     * @param name Name of the equipment
     * @param description of the equipment
     * @param typeOfEquipment which type it is
     * @param weight of the equipment
     * @param dimension the dimension
     * @param dateOfPurchase of the equipment
     * @param location of the equipment
     * @param price of the equipment
     * @param weightClassList the actual weight class list
     */
    public Equipment(String name, String description, TypeOfEquipment typeOfEquipment, int weight, Dimension dimension, LocalDate dateOfPurchase, Location location, float price, WeightClassList weightClassList) {
        this.name = name;
        this.description = description;
        this.typeOfEquipment = typeOfEquipment;
        this.weight = weight;
        // is Present check
        if (Calc.calcActualWeightClass(weightClassList, weight).isPresent())
            this.weightClass = Calc.calcActualWeightClass(weightClassList, weight).get();
        else
            this.weightClass = weightClassList.getBiggestWeightClass();
        this.dimension = dimension;
        this.dateOfPurchase = dateOfPurchase;
        this.location = location;
        this.price = price;
    }

    /***
     * Constructor without the optional param dateOfPurchase
     *
     */
    public Equipment(String name, String description, TypeOfEquipment typeOfEquipment, int weight, Dimension dimension, Location location, float price, WeightClassList weightClassList) {
        this.name = name;
        this.description = description;
        this.typeOfEquipment = typeOfEquipment;
        this.weight = weight;
        this.weightClass = Calc.calcActualWeightClass(weightClassList, weight).get();
        this.dimension = dimension;
        this.location = location;
        this.price = price;
    }
}
