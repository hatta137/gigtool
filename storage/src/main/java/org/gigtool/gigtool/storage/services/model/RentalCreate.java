package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RentalCreate extends HappeningCreate {
    private String responsiblePerson;
}
