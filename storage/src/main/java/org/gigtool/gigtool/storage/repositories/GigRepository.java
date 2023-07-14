package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Gig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GigRepository extends JpaRepository<Gig, UUID> {
}
