package org.gigtool.gigtool.storage;

import org.gigtool.gigtool.storage.model.Dimension;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Dimension.class)
public class DimensionTest extends BeforeAllTests{


    @Test
    void testCube(){

        assertEquals(88000, equipment1.getDimension().getCuboidVolume());

    }
}
