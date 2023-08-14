package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.TypeOfEquipment;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfEquipmentResponse {

    private UUID id;
    private String name;
    private String description;

    public TypeOfEquipmentResponse(TypeOfEquipment typeOfEquipment) {
        id = typeOfEquipment.getId();
        name = typeOfEquipment.getName();
        description =  typeOfEquipment.getDescription();
    }
}
