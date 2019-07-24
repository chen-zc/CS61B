import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    /**
     * Tests for Flik
     */
    @Test
    public void test() {
        assertTrue(Flik.isSameNumber(1, 1));
        assertFalse(Flik.isSameNumber(1, 2));
        assertTrue(Flik.isSameNumber(127, 127));
        //assertTrue(Flik.isSameNumber(128, 128));
        assertTrue(Flik.isSameNumber(129, 129));

    }

}
