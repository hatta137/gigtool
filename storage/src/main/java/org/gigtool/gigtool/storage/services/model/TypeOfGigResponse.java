package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Genre;
import org.gigtool.gigtool.storage.model.TypeOfGig;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TypeOfGigResponse {
    private UUID id;
    private String name;
    private String description;

    public TypeOfGigResponse(TypeOfGig typeOfGig){
        this.id = typeOfGig.getId();
        this.name = typeOfGig.getName();;
        this.description = typeOfGig.getDescription();
    }
}
