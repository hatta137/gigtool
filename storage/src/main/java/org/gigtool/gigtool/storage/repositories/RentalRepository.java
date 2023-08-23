package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {

    @Query("SELECT r FROM Rental r " +
            "WHERE (r.startTime <= :newEndTime AND r.endTime >= :newStartTime) ")
    List<Rental> findOverlappingRentals(LocalDateTime newStartTime, LocalDateTime newEndTime);
}
