package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

    List<Equipment> findByTypeOfEquipmentId( UUID typeOfEquipmentId );

    List<Equipment> findByLocationId( UUID typeOfEquipmentId );
}


