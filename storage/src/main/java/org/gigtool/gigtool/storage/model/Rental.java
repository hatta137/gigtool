package org.gigtool.gigtool.storage.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Author: Hendrik Lendeckel
 * This class represents a loan of equipment.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Rental extends Happening {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String responsiblePerson;
    private LocalDate returnDate;

    public Rental(String name, String description, Address address, Timeslot timeslot, String responsiblePerson, LocalDate returnDate) {
        super(name, description, address, timeslot);
        this.responsiblePerson = responsiblePerson;
        this.returnDate = returnDate;
    }
}
