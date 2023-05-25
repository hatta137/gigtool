

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DimensionTest extends BeforeAllTests{


    @Test
    void testCube(){

        assertEquals(88000, equipment1.getDimension().getCuboidVolume());

    }
}
