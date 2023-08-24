package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.Gig;
import org.gigtool.gigtool.storage.model.TypeOfGig;

@Getter
@Setter
public class GigResponse extends HappeningResponse {
    private TypeOfGigResponse typeOfGig;
    private BandResponse band;

    public GigResponse(Gig gig){
        super(gig);
        this.typeOfGig = new TypeOfGigResponse(gig.getTypeOfGig());
        this.band = new BandResponse(gig.getBand());
    }
}
