package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DimensionRepository extends JpaRepository<Dimension, UUID> {

}
