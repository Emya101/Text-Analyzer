//Emhenya Supreme 
import java.util.Iterator;

/**
 * An abstract class implementing the basic functionality of a map.
 * Subclasses must provide implementations for specific map operations.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public abstract class AbstractMap<K,V> implements Map<K,V> {

    /**
     * Checks whether the map is empty.
     *
     * @return true if the map is empty, false otherwise
     */
    public boolean isEmpty(){return size() ==0;}

    /**
     * A map entry (key-value pair).
     *
     * @param <K> the type of keys
     * @param <V> the type of values
     */
    protected static class MapEntry<K,V> implements Entry<K,V>{
        private K k;
        private V v;
        public MapEntry(K key,V value){
            k=key;
            v=value;
        }

        public K getKey(){return k;}
        public V getValue(){return v;}

        protected void setKey (K key){k=key;}
        protected V setValue(V value){
            V old=v;
            v=value;
            return old;
        }
    }

    /**
     * An iterator over the keys in the map.
     */
    private class KeyIterator implements Iterator<K>{
         private Iterator<Entry<K, V>> entries=entrySet().iterator();
        public boolean hasNext() {return entries.hasNext();}
        public K next() {return entries.next().getKey(); }
        public void remove() {throw new UnsupportedOperationException();}
    }

    /**
     * An iterable for the keys in the map.
     */
    private class KeyIterable implements Iterable<K>{
        public Iterator<K> iterator(){return new KeyIterator();}
    }

    /**
     * Retrieves an iterable for the keys in the map.
     *
     * @return an iterable for the keys in the map
     */
    public Iterable<K>keySet(){return new KeyIterable();}

    /**
     * An iterator over the values in the map.
     */
    private class ValueIterator implements Iterator<V>{
        private Iterator<Entry<K, V>> entries=entrySet().iterator();
        public boolean hasNext() {return entries.hasNext();}
        public V next() {return entries.next().getValue();}
        public void remove() {throw new UnsupportedOperationException();}
    }

    /**
     * An iterable for the values in the map.
     */
    private class ValueIterable implements Iterable<V>{
        public Iterator<V> iterator(){return new ValueIterator();}
    }

    /**
     * Retrieves an iterable for the values in the map.
     *
     * @return an iterable for the values in the map
     */
    public Iterable<V>value(){return new ValueIterable();}
}
