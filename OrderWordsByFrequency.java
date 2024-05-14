//Emhenya Supreme 
import java.util.Comparator;

/**
 * Comparator for ordering entries by frequency. If frequencies are the same, it falls back to a specified ordering of keys.
 *
 * @param <K> the type of the keys in the entries
 */
public class OrderWordsByFrequency<K> implements Comparator<Entry<K, Integer>> {
    private final Comparator<K> usedComparator;

    /**
     * Constructs a comparator that orders entries by their values (frequencies).
     * If frequencies are the same, it uses a specified comparator for the keys.
     */
    public OrderWordsByFrequency() {
        this.usedComparator = new DefaultComparator<>();
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
            // If frequencies are the same, compare by the specified comparator
            return usedComparator.compare(entry1.getKey(), entry2.getKey());
        }
    }
}
