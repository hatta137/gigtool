package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleInTheBandRepository extends JpaRepository<RoleInTheBand, UUID> {
}
