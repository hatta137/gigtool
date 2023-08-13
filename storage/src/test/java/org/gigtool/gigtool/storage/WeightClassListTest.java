package org.gigtool.gigtool.storage;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.model.WeightClassList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = WeightClassList.class)
public class WeightClassListTest extends BeforeAllTests{

    /*@Test
    void addAndGetWeightClass(){

        // check add and get from weightClasses and weightClassList from BeforeAllTests
        assertEquals(wClass1, weightClassList.getWeightClass(0).orElse(null));
        assertEquals(wClass2, weightClassList.getWeightClass(1).orElse(null));
        assertEquals(wClass3, weightClassList.getWeightClass(2).orElse(null));
        assertEquals(wClass4, weightClassList.getWeightClass(3).orElse(null));

        // Testing get The Biggest weight Class
        assertEquals(wClass4, weightClassList.getBiggestWeightClass());

        // creating a new biggest weightClass
        WeightClass newBiggestWeightClass = new WeightClass("extrem", "ganz schwer", 1034, 100);

        weightClassList.addWeightClass(newBiggestWeightClass);

        assertEquals(newBiggestWeightClass, weightClassList.getBiggestWeightClass());

        // Test what happens when the weight range of the new weight class overlaps with the last weight class
        WeightClass overlappingWeightClass = new WeightClass("overlap", "komisch", 1050, 100);
        weightClassList.addWeightClass(overlappingWeightClass);

        assertEquals(1134, weightClassList.getBiggestWeightClass().getWeightStart());

        //Test what happens when the weight range of the new weight class is within the given weight classes
        WeightClass withinWeightClass = new WeightClass("within", "komisch", 2, 2);
        weightClassList.addWeightClass(withinWeightClass);

        assertEquals(wClass1, weightClassList.getWeightClass(0).orElse(null));
        assertEquals(wClass2, weightClassList.getWeightClass(1).orElse(null));
        assertEquals(wClass3, weightClassList.getWeightClass(2).orElse(null));
        assertEquals(wClass4, weightClassList.getWeightClass(3).orElse(null));
        assertEquals(newBiggestWeightClass, weightClassList.getWeightClass(4).orElse(null));
        assertEquals(overlappingWeightClass.getName(), weightClassList.getWeightClass(5).orElse(null).getName());

        assertEquals(6, weightClassList.getSizeOfWeightClassList());
    }

    @Test
    void deleteWeightClass(){

        WeightClass weightClassToDelete = new WeightClass("to delete", "delete", 1032, 500);
        weightClassList.addWeightClass(weightClassToDelete);

        // check if the max. weightclass in List is the wClass4 from BeforeAllTests
        assertEquals(weightClassToDelete, weightClassList.getBiggestWeightClass());

        // delete the last weightClass
        weightClassList.deleteWeightClass();

        // check if the new last/biggest weightClass is the wClass3
        assertEquals(wClass4, weightClassList.getBiggestWeightClass());
    }*/

/*    @Test
    void maxWeightInWeightClassList(){

        //Testing getMaxWeightInWeightClass
        assertEquals(1032, weightClassList.getMaxWeightInWeightClassList());
    }*/
}
