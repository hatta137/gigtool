package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

}


