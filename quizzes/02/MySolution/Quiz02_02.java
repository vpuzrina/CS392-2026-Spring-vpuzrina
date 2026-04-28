//
// HX: 50 points
// Here we revisit a question on quiz01 (Quiz01_03).
// Instead of sorting 10 elements without recursion,
// you are asked to insertion-sort up to 1 million
// elements without recursion.
// Attention:
// You are suppose to do insertion-sort. If you do
// bubble-sort, you can receive up to 60%, that is
// 30 points of 50.
//
public class Quiz02_02 {
    public static
	<T extends Comparable<T>>
	void sort1000WithNoRecursion(T[] A) {
	// HX-2025-11-20:
	// A is an array of size at most 1000K.
	// Please implement a sorting algorithm
	// WITHOUT recursion that can effectively
	// sort A.
	for (int i = 1; i < A.length; i += 1) {
	    T key = A[i];
	    int j = i - 1;
	    while (j >= 0 && A[j].compareTo(key) > 0) {
		A[j + 1] = A[j];
		j -= 1;
	    }
	    A[j + 1] = key;
	}
    }

    private static <T extends Comparable<T>>
    boolean isSorted(T[] A) {
	for (int i = 1; i < A.length; i += 1) {
	    if (A[i - 1].compareTo(A[i]) > 0) return false;
	}
	return true;
    }

    private static <T>
    void printArray(T[] A) {
	for (int i = 0; i < A.length; i += 1) {
	    if (i > 0) System.out.print(" ");
	    System.out.print(A[i]);
	}
	System.out.println();
    }

    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for sort1000WithNoRecursion
	Integer[] sorted = new Integer[] {1, 2, 3, 4, 5, 6};
	sort1000WithNoRecursion(sorted);
	System.out.println("sorted: " + isSorted(sorted));
	printArray(sorted);

	Integer[] reverse = new Integer[] {9, 8, 7, 6, 5, 4, 3};
	sort1000WithNoRecursion(reverse);
	System.out.println("reverse: " + isSorted(reverse));
	printArray(reverse);

	Integer[] duplicates = new Integer[] {3, 1, 3, 2, 3, 1, 2, 2};
	sort1000WithNoRecursion(duplicates);
	System.out.println("duplicates: " + isSorted(duplicates));
	printArray(duplicates);

	Integer[] nearlySorted = new Integer[] {1, 2, 3, 4, 6, 5, 7, 8, 9, 10};
	sort1000WithNoRecursion(nearlySorted);
	System.out.println("nearlySorted: " + isSorted(nearlySorted));
	printArray(nearlySorted);

	return /*void*/;
    }
} // end of [public class Quiz02_02{...}]
