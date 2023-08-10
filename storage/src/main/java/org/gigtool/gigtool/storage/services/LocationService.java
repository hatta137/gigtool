package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.TypeOfLocation;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.gigtool.gigtool.storage.repositories.LocationRepository;
import org.gigtool.gigtool.storage.repositories.TypeOfLocationRepository;
import org.gigtool.gigtool.storage.services.model.LocationCreate;
import org.gigtool.gigtool.storage.services.model.LocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final AddressRepository addressRepository;
    private final TypeOfLocationRepository typeOfLocationRepository;

    public LocationService(LocationRepository locationRepository, AddressRepository addressRepository, TypeOfLocationRepository typeOfLocationRepository) {
        this.locationRepository = locationRepository;
        this.addressRepository = addressRepository;
        this.typeOfLocationRepository = typeOfLocationRepository;
    }

    private ResponseEntity<LocationResponse> addLocation(LocationCreate locationCreate) {

        if (locationCreate.getAddressId() == null || locationCreate.getTypeOfLocationId() == null) {
            // If any required information is missing, return a bad request response
            return ResponseEntity.badRequest().build();
        }

        Optional<Address> address = addressRepository.findById(locationCreate.getAddressId());
        Optional<TypeOfLocation> typeOfLocation = typeOfLocationRepository.findById(locationCreate.getTypeOfLocationId());

        Location location = new Location(
                address.get(),
                typeOfLocation.get()
        );

        Location savedLocation = locationRepository.saveAndFlush( location );

        return ResponseEntity.accepted().body( new LocationResponse( savedLocation ));
    }

    private ResponseEntity<List<LocationResponse>> getAllLocation() {
        return null;
    }

    private ResponseEntity<LocationResponse> getLocationById(UUID id) {
        return null;
    }

    private ResponseEntity<LocationResponse> updateLocation(UUID id, LocationCreate locationCreate) {
        return null;
    }

    private ResponseEntity<LocationResponse> deleteLocation(UUID id) {
        return null;
    }
}
