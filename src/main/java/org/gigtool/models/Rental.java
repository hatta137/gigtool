package org.gigtool.models;

import org.gigtool.modules.Happening;
import java.time.LocalDate;

/**
 * Author: Hendrik Lendeckel
 * This class represents a loan of equipment.
 */

public class Rental extends Happening {

    private String responsiblePerson;
    private LocalDate returnDate;

    public Rental(String name, String description, Address address, Timeslot timeslot, String responsiblePerson, LocalDate returnDate) {
        super(name, description, address, timeslot);
        this.responsiblePerson = responsiblePerson;
        this.returnDate = returnDate;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }
    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}
