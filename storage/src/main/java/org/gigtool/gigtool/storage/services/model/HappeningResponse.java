package org.gigtool.gigtool.storage.services.model;


import lombok.Getter;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Happening;

import java.time.LocalDate;
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

    public HappeningResponse(Happening happening) {
        this.setName(happening.getName());
        this.setStartTime(happening.getStartTime());
        this.setEndTime(happening.getEndTime());
        this.setDescription(happening.getDescription());
        this.setAddress(new AddressResponse(happening.getAddress()));
        this.setEquipmentList(happening.getEquipmentList().stream().map(EquipmentResponse::new).toList());
        this.setId(happening.getId());
    }


}
