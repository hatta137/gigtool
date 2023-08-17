package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/***
 * This class describes the role a musician can have in a band.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class RoleInTheBand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    public RoleInTheBand(String name, String description){
        this.name = name;
        this.description = description;
    }
}
