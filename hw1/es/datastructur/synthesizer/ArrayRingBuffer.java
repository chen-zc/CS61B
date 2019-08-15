package es.datastructur.synthesizer;
import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    private  int capacity;

    public int capacity() {
        return this.capacity;
    }     // return size of the buffer

    public int fillCount() {
        return this.fillCount;
    }    // return number of items currently in the buffer

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[this.last] = x;
        this.last = (this.last + 1) % this.capacity;
        this.fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        this.fillCount -= 1;
        T item = rb[this.first];
        rb[this.first] = null;
        this.first = (this.first + 1) % this.capacity;
        return item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T item = rb[this.first];
        return item;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int index;

        public ArrayRingBufferIterator() {
            index = first;
        }

        public boolean hasNext() {
            return index != last;
        }

        public T next() {
            T item = rb[index];
            index = (index + 1) % capacity;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayRingBuffer && ((ArrayRingBuffer) o).capacity == this.capacity) {
            if (((ArrayRingBuffer) o).fillCount != this.fillCount) {
                return false;
            }
            Iterator a = ((ArrayRingBuffer) o).iterator();
            Iterator b = this.iterator();
            while (b.hasNext() && a.hasNext()) {
                if (!b.next().equals(a.next())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

