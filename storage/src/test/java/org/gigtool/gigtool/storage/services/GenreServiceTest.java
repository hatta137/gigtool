package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.GenreCreate;
import org.gigtool.gigtool.storage.services.model.GenreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private TestUtils testUtils;

    private GenreCreate genreToSave;
    private ResponseEntity<GenreResponse> savedGenre;
    private UUID savedGenreId;

    @BeforeEach
    private void setup() {
        genreToSave = testUtils.getRandomGenreCreate();
        savedGenre = genreService.addGenre(genreToSave);
        savedGenreId = Objects.requireNonNull(savedGenre.getBody().getId());
    }

    @Test
    public void testAddGenre() {

        assertEquals(savedGenre.getBody().getName(), genreToSave.getName());
        assertEquals(savedGenre.getBody().getDescription(), genreToSave.getDescription());

        // Negative Test: Try adding a Genre with missing information
        GenreCreate incompleteGenre = new GenreCreate(
                "name",
                null
        );

        ResponseEntity<GenreResponse> negativeResult = genreService.addGenre(incompleteGenre);

        assertFalse(negativeResult.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetAllGenres() {

        GenreCreate genreToSave1 = testUtils.getRandomGenreCreate();
        GenreCreate genreToSave2 = testUtils.getRandomGenreCreate();

        ResponseEntity<GenreResponse> savedGenre1 = genreService.addGenre(genreToSave1);
        ResponseEntity<GenreResponse> savedGenre2 = genreService.addGenre(genreToSave2);

        ResponseEntity<List<GenreResponse>> savedGenreList = genreService.getAllGenres();

        assertNotNull(savedGenreList);
        assertFalse(Objects.requireNonNull(savedGenreList.getBody()).isEmpty());

        assertEquals(3, savedGenreList.getBody().size());

        assertEquals(genreToSave1.getName(), savedGenreList.getBody().get(1).getName());
        assertEquals(genreToSave1.getDescription(), savedGenreList.getBody().get(1).getDescription());

        assertEquals(genreToSave2.getName(), savedGenreList.getBody().get(2).getName());
        assertEquals(genreToSave2.getDescription(), savedGenreList.getBody().get(2).getDescription());
    }

    @Test
    public void testGetGenreById() {

        // Positive test
        ResponseEntity<GenreResponse> genreInDatabaseById = genreService.getGenreById(savedGenreId);

        assertEquals(Objects.requireNonNull(genreInDatabaseById.getBody()).getId(), Objects.requireNonNull(savedGenre.getBody()).getId());
        assertEquals(genreInDatabaseById.getBody().getName(), savedGenre.getBody().getName());
        assertEquals(genreInDatabaseById.getBody().getDescription(), savedGenre.getBody().getDescription());

        // Negative test
        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedGenreId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<GenreResponse> falseGenreInDatabaseId = genreService.getGenreById(randomUUID);

        assertNull(falseGenreInDatabaseId.getBody());
    }

    @Test
    public void testUpdateGenre() {

        // Positive test
        ResponseEntity<GenreResponse> genreBeforeUpdate = savedGenre;

        GenreCreate updateForGenre = new GenreCreate(
                "newName",
                "newDescription"
        );

        ResponseEntity<GenreResponse> updatedGenre = genreService.updateGenre(savedGenreId, updateForGenre);

        assertEquals(genreBeforeUpdate.getBody().getId(), Objects.requireNonNull(updatedGenre.getBody()).getId());
        assertEquals(updatedGenre.getBody().getName(), "newName");
        assertEquals(updatedGenre.getBody().getDescription(), "newDescription");

        // Negative test
        GenreCreate updateForGenreFalse = new GenreCreate(
                "newName",
                null
        );

        ResponseEntity<GenreResponse> updatedGenreFalse = genreService.updateGenre(savedGenreId, updateForGenreFalse);

        assertTrue(updatedGenreFalse.getStatusCode().is4xxClientError());

        UUID randomUUID = UUID.randomUUID();
        while (randomUUID.equals(savedGenreId)) {
            randomUUID = UUID.randomUUID();
        }

        ResponseEntity<String> existingGenreFalse = genreService.deleteGenre(randomUUID);

        assertTrue(existingGenreFalse.getStatusCode().is4xxClientError());
    }

    @Test
    public void testUpdateGenreWithNonExistingId() {
        UUID nonExistingGenreId = UUID.randomUUID();
        GenreCreate updateForNonExistingGenre = new GenreCreate(
                "newName",
                "newDescription"
        );

        ResponseEntity<GenreResponse> updatedGenre = genreService.updateGenre(nonExistingGenreId, updateForNonExistingGenre);

        assertTrue(updatedGenre.getStatusCode().is4xxClientError());
        assertEquals(HttpStatus.NOT_FOUND, updatedGenre.getStatusCode());
    }

    @Test
    public void testDeleteGenre() {
        ResponseEntity<String> deletedGenre = genreService.deleteGenre(savedGenreId);
        assertTrue(deletedGenre.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testDeleteGenreWithNullId() {
        ResponseEntity<String> response = genreService.deleteGenre(null);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteNonExistingGenre() {
        UUID nonExistingGenreId = UUID.randomUUID();
        ResponseEntity<String> response = genreService.deleteGenre(nonExistingGenreId);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
