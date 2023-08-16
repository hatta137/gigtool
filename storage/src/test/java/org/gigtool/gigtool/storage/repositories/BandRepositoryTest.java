package org.gigtool.gigtool.storage.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
