import java.util.function.ToIntBiFunction;

public class Assign06_01 {
    public static <T> void arrayQuickSort(T[] A, ToIntBiFunction<T, T> cmp) {
        quickSort(A, 0, A.length - 1, cmp);
    }

    private static <T> void quickSort(T[] A, int lo, int hi, ToIntBiFunction<T, T> cmp) {
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;
        swap(A, lo, mid);

        T pivot = A[lo];
        int lt = lo;
        int i = lo + 1;
        int gt = hi;

        while (i <= gt) {
            int c = cmp.applyAsInt(A[i], pivot);
            if (c < 0) {
                swap(A, lt, i);
                lt += 1;
                i += 1;
            } else if (c > 0) {
                swap(A, i, gt);
                gt -= 1;
            } else {
                i += 1;
            }
        }

        quickSort(A, lo, lt - 1, cmp);
        quickSort(A, gt + 1, hi, cmp);
    }

    private static <T> void swap(T[] A, int i, int j) {
        T t = A[i];
        A[i] = A[j];
        A[j] = t;
    }

    private static <T> boolean isSorted(T[] A, ToIntBiFunction<T, T> cmp) {
        for (int i = 1; i < A.length; i += 1) {
            if (cmp.applyAsInt(A[i - 1], A[i]) > 0) return false;
        }
        return true;
    }

    private static Integer[] makePseudoRandomArray(int n) {
        Integer[] A = new Integer[n];
        int seed = 123456789;
        for (int i = 0; i < n; i += 1) {
            seed = seed * 1103515245 + 12345;
            A[i] = (seed >>> 1) % 10000;
        }
        return A;
    }

    private static Integer[] makeSortedArray(int n) {
        Integer[] A = new Integer[n];
        for (int i = 0; i < n; i += 1) A[i] = i;
        return A;
    }

    private static Integer[] makeReverseSortedArray(int n) {
        Integer[] A = new Integer[n];
        for (int i = 0; i < n; i += 1) A[i] = n - 1 - i;
        return A;
    }

    private static Integer[] makeDuplicateArray() {
        return new Integer[] {5, 1, 3, 5, 2, 3, 5, 1, 2, 5, 3, 1, 5, 2, 3};
    }

    private static Integer[] makeAllEqualArray(int n, int x) {
        Integer[] A = new Integer[n];
        for (int i = 0; i < n; i += 1) A[i] = x;
        return A;
    }

    private static Integer[] makeLargeManyEqualArray(int n) {
        Integer[] A = new Integer[n];
        for (int i = 0; i < n; i += 1) {
            if (i % 20 == 0) {
                A[i] = i % 7;
            } else {
                A[i] = 0;
            }
        }
        return A;
    }

    private static void runTest(String name, Integer[] A) {
        long t0 = System.currentTimeMillis();
        arrayQuickSort(A, Integer::compare);
        long t1 = System.currentTimeMillis();
        boolean ok = isSorted(A, Integer::compare);
        System.out.println(name + " (n=" + A.length + "): " + (ok ? "PASS" : "FAIL") + " in " + (t1 - t0) + " ms");
    }

    public static void main(String[] args) {
        runTest("array of 1M zeros", makeAllEqualArray(1_000_000, 0));
        runTest("random numbers", makePseudoRandomArray(1000));
        runTest("already sorted array", makeSortedArray(1000));
        runTest("reverse sorted array", makeReverseSortedArray(1000));
        runTest("array with duplicates", makeDuplicateArray());
        runTest("array with all equal values (small)", makeAllEqualArray(1000, 0));
        runTest("large array with many equal values", makeLargeManyEqualArray(20000));
        runTest("random numbers (1M)", makePseudoRandomArray(1_000_000));
        runTest("array with all 7s (1M)", makeAllEqualArray(1_000_000, 7));
    }
}
