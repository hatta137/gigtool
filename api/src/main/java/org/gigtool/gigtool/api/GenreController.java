package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.GenreService;
import org.gigtool.gigtool.storage.services.model.GenreCreate;
import org.gigtool.gigtool.storage.services.model.GenreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gig/band/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<GenreResponse> addGenre(@RequestBody GenreCreate genreCreate) {
        return genreService.addGenre(genreCreate);
    }

    @GetMapping
    public ResponseEntity<List<GenreResponse>> allGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> getGenreByID(@PathVariable UUID id) {
        return genreService.getGenreById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponse> updateGenre(@PathVariable UUID id, @RequestBody GenreCreate genreRequest) {
        return genreService.updateGenre(id, genreRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable UUID id) {
        return genreService.deleteGenre(id);
    }
}
