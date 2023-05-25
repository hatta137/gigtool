package org.gigtool.modules;

import org.gigtool.models.Equipment;

import java.util.ArrayList;
import java.util.Optional;


/***
 * Author: Hendrik Lendeckel
 * This class represents a listing of equipment. The total weight is also calculated on this list
 */


public class EquipmentList {

    private final ArrayList<Equipment> listOfEquipment = new ArrayList<>();
    private int totalWeight = 0;
    public EquipmentList() {
    }


    public ArrayList<Equipment> getListOfEquipment() {
        return listOfEquipment;
    }

    /***
     * Author: Hendrik Lendeckel
     * Methode: addEquipmentList
     * @param equipment Equipment to add
     * @return listOfEquipment
     */
    public EquipmentList addEquipment(Equipment equipment){

        this.listOfEquipment.add(equipment);

        totalWeight = Calc.calcWeight(this);

        return this;
    }

    /**
     * Gets optional Equipment by Index
     * Author: Hendrik Lendeckel
     * @param index Index of listOfEquipment
     * @return Optional of Equipment or Optional Empty
     */
    public Optional<Equipment> getEquipment(int index){

        if (index >= 0 && index < listOfEquipment.size()){

            return Optional.of(this.listOfEquipment.get(index));

        }

        return Optional.empty();
    }


    /***
     * Author: Hendrik Lendeckel
     * Methode: deleteEquipment
     * @param index Index of Equipment
     * @return ArrayList if index is valid and null if not
     */
    public boolean deleteEquipment(int index){

        if (index >= 0 && index < this.listOfEquipment.size()){

            this.totalWeight = totalWeight - listOfEquipment.get(index).getWeight();

            listOfEquipment.remove(index);

            return true;

        }
        else return false;
    }

    /**
     * Author: Hendrik Lendeckel
     * @return the Size of the Equipment List
     */
    public int getSizeOfEquipmentList(){
        return listOfEquipment.size();
    }

}
