package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.TypeOfLocation;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfLocationResponse {

    private UUID id;
    private String name;
    private String description;

    public TypeOfLocationResponse( TypeOfLocation typeOfLocation ) {
        id = typeOfLocation.getId();
        name = typeOfLocation.getName();
        description = typeOfLocation.getDescription();
    }
}
