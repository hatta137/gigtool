package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.gigtool.gigtool.storage.repositories.EquipmentRepository;
import org.gigtool.gigtool.storage.repositories.HappeningRepository;
import org.gigtool.gigtool.storage.services.model.CalcResponse;
import org.gigtool.gigtool.storage.services.model.LocationResponse;
import org.gigtool.gigtool.storage.services.model.TimetableResponse;
import org.gigtool.gigtool.storage.services.model.whereismyequipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dario
 * Service class for managing timetable operations
 */
@Service
public class TimetableService {

    private final HappeningRepository happeningRepository;
    private final EquipmentRepository equipmentRepository;

    private final AddressRepository addressRepository;

    public TimetableService(HappeningRepository happeningRepository, EquipmentRepository equipmentRepository, AddressRepository addressRepository) {
        this.happeningRepository = happeningRepository;
        this.equipmentRepository = equipmentRepository;
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<List<TimetableResponse>> getAll() {
        List<Happening> happeningList = happeningRepository.findAll();

        List<TimetableResponse> responseList =
                happeningList
                .stream()
                .map(TimetableResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);

    }

    /**
     * Filters a happening by name and description.
     * @param name
     * @param description
     * @return A ResponseEntity containing a list of happening responses.
     */
    public ResponseEntity<List<TimetableResponse>> getFilteredHappenings(String name,  String description) {

        List<Happening> happeningList = happeningRepository.findFilteredHappenings(name, description);

        List<TimetableResponse> responseList =
                happeningList
                        .stream()
                        .map(TimetableResponse::new)
                        .toList();

        return ResponseEntity.ok(responseList);
    }

    /**
     * Sums up the value (each) of weight, volume and costs of a happening by its id.
     * @param id
     * @return A ResponseEntity containing list of calc responses if happenings are existing.
     */
    public  ResponseEntity<CalcResponse> getTotalValuesOfHappening(UUID id) {

        int totalWeight = 0;
        int totalCuboidVolume = 0;
        float totalCosts = 0;

        Optional<Happening> existingHappening = happeningRepository.findById(id);

        if(existingHappening.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Happening happening = existingHappening.get();
        List<Equipment> equipmentList;

        if (happening instanceof Gig gig) {
            equipmentList = gig.getEquipmentList();
            equipmentList.addAll(gig.getBand().getEquipmentList());
            happening.setEquipmentList(equipmentList);
        }else{
            equipmentList = happening.getEquipmentList();
        }


        for (Equipment equipment: equipmentList) {
            totalWeight+= equipment.getWeight();
            totalCuboidVolume+= equipment.getCuboidVolume();
            totalCosts+= equipment.getPrice();
        }

        return ResponseEntity.ok(new CalcResponse(happening, totalWeight, totalCuboidVolume, totalCosts));
    }

    /**
     * Retrieves the location of equipment by its id
     * @param id
     * @return A ResponseEntity containing a list of locations
     */
    public ResponseEntity<List<whereismyequipmentResponse>> getLocationsOfEquipmentFromHappening(UUID id) {

        Optional<Happening> existingHappening = happeningRepository.findById(id);

        if(existingHappening.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Happening happening = existingHappening.get();
        List<Equipment> equipmentList;

        if (happening instanceof Gig gig) {
            equipmentList = gig.getEquipmentList();
            equipmentList.addAll(gig.getBand().getEquipmentList());
            happening.setEquipmentList(equipmentList);
        }else{
            equipmentList = happening.getEquipmentList();
        }

        List<whereismyequipmentResponse> locationList = new ArrayList<>();

        for (Equipment equipment: equipmentList) {
            locationList.add(new whereismyequipmentResponse(equipment.getId(), equipment.getLocation().getId()));
        }

        return ResponseEntity.ok(locationList);
    }

}
