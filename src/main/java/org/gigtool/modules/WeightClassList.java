package org.gigtool.modules;

import org.gigtool.models.WeightClass;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Author: Hendrik Lendeckel
 * This class describes a list of weight classes. The maximum weight is 2000kg.
 */
public class WeightClassList {

    private final ArrayList<WeightClass> listOfWeightClass = new ArrayList<>();
    private int maxWeightInWeightClassList = 0;
    public WeightClassList(){
    }
    public ArrayList<WeightClass> getListOfWeightClass() {
        return listOfWeightClass;
    }
    public int getSizeOfWeightClassList(){
        return listOfWeightClass.size();
    }
    public int getMaxWeightInWeightClassList() {
        return maxWeightInWeightClassList;
    }

    /***
     * Author: Hendrik Lendeckel
     * Adding a new WeightClass to the List. The Maximum weight in the list can be 2000, so the weightStart + Duration must be lower than 2000
     * @return listOfWeightClass
     */
    public WeightClassList addWeightClass(WeightClass weightClass){

        if (weightClass == null) {

            throw new IllegalArgumentException("Weight class cannot be null.");

        }

        int endWeight = weightClass.getWeightStart() + weightClass.getDuration();

        if (endWeight > 2000){

            endWeight = 2000;

            weightClass.setDuration(endWeight - weightClass.getWeightStart());

        }

        int actualMaxWeightInList = this.getMaxWeightInWeightClassList();

        // Check if the existing maximum weight in the list is lower or equal the start weight of the new weightClass
        if (weightClass.getWeightStart() >= maxWeightInWeightClassList) {

            this.listOfWeightClass.add(weightClass);

            maxWeightInWeightClassList = endWeight;

            return this;

        } else if (weightClass.getWeightStart() < maxWeightInWeightClassList && endWeight > maxWeightInWeightClassList) {

            WeightClass weightClassAutoGen = new WeightClass(weightClass.getName(), weightClass.getDescription(), actualMaxWeightInList , endWeight - actualMaxWeightInList);

            this.listOfWeightClass.add(weightClassAutoGen);

            maxWeightInWeightClassList = endWeight;

        }
        return this;
    }





    /**
     * Author: Hendrik Lendeckel
     * Returns the weight class at the specified index in the list of weight classes.
     *
     * @param index the index of the weight class to retrieve
     * @return the weight class at the specified index, or null if the index is out of bounds
     * @throws IndexOutOfBoundsException if the index is negative or greater than or equal to the size of the list
     */
    public Optional<WeightClass> getWeightClass(int index) {

        if (index < 0 || index >= this.listOfWeightClass.size()) {

            return Optional.empty();

        }

        return Optional.of(this.listOfWeightClass.get(index));

    }

    /**
     * Author: Hendrik Lendeckel
     * Deletes the last weight class in the list of weight classes.
     *
     * @return the updated weight class list
     * @throws NoSuchElementException if the list is empty
     */

    public WeightClassList deleteWeightClass(){

        if (this.listOfWeightClass.isEmpty()) {

            throw new NoSuchElementException("Cannot delete weight class from empty list.");

        }

        maxWeightInWeightClassList = this.getMaxWeightInWeightClassList() - listOfWeightClass.get(listOfWeightClass.size()-1).getDuration();

        this.listOfWeightClass.remove(this.listOfWeightClass.size() - 1);

        return this;

    }

    /**
     * Author: Hendrik Lendeckel
     * Returns the last weight class in the list of weight classes.
     *
     * @return the last weight class in the list
     * @throws NoSuchElementException if the list is empty
     */

    public WeightClass getBiggestWeightClass(){

        if (this.listOfWeightClass.isEmpty()) {

            throw new NoSuchElementException("Cannot get weight class from empty list.");

        }

        return this.listOfWeightClass.get(this.listOfWeightClass.size() - 1);
    }

}
