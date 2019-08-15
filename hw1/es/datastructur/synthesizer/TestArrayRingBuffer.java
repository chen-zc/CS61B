package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {

    @Test
    public void testBasicEnqueue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(2);
        arb.enqueue(3);
        int expected = 3;
        assertEquals((Integer) expected, arb.peek());
    }

    @Test
    public void testBasicDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(2);
        arb.enqueue(3);
        assertEquals((Integer) 3, arb.dequeue());
    }

    @Test
    public void testEnqueueDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        arb.enqueue(3);
        arb.enqueue(4);

        /* Testing basic dequeue. */
        assertEquals((Integer) 3, arb.dequeue());
        assertEquals((Integer) 4, arb.dequeue());

        /* Testing wrap around*/
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals((Integer) 1, arb.dequeue());
        assertEquals((Integer) 2, arb.dequeue());
        arb.enqueue(4);
        arb.enqueue(5);
        assertEquals((Integer) 3, arb.dequeue());
        assertEquals((Integer) 4, arb.dequeue());
        assertEquals((Integer) 5, arb.dequeue());
    }

    @Test
    public void testEquals() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        arb.enqueue(4);
        ArrayRingBuffer<Integer> o = new ArrayRingBuffer<>(3);
        o.enqueue(4);
        ArrayRingBuffer<Integer> o2 = new ArrayRingBuffer<>(1);
        o2.enqueue(3);
        assertTrue(arb.equals(o));
        assertFalse(arb.equals(o2));
    }

}
