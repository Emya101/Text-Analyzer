//Emhenya Supreme 
import java.util.Comparator;
import java.util.Arrays;

/**
 * A class that implements the merge sort algorithm for sorting arrays.
 */

public class MergeSort {
    /**
     * Sorts the elements of the array using the merge sort algorithm.
     *
     * @param <K>       the type of elements in the array
     * @param S         the array to be sorted
     * @param comp      the comparator to determine the order of elements
     * @param ascending true to sort the array in ascending order, false to sort in descending order
     */
public static <K> void mergeSort(K[] S,Comparator<K> comp,boolean ascending){
    int n=S.length;
    if(n<2) return;
    int mid=n/2;
    K[] S1=Arrays.copyOfRange(S,0,mid);
    K[] S2=Arrays.copyOfRange(S,mid,n);

    mergeSort(S1,comp,ascending);
    mergeSort(S2, comp,ascending);

    if(ascending){
        merge(S1,S2,S,comp);
    }
    else{
        mergeDescending(S1,S2,S,comp);
    }
}
/**
     * Merges two sorted arrays into a single sorted array.
     *
     * @param <K>  the type of elements in the arrays
     * @param S1   the first sorted array
     * @param S2   the second sorted array
     * @param S    the array to store the merged result
     * @param comp the comparator to determine the order of elements
     */

public static <K> void merge(K[] S1,K[] S2,K[]S,Comparator<K> comp){
    int i=0;
    int j=0;

    while(i+j<S.length){
        if(j==S2.length||(i<S1.length && comp.compare(S1[i],S2[j])<0))
        S[i+j]=S1[i++];
        else
        S[i+j]=S2[j++];
    }
}
/**
     * Merges two sorted arrays into a single sorted array in descending order.
     *
     * @param <K>  the type of elements in the arrays
     * @param S1   the first sorted array
     * @param S2   the second sorted array
     * @param S    the array to store the merged result
     * @param comp the comparator to determine the order of elements
     */
//reverse the compare method
public static <K> void mergeDescending(K[] S1, K[] S2, K[] S, Comparator<K> comp) {
    int i = 0;
    int j = 0;

    while (i + j < S.length) {
        if (j == S2.length || (i < S1.length && comp.compare(S1[i], S2[j]) >= 0)) {
            S[i + j] = S1[i++];
        } else {
            S[i + j] = S2[j++];
        }
    }
}
}