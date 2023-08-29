package org.gigtool.gigtool.storage.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Dario Daßler
 * The genre class represents a bands music style, including its name and description
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
