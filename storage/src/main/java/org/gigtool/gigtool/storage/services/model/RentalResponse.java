package org.gigtool.gigtool.storage.services.model;

import lombok.*;
import org.gigtool.gigtool.storage.model.Rental;

@NoArgsConstructor
@Getter
@Setter
public class RentalResponse extends HappeningResponse {
    private String responsiblePerson;

    public RentalResponse(Rental rental) {
        this.setName(rental.getName());
        this.setStartTime(rental.getStartTime());
        this.setEndTime(rental.getEndTime());
        this.setDescription(rental.getDescription());
        this.setAddress(rental.getAddress());
        this.setEquipmentList(rental.getEquipmentList());
        this.setId(rental.getId());
        this.responsiblePerson = rental.getResponsiblePerson();
    }
}

