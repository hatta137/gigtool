package org.gigtool.gigtool.storage.repositories;


import org.gigtool.gigtool.storage.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.gigtool.gigtool.storage.BeforeAllTests;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EquipmentRepositoryTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

/*    @Test
    public void testSave() {
        assertNotNull(equipment1);

        Equipment savedEquipment = equipmentRepository.saveAndFlush(equipment1);
        assertNotNull(savedEquipment);
        assertNotNull(savedEquipment.getName());
        assertEquals(savedEquipment.getName(), equipment1.getName());
        assertEquals(savedEquipment.getDescription(), equipment1.getDescription());
        assertEquals(savedEquipment.getTypeOfEquipment(), equipment1.getTypeOfEquipment());
        assertEquals(savedEquipment.getDimension(), equipment1.getDimension());
        assertEquals(savedEquipment.getLocation(), equipment1.getLocation());
        assertEquals(savedEquipment.getPrice(), equipment1.getPrice());
        assertEquals(savedEquipment.getWeightClass(), equipment1.getWeightClass());
    }*/
}



















