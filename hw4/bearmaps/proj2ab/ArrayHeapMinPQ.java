package bearmaps.proj2ab;


import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private int size;
    public PriorityNode pq[];
    public HashMap<T, Integer> items = new HashMap<>();
    public class PriorityNode implements Comparable<PriorityNode> {
        public T item;
        public double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    ArrayHeapMinPQ() {
        pq = new ArrayHeapMinPQ.PriorityNode[16];
        size = 0;
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("");
        }
        if (size == pq.length - 1) {
            grow();
        }
        PriorityNode p = new PriorityNode(item, priority);
        if (size == 0) {
            pq[1] = p;
            items.put(item, 1);
        } else {
            int index = size + 1;
            pq[index] = p;
            items.put(item, index);
            swim(index);
        }
        size += 1;
    }

    private int parent(int i) {
        return i/2;
    }

    private int leftChild(int i) {
        return i * 2;
    }

    private int rightChild(int i) {
        return i * 2 + 1;
    }

    private void grow() {
        int newSize = pq.length * 2;
        PriorityNode temp[] = new ArrayHeapMinPQ.PriorityNode[newSize];
        System.arraycopy(pq, 0, temp, 0, pq.length);
        pq = temp;
    }

    private void shrink() {
        int newSize = (int) Math.round(pq.length * 0.75 + 1);
        PriorityNode temp[] = new ArrayHeapMinPQ.PriorityNode[newSize];
        System.arraycopy(pq, 0, temp, 0, newSize);
        pq = temp;
    }

    private void swim(int i) {
        while (i > 1 && pq[i].priority < pq[parent(i)].priority) {
            PriorityNode temp = pq[i];
            pq[i] = pq[parent(i)];
            pq[parent(i)] = temp;
            items.put(pq[i].item, i);
            items.put(pq[parent(i)].item, parent(i));
            i = parent(i);

        }
    }


    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return items.containsKey(item);
    }


    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        return pq[1].item;
    }


    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("");
        }
        T returnItem = pq[1].item;
        pq[1] = pq[size];
        sink(1);
        size -= 1;
        items.remove(returnItem);
        if (size / pq.length < 0.75) {
            shrink();
        }
        return returnItem;
    }

    private void sink(int i) {
        while (leftChild(i) <= size) {
            int j = leftChild(i);
            if (j < size && (pq[rightChild(i)].priority < pq[j].priority)) {
                //right is smaller
                j += 1;
            }
            if (pq[i].priority < pq[j].priority) {
                break;
            }
            PriorityNode temp = pq[i];
            pq[i] = pq[j];
            pq[j] = temp;
            items.put(pq[i].item, i);
            items.put(pq[j].item, j);
            i = j;
        }
    }


    /* Returns the number of items in the PQ. */
    public int size() {
        return size;
    }


    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        if (! contains(item)) {
            throw new NoSuchElementException("No");
        }
        int index = items.get(item);
        double old = pq[index].priority;
        pq[index].priority = priority;
        if (old > priority) {
            swim(index);
        } else {
            sink(index);
        }
    }

}
