package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Genre;
import org.gigtool.gigtool.storage.repositories.GenreRepository;
import org.gigtool.gigtool.storage.services.model.GenreCreate;
import org.gigtool.gigtool.storage.services.model.GenreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<GenreResponse> addGenre(GenreCreate genreCreate) {

        if (genreCreate.getName() == null || genreCreate.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        Genre newGenre = new Genre();

        newGenre.setName(genreCreate.getName());
        newGenre.setDescription(genreCreate.getDescription());

        Genre savedGenre = genreRepository.saveAndFlush(newGenre);

        return ResponseEntity.ok(new GenreResponse(savedGenre));
    }

    public ResponseEntity<List<GenreResponse>> getAllGenres() {

        List<Genre> genreList = genreRepository.findAll();

        List<GenreResponse> responseList = genreList.stream()
                .map(GenreResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<GenreResponse> getGenreById(UUID genreId) {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);

        return genreOptional.map(genre -> ResponseEntity.ok(new GenreResponse(genre)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<GenreResponse> updateGenre(UUID genreId, GenreCreate genreRequest) {

        if ((genreId == null) || (genreRequest.getName() == null) || (genreRequest.getDescription() == null) ) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Genre> existingGenre = genreRepository.findById(genreId);

        if (existingGenre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Genre updatedGenre = existingGenre.get();

        updatedGenre.setName(genreRequest.getName());
        updatedGenre.setDescription(genreRequest.getDescription());

        Genre savedGenre = genreRepository.saveAndFlush(updatedGenre);

        return ResponseEntity.ok(new GenreResponse(savedGenre));
    }

    public ResponseEntity<String> deleteGenre(UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<Genre> existingGenre = genreRepository.findById(id);

        if (existingGenre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        genreRepository.delete(existingGenre.get());

        return ResponseEntity.ok("Genre is deleted");
    }
}
