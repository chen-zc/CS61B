import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    int size = 0;
    private Node root;

    private class Node {
        private K key;           // sorted by key
        private V val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BSTMap() {
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node n, K key) {
        if (key == null || n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            return n.val;
        }
        if (cmp < 0) {
            return get(n.left, key);
        }
        else {
            return get(n.right, key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    public int size(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    public Node put(Node n, K key, V value) {
        if (n == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            n.val = value;
        }
        if (cmp < 0) {
            n.left = put(n.left, key, value);
        }
        else {
            n.right = put(n.right, key, value);
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }


    /* prints out your BSTMap in order of increasing Key. */
    public void printInOrder() {
        if (root == null) {
            System.out.println("None");
        }
        printInOrder(root);
    }

    public void printInOrder(Node n) {
        if (n.left == null && n.right == null) {
            System.out.println(n.val.toString());
        }
        if (n.left != null && n.right == null) {
            printInOrder(n.left);
            System.out.println(n.val.toString());
        }
        if (n.left == null && n.right != null) {
            System.out.println(n.val.toString());
            printInOrder(n.right);
        }
        else {
            printInOrder(n.left);
            System.out.println(n.val.toString());
            printInOrder(n.right);
        }
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
