import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    OffByN offBy5 = new OffByN(5);

    @Test
    public void testEqualChars() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertFalse(offBy5.equalChars('a', 'd'));
    }

}