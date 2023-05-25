import org.gigtool.modules.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.io.Console;
import java.util.Optional;

public class EquipmentListTest extends BeforeAllTests{
    @Test
    void addGetDeleteEquipment(){

        EquipmentList newEquipmentList = new EquipmentList();

        // adding equipment
        newEquipmentList
                .addEquipment(equipment1)
                .addEquipment(equipment2)
                .addEquipment(equipment3)
                .addEquipment(equipment4)
                .addEquipment(equipment5);


        // check add and get
        assertEquals(equipment1, newEquipmentList.getEquipment(0).get());
        assertEquals(equipment2, newEquipmentList.getEquipment(1).get());
        assertEquals(equipment3, newEquipmentList.getEquipment(2).get());

        // delete equipment
        newEquipmentList.deleteEquipment(0);

        // check delete
        assertEquals(Optional.of(equipment2), newEquipmentList.getEquipment(0));
        assertEquals(Optional.of(equipment3), newEquipmentList.getEquipment(1));
        assertEquals(Optional.of(equipment4), newEquipmentList.getEquipment(2));
        assertEquals(Optional.of(equipment5), newEquipmentList.getEquipment(3));


        assertEquals(Optional.empty(), newEquipmentList.getEquipment(4));



    }

    @Test
    void getSizeOfEquipmentList(){

        // the check uses the equipmentList from BeforeAllTests.java
        assertEquals(3, equipmentList1.getSizeOfEquipmentList());
    }
}
