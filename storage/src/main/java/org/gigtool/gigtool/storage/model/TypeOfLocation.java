package org.gigtool.gigtool.storage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Getter
@Setter
public class TypeOfLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;

    public TypeOfLocation(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
