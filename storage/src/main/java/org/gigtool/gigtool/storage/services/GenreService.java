package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.Genre;
import org.gigtool.gigtool.storage.repositories.GenreRepository;
import org.gigtool.gigtool.storage.services.model.GenreCreate;
import org.gigtool.gigtool.storage.services.model.GenreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dario
 * Service class for managing genre operations in the application.
 */
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Adds a new genre with the provided name and description.
     *
     * @param genreCreate An object containing the details needed to create a new genre.
     * @return A response entity indicating the outcome of the genre addition operation.
     */
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

    /**
     * Retrieves a list of all available genres.
     *
     * @return A response entity containing a list of genre responses.
     */
    public ResponseEntity<List<GenreResponse>> getAllGenres() {

        List<Genre> genreList = genreRepository.findAll();

        List<GenreResponse> responseList = genreList.stream()
                .map(GenreResponse::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    /**
     * Retrieves detailed information about a genre based on its unique identifier.
     *
     * @param genreId The unique identifier of the genre to retrieve information for.
     * @return A response entity containing the genre's detailed information if found, or a not found response.
     */
    public ResponseEntity<GenreResponse> getGenreById(UUID genreId) {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);

        return genreOptional.map(genre -> ResponseEntity.ok(new GenreResponse(genre)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates specific details of a genre based on the provided information.
     *
     * @param genreId The unique identifier of the genre to be updated.
     * @param genreRequest An object containing the genre's updated information.
     * @return A response entity indicating the outcome of the genre update operation.
     */
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

    /**
     * Deletes a genre based on its unique identifier.
     *
     * @param genreId The unique identifier of the genre to be deleted.
     * @return A response entity indicating the outcome of the genre deletion operation.
     */
    public ResponseEntity<String> deleteGenre(UUID genreId) {

        if (genreId == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<Genre> existingGenre = genreRepository.findById(genreId);

        if (existingGenre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int bandsWithGenre = genreRepository.countBandsWithGenre(genreId);

        if(bandsWithGenre > 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Genre has a relation to a Band. You cannot delete this Genre!");
        }

        genreRepository.delete(existingGenre.get());

        return ResponseEntity.ok("Genre is deleted");
    }
}
