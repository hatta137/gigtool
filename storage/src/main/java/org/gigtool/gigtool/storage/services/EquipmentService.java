package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.repositories.EquipmentRepository;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }


}
