package org.gigtool.gigtool.storage.services;
import org.gigtool.gigtool.storage.model.Equipment;

import java.util.UUID;


/***
 * Author: Max Schelenz & Hendrik Lendeckel
 * The Inventory class represents a collection of equipment that a musician can hold.
 * It is implemented as a singleton to ensure that there is only one instance of the inventory.
 *
 */
public class Inventory {

    // Singleton instance of the inventory
    private static Inventory instance;

    // List of equipment in the inventory
    private final EquipmentList equipmentList;

    /**
     * Private constructor for the Inventory class. It initializes the name and equipment list of the inventory.
     */
    private Inventory() {
        this.equipmentList = new EquipmentList();
    }

    /**
     * Returns the singleton instance of the inventory. If it does not exist, it is created.
     * @return the singleton instance of the inventory
     */
    public static synchronized Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }


    public EquipmentList getEquipmentList() {
        return equipmentList;
    }

    /***
     * resets the instance to null to create a new Instance -> needed for Testcase
     */
    public static void resetInstance(){
        instance = null;
    }

    /**
     * Author: Hendrik Lendeckel
     * This method checks whether a certain piece of equipment is already in the inventory.
     * @param id of the equipment to be checked
     * @return boolean
     */
    public boolean isEquipmentInInventory(UUID id){

        for (Equipment e : equipmentList.getListOfEquipment()) {
            if (id == e.getId()){
                return true;
            }
        }
        return false;
    }
}
