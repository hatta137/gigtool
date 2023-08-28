package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Gig;
import org.gigtool.gigtool.storage.model.Happening;

@Setter
@Getter
public class TimetableResponse extends HappeningResponse {

    private String type;

    public TimetableResponse( Happening happening ) {
        super(happening);

        if (happening instanceof Gig){
            this.type = "Gig";
        }else{
            this.type = "Rental";
        }
    }
}
