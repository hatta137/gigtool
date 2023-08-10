package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private UUID id;
    private AddressResponse addressResponse;
    private TypeOfLocationResponse typeOfLocationResponse;
    private List<Equipment> equipments;

    public LocationResponse(Location location) {
        id = location.getId();
        addressResponse = new AddressResponse( location.getAddress() );
        typeOfLocationResponse = new TypeOfLocationResponse( location.getTypeOfLocation() );
        equipments = location.getEquipments();
    }
}
