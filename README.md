
Description

TextAnalyzer is a Java program that reads a text file and analyzes the frequency of words and letters. It identifies the most and least frequently occurring words and letters, as well as specific categories like pronouns.

Features
Analyzes a text file for word and letter frequency.
Identifies the most and least frequently occurring words and letters.
Handles specific categories such as pronouns to find their frequency.
Uses hash maps with open addressing for efficient storage and retrieval.
Implements merge sort for sorting entries based on frequency.
Classes and Their Responsibilities
ProbeHashMap<K, V>:

A hash map implementation with open addressing and linear probing.
Handles basic operations like get, put, and remove.
Resizes the table when the load factor exceeds a threshold.

LinkedPositionalList<E>:

A doubly linked list that maintains positional references.
Supports operations like add, remove, set, and iteration over elements.

OrderWordsByFrequency<K>:

A comparator to order words by their frequency.
Uses a default comparator for tie-breaking based on natural order.

OrderLettersByFrequency<K>:

A comparator to order letters by their frequency.
Uses a default comparator for tie-breaking based on natural order.

MergeSort:

Implements the merge sort algorithm.
Supports sorting in both ascending and descending order.

PartA_Driver:

The main driver class for the TextAnalyzer program.
Reads a text file, processes it to count word and letter frequencies, and displays the results.

Example
i placed a text file named PartA.txt in the same directory as the compiled classes. The program will read this file and output the analysis results to the console.

Output
The program outputs:

Total number of distinct words.
Total number of distinct letters.
Most and least occurring letters.
Most and least occurring words.
Most and least occurring pronouns.

Sample Output:
Text Analyzer
Total number of distinct words: 100
Total number of distinct letters: 26
Most Occurring Letter: e, 150
Least Occurring Letter: q, 5
Most Occurring Word: the, 50
Least Occurring Word: aardvark, 1
Most Occurring pronoun: you, 20
Least Occurring pronoun: it, 5








