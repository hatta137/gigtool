package org.gigtool.models;

import org.gigtool.modules.Band;
import org.gigtool.modules.Happening;

/**
 * Author: Robin Harris
 * This class inherits from the Happening class and represents a performance by the musician.
 */

public class Gig extends Happening {

    private TypeOfGig typeOfGig;
    private Band band;

    public Gig(String name, String description, Address address, Timeslot timeslot, TypeOfGig typeOfGig, Band band){
        super(name, description, address,timeslot);
        this.setTypeOfGig(typeOfGig);
        this.setBand(band);
        this.setBand(band);
    }

    public TypeOfGig getTypeOfGig() {
        return typeOfGig;
    }
    public void setTypeOfGig(TypeOfGig typeOfGig) {
        this.typeOfGig = typeOfGig;
    }
    public Band getBand() {
        return band;
    }
    public void setBand(Band band) {
        this.band = band;
    }

}