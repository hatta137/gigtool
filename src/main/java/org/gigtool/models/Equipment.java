package org.gigtool.models;

import org.gigtool.modules.Calc;
import org.gigtool.modules.WeightClassList;

import java.time.LocalDate;

/***
 * Author: Hendrik Lendeckel
 * This class represents the equipment with all necessary properties.
 */

public class Equipment {

    private int equipmentID;
    private String name;
    private String description;
    private TypeOfEquipment typeOfEquipment;
    private int weight;
    private final WeightClass weightClass;
    private Dimension dimension;
    private LocalDate dateOfPurchase;
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

    /***
     * Getter and Setter
     * Author: Hendrik Lendeckel
     *
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public TypeOfEquipment getTypeOfEquipment() {
        return typeOfEquipment;
    }
    public void setTypeOfEquipment(TypeOfEquipment typeOfEquipment) {
        this.typeOfEquipment = typeOfEquipment;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public WeightClass getWeightClass() {
        return weightClass;
    }
    public Dimension getDimension() {
        return dimension;
    }
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }
    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public int getEquipmentID() {
        return equipmentID;
    }
    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

}
