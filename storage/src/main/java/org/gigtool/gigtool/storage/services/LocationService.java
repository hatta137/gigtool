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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * @author Hendrik Lendeckel
 * Service class for managing location-related operations.
 */
@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final AddressRepository addressRepository;
    private final TypeOfLocationRepository typeOfLocationRepository;


    public LocationService(LocationRepository locationRepository,
                           AddressRepository addressRepository,
                           TypeOfLocationRepository typeOfLocationRepository) {
        this.locationRepository = locationRepository;
        this.addressRepository = addressRepository;
        this.typeOfLocationRepository = typeOfLocationRepository;
    }

    /**
     * Adds a new location.
     *
     * @param locationCreate The details of the location to be added.
     * @return A ResponseEntity containing the added location response or a bad request if input is invalid.
     */
    @Transactional
    public ResponseEntity<LocationResponse> addLocation( LocationCreate locationCreate ) {

        if (locationCreate.getAddressId() == null || locationCreate.getTypeOfLocationId() == null)
            return ResponseEntity.badRequest().build();


        Address address = addressRepository.findById(locationCreate.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + locationCreate.getAddressId()));

        TypeOfLocation typeOfLocation = typeOfLocationRepository.findById(locationCreate.getTypeOfLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Type of location not found with id: " + locationCreate.getTypeOfLocationId()));

        Location location = new Location(address, typeOfLocation);

        Location savedLocation = locationRepository.saveAndFlush( location );

        return ResponseEntity.accepted().body( new LocationResponse( savedLocation ) );
    }

    /**
     * Retrieves a list of all locations.
     *
     * @return A ResponseEntity containing a list of location responses.
     */
    public ResponseEntity<List<LocationResponse>> getAllLocation() {

        List<Location> locationList = locationRepository.findAll();

        List<LocationResponse> responseList = locationList
                .stream()
                .map(LocationResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(200).body( responseList );
    }

    /**
     * Retrieves location by its ID.
     *
     * @param id The ID of the location to retrieve.
     * @return A ResponseEntity containing the location response or not found if the location doesn't exist.
     */
    public ResponseEntity<LocationResponse> getLocationById( UUID id ) {
        Optional<Location> foundLocation = locationRepository.findById( id );

        if (foundLocation.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.accepted().body( new LocationResponse( foundLocation.get() ));
    }

/*    public ResponseEntity<List<LocationResponse>> getLocationByTypeOfLocationId( UUID TypeOfLocationId ) {

        List<Location> locationList = locationRepository.findByTypeOfLocationId( TypeOfLocationId );

        if (locationList.isEmpty())
            return ResponseEntity.notFound().build();

        List<LocationResponse> responseList = locationList
                .stream().map(LocationResponse::new).toList();

        return ResponseEntity.status(200).body( responseList );
    }

    public ResponseEntity<List<LocationResponse>> getLocationByAddressId( UUID addressId ) {

        List<Location> locationList = locationRepository.findByAddressId( addressId );

        if (locationList.isEmpty())
            return ResponseEntity.notFound().build();

        List<LocationResponse> responseList = locationList
                .stream().map(LocationResponse::new).toList();

        return ResponseEntity.status(200).body( responseList );
    }*/

    /**
     * Updates an existing location.
     *
     * @param id               The ID of the location to update.
     * @param locationCreate The updated location details.
     * @return A ResponseEntity containing the updated location response or not found if the location doesn't exist.
     */
    public ResponseEntity<LocationResponse> updateLocation( UUID id, LocationCreate locationCreate ) {

        if (locationCreate.getAddressId() == null && locationCreate.getTypeOfLocationId() == null)
            return ResponseEntity.badRequest().build();


        Optional<Location> existingLocation = locationRepository.findById( id );

        if (existingLocation.isEmpty())
            return ResponseEntity.notFound().build();

        Location locationToUpdate = existingLocation.get();

        if (locationCreate.getTypeOfLocationId() != null) {

            Optional<TypeOfLocation> typeOfLocationOptional = typeOfLocationRepository.findById(locationCreate.getTypeOfLocationId());

            if (typeOfLocationOptional.isPresent())
                locationToUpdate.setTypeOfLocation(typeOfLocationOptional.get());

        }

        if (locationCreate.getAddressId() != null) {

            Optional<Address> addressOptional = addressRepository.findById(locationCreate.getAddressId());

            if (addressOptional.isPresent())
                locationToUpdate.setAddress(addressOptional.get());

        }

        Location savedLocation = locationRepository.saveAndFlush( locationToUpdate );

        return ResponseEntity.ok().body( new LocationResponse( savedLocation ));
    }

    /**
     * Deletes an existing location.
     *
     * @param id The ID of the location to delete.
     * @return A ResponseEntity indicating the success of the deletion or not found if the location doesn't exist.
     */
    public ResponseEntity<String> deleteLocation( UUID id ) {

        if (id == null)
            return ResponseEntity.badRequest().body("No ID");

        Optional<Location> foundLocation = locationRepository.findById( id );

        if (foundLocation.isEmpty())
            return ResponseEntity.notFound().build();

        Location locationToDelete = foundLocation.get();

        locationRepository.delete(locationToDelete);

        return ResponseEntity.ok("Equipment deleted");
    }
}

























