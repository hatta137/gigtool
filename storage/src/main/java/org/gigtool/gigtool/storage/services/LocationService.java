package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.TypeOfLocation;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.gigtool.gigtool.storage.repositories.LocationRepository;
import org.gigtool.gigtool.storage.repositories.TypeOfLocationRepository;
import org.gigtool.gigtool.storage.services.model.LocationCreate;
import org.gigtool.gigtool.storage.services.model.LocationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final AddressRepository addressRepository;
    private final TypeOfLocationRepository typeOfLocationRepository;

    public LocationService(LocationRepository locationRepository, AddressRepository addressRepository, TypeOfLocationRepository typeOfLocationRepository) {
        this.locationRepository = locationRepository;
        this.addressRepository = addressRepository;
        this.typeOfLocationRepository = typeOfLocationRepository;
    }

    @Transactional
    public ResponseEntity<LocationResponse> addLocation(LocationCreate locationCreate) {

        if (locationCreate.getAddressId() == null || locationCreate.getTypeOfLocationId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Address address = addressRepository.findById(locationCreate.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + locationCreate.getAddressId()));

        TypeOfLocation typeOfLocation = typeOfLocationRepository.findById(locationCreate.getTypeOfLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Type of location not found with id: " + locationCreate.getTypeOfLocationId()));

        Location location = new Location(address, typeOfLocation);

        Location savedLocation = locationRepository.saveAndFlush(location);

        return ResponseEntity.accepted().body(new LocationResponse(savedLocation));
    }

    public ResponseEntity<List<LocationResponse>> getAllLocation() {

        List<Location> locationList = locationRepository.findAll();

        List<LocationResponse> responseList = locationList
                .stream()
                .map(LocationResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(200).body( responseList );
    }

    public ResponseEntity<LocationResponse> getLocationById( UUID id ) {
        Optional<Location> foundLocation = locationRepository.findById( id );

        if (foundLocation.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.accepted().body( new LocationResponse( foundLocation.get() ));
    }

    public ResponseEntity<LocationResponse> updateLocation( UUID id, LocationCreate locationCreate ) {

        Optional<Location> existingLocation = locationRepository.findById( id );

        if (existingLocation.isEmpty())
            throw new RuntimeException( "Location not found with id: " + id );

        Location locationToUpdate = existingLocation.get();

        if (locationCreate.getTypeOfLocationId() != null) {

            Optional<TypeOfLocation> typeOfLocation = typeOfLocationRepository.findById( locationCreate.getTypeOfLocationId() );
            locationToUpdate.setTypeOfLocation( typeOfLocation.get() );
        }

        if (locationCreate.getAddressId() != null) {

            Optional<Address> address = addressRepository.findById( locationCreate.getAddressId() );
            locationToUpdate.setAddress( address.get() );
        }

        Location savedLocation = locationRepository.saveAndFlush( locationToUpdate );

        return ResponseEntity.ok().body( new LocationResponse( savedLocation ));
    }

    public ResponseEntity<LocationResponse> deleteLocation( UUID id ) {

        Optional<Location> foundLocation = locationRepository.findById( id );

        if (foundLocation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Location locationToDelete = foundLocation.get();

        locationRepository.delete(locationToDelete);

        return ResponseEntity.accepted().build();
    }
}

























