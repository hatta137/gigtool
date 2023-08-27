package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Equipment;
import org.gigtool.gigtool.storage.model.Happening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HappeningRepository extends JpaRepository<Happening, UUID> {


    @Query("SELECT h FROM Happening h " +
            "WHERE h.address.id = :addressId")
    List<Happening> findByAddressId(@Param("addressId") UUID addressId);

    @Query("SELECT h FROM Happening h " +
            "JOIN h.equipmentList e " +
            "WHERE e.id = :equipmentId")
    List<Happening> findByEquipmentId(@Param("equipmentId") UUID equipmentId);
    @Query("""
            SELECT h FROM Happening h
            LEFT JOIN Gig g ON h.id = g.id
            LEFT JOIN g.band b
            WHERE (h.startTime <= :endTime AND h.endTime >= :startTime)
            AND (:equipment MEMBER OF h.equipmentList
            OR (:equipment MEMBER OF b.equipmentList))""")
    List<Happening> findOverlappingHappeningsWithEquipment(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
                                                           @Param("equipment") Equipment equipment);

    @Query(""" 
            SELECT h FROM Happening h WHERE (:name IS NULL OR LOWER(h.name) LIKE LOWER(concat('%', :name, '%'))
            AND :description IS NULL OR LOWER(h.description) LIKE LOWER(concat('%', :description, '%')))
            """)
    List<Happening> findFilteredHappenings(String name, String description);
}
