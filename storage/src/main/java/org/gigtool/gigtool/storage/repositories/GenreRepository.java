package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    @Query("SELECT COUNT(b) FROM Band b WHERE b.genre.id = :genreId")
    int countBandsWithGenre(@Param("genreId") UUID genreId);

}
