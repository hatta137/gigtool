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
    private String description;

    public RoleInTheBand(String name, String description){
        this.name = name;
        this.description = description;
    }
}
