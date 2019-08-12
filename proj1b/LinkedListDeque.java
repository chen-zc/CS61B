/** build a class called LinkedListDeque with all the features.
 *
 */
public class LinkedListDeque<T> implements Deque<T> {
    private IntNode sentinel;
    private int size;

    /** create IntNode with prev, item and next. */
    public class IntNode {
        private T item;
        private IntNode prev;
        private IntNode next;

        public IntNode(IntNode p, T i, IntNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        size = 0;
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /** Creates an empty linked list deque. */
    public LinkedListDeque(T i) {
        size = 1;
        sentinel = new IntNode(null, null, null);
        sentinel.next = new IntNode(sentinel, i, sentinel);
    }

    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        size += 1;
        IntNode temp = new IntNode(sentinel, item, sentinel.next);
        sentinel.next.prev = temp;
        sentinel.next = temp;
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        size += 1;
        IntNode temp = new IntNode(sentinel.prev, item, sentinel);
        sentinel.prev.next = temp;
        sentinel.prev = temp;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
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
        IntNode p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.next.item.toString() + ' ');
            p = p.next;
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
        IntNode a = sentinel.next;
        IntNode p = sentinel.next.next;
        p.prev = sentinel;
        sentinel.next = p;
        return a.item;
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
        T last = sentinel.prev.item;
        IntNode p = sentinel.prev.prev;
        p.next = sentinel;
        sentinel.prev = p;
        return last;
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     */
    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        IntNode p = sentinel.next;
        for (int i = index; i > 0; i -= 1) {
            p = p.next;
        }
        T item = p.item;
        return item;
    }

    /** Creates a deep copy of other. */
    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        for (int i = 0; i < size; i ++) {
            addLast((T) other.get(i));
        }

    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        IntNode p = sentinel;
        if (index == 0) {
            return p.next.item;
        }
        p = p.next;
        return getRecursive(index - 1);
    }
}
