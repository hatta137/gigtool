package org.gigtool.gigtool.storage.services.model;


import lombok.Getter;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.Equipment;

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
    private Address address;
    private List<Equipment> equipmentList;



}
