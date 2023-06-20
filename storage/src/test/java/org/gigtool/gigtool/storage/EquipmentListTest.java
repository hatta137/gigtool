package org.gigtool.gigtool.storage;

import org.gigtool.gigtool.storage.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = EquipmentList.class)
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
