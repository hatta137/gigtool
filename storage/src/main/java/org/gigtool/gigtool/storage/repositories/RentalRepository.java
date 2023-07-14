package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
}
