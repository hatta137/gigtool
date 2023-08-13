/*package org.gigtool.gigtool.storage;

import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.WeightClassList;
import org.gigtool.gigtool.storage.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Calc.class)
public class CalcTest extends BeforeAllTests {


    @Test
    void testCalcWeight() {

        //calculating the total weight in the equipmentList from BeforeAllTests
        int totalWeight = Calc.calcWeight(equipmentList1);

        //check
        assertEquals(18, totalWeight);


    }



    @Test
    void testCalcTotalCosts() {

        //calculating the total costs in the List
        float totalCosts = Calc.calcTotalCosts(equipmentList1);

        //check
        assertEquals(4050.0f, totalCosts);

    }



*//*
    @Test
    void testCalcActualWeightClass(){

        WeightClassList newWeightClassList = new WeightClassList()
                .addWeightClass(wClass1)
                .addWeightClass(wClass2)
                .addWeightClass(wClass3)
                .addWeightClass(wClass4);

        // checking calculation with existing weightClassList from BeforeAllTests
        assertEquals(wClass2, Calc.calcActualWeightClass(newWeightClassList, 6).orElse(null));

        // check for a weight that is bigger than the max weight in the List
        assertEquals(wClass4, Calc.calcActualWeightClass(newWeightClassList, 2500).orElse(null));

    }


    @Test
    void sortEquipmentLocations(){

        //creating new EquipmentList
        EquipmentList newEquipmentList = new EquipmentList()
                .addEquipment(equipment1)
                .addEquipment(equipment2)
                .addEquipment(equipment5)
                .addEquipment(equipment1)
                .addEquipment(equipment2)
                .addEquipment(equipment3)
                .addEquipment(equipment5);
*//*

        //sorting the Locations after the LocationType-Name
        ArrayList<Location> sortedLocations = Calc.sortLocationsByTypeOfLocationName(Calc.getEquipmentLocation(newEquipmentList));

        //checking the order
        assertEquals(typeOfLocation2, sortedLocations.get(0).getTypeOfLocation());  //TypeOfLocation->Name:"Buero-EF"
        assertEquals(typeOfLocation2, sortedLocations.get(1).getTypeOfLocation());  //TypeOfLocation->Name:"Buero-EF"
        assertEquals(typeOfLocation1, sortedLocations.get(2).getTypeOfLocation());  //TypeOfLocation->Name:"Lager-EF"
        assertEquals(typeOfLocation1, sortedLocations.get(3).getTypeOfLocation());  //TypeOfLocation->Name:"Lager-EF"
        assertEquals(typeOfLocation1, sortedLocations.get(4).getTypeOfLocation());  //TypeOfLocation->Name:"Lager-EF"
        assertEquals(typeOfLocation1, sortedLocations.get(5).getTypeOfLocation());  //TypeOfLocation->Name:"Lager-EF"
        assertEquals(typeOfLocation3, sortedLocations.get(6).getTypeOfLocation());  //TypeOfLocation->Name:"Proberaum-WM"

    }
}*/

