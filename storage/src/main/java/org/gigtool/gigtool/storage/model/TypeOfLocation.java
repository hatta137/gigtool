package org.gigtool.gigtool.storage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/***
 * Author: Max Schelenz
 * The class TypeOfLocation represents a type of location, such as "concert hall" or "outdoor festival site".
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TypeOfLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;

    /**
     * Constructor with all attributes.
     * @param name
     * @param description
     */
    public TypeOfLocation(String name, String description) {
        this.name = name;
        this.description = description;
    }
}