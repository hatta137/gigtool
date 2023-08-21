package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.TypeOfGig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TypeOfGigRepository extends JpaRepository<TypeOfGig, UUID> {
    @Query("SELECT COUNT(g) FROM Gig g WHERE g.typeOfGig.id = :togId")
    int countBandsWithGenre(@Param("togId") UUID togId);
}
