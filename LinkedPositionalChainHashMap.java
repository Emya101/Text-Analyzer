//Emhenya Supreme
/**
 * An implementation of a hash map using separate chaining with linked lists.
 * It extends the AbstractHashMap class.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */ 
public class LinkedPositionalChainHashMap<K, V> extends AbstractHashMap<K, V> {
    private LinkedPositionalList<Entry<K, V>>[] table;
    private int collisions;

    /**
     * Constructs a hash map with the specified initial capacity and prime modulus.
     *
     * @param cap the initial capacity
     * @param p the prime modulus
     */

    public LinkedPositionalChainHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Constructs a hash map with the specified initial capacity and default prime modulus.
     *
     * @param cap the initial capacity
     */

    public LinkedPositionalChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Constructs a hash map with default initial capacity and prime modulus.
     */

    public LinkedPositionalChainHashMap() {
        super();
    }
    
    /**
     * Creates the table for storing entries in the hash map.
     */
    protected void createTable() {
        table = (LinkedPositionalList<Entry<K, V>>[]) new LinkedPositionalList[capacity];
    }

    /**
     * Retrieves the value associated with the specified key from the bucket at the given hash index.
     *
     * @param h the hash index
     * @param k the key
     * @return the value associated with the key, or null if no mapping for the key is found
     */
    protected V bucketGet(int h, K k) {
        LinkedPositionalList<Entry<K, V>> storage = table[h];
        if (storage == null) return null;
        for (Entry<K, V> entries : storage) {
            if (entries.getKey().equals(k)) {
                return entries.getValue();
            }
        }
        return null;
    }
    /**
     * Associates the specified value with the specified key in the bucket at the given hash index.
     *
     * @param h the hash index
     * @param k the key
     * @param v the value
     * @return the previous value associated with the key, or null if there was no mapping for the key
     */
    protected V bucketPut(int h, K k, V v) {
        LinkedPositionalList<Entry<K, V>> storage = table[h];
        if (storage == null) {
            storage = table[h] = new LinkedPositionalList<>();
        }
        for (Position<Entry<K, V>> pos : storage.positions()) {
            if (pos.getElement().getKey().equals(k)) {
                V oldValue = pos.getElement().getValue();
                Entry<K, V> updatedEntry = new MapEntry<>(k, v);
                storage.set(pos, updatedEntry);
                return oldValue;
            }
        }
        storage.addLast(new MapEntry<>(k, v));
        n++;
        if (storage.size() > 1) {
            collisions++;
        }
        if ((double) n / capacity > 0.5) {
            resize(2 * capacity); 
        }

        return null;
    }
    
     /**
     * Removes the mapping for a key from the bucket at the given hash index if it is present.
     *
     * @param h the hash index
     * @param k the key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key
     */

    protected V bucketRemove(int h, K k) {
        LinkedPositionalList<Entry<K, V>> storage = table[h];
        if (storage == null) return null;
        for (Position<Entry<K, V>> pos : storage.positions()) {
            if (pos.getElement().getKey().equals(k)) {
                V value = pos.getElement().getValue();
                storage.remove(pos);
                n--;
                return value;
            }
        }
        return null;
    }

    /**
     * Retrieves the number of collisions that have occurred during put operations.
     *
     * @return the number of collisions
     */
    public int getCollisions() {
        return collisions;
    }

    /**
     * Resizes the hash map to the specified new capacity.
     *
     * @param newCap the new capacity
     */
    protected void resize(int newCap) {
        LinkedPositionalList<Entry<K, V>>[] newTable = (LinkedPositionalList<Entry<K, V>>[]) new LinkedPositionalList[newCap];
        for (Entry<K, V> entry : entrySet()) {
            int h = hashValue(entry.getKey(), newCap);
            if (newTable[h] == null) {
                newTable[h] = new LinkedPositionalList<>();
            }
            newTable[h].addLast(entry);
        }
        capacity = newCap;
        table = newTable;
    }

    /**
     * Calculates the hash value for the given key and new capacity.
     *
     * @param key the key
     * @param newCap the new capacity
     * @return the hash value for the key and new capacity
     */
    private int hashValue(K key, int newCap) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % newCap);
    }

     /**
     * Retrieves an iterable of all entries in the hash map.
     *
     * @return an iterable of all entries
     */
    public Iterable<Entry<K, V>> entrySet() {
        LinkedPositionalList<Entry<K, V>> list = new LinkedPositionalList<>();
        for (LinkedPositionalList<Entry<K, V>> storage : table) {
            if (storage != null) {
                for (Entry<K, V> entry : storage) {
                    list.addLast(entry);
                }
            }
        }
        return list;
    }

     /**
     * Clears all entries from the hash map.
     */
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        n = 0;
        collisions = 0;
    }

        /**
     * Checks whether the hash map contains the specified key.
     *
     * @param key the key to check
     * @return true if the map contains the key, otherwise false
     */

    public boolean containsKey(K key) {
        return get(key) != null;
    }

     /**
     * Checks whether the hash map contains the specified value.
     *
     * @param value the value to check
     * @return true if the map contains the value, otherwise false
     */
    public boolean containsValue(V value) {
        for (LinkedPositionalList<Entry<K, V>> storage : table) {
            if (storage != null) {
                for (Entry<K, V> entry : storage) {
                    if (entry.getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Retrieves an iterable of all values in the hash map.
     *
     * @return an iterable of all values
     */
    public Iterable<V> values() {
        LinkedPositionalList<V> valueList = new LinkedPositionalList<>();
        for (LinkedPositionalList<Entry<K, V>> storage : table) {
            if (storage != null) {
                for (Entry<K, V> entry : storage) {
                    valueList.addLast(entry.getValue());
                }
            }
        }
        return valueList;
    }

}

