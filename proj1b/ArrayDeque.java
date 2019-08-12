/** build a class called ArrayDeque with all the features.
 *
 */
public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst, nextLast;
    private static int Rfactor = 2;

    /** create an empty array. */
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** resize the array to size Cap. */
    private void resize(int cap) {
        T[] new_items = (T[]) new Object[cap];
        int index = NewNextLast(nextFirst);
        for (int i = 0; i < size; i += 1) {
            System.arraycopy(items, index, new_items, i, 1);
            index = NewNextLast(index);
        }
        items = new_items;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /** return a new NextFirst position. */
    public int NewNextFirst(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    /** return a new NextLast position. */
    public int NewNextLast(int index) {
        return (index + 1) % items.length;
    }

    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * Rfactor);
        }
        size += 1;
        items[nextFirst] = item;
        nextFirst = NewNextFirst(nextFirst);
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * Rfactor);
        }
        size += 1;
        items[nextLast] = item;
        nextLast = NewNextLast(nextLast);
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }


    /** Prints the items in the deque from first to last,
     * separated by a space. Once all the items have been
     * printed, print out a new line.
     * */
    @Override
    public void printDeque() {
        int index = NewNextLast(nextFirst);
        for (int i = 0; i < size; i ++) {
            System.out.print(items[index].toString() + " ");
            index = NewNextLast(nextFirst);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T first = items[NewNextLast(nextFirst)];
        items[NewNextLast(nextFirst)] = null;
        nextFirst = NewNextLast(nextFirst);
        shrink();
        return first;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T last = items[NewNextFirst(nextLast)];
        items[NewNextFirst(nextLast)] = null;
        nextLast = NewNextFirst(nextLast);
        shrink();
        return last;
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     */
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[NewNextLast(index + nextFirst)];
    }

    /** if the size is less than 25% of the whole array length,
     * shrink it by half. */
    public void shrink() {
        if (items.length >= 16) {
            if (size / items.length < 0.25) {
                resize(items.length / Rfactor);
            }
        }
    }

    /** Creates a deep copy of other. Creating a deep copy means
     * that you create an entirely new ArrayDeque, with the exact
     * same items as other. However, they should be different
     * objects, i.e. if you change other, the new ArrayDeque you
     * created should not change as well.
     */
    public ArrayDeque(ArrayDeque other) {
        items = (T []) new Object[other.size * 4];
        size = 0;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;

        for (int i = 0; i < items.length; i ++) {
            addLast((T) other.get(i));
        }
    }

}
