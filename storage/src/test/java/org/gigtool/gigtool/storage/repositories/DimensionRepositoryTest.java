package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Dimension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DimensionRepositoryTest {

    @Autowired
    private DimensionRepository dimensionRepository;

    @Test
    public void testSave() {
        Dimension dimension = getRandomDimension();
        assertNotNull(dimension);

        Dimension savedDimension = dimensionRepository.saveAndFlush(dimension);
        assertNotNull(savedDimension);
        assertEquals(savedDimension.getId(), dimension.getId());
        assertEquals(savedDimension.getLength(), dimension.getLength());
        assertEquals(savedDimension.getWidth(), dimension.getWidth());
        assertEquals(savedDimension.getHeight(), dimension.getHeight());
    }

    public static Dimension getRandomDimension() {
        RandomGenerator randomGenerator = RandomGenerator.getDefault();
        return new Dimension(
                randomGenerator.nextInt(100),
                randomGenerator.nextInt(100),
                randomGenerator.nextInt(100)
        );
    }
}
