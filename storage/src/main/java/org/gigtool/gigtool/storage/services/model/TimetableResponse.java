package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Gig;
import org.gigtool.gigtool.storage.model.Happening;

@NoArgsConstructor
@Setter
@Getter
public class TimetableResponse extends HappeningResponse {
    private String type;

    public TimetableResponse(Happening happening) {
        this.setName(happening.getName());
        this.setStartTime(happening.getStartTime());
        this.setEndTime(happening.getEndTime());
        this.setDescription(happening.getDescription());
        this.setAddress(happening.getAddress());
        this.setEquipmentList(happening.getEquipmentList());
        this.setId(happening.getId());
        if (happening instanceof Gig){
            this.type = "Gig";
        }else{
            this.type = "Rental";
        }
    }

}
