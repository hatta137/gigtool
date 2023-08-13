package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.services.Calc;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
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
    @ManyToOne
    private TypeOfEquipment typeOfEquipment;
    private int weight;
    @ManyToOne
    private WeightClass weightClass;
    private int length;
    private int width;
    private int height;
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
     * @param dateOfPurchase of the equipment
     * @param location of the equipment
     * @param price of the equipment
     * @param weightClassList the actual weight class list
     */

    public Equipment(
            String name,
            String description,
            TypeOfEquipment typeOfEquipment,
            int weight, int length, int width, int height,
            LocalDate dateOfPurchase,
            Location location,
            float price, Calc calc,
            WeightClassList weightClassList) {

        this.name = name;
        this.description = description;
        this.typeOfEquipment = typeOfEquipment;
        this.weight = weight;

        Optional<WeightClass> calculatedWeightClass = calc.calcActualWeightClass(weight);
        this.weightClass = calculatedWeightClass.orElse(null);

        this.length = length;
        this.width = width;
        this.height = height;
        this.dateOfPurchase = dateOfPurchase;
        this.location = location;
        this.price = price;
    }

    /***
     * Constructor without the optional param dateOfPurchase
     *
     */
    public Equipment(String name, String description, TypeOfEquipment typeOfEquipment, int weight, int length, int width, int height, Location location, float price, WeightClassList weightClassList) {
        this.name = name;
        this.description = description;
        this.typeOfEquipment = typeOfEquipment;
        this.weight = weight;
        this.weightClass = Calc.calcActualWeightClass(weight).get();
        this.length = length;
        this.weight = width;
        this.height = height;
        this.location = location;
        this.price = price;
    }

    public int getCuboidVolume(){
        return this.height * this.length * this.width;
    }
}
