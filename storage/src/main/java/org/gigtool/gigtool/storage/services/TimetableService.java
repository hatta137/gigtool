package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.AddressRepository;
import org.gigtool.gigtool.storage.repositories.EquipmentRepository;
import org.gigtool.gigtool.storage.repositories.HappeningRepository;
import org.gigtool.gigtool.storage.services.model.CalcResponse;
import org.gigtool.gigtool.storage.services.model.TimetableResponse;
import org.gigtool.gigtool.storage.services.model.WhereismyequipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    /**
     * Retrieves a list of all happenings in the system for the timetable.
     *
     * @return A response entity containing a list of timetable responses.
     */
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
     * Retrieves a filtered list of happenings based on name and description for the timetable.
     *
     * @param name        The name to filter happenings by.
     * @param description The description to filter happenings by.
     * @return A response entity containing a filtered list of timetable responses.
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
     * Calculates and retrieves the total values (weight, volume, costs) of equipment used in a happening.
     *
     * @param happeningId The unique identifier of the happening to calculate total values for.
     * @return A response entity containing calculated total values for the specified happening.
     */
    public  ResponseEntity<CalcResponse> getTotalValuesOfHappening(UUID happeningId) {

        int totalWeight = 0;
        int totalCuboidVolume = 0;
        float totalCosts = 0;

        Optional<Happening> existingHappening = happeningRepository.findById(happeningId);

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
     * Retrieves the locations of equipment used in a happening.
     *
     * @param id The unique identifier of the happening to retrieve equipment locations from.
     * @return A response entity containing a list of equipment locations within the specified happening.
     */
    public ResponseEntity<List<WhereismyequipmentResponse>> getLocationsOfEquipmentFromHappening(UUID id) {

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

        List<WhereismyequipmentResponse> locationList = new ArrayList<>();

        for (Equipment equipment: equipmentList) {
            locationList.add(new WhereismyequipmentResponse(equipment.getId(), equipment.getLocation().getId()));
        }

        return ResponseEntity.ok(locationList);
    }
}
