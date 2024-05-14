// Emhenya Supreme 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;

public class PartA_Driver {
    public static void main(String[] args) {
        // Create a ProbeHashMap for words and their frequencies
        ProbeHashMap<String, Integer> wordMap = new ProbeHashMap<>();
        // Create a ProbeHashMap for letters and their frequencies
        ProbeHashMap<Character, Integer> letterMap = new ProbeHashMap<>();
        // Initialize a list of pronouns
        List<String> pronouns = new LinkedList<>();

        // Add pronouns to the list
        pronouns.add("i");
        pronouns.add("you");
        pronouns.add("he");
        pronouns.add("she");
        pronouns.add("we");
        pronouns.add("it");
        pronouns.add("they");

        try {
            // Open the file "PartA.txt"
            File file = new File("PartA.txt");
            Scanner scanner = new Scanner(file);

            // Process each line in the file
            while (scanner.hasNextLine()) {
                // Read the line and convert to lowercase
                String line = scanner.nextLine().toLowerCase();
                // Remove punctuations and replace them with spaces
                line = line.replaceAll("[^a-zA-Z]+", " ");

                // Split the line into words
                String[] words = line.split("\\s");
                for (String word : words) {
                    // Process each word
                    if (word.length() != 0) {
                        // Get the current frequency of the word
                        Integer frequency = wordMap.bucketGet(wordMap.hashValue(word), word);
                        if (frequency == null) {
                            frequency = 0; 
                        }
                        // Increment the frequency and put it back into the map
                        wordMap.bucketPut(wordMap.hashValue(word), word, frequency + 1);
                    }

                    // Process each character in the word
                    for (int i = 0; i < word.length(); i++) {
                        char c = word.charAt(i);
                        // Get the current frequency of the character
                        Integer lfrequency = letterMap.bucketGet(letterMap.hashValue(c), c);
                        if (lfrequency == null) {
                            lfrequency = 0; 
                        }
                        // Increment the frequency and put it back into the map
                        letterMap.bucketPut(letterMap.hashValue(c), c, lfrequency + 1);
                    }
                }
            }
            // Close the scanner
            scanner.close();

            // Create comparators for words and letters by frequency
            OrderWordsByFrequency<String> comp = new OrderWordsByFrequency<>();
            OrderLettersByFrequency<Character> lcomp = new OrderLettersByFrequency<>();

            // Find the most and least occurring word
            Entry<String, Integer> mostWord = findMaxLeast(wordMap, true, comp);
            Entry<String, Integer> leastWord = findMaxLeast(wordMap, false, comp);

            // Find the most and least occurring letter
            Entry<Character, Integer> maxLetterEntry = findMaxLeast(letterMap, true, lcomp);
            Entry<Character, Integer> leastLetterEntry = findMaxLeast(letterMap, false, lcomp);

            // Print results
            System.out.println("Text Analyzer");
            System.out.println("Total number of distinct words: " + wordMap.size());
            System.out.println("Total number of distinct letters: " + letterMap.size());
            System.out.println("Most Occurring Letter: " + maxLetterEntry.getKey() + ", " + maxLetterEntry.getValue());
            System.out.println("Least Occurring Letter: " + leastLetterEntry.getKey() + ", " + leastLetterEntry.getValue());
            System.out.println("Most Occurring Word: " + mostWord.getKey() + ", " + mostWord.getValue());
            System.out.println("Least Occurring Word: " + leastWord.getKey() + ", " + leastWord.getValue());

            // Find the most and least occurring pronoun
            Entry<String, Integer> mostPronoun = findCategoryMaxLeast(wordMap, pronouns, true, comp);
            Entry<String, Integer> leastPronoun = findCategoryMaxLeast(wordMap, pronouns, false, comp);

            // Print pronoun results
            System.out.println("Most Occurring Pronoun: " + mostPronoun.getKey() + ", " + mostPronoun.getValue());
            System.out.println("Least Occurring Pronoun: " + leastPronoun.getKey() + ", " + leastPronoun.getValue());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the maximum or minimum entry in the map based on the comparator.
     * 
     * @param <K> the type of keys
     * @param <V> the type of values
     * @param map the map to search
     * @param determinant if true, find the maximum; if false, find the minimum
     * @param comparator the comparator to determine order
     * @return the entry with the maximum or minimum value
     */
    public static <K, V> Entry<K, V> findMaxLeast(ProbeHashMap<K, V> map, boolean determinant, Comparator<Entry<K, V>> comparator) {
        // Convert the map entries to an array
        Entry<K, V>[] entries = toArray(map.entrySet()); 
        // Sort the entries in ascending or descending order based on the determinant
        if (!determinant) {
            MergeSort.mergeSort(entries, comparator, true); 
        } else {
            MergeSort.mergeSort(entries, comparator, false); 
        }
        // Return the first entry in the sorted array
        return entries[0];
    }

    /**
     * Finds the maximum or minimum entry in the map for a specific category of keys.
     * 
     * @param <K> the type of keys
     * @param <V> the type of values
     * @param map the map to search
     * @param words the list of category keys to search for
     * @param determinant if true, find the maximum; if false, find the minimum
     * @param comparator the comparator to determine order
     * @return the entry with the maximum or minimum value in the category
     */
    public static <K, V> Entry<K, V> findCategoryMaxLeast(ProbeHashMap<K, V> map, List<K> words, boolean determinant, Comparator<Entry<K, V>> comparator) {
        // Create a new map to store filtered entries
        ProbeHashMap<K, V> filteredMap = new ProbeHashMap<>();
        for (K word : words) {
            if (map.containsKey(word)) {
                filteredMap.put(word, map.get(word));
            }
        }
        // Find the maximum or minimum entry in the filtered map
        return findMaxLeast(filteredMap, determinant, comparator);
    }

    /**
     * Converts an iterable of entries to an array.
     * 
     * @param <K> the type of keys
     * @param <V> the type of values
     * @param iterable the iterable to convert
     * @return an array of entries
     */
    private static <K, V> Entry<K, V>[] toArray(Iterable<Entry<K, V>> iterable) {
        // Create a list to store the entries
        LinkedPositionalList<Entry<K, V>> list = new LinkedPositionalList<>();
        int index = 0;
        for (Entry<K, V> entry : iterable) {
            list.addLast(entry);
        }
        // Create an array from the list
        Entry<K, V>[] array = (Entry<K, V>[]) new Entry[list.size()];
        for (Entry<K, V> entry : list) {
            array[index++] = entry;
        }
        return array;
    }
}
