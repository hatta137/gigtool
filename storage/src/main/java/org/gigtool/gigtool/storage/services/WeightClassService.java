package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Address;
import org.gigtool.gigtool.storage.model.WeightClass;
import org.gigtool.gigtool.storage.repositories.WeightClassRepository;
import org.gigtool.gigtool.storage.services.model.AddressResponse;
import org.gigtool.gigtool.storage.services.model.WeightClassCreate;
import org.gigtool.gigtool.storage.services.model.WeightClassResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WeightClassService {

    private final WeightClassRepository weightClassRepository;

    public WeightClassService(WeightClassRepository weightClassRepository) {
        this.weightClassRepository = weightClassRepository;
    }

    public ResponseEntity<WeightClassResponse> addWeightClass( WeightClassCreate weightClassCreate ) {

        if (weightClassCreate.getName() == null || weightClassCreate.getDescription() == null)
            return ResponseEntity.badRequest().build();

        WeightClass weightClass = new WeightClass(
                weightClassCreate.getName(),
                weightClassCreate.getDescription(),
                weightClassCreate.getWeightStart(),
                weightClassCreate.getDuration()
        );

        WeightClass savedWeightClass = weightClassRepository.saveAndFlush( weightClass );

        return ResponseEntity.accepted().body( new WeightClassResponse( savedWeightClass ));
    }
    public ResponseEntity<List<WeightClassResponse>> getAllWeightClass() {

        List<WeightClass> weightClassList = weightClassRepository.findAll();

        List<WeightClassResponse> responseList = weightClassList
                .stream()
                .map(WeightClassResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(200).body( responseList );
    }
    public ResponseEntity<WeightClassResponse> getWeightClassById(UUID id) {

        Optional<WeightClass> foundWeightClass = weightClassRepository.findById(id);

        if (foundWeightClass.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.accepted().body( new WeightClassResponse( foundWeightClass.get() ));
    }
    public ResponseEntity<WeightClassResponse> updateWeightClass( UUID id, WeightClassCreate weightClassCreate ) {

        Optional<WeightClass> existingWeightClass = weightClassRepository.findById( id );

        if (existingWeightClass.isEmpty())
            throw new RuntimeException( "WeightClass not found with id: " + id );

        WeightClass weightClassToUpdate = existingWeightClass.get();

        if ( weightClassCreate.getName() != null ) {
            weightClassToUpdate.setName(weightClassCreate.getName());
        }
        if ( weightClassCreate.getDescription() != null ) {
            weightClassToUpdate.setDescription(weightClassCreate.getDescription());
        }
        if ( weightClassCreate.getWeightStart() != 0 ) {
            weightClassToUpdate.setWeightStart(weightClassCreate.getWeightStart());
        } //TODO @Hendrik Check wenn duration nicht passt?
        if ( weightClassCreate.getDuration() != 0 ) {
            weightClassToUpdate.setDuration(weightClassCreate.getDuration());
        }

        WeightClass savedWeightClass = weightClassRepository.saveAndFlush( weightClassToUpdate );

        return ResponseEntity.ok().body( new WeightClassResponse( savedWeightClass ));
    }
    public ResponseEntity<WeightClassResponse> deleteWeightClass(UUID id) {
        Optional<WeightClass> foundWeightClass = weightClassRepository.findById(id);

        if (foundWeightClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        WeightClass weightClassToDelete = foundWeightClass.get();

        weightClassRepository.delete(weightClassToDelete);

        return ResponseEntity.accepted().build();
    }
}
