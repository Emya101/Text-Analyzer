//Emhenya Supreme 

/**
 * Implementation of a hash map using open addressing with linear probing.
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    /**
     * Constructs an empty probe hash map with default initial capacity and prime factor.
     */
    public ProbeHashMap() {
        super();
    }

    /**
     * Constructs an empty probe hash map with the specified initial capacity.
     * 
     * @param cap the initial capacity of the hash table
     */
    public ProbeHashMap(int cap) {
        super(cap);
    }

     /**
     * Constructs an empty probe hash map with the specified initial capacity and prime factor.
     * 
     * @param cap the initial capacity of the hash table
     * @param p the prime factor used in hash code computation
     */
    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Creates the hash table with the specified capacity.
     */
    protected void createTable() {
        table = (MapEntry<K, V>[]) new MapEntry[capacity];
    }

    /**
     * Checks if the given slot is available.
     * 
     * @param j the index of the slot to check
     * @return true if the slot is available, false otherwise
     */

    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

     /**
     * Finds the slot for a given key.
     * 
     * @param h the hash value of the key
     * @param k the key to find the slot for
     * @return the index of the slot, or -(available index + 1) if not found
     */

    private int findSlot(int h, K k) {
        int avail = -1;
        int j = h;
        do {
            if (isAvailable(j)) {
                if (avail == -1)
                    avail = j;
                if (table[j] == null)
                    break;
            } else if (table[j].getKey().equals(k))
                return j;
            j = (j + 1) % capacity;
        } while (j != h);
        return -(avail + 1);
    }

     /**
     * Retrieves the value associated with a key in the specified bucket.
     * 
     * @param h the hash value of the key
     * @param k the key to look for
     * @return the value associated with the key, or null if not found
     */

    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)
            return null;
        return table[j].getValue();
    }

    /**
     * Associates a value with a key in the specified bucket.
     * 
     * @param h the hash value of the key
     * @param k the key to associate the value with
     * @param v the value to be associated
     * @return the old value associated with the key, or null if no such value existed
     */

    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0)
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v);
        n++;
        if ((n * 1.0) / capacity > 0.5) // if factor passes the limit,very important
            resize(2 * capacity); // Double the capacity of the array
        return null;
    }

    /**
     * Removes the value associated with a key in the specified bucket.
     * 
     * @param h the hash value of the key
     * @param k the key to remove the value for
     * @return the value that was removed, or null if no such value existed
     */
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)
            return null;
        V answer = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return answer;
    }

    /**
     * Returns an iterable collection of all entries in the hash table.
     * 
     * @return an iterable collection of all entries
     */
    public Iterable<Entry<K, V>> entrySet() {
        LinkedPositionalList<Entry<K, V>> buffer = new LinkedPositionalList<>();
        for (int h = 0; h < capacity; h++) {
            if (table[h] != null && table[h] != DEFUNCT) {
                buffer.addLast(table[h]);
            }
        }
        return buffer;
    }

    /**
     * Returns an iterable collection of all values in the hash table.
     * 
     * @return an iterable collection of all values
     */
    public Iterable<V> values() {
        LinkedPositionalList<V> values = new LinkedPositionalList<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null && entry != DEFUNCT) {
                values.addLast(entry.getValue());
            }
        }
        return values;
    }

    /**
     * Resizes the hash table to the specified capacity.
     * 
     * @param newCap the new capacity of the hash table
     */
    public void resize(int newCap) {
        LinkedPositionalList<Entry<K, V>> buffer = new LinkedPositionalList<>();
        for (Entry<K, V> e : entrySet()) {
            buffer.addLast(e);
        }
        capacity = newCap;
        createTable();
        n = 0;
        for (Entry<K, V> e : buffer) {
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Checks if the hash table contains the specified key.
     * 
     * @param key the key to check for
     * @return true if the hash table contains the key, false otherwise
     */
    public boolean containsKey(K key) {
        for (Entry<K, V> entry : entrySet()) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the hash table.
     * 
     * @return a string representation of the hash table
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Entry<K, V> entry : entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

}
