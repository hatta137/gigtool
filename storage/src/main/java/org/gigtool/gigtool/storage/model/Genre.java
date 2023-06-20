package org.gigtool.gigtool.storage.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

//TODO comments
/* Author: Dario Daßler*/
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;

    @OneToMany
    private ArrayList<Band> bands;


    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
