import org.gigtool.modules.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class InventoryTest extends BeforeAllTests{


    @Test
    void createInventory(){

        //creating new Inventory
        Inventory newInventory = Inventory.getInstance();

        //trying to create another Inventory
        Inventory anotherInventory = Inventory.getInstance();

        //check if the singleton is working
        assertEquals(newInventory, anotherInventory);

        //reset Instance
        newInventory.resetInstance();

        //another Instance
        Inventory thirdInventory = Inventory.getInstance();

        //check if reset is working
        assertNotEquals(newInventory, thirdInventory);

    }


    @Test
    void isEquipmentInInventory(){

        Inventory inventory = Inventory.getInstance();

        //adding Equipment to Inventory
        inventory.getEquipmentList().addEquipment(equipment1).addEquipment(equipment2).addEquipment(equipment3);

        //check if equipment2 is in list
        boolean isEquipmentInInventory = inventory.isEquipmentInInventory(equipment2.getEquipmentID());
        assertTrue(isEquipmentInInventory);

    }
}
