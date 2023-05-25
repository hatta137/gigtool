import org.gigtool.modules.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.Optional;



public class BandTest extends BeforeAllTests{

    /**
     *  The Main Equipment-Part is checked in der EquipmentListTest.java
     *  in the following the adding to a band is checked to proof that every equipment
     *  that is part of a Band is part of the inventory too
     */
    @Test
    void addEquipmentToBand(){

        Inventory.resetInstance();

        // creating the Inventory
        Inventory inventory = Inventory.getInstance();

        //check if the equipment from BeforeAllTests is part of the Inventory
        assertFalse(inventory.isEquipmentInInventory(equipment1.getEquipmentID()));

        //add Equipment from BeforeAllTests to the Band
        band1.addEquipment(equipment1);

        //check if the Equipment is part now Part of the Inventory
        assertTrue(inventory.isEquipmentInInventory(equipment1.getEquipmentID()));

        Inventory.resetInstance();

        // delete the equipment
        band1.deleteEquipment(equipment1);

        //check delete
        assertEquals(0, band1.getEquipmentList().getSizeOfEquipmentList());

    }



    /***
     *  Checking the array-list roleInTheBand
     */

    @Test
    void roleInTheBand(){

        // Adding roles to the band
        band2.addRoleInTheBand(roleDrummer);
        band2.addRoleInTheBand(roleBass);
        band2.addRoleInTheBand(roleBackgroundSinger);
        band2.addRoleInTheBand(roleFrontSinger);
        band2.addRoleInTheBand(roleLeadGuitarist);

        // checking add and get
        assertNotNull(band1.getRoleInTheBand(0));
        assertEquals(roleDrummer, band2.getRoleInTheBand(0).orElse(null));
        assertEquals(roleBass, band2.getRoleInTheBand(1).orElse(null));
        assertEquals(roleBackgroundSinger, band2.getRoleInTheBand(2).orElse(null));
        assertEquals(roleFrontSinger, band2.getRoleInTheBand(3).orElse(null));
        assertEquals(roleLeadGuitarist, band2.getRoleInTheBand(4).orElse(null));

        //delete role
        band2.deleteRoleInTheBand(roleFrontSinger);

        // checking delete
        assertEquals(roleDrummer, band2.getRoleInTheBand(0).orElse(null));
        assertEquals(roleBass, band2.getRoleInTheBand(1).orElse(null));
        assertEquals(roleBackgroundSinger, band2.getRoleInTheBand(2).orElse(null));
        assertEquals(roleLeadGuitarist, band2.getRoleInTheBand(3).orElse(null));
        assertEquals(Optional.empty(), band2.getRoleInTheBand(4));

    }
}
