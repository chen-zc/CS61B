
import java.util.*;


public class MyHashMap<K, V> implements Map61B<K, V> {

    private int size = 0;
    private int tablesize;
    private static final int initialSize = 16;
    private double loadFactor = 0.75;
    private ArrayList<pair> lists;

    public MyHashMap() {
        this(initialSize);
    }

    public MyHashMap(int i) {
        lists = new ArrayList<>(i);
        tablesize = i;
    }


    public MyHashMap(int i, double f) {
        this(i);
        loadFactor = f;
    }

    private void resize(int s) {
        MyHashMap temp = new MyHashMap(s);
        for (K e : keySet()) {
            temp.put(e, this.get(e));
        }
        this.size = temp.size;
        this.tablesize = temp.tablesize;
        this.lists = temp.lists;
    }

    private class pair {
        K key;
        V val;
        pair next;

        pair(K k, V v, pair n) {
            key = k;
            val = v;
            next = n;
        }
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        lists = new ArrayList<>(tablesize);
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int h = hash(key);
        pair e = find(key, lists.get(h));
        return ( e == null) ? null : e.val;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % (tablesize - 1);
    }

    private pair find(K key, pair l) {
        if (l == null) {
            return null;
        }
        for (pair e = l; e != null; e = e.next) {
            if (key.equals(e.key)) {
                return e;
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        int h = hash(key);

        if (lists.get(h) == null) {
            pair p = new pair(key, value, null);
            lists.add(h, p);
        }
        for (pair e = lists.get(h); e != null; e = e.next) {
            if (key.equals(e.key)) {
                e.val = value;
            }
        }
        lists.get(h).next = new pair(key, value, null);
        size ++;

        if ((double) size / tablesize > loadFactor) {
            resize( 2 * tablesize);
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        for (int i = 0; i < tablesize; i += 1) {
            pair p = lists.get(i);
            while (p != null) {
                keyset.add(p.key);
                p = p.next;
            }
        }
        return keyset;
    }

    public Iterator<K> iterator() {
        Set keys = keySet();
        return keys.iterator();
    }



    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("");
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("");
    }

}
