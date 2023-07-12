package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.WeightClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeightClassRepository extends JpaRepository<WeightClass, UUID> {
}
