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
 * The TypeOfGig class represents a type of gig, with a name and a description.
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
public class TypeOfGig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;

    /**
     * Constructor with all attributes.
     * @param name Name of the Gigtype
     * @param description Description of the TYpe of Gig
     */
    public TypeOfGig(String name, String description) {
        this.name=name;
        this.description=description;
    }
}
