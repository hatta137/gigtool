package org.gigtool.modules;

import org.gigtool.models.Address;
import org.gigtool.models.Equipment;
import org.gigtool.models.Timeslot;

import java.util.Optional;

/***
 * Author: Hendrik Lendeckel
 * This class is the parent class of Gig and Rental and contains details about the name, description, equipment list, address and timeslot.
 */

public abstract class Happening {

    private String name;
    private String description;
    private Address address;
    private Timeslot timeslot;
    private final EquipmentList listOfEquipment = new EquipmentList();

    public Happening(String name, String description, Address address, Timeslot timeslot) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.timeslot = timeslot;

    }

    /**
     * Getter and Setter
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
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public Timeslot getTimeslot() {
        return timeslot;
    }
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }
    public EquipmentList getEquipmentList(){
        return this.listOfEquipment;
    }


    /**
     * Add equipment to the list of equipment and check if the equipment is also part of the inventory.
     * If not, it will be added.
     * Author: Hendrik Lendeckel
     * @param equipment the equipment to add
     */
    public EquipmentList addEquipment(Equipment equipment){

        if (equipment == null) {

            return this.listOfEquipment;

        }

        if (!Inventory.getInstance().isEquipmentInInventory(equipment.getEquipmentID())) {

            Inventory.getInstance().getEquipmentList().addEquipment(equipment);

        }

        this.listOfEquipment.getListOfEquipment().add(equipment);

        return this.listOfEquipment;

    }

    /**
     * Gets optional Equipment by Index
     * Author: Hendrik Lendeckel
     * @param index Index of listOfEquipment
     * @return Optional of Equipment or Optional Empty
     */
    public Optional<Equipment> getEquipment(int index){

        if (index >= 0 && index < listOfEquipment.getSizeOfEquipmentList()){

            return Optional.of(this.listOfEquipment.getListOfEquipment().get(index));

        }

        return Optional.empty();

    }

    /***
     * Author: Hendrik Lendeckel
     * Methode: deleteEquipment
     * @param equipment Index of Equipment
     * @return ArrayList if index is valid and null if not
     */
    public EquipmentList deleteEquipment(Equipment equipment){

        if (equipment == null) {

            throw new IllegalArgumentException("Equipment cannot be null.");

        }

        this.listOfEquipment.getListOfEquipment().remove(equipment);

        return this.listOfEquipment;
    }

}
