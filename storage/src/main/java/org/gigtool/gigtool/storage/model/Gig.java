package org.gigtool.gigtool.storage.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Robin Harris
 * This class inherits from the Happening class and represents a performance by the musician.
 */
// TODO extends muss raus, da Datenbank
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Gig extends Happening {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    private TypeOfGig typeOfGig;
    @ManyToOne
    private Band band;
    //TODO OneToOne Happening


    public Gig(String name, String description, Address address, Timeslot timeslot, TypeOfGig typeOfGig, Band band){
        super(name, description, address,timeslot);
        this.setTypeOfGig(typeOfGig);
        this.setBand(band);
        this.setBand(band);
    }
}