package org.gigtool.gigtool.storage.services;
import org.gigtool.gigtool.storage.model.Equipment;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class Calc {


    /**
     * Author: Hendrik Lendeckel
     * Calculates the total weight of a list of equipment.
     *
     * @param equipmentList the list of equipment
     * @return the total weight of the equipment
     * @throws IllegalArgumentException if the equipment list is null or empty
     */
    // TODO zu Happening
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
    // TODO zu Happening
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

    // TODO Happening setAllEquipmentLocationToHappeningLocation( UUID happeningId )
}
