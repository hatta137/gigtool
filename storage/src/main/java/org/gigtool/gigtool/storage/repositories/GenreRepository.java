package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
}
