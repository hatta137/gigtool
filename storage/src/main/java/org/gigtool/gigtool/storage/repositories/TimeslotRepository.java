package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimeslotRepository extends JpaRepository<Timeslot, UUID> {
}
