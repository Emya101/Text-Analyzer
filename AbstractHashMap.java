//Emhenya Supreme
import java.util.Random;

/**
 * An abstract class implementing the basic functionality of a hash map.
 * Subclasses must provide implementations for table creation and bucket operations.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
    protected int n = 0; 
    protected int capacity; 
    public int prime; 
    public long scale;
    protected long shift; 

    /**
     * Constructs a hash map with the specified initial capacity and prime modulus.
     *
     * @param cap the initial capacity
     * @param p the prime modulus
     */
    public AbstractHashMap(int cap, int p) {
        prime = p;
        capacity = cap;
        java.util.Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    /**
     * Constructs a hash map with the specified initial capacity and default prime modulus.
     *
     * @param cap the initial capacity
     */
    public AbstractHashMap(int cap) {
        this(cap, 109345121);
    }

    /**
     * Constructs a hash map with default initial capacity and prime modulus.
     */
    public AbstractHashMap() {
        this(17);
    }

    /**
     * Returns the number of entries in the hash map.
     *
     * @return the number of entries
     */
    public int size() {
        return n;
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key whose associated value is to be retrieved
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }

    /**
     * Removes the mapping for a key from this hash map if it is present.
     *
     * @param key the key whose mapping is to be removed from the map
     * @return the previous value associated with the specified key, or null if there was no mapping for the key
     */
    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }

    /**
     * Associates the specified value with the specified key in this hash map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with the key, or null if there was no mapping for the key
     */
    public V put(K key, V value) {
        V answer = bucketPut(hashValue(key), key, value);
        if (n > capacity / 2)
            resize(2 * capacity - 1); 
        return answer;
    }

    /**
     * Calculates the hash value for the given key.
     *
     * @param key the key to calculate the hash value for
     * @return the hash value for the key
     */
    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    /**
     * Resizes the hash map to the specified new capacity.
     *
     * @param newCap the new capacity
     */
    private void resize(int newCap) {
        LinkedPositionalList<Entry<K, V>> buffer = new LinkedPositionalList<>();
        for (Entry<K, V> e : entrySet())
            buffer.addLast(e);
    
        capacity = newCap;
        createTable();
        n = 0;
    
        for (Entry<K, V> e : buffer)
            put(e.getKey(), e.getValue());
    }
    

    /**
     * Creates the table for storing entries in the hash map.
     */
    protected abstract void createTable();

    /**
     * Retrieves the value associated with the specified key from the bucket at the given hash index.
     *
     * @param h the hash index
     * @param k the key
     * @return the value associated with the key, or null if no mapping for the key is found
     */
    protected abstract V bucketGet(int h, K k);

    /**
     * Associates the specified value with the specified key in the bucket at the given hash index.
     *
     * @param h the hash index
     * @param k the key
     * @param v the value
     * @return the previous value associated with the key, or null if there was no mapping for the key
     */
    protected abstract V bucketPut(int h, K k, V v);

    /**
     * Removes the mapping for a key from the bucket at the given hash index if it is present.
     *
     * @param h the hash index
     * @param k the key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key
     */
    protected abstract V bucketRemove(int h, K k);
}
