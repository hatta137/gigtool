package org.gigtool.gigtool.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeOfGig extends JpaRepository<TypeOfGig, UUID> {
}
