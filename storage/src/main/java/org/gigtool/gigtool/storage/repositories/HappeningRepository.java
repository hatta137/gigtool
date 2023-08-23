package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Happening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HappeningRepository extends JpaRepository<Happening, UUID> {

    @Query("SELECT h FROM Happening h " +
            "LEFT JOIN h.band b " +
            "WHERE (h.startTime <= :endTime AND h.endTime >= :startTime) " +
            "AND (:equipment MEMBER OF h.equipmentList " +
            "OR (:equipment MEMBER OF b.equipmentList ))")
    List<Happening> findOverlappingHappeningsWithEquipment(LocalDateTime startTime,
                                                           LocalDateTime endTime,
                                                           Equipment equipment);
}
