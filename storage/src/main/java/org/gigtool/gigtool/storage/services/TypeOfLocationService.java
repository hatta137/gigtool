package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.TypeOfLocation;
import org.gigtool.gigtool.storage.repositories.TypeOfLocationRepository;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationCreate;
import org.gigtool.gigtool.storage.services.model.TypeOfLocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TypeOfLocationService {

    private final TypeOfLocationRepository typeOfLocationRepository;

    public TypeOfLocationService(TypeOfLocationRepository typeOfLocationRepository) {
        this.typeOfLocationRepository = typeOfLocationRepository;
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

        Optional<TypeOfLocation> existingTypeOfLocation = typeOfLocationRepository.findById( id );

        if (existingTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();

        TypeOfLocation typeOfLocationToUpdate = existingTypeOfLocation.get();

        if (typeOfLocationCreate.getName() != null) {
            typeOfLocationToUpdate.setName(typeOfLocationCreate.getName());
        }

        if (typeOfLocationCreate.getDescription() != null) {
            typeOfLocationToUpdate.setDescription(typeOfLocationCreate.getDescription());
        }

        TypeOfLocation savedTypeOfLocation = typeOfLocationRepository.saveAndFlush( typeOfLocationToUpdate );

        return ResponseEntity.ok().body( new TypeOfLocationResponse( savedTypeOfLocation ));
    }

    public ResponseEntity<TypeOfLocationResponse> deleteTypeOfLocation( UUID id ) {

        Optional<TypeOfLocation> foundTypeOfLocation = typeOfLocationRepository.findById( id );

        if (foundTypeOfLocation.isEmpty())
            return ResponseEntity.notFound().build();


        TypeOfLocation typeOfLocationToDelete = foundTypeOfLocation.get();

        typeOfLocationRepository.delete(typeOfLocationToDelete);

        return ResponseEntity.accepted().build();
    }
}
