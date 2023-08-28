package org.gigtool.gigtool.storage.services.model;


import lombok.Getter;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Happening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class HappeningResponse {

    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private AddressResponse address;
    private List<EquipmentResponse> equipmentList;

    public HappeningResponse( Happening happening ) {
        this.name = happening.getName();
        this.startTime = happening.getStartTime();
        this.endTime = happening.getEndTime();
        this.description = happening.getDescription();
        this.address = new AddressResponse(happening.getAddress());
        this.equipmentList = happening.getEquipmentList().stream().map( EquipmentResponse::new ).toList();
        this.id = happening.getId();
    }


}
