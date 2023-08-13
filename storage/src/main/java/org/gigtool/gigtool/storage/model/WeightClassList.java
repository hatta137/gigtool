package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.services.WeightClassListService;

import java.util.*;

/**
 * Author: Hendrik Lendeckel
 * This class describes a list of weight classes. The maximum weight is 2000kg.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class WeightClassList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToMany
    private List<WeightClass> listOfWeightClass = new ArrayList<>();
    private int maxWeightInWeightClassList = 0;

    public int getSizeOfWeightClassList(){
        return listOfWeightClass.size();
    }



/*
    */
/**
     * Author: Hendrik Lendeckel
     * Returns the weight class at the specified index in the list of weight classes.
     *
     * @param index the index of the weight class to retrieve
     * @return the weight class at the specified index, or null if the index is out of bounds
     * @throws IndexOutOfBoundsException if the index is negative or greater than or equal to the size of the list
     *//*

    public Optional<WeightClass> getWeightClass(int index) {

        if (index < 0 || index >= this.listOfWeightClass.size()) {

            return Optional.empty();

        }

        return Optional.of(this.listOfWeightClass.get(index));

    }

    */
/**
     * Author: Hendrik Lendeckel
     * Deletes the last weight class in the list of weight classes.
     *
     * @return the updated weight class list
     * @throws NoSuchElementException if the list is empty
     *//*


    public WeightClassList deleteWeightClass(){

        if (this.listOfWeightClass.isEmpty()) {

            throw new NoSuchElementException("Cannot delete weight class from empty list.");

        }

        maxWeightInWeightClassList = this.getMaxWeightInWeightClassList() - listOfWeightClass.get(listOfWeightClass.size()-1).getDuration();

        this.listOfWeightClass.remove(this.listOfWeightClass.size() - 1);

        return this;

    }

    */
/**
     * Author: Hendrik Lendeckel
     * Returns the last weight class in the list of weight classes.
     *
     * @return the last weight class in the list
     * @throws NoSuchElementException if the list is empty
     *//*


    public WeightClass getBiggestWeightClass(){

        if (this.listOfWeightClass.isEmpty()) {

            throw new NoSuchElementException("Cannot get weight class from empty list.");
        }
        return this.listOfWeightClass.get(this.listOfWeightClass.size() - 1);
    }
*/

}
