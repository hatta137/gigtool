package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.TypeOfEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeOfEquipmentRepository extends JpaRepository<TypeOfEquipment, UUID> {
}
