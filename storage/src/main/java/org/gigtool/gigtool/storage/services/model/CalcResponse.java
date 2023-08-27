package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Gig;
import org.gigtool.gigtool.storage.model.Happening;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CalcResponse {

    private UUID id;
    private String type;
    private String name;
    private int totalWeight;
    private int totalCuboidVolume;
    private float totalCosts;

    public CalcResponse( Happening happening, int totalWeight, int totalVolume, float totalCosts ) {
        this.id = happening.getId();
        this.name = happening.getName();
        this.totalWeight = totalWeight;
        this.totalCuboidVolume = totalVolume;
        this.totalCosts = totalCosts;
        if (happening instanceof Gig){
            this.type = "Gig";
        }else{
            this.type = "Rental";
        }
    }
}
