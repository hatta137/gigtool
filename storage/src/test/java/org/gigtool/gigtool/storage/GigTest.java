package org.gigtool.gigtool.storage;

import org.gigtool.gigtool.storage.services.*;
import org.gigtool.gigtool.storage.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Gig.class)
public class GigTest extends BeforeAllTests{


    /**
     *  The Main Equipment-Part is checked in der EquipmentListTest.java
     *  in the following the adding to a band is checked to proof that every equipment
     *  that is part of a happening is part of the inventory too
     */

    @Test
    void addEquipmentToGig(){

        // Reset Instance
        Inventory.resetInstance();

        // creating the Inventory
        Inventory inventory = Inventory.getInstance();

        myGig.addEquipment(equipment1).addEquipment(equipment2).addEquipment(equipment3);

        assertEquals(equipment1, myGig.getEquipment(0).orElse(null));
        assertEquals(equipment2, myGig.getEquipment(1).orElse(null));
        assertEquals(equipment3, myGig.getEquipment(2).orElse(null));


        //check if the equipment  is part of the Inventory
        assertTrue(inventory.isEquipmentInInventory(equipment1.getEquipmentID()));
        assertTrue(inventory.isEquipmentInInventory(equipment2.getEquipmentID()));
        assertTrue(inventory.isEquipmentInInventory(equipment3.getEquipmentID()));

        assertEquals(3, myGig.getEquipmentList().getSizeOfEquipmentList());

        //delete Equipment from Gig
        myGig.deleteEquipment(equipment1);
        myGig.deleteEquipment(equipment2);
        myGig.deleteEquipment(equipment3);

        //check delete
        assertEquals(0, myGig.getEquipmentList().getSizeOfEquipmentList());

        //check if the equipment  is part of the Inventory
        assertTrue(inventory.isEquipmentInInventory(equipment1.getEquipmentID()));
        assertTrue(inventory.isEquipmentInInventory(equipment2.getEquipmentID()));
        assertTrue(inventory.isEquipmentInInventory(equipment3.getEquipmentID()));

        Inventory.resetInstance();

    }

    @Test
    void addBandToGig(){

        // Reset Instance
        Inventory.resetInstance();

        // creating the Inventory
        Inventory inventory = Inventory.getInstance();

        band1.addEquipment(equipment1).addEquipment(equipment2).addEquipment(equipment3);

        myGig.setBand(band1);

        //check Band-Equipment
        assertEquals(equipment1, myGig.getBand().getEquipmentList().getEquipment(0).orElse(null));
        assertEquals(equipment2, myGig.getBand().getEquipmentList().getEquipment(1).orElse(null));
        assertEquals(equipment3, myGig.getBand().getEquipmentList().getEquipment(2).orElse(null));


        myGig.getBand().deleteEquipment(equipment1);
        myGig.getBand().deleteEquipment(equipment2);
        myGig.getBand().deleteEquipment(equipment3);

        //check delete
        assertEquals(0, myGig.getBand().getEquipmentList().getSizeOfEquipmentList());

    }
}
