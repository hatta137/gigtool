package org.gigtool.gigtool.storage.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Author: Robin Harris
 * This class inherits from the Happening class and represents a performance by the musician.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Gig extends Happening {

    @ManyToOne
    private TypeOfGig typeOfGig;
    @ManyToOne
    private Band band;


}