package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface GigRepository extends JpaRepository<Gig, UUID> {

    @Query("SELECT g FROM Gig g " +
            "WHERE (g.startTime <= :newEndTime AND g.endTime >= :newStartTime) ")
    List<Gig> findOverlappingGigs(LocalDateTime newStartTime, LocalDateTime newEndTime);

    List<Gig> findGigsByBand(Band band);
}
