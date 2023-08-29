package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.TypeOfLocation;
import org.gigtool.gigtool.storage.repositories.LocationRepository;
import org.gigtool.gigtool.storage.repositories.TypeOfLocationRepository;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * @author Hendrik Lendeckel
 * Service class for managing type of location-related operations.
 */
@Service
public class TypeOfLocationService {

    private final TypeOfLocationRepository typeOfLocationRepository;
    private final LocationRepository locationRepository;

    public TypeOfLocationService( TypeOfLocationRepository typeOfLocationRepository,
                                  LocationRepository locationRepository ) {
        this.typeOfLocationRepository = typeOfLocationRepository;
        this.locationRepository = locationRepository;
    }

    /**
     * Adds a new type of location.
     *
     * @param typeOfLocationCreate The details of the type of location to be added.
     * @return A ResponseEntity containing the added type of location response or a bad request if input is invalid.
     */
    @Transactional
    public ResponseEntity<TypeOfLocationResponse> addTypeOfLocation( TypeOfLocationCreate typeOfLocationCreate ) {

        if (typeOfLocationCreate.getName() == null || typeOfLocationCreate.getDescription() == null)
            return ResponseEntity.badRequest().build();


        TypeOfLocation typeOfLocation = new TypeOfLocation(
                typeOfLocationCreate.getName(),
                typeOfLocationCreate.getDescription()
        );

        TypeOfLocation savedTypeOfLocation = typeOfLocationRepository.saveAndFlush( typeOfLocation );

        return ResponseEntity.accepted().body( new TypeOfLocationResponse( savedTypeOfLocation ));
    }

    /**
     * Retrieves a list of all types of location.
     *
     * @return A ResponseEntity containing a list of type of location responses.
     */
    public ResponseEntity<List<TypeOfLocationResponse>> getAllTypeOfLocation() {

        List<TypeOfLocation> typeOfLocationList = typeOfLocationRepository.findAll();

        List<TypeOfLocationResponse> responseList = typeOfLocationList
                .stream()
                .map( TypeOfLocationResponse::new )
                .toList();

        return ResponseEntity.status(200).body( responseList );
    }

    /**
     * Retrieves type of location by its ID.
     *
     * @param id The ID of the type of location to retrieve.
     * @return A ResponseEntity containing the type of location response or not found if the type of location doesn't exist.
     */
    public ResponseEntity<TypeOfLocationResponse> getTypeOfLocationById( UUID id ) {

        Optional<TypeOfLocation> foundTypeOfLocation = typeOfLocationRepository.findById( id );

        if (foundTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.accepted().body( new TypeOfLocationResponse( foundTypeOfLocation.get() ));
    }

    /**
     * Updates an existing type of location.
     *
     * @param id                     The ID of the type of location to update.
     * @param typeOfLocationCreate The updated type of location details.
     * @return A ResponseEntity containing the updated type of location response or not found if the type of location doesn't exist.
     */
    public ResponseEntity<TypeOfLocationResponse> updateTypeOfLocation( UUID id, TypeOfLocationCreate typeOfLocationCreate ) {

        if (typeOfLocationCreate.getName() == null || typeOfLocationCreate.getDescription() == null)
            return ResponseEntity.badRequest().build();


        Optional<TypeOfLocation> existingTypeOfLocation = typeOfLocationRepository.findById( id );

        if (existingTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();

        TypeOfLocation typeOfLocationToUpdate = existingTypeOfLocation.get();

        typeOfLocationToUpdate.setName(typeOfLocationCreate.getName());

        typeOfLocationToUpdate.setDescription(typeOfLocationCreate.getDescription());

        TypeOfLocation savedTypeOfLocation = typeOfLocationRepository.saveAndFlush( typeOfLocationToUpdate );

        return ResponseEntity.ok().body( new TypeOfLocationResponse( savedTypeOfLocation ));
    }

    /**
     * Deletes an existing type of location.
     *
     * @param id The ID of the type of location to delete.
     * @return A ResponseEntity indicating the success of the deletion or not found if the type of location doesn't exist.
     */
    public ResponseEntity<String> deleteTypeOfLocation( UUID id ) {

        if (id == null)
            return ResponseEntity.badRequest().body("No ID");

        Optional<TypeOfLocation> foundTypeOfLocation = typeOfLocationRepository.findById( id );

        if (foundTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();

        List<Location> foundLocationByTypeOfLocation = locationRepository.findByTypeOfLocationId( id );

        if (!foundLocationByTypeOfLocation.isEmpty())
            return ResponseEntity.badRequest().build();

        TypeOfLocation typeOfLocationToDelete = foundTypeOfLocation.get();

        typeOfLocationRepository.delete( typeOfLocationToDelete );

        return ResponseEntity.ok("Type Of Location deleted");
    }
}
