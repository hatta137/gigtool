package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.WeightClassList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeightClassListRepository extends JpaRepository<WeightClassList, UUID> {
}
