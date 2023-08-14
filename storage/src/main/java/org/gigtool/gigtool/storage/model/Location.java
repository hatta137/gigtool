package org.gigtool.gigtool.storage.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/***
 * Author: Robin Harris
 * The Location class represents a physical location and contains information about its address and type.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    private Address address;
    @OneToOne
    private TypeOfLocation typeOfLocation;
    @OneToMany
    private List<Equipment> equipments;

    public Location(Address address, TypeOfLocation typeOfLocation) {
        this.address = address;
        this.typeOfLocation = typeOfLocation;
    }
}

