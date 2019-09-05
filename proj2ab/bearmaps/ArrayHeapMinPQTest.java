package bearmaps;

import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayHeapMinPQTest {

    @Test
    public void testAdd() {
        ArrayHeapMinPQ pq = new ArrayHeapMinPQ();
        pq.add('a', 1);
        pq.add('b', 2);
        pq.add('c', 0.5);
        pq.add('d', 0.4);
        assertEquals(pq.size(), 4);
        System.out.println(pq.pq[1].item);
        System.out.println(pq.pq[2].item);
        System.out.println(pq.pq[3].item);
        System.out.println(pq.pq[4].item);
    }

    @Test
    public void testContain() {
        ArrayHeapMinPQ pq = new ArrayHeapMinPQ();
        pq.add('a', 1);
        pq.add('b', 2);
        assertTrue(pq.contains('a'));
        assertTrue(pq.contains('b'));
        assertFalse(pq.contains('c'));
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ pq = new ArrayHeapMinPQ();
        pq.add('a', 1);
        pq.add('b', 2);
        pq.add('c', 0.5);
        pq.add('d', 0.4);
        assertEquals(pq.getSmallest(), 'd');
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ pq = new ArrayHeapMinPQ();
        pq.add('a', 1);
        pq.add('b', 2);
        pq.add('c', 0.5);
        pq.add('d', 0.4);
        assertEquals(pq.removeSmallest(), 'd');
        assertEquals(pq.size(), 3);
        System.out.println(pq.pq[1].item);
        System.out.println(pq.pq[2].item);
        System.out.println(pq.pq[3].item);
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ pq = new ArrayHeapMinPQ<>();
        pq.add('a', 1);
        pq.add('c', 3);
        pq.add('b', 2);
        pq.changePriority('a', 5);
        pq.changePriority('c', 1);
        assertEquals('c', pq.getSmallest());
    }

    @Test
    public void testNoSuchElement() {
        ArrayHeapMinPQ pq = new ArrayHeapMinPQ<>();
        pq.add('a', 1);
        pq.add('c', 3);
        pq.add('b', 2);
        pq.changePriority('e', 5);
    }

}
