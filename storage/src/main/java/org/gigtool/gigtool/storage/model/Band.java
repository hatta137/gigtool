package org.gigtool.gigtool.storage.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.services.EquipmentList;
import org.gigtool.gigtool.storage.services.Inventory;

import java.util.*;

/***
 * Author: Hendrik Lendeckel
 * This class represents a kind of preset for equipment.
 * The musician can create bands and give them predefined equipment lists.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    private Genre genre;
    @OneToMany
    private final ArrayList<RoleInTheBand>    listOfRole = new ArrayList<>();
    @Transient
    private EquipmentList               equipmentList = new EquipmentList();



    /**
     * Constructor with all attributes. Instead of a complete arraylist RoleInTheBand, only the main - Role must be specified
     * @param name Name of the band
     * @param genre Genre of the band
     * @param mainRoleInTheBand The main role played by the musician in this band or his main instrument position
     */
    public Band(String name, Genre genre, RoleInTheBand mainRoleInTheBand) {

        this.name = name;

        this.genre = genre;

        this.listOfRole.add(mainRoleInTheBand);

        this.equipmentList = new EquipmentList();
    }

    /**
     * Constructor without Genre
     * @param name Name of the Band
     * @param mainRoleInTheBand The main role played by the musician in this band or his main instrument position
     */
    public Band(String name, RoleInTheBand mainRoleInTheBand) {

        this.name = name;

        this.listOfRole.add(mainRoleInTheBand);

        this.genre = new Genre("keine Angabe", "-");

        this.equipmentList = new EquipmentList();
    }

    /**
     * Author: Hendrik Lendeckel
     * Adds an equipment object to the equipment list.
     * If the equipment is not already part of the inventory, it will be added to it
     * @param equipment the equipment object to add to the equipment list.
     * @return the updated equipment list after the equipment object has been added.
     * @throws IllegalArgumentException if the equipment object is null.
     */
    public EquipmentList addEquipment(Equipment equipment){

        if (equipment == null) {

            throw new IllegalArgumentException("Equipment cannot be null.");
        }

        if (!Inventory.getInstance().isEquipmentInInventory(equipment.getEquipmentID())) {

            Inventory.getInstance().getEquipmentList().addEquipment(equipment);
        }

        this.equipmentList.getListOfEquipment().add(equipment);

        return this.equipmentList;
    }

    /***
     * Author Hendrik Lendeckel
     * @param equipment that will be deleted
     * @return the updatet EquipmentList
     */

    public EquipmentList deleteEquipment(Equipment equipment){

        if (equipment == null) {

            throw new IllegalArgumentException("Equipment cannot be null.");
        }

        this.equipmentList.getListOfEquipment().remove(equipment);

        return this.equipmentList;
    }


    /**
     * Adds another role in the band object to the list of roles in the band.
     * @param roleInTheBand the role in the band object to add to the list of roles in the band.
     * @return the updated list of roles in the band after the role in the band object has been added.
     */
    public ArrayList<RoleInTheBand> addRoleInTheBand(RoleInTheBand roleInTheBand){

        this.listOfRole.add(roleInTheBand);

        return this.listOfRole;
    }


    /**
     * Gets an optional RoleInTheBand by Index
     * Author: Hendrik Lendeckel
     * @param index Index of listOfRole
     * @return Optional of RoleInTheBand or Optional Empty
     */
    public Optional<RoleInTheBand> getRoleInTheBand(int index){

        if (index >= 0 && index < listOfRole.size()){

            return Optional.of(this.listOfRole.get(index));
        }
        return Optional.empty();
    }

    /***
     * Author: Hendrik Lendeckel
     * @param roleInTheBand the role in the band object to add to the list of roles in the band.
     * @return ArrayList if index is valid and null if not
     */
    public boolean deleteRoleInTheBand(RoleInTheBand roleInTheBand){

        if (this.listOfRole.isEmpty()){
            throw new NoSuchElementException("No listOfRole existing");
        }

        if (roleInTheBand == null){
            throw new NoSuchElementException("No such RoleInTheBand existing");
        }

        Iterator<RoleInTheBand> iterator = this.listOfRole.iterator();

        while (iterator.hasNext()) {

            RoleInTheBand role = iterator.next();

            if (roleInTheBand.getName().equals(role.getName())){

                iterator.remove();

                return true;
            }
        }
        return false;
    }
}
