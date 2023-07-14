package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.TypeOfLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeOfLocationRepository extends JpaRepository<TypeOfLocation, UUID> {
}
