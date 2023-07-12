package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.Genre;
import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BandRepositoryTest {

    @Autowired
    private BandRepository bandRepository;

   /* @Test
    public void testSave() {
        Band band = getRandomBand();
        assertNotNull(band);

        Band savedBand = bandRepository.saveAndFlush(band);
        assertNotNull(savedBand);
        assertEquals(savedBand.getId(), band.getId());
        assertEquals(savedBand.getName(), band.getName());
        assertEquals(savedBand.getGenre(), band.getGenre());
        assertEquals(savedBand.getRoleInTheBand(0), band.getRoleInTheBand(0));
    }

    public Band getRandomBand() {
        return new Band(
                UUID.randomUUID() + "Name",
                new Genre(UUID.randomUUID() + "Name", UUID.randomUUID() + "Description"),
                new RoleInTheBand(UUID.randomUUID() + "Name", UUID.randomUUID() + "Description")
        );
    }*/
}
