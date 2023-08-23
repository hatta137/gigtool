package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Happening;
import org.gigtool.gigtool.storage.repositories.EquipmentRepository;
import org.gigtool.gigtool.storage.repositories.HappeningRepository;
import org.gigtool.gigtool.storage.services.model.HappeningResponse;
import org.gigtool.gigtool.storage.services.model.TimetableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    private final HappeningRepository happeningRepository;
    private final EquipmentRepository equipmentRepository;

    public TimetableService(HappeningRepository happeningRepository, EquipmentRepository equipmentRepository) {
        this.happeningRepository = happeningRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public ResponseEntity<List<TimetableResponse>> getAll(){
        List<Happening> happeningList = happeningRepository.findAll();

        List<TimetableResponse> responseList =
                happeningList
                .stream()
                .map(TimetableResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);

    }
}
