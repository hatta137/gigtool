package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Happening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HappeningRepository extends JpaRepository<Happening, UUID> {
}
