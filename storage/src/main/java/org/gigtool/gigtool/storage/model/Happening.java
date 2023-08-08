package org.gigtool.gigtool.storage.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.services.EquipmentList;
import org.gigtool.gigtool.storage.services.Inventory;

import java.util.Optional;
import java.util.UUID;

/***
 * Author: Hendrik Lendeckel
 * This class is the parent class of Gig and Rental and contains details about the name, description, equipment list, address and timeslot.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public abstract class Happening {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    @ManyToOne
    private Address address;
    @OneToOne
    private Timeslot timeslot;
    @Transient
    private final EquipmentList listOfEquipment = new EquipmentList();

    public Happening(String name, String description, Address address, Timeslot timeslot) {
        this.name = name;
        this.description = description;
        this.address = address;
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
