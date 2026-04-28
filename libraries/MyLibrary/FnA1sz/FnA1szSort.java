package MyLibrary.FnA1sz;

import java.util.function.ToIntBiFunction;

public class FnA1szSort {
    public static <T extends Comparable<T>>
    void quickSort(FnA1sz<T> A) {
        quickSort(A, (x1, x2) -> x1.compareTo(x2));
    }

    public static <T>
    void quickSort(FnA1sz<T> A, ToIntBiFunction<T, T> cmp) {
        sort(A, 0, A.length() - 1, cmp);
    }

    private static <T>
    void sort(FnA1sz<T> A, int lo, int hi, ToIntBiFunction<T, T> cmp) {
        if (lo >= hi) return;
        int p = partition(A, lo, hi, cmp);
        sort(A, lo, p - 1, cmp);
        sort(A, p + 1, hi, cmp);
    }

    private static <T>
    int partition(FnA1sz<T> A, int lo, int hi, ToIntBiFunction<T, T> cmp) {
        T pivot = A.getAt(hi);
        int i = lo;
        for (int j = lo; j < hi; j += 1) {
            if (cmp.applyAsInt(A.getAt(j), pivot) <= 0) {
                swap(A, i, j);
                i += 1;
            }
        }
        swap(A, i, hi);
        return i;
    }

    private static <T>
    void swap(FnA1sz<T> A, int i, int j) {
        T xi = A.getAt(i);
        A.setAt(i, A.getAt(j));
        A.setAt(j, xi);
    }
}
