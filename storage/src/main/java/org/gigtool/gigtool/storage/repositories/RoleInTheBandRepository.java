package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RoleInTheBandRepository extends JpaRepository<RoleInTheBand, UUID> {

    @Query("SELECT COUNT(b) FROM Band b JOIN b.listOfRole r WHERE r.id = :roleId")
    int countBandsWithRole(@Param("roleId") UUID roleId);
}
