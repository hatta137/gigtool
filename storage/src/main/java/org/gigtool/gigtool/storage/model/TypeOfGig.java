package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/***
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
    @Column(columnDefinition = "TEXT")
    private String description;

    public TypeOfGig(String name, String description) {
        this.name=name;
        this.description=description;
    }
}
