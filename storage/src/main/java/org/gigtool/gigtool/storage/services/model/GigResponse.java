package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.Gig;
import org.gigtool.gigtool.storage.model.TypeOfGig;

@NoArgsConstructor
@Getter
@Setter
public class GigResponse extends HappeningResponse {
    private TypeOfGig typeOfGig;
    private Band band;

    public GigResponse(Gig gig){
        this.setName(gig.getName());
        this.setStartTime(gig.getStartTime());
        this.setEndTime(gig.getEndTime());
        this.setDescription(gig.getDescription());
        this.setAddress(gig.getAddress());
        this.setEquipmentList(gig.getEquipmentList());
        this.setId(gig.getId());
        this.typeOfGig = gig.getTypeOfGig();
        this.band = gig.getBand();
    }
}
