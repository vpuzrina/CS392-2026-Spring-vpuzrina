import Library00.FnA1sz.*;
import MyLibrary.FnList.FnList;
import MyLibrary.FnList.FnListSort;

public class Quiz02_02 {
    public static
    <T extends Comparable<T>>
    void sort1000WithNoRecursion(T[] A) {
        FnA1sz<T> wrap = new FnA1sz<T>(A);
        FnList<T> list = FnA1szSUtil.<T, FnList<T>>rfolditm(
            wrap,
            new FnList<T>(),
            (x, acc) -> new FnList<T>(x, acc)
        );
        FnList<T> sorted = FnListSort.insertSort(list);
        int[] idx = new int[]{0};
        sorted.foritm(x -> { A[idx[0]] = x; idx[0] += 1; });
    }

    private static <T extends Comparable<T>>
    boolean isSorted(T[] A) {
        return new FnA1sz<T>(A).iforall((i, x) -> i == 0 || A[i - 1].compareTo(x) <= 0);
    }

    private static <T>
    void printArray(T[] A) {
        new FnA1sz<T>(A).iforitm((i, x) -> {
            if (i > 0) System.out.print(" ");
            System.out.print(x);
        });
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
        new FnA1sz<Integer>(big).iforitm((i, x) -> {
            big[i] = (i % 2 == 0) ? (i + 1) : (i - 1);
        });
        long t0 = System.currentTimeMillis();
        sort1000WithNoRecursion(big);
        long t1 = System.currentTimeMillis();
        System.out.println("1M nearly sorted: " + isSorted(big) + " in " + (t1 - t0) + " ms");

        return;
    }
}
