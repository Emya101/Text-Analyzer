//Emhenya Supreme 
import java.util.Comparator;

/**
 * A default implementation of the Comparator interface.
 * It compares objects using their natural ordering.
 *
 * @param <K> the type of objects to compare
 */
public class DefaultComparator<K> implements Comparator<K> {

    /**
     * Compares two objects using their natural ordering.
     *
     * @param a the first object to be compared
     * @param b the second object to be compared
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to,
     *         or greater than the second
     * @throws ClassCastException if the arguments' types prevent them from being compared by this comparator.
     */
    public int compare(K a, K b) {
        return ((Comparable<K>)a).compareTo(b);
    }
}


