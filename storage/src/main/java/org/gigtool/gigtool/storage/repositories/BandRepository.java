package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BandRepository extends JpaRepository<Band, UUID> {

    @Query("SELECT COUNT(g) FROM Gig g WHERE g.band.id = :bandId")
    int countGigsForBand(@Param("bandId") UUID bandId);

    @Query("SELECT b FROM Band b " +
            "WHERE :equipmentId MEMBER OF b.equipmentList")
    List<Band> findByEquipmentId(UUID equipmentId);
}
