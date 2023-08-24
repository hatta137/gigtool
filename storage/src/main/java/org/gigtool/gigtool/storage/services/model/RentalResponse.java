package org.gigtool.gigtool.storage.services.model;

import lombok.*;
import org.gigtool.gigtool.storage.model.Rental;

@Getter
@Setter
public class RentalResponse extends HappeningResponse {
    private String responsiblePerson;

    public RentalResponse(Rental rental) {
        super(rental);
        this.responsiblePerson = rental.getResponsiblePerson();
    }
}

