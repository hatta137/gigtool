package org.gigtool.gigtool.storage.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Location;


import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private UUID id;
    private AddressResponse addressResponse;
    private TypeOfLocationResponse typeOfLocationResponse;

    public LocationResponse(Location location) {
        id = location.getId();
        addressResponse = new AddressResponse( location.getAddress() );
        typeOfLocationResponse = new TypeOfLocationResponse( location.getTypeOfLocation() );
    }
}
