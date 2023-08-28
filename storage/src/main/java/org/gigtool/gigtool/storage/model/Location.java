package org.gigtool.gigtool.storage.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;

/***
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
    @ManyToOne
    private Address address;
    @ManyToOne
    private TypeOfLocation typeOfLocation;

    public Location(Address address, TypeOfLocation typeOfLocation) {
        this.address = address;
        this.typeOfLocation = typeOfLocation;
    }
}

