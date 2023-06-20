package org.gigtool.gigtool.storage.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

/***
 * Author: Max Schelenz
 * This class describes a weight class in which an instrument is based on its own weight.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class WeightClass {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private int weightStart;
    private int duration;
    @OneToMany
    private ArrayList<Equipment> equipments;


    /**
     * Constructor with all attributes.
     * @param name
     * @param description
     * @param weightStart
     * @param duration
     */
    public WeightClass(String name, String description, int weightStart, int duration) {
        this.name=name;
        this.description=description;
        this.weightStart=weightStart;
        this.duration=duration;
    }
}







