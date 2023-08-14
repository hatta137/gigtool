package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
}
