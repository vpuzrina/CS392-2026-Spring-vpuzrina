public class Quiz02_02 {
    public static
    <T extends Comparable<T>>
    void sort1000WithNoRecursion(T[] A) {
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

    public static void main(String[] args) {
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

        final int n = 1_000_000;
        Integer[] big = new Integer[n];
        for (int k = 0; k < n; k += 2) {
            big[k] = k + 1;
            if (k + 1 < n) big[k + 1] = k;
        }
        long t0 = System.currentTimeMillis();
        sort1000WithNoRecursion(big);
        long t1 = System.currentTimeMillis();
        System.out.println("1M nearly sorted: " + isSorted(big) + " in " + (t1 - t0) + " ms");

        return;
    }
}
