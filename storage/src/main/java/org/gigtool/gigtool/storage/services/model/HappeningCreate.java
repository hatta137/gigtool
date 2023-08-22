package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class HappeningCreate {

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private UUID  address;


}
