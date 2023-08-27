package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Genre;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenreResponse {
    private UUID id;
    private String name;
    private String description;

    public GenreResponse(Genre genre){
        this.id = genre.getId();
        this.name = genre.getName();
        this.description = genre.getDescription();
    }
}
