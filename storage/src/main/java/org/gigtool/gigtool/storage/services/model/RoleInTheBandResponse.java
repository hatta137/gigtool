package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.RoleInTheBand;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleInTheBandResponse {
    private UUID id;
    private String name;
    private String description;

    public RoleInTheBandResponse(RoleInTheBand roleInTheBand){
        this.id = roleInTheBand.getId();
        this.name = roleInTheBand.getName();
        this.description = roleInTheBand.getDescription();
    }
}
