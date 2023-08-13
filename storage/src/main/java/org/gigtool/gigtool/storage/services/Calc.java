package org.gigtool.gigtool.storage.services;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.WeightClass;
import org.gigtool.gigtool.storage.model.WeightClassList;
import org.gigtool.gigtool.storage.repositories.WeightClassListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Optional;


/**
 * In this class all calculations are carried out which are required in several other classes.
 */
@Service
public class Calc {

    private static WeightClassListService weightClassListService;

    private static WeightClassListRepository weightClassListRepository;

    public static void initialize(WeightClassListService wcls, WeightClassListRepository wclr) {
        weightClassListService = wcls;
        weightClassListRepository = wclr;
    }



    /**
     * Author: Hendrik Lendeckel
     * Calculates the weight class for a given weight, using a list of predefined weight classes.
     * @param weight the weight to find the matching weight class for
     * @return an Optional containing the matching weight class, or an empty Optional if no matching weight class is found
     * @throws IllegalArgumentException if the weight class list is null or empty
     */
     public static Optional<WeightClass> calcActualWeightClass(int weight){

         WeightClassListService weightClassListService = new WeightClassListService(weightClassListRepository); // Erstellen Sie hier eine Instanz des WeightClassListService

         WeightClassList weightClassList = weightClassListService.getWeightClassList().getBody();

         if (weight > 2000)
             weight = 2000;

         if (weight > weightClassList.getMaxWeightInWeightClassList())
             weight = weightClassList.getMaxWeightInWeightClassList();

         if (weightClassList == null) { //TODO @Hendrik always false
             throw new IllegalArgumentException("Weight class list cannot be null.");
         }
         if (weightClassList.getSizeOfWeightClassList() < 0) {
             throw new IllegalArgumentException("Weight class list must contain at least one weight class.");
         }

        if (weight <= weightClassList.getMaxWeightInWeightClassList()){
            for (WeightClass item : weightClassList.getListOfWeightClass()) {
                if (weight >= item.getWeightStart() && weight <= item.getWeightStart() + item.getDuration())

                    return Optional.of(item);
            }
        }


          /** If the weight is not within the range instead of the predefined weight classes,
          * the method creates a new weight class with a start weight equal to the previous maximum weight and
          * a span equal to the difference between the specified weight and the previous maximum weight.
          * The method then adds this new weight class to the list
          */

        WeightClass weightClassAutoGen = new WeightClass("auto-generated weightClass",
               "no weightClass for the weight available",
                weightClassList.getMaxWeightInWeightClassList(),
               weight - weightClassList.getMaxWeightInWeightClassList());


        weightClassListService.addWeightClassToWeightClassList(weightClassAutoGen);

        return Optional.of(weightClassListService.getBiggestWeightClass());
    }


    /**
     * Author: Hendrik Lendeckel
     * Calculates the total weight of a list of equipment.
     *
     * @param equipmentList the list of equipment
     * @return the total weight of the equipment
     * @throws IllegalArgumentException if the equipment list is null or empty
     */
    public static int calcWeight(EquipmentList equipmentList){

        if (equipmentList == null) {

            throw new IllegalArgumentException("Equipment list cannot be null.");

        }

        if (equipmentList.getSizeOfEquipmentList() == 0) {

            throw new IllegalArgumentException("Equipment list must contain at least one item.");

        }

        int result = 0;

        for (Equipment item : equipmentList.getListOfEquipment()) {

            result = result + item.getWeight();

        }
        return result;
    }

    /**
     * Author: Max Schelenz
     * Calculates the price of all instruments in a EquipmentList.
     * Uses getEquipment to get the all Instruments from the EquipmentList and sum up their prices to return the totalCosts.
     * @return the total costs (float)
     */
    public static float calcTotalCosts(EquipmentList equipmentList){

        int result = 0;

        Optional<Equipment> currentEquipment;

        for(int i = 0; i< equipmentList.getSizeOfEquipmentList(); i++){

            currentEquipment = equipmentList.getEquipment(i);

            if(currentEquipment.isPresent()) {

                result += currentEquipment.get().getPrice();

            }
        }
        return result;
    }

    /***
     * Author: Hendrik Lendeckel
     * This method collects all Locations from the equipment-list
     * @return Array List with the locations
     */
    public static ArrayList<Location> getEquipmentLocation(EquipmentList equipmentList){

        ArrayList<Location> locations = new ArrayList<>();

        for (int i = 0; i < equipmentList.getSizeOfEquipmentList(); i++){

            if (equipmentList.getEquipment(i).isPresent())
                locations.add(equipmentList.getEquipment(i).get().getLocation());

        }

        return locations;
    }

    public static ArrayList<Location> sortLocationsByTypeOfLocationName(ArrayList<Location> locations){

        locations.sort(((o1, o2) -> o1.getTypeOfLocation().getName().compareToIgnoreCase(o2.getTypeOfLocation().getName())));

        return locations;

    }
}
