import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> list = new Queue<>();
        list.enqueue(5);
        list.enqueue(3);
        list.enqueue(9);
        list.enqueue(7);
        list.enqueue(1);
        Queue<Integer> result = QuickSort.quickSort(list);
        assertEquals("1 3 5 7 9", result.toString());
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> list = new Queue<>();
        list.enqueue(5);
        list.enqueue(3);
        list.enqueue(9);
        list.enqueue(7);
        list.enqueue(1);
        Queue<Integer> result = MergeSort.mergeSort(list);
        assertEquals("1 3 5 7 9", result.toString());
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
