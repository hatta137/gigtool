package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.model.TypeOfLocation;
import org.gigtool.gigtool.storage.repositories.LocationRepository;
import org.gigtool.gigtool.storage.repositories.TypeOfLocationRepository;
import org.gigtool.gigtool.storage.services.model.EquipmentResponse;
import org.gigtool.gigtool.storage.services.model.LocationResponse;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TypeOfLocationService {

    private final TypeOfLocationRepository typeOfLocationRepository;

    private final LocationRepository locationRepository;



    public TypeOfLocationService(TypeOfLocationRepository typeOfLocationRepository, LocationRepository locationRepository) {
        this.typeOfLocationRepository = typeOfLocationRepository;
        this.locationRepository = locationRepository;
    }



    @Transactional
    public ResponseEntity<TypeOfLocationResponse> addTypeOfLocation( TypeOfLocationCreate typeOfLocationCreate ) {

        if (typeOfLocationCreate.getName() == null || typeOfLocationCreate.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        TypeOfLocation typeOfLocation = new TypeOfLocation(
                typeOfLocationCreate.getName(),
                typeOfLocationCreate.getDescription()
        );

        TypeOfLocation savedTypeOfLocation = typeOfLocationRepository.saveAndFlush( typeOfLocation );

        return ResponseEntity.accepted().body( new TypeOfLocationResponse( savedTypeOfLocation ));
    }


    public ResponseEntity<List<TypeOfLocationResponse>> getAllTypeOfLocation() {

        List<TypeOfLocation> typeOfLocationList = typeOfLocationRepository.findAll();

        List<TypeOfLocationResponse> responseList = typeOfLocationList
                .stream()
                .map(TypeOfLocationResponse::new)
                .toList();

        return ResponseEntity.status(200).body( responseList );
    }

    public ResponseEntity<TypeOfLocationResponse> getTypeOfLocationById( UUID id ) {

        Optional<TypeOfLocation> foundTypeOfLocation = typeOfLocationRepository.findById( id );

        if (foundTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.accepted().body( new TypeOfLocationResponse( foundTypeOfLocation.get() ));

    }

    public ResponseEntity<TypeOfLocationResponse> updateTypeOfLocation( UUID id, TypeOfLocationCreate typeOfLocationCreate ) {

        if (typeOfLocationCreate.getName() == null || typeOfLocationCreate.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<TypeOfLocation> existingTypeOfLocation = typeOfLocationRepository.findById( id );

        if (existingTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();

        TypeOfLocation typeOfLocationToUpdate = existingTypeOfLocation.get();

        typeOfLocationToUpdate.setName(typeOfLocationCreate.getName());

        typeOfLocationToUpdate.setDescription(typeOfLocationCreate.getDescription());

        TypeOfLocation savedTypeOfLocation = typeOfLocationRepository.saveAndFlush( typeOfLocationToUpdate );

        return ResponseEntity.ok().body( new TypeOfLocationResponse( savedTypeOfLocation ));
    }

    public ResponseEntity<TypeOfLocationResponse> deleteTypeOfLocation( UUID id ) {

        Optional<TypeOfLocation> foundTypeOfLocation = typeOfLocationRepository.findById( id );

        if (foundTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();

        List<Location> foundLocationByTypeOfLocation = locationRepository.findByTypeOfLocationId( id );

        if (!foundLocationByTypeOfLocation.isEmpty())
            return ResponseEntity.badRequest().build();

        TypeOfLocation typeOfLocationToDelete = foundTypeOfLocation.get();

        typeOfLocationRepository.delete(typeOfLocationToDelete);

        return ResponseEntity.accepted().build();
    }
}
