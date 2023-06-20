package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class represents an abstract idea of the size of a piece of equipment
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Dimension {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int length;
    private int width;
    private int height;
    @OneToMany
    private ArrayList<Equipment> equipments;

    public Dimension(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    /***
     * Calc the Volume of an imaginary cuboid
     * @return volume
     */
    public int getCuboidVolume(){
        return this.height * this.length * this.width;
    }

}
