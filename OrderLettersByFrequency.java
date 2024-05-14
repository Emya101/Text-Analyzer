//Emhenya Supreme 
import java.util.Comparator;

/**
 * Comparator for ordering entries by frequency. If frequencies are the same, it falls back to natural ordering of keys.
 *
 * @param <K> the type of the keys in the entries
 */
public class OrderLettersByFrequency<K> implements Comparator<Entry<K, Integer>> {
    private final Comparator<K> defaultComparator;

    /**
     * Constructs a comparator that orders entries by their values (frequencies).
     * If frequencies are the same, it uses the natural ordering of the keys.
     */
    public OrderLettersByFrequency() {
        this.defaultComparator = new DefaultComparator<>();
    }

    /**
     * Compares two entries first by their values (frequencies). If frequencies are the same, it compares by their keys.
     *
     * @param entry1 the first entry to be compared
     * @param entry2 the second entry to be compared
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    public int compare(Entry<K, Integer> entry1, Entry<K, Integer> entry2) {
        int freqComp = entry1.getValue().compareTo(entry2.getValue());
        if (freqComp != 0) {
            return freqComp; 
        } else {
            // If frequencies are the same, compare by natural ordering
            return defaultComparator.compare(entry1.getKey(), entry2.getKey());
        }
    }
}
