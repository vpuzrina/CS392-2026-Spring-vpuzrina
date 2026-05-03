import Library00.FnList.*;
import Library00.FnA1sz.*;

// Complexity: O(n^2) time.
// fillBestFrom recurses over n elements; each call invokes maxFollowers
// which scans the suffix in O(n). DP fill is therefore O(n^2).
// reconstruct combined with pickLeftmost also performs at most O(n^2) work.
public class Quiz02_01 {
    public static
    <T extends Comparable<T>>
    FnList<Integer> FnA1szLongestMonoSubsequence(FnA1sz<T> xs) {
        int n = xs.length();
        if (n == 0) return new FnList<Integer>();
        int[] bestFrom = new int[n];
        fillBestFrom(xs, n, n - 1, bestFrom);
        int L = maxArr(bestFrom, 0, n, 0);
        return reconstruct(xs, n, bestFrom, 0, L, null);
    }

    private static <T extends Comparable<T>>
    void fillBestFrom(FnA1sz<T> xs, int n, int i, int[] bestFrom) {
        if (i < 0) return;
        bestFrom[i] = 1 + maxFollowers(xs, n, xs.getAt(i), i + 1, bestFrom);
        fillBestFrom(xs, n, i - 1, bestFrom);
    }

    private static <T extends Comparable<T>>
    int maxFollowers(FnA1sz<T> xs, int n, T xi, int j, int[] bestFrom) {
        if (j >= n) return 0;
        int rest = maxFollowers(xs, n, xi, j + 1, bestFrom);
        if (xi.compareTo(xs.getAt(j)) <= 0) {
            int here = bestFrom[j];
            if (here > rest) return here;
        }
        return rest;
    }

    private static int maxArr(int[] arr, int i, int n, int acc) {
        if (i >= n) return acc;
        int best = arr[i] > acc ? arr[i] : acc;
        return maxArr(arr, i + 1, n, best);
    }

    private static <T extends Comparable<T>>
    FnList<Integer> reconstruct(FnA1sz<T> xs, int n, int[] bestFrom, int start, int remaining, T prev) {
        if (remaining == 0) return new FnList<Integer>();
        int i = pickLeftmost(xs, n, bestFrom, start, remaining, prev);
        T xi = xs.getAt(i);
        return new FnList<Integer>(i, reconstruct(xs, n, bestFrom, i + 1, remaining - 1, xi));
    }

    private static <T extends Comparable<T>>
    int pickLeftmost(FnA1sz<T> xs, int n, int[] bestFrom, int i, int remaining, T prev) {
        if (i >= n) return -1;
        if (bestFrom[i] >= remaining) {
            T xi = xs.getAt(i);
            if (prev == null || prev.compareTo(xi) <= 0) {
                return i;
            }
        }
        return pickLeftmost(xs, n, bestFrom, i + 1, remaining, prev);
    }

    public static void main(String[] args) {
        Integer[] a1 = new Integer[] {1, 2, 1, 2, 3, 1, 2, 3, 4};
        FnA1sz<Integer> xs1 = new FnA1sz<Integer>(a1);
        FnList<Integer> r1 = FnA1szLongestMonoSubsequence(xs1);
        r1.iforitm((i, x) -> System.out.print((i > 0 ? "," : "") + x));
        System.out.println();

        Integer[] a2 = new Integer[] {5, 4, 3, 2};
        FnA1sz<Integer> xs2 = new FnA1sz<Integer>(a2);
        FnList<Integer> r2 = FnA1szLongestMonoSubsequence(xs2);
        r2.iforitm((i, x) -> System.out.print((i > 0 ? "," : "") + x));
        System.out.println();

        Integer[] a3 = new Integer[] {7};
        FnA1sz<Integer> xs3 = new FnA1sz<Integer>(a3);
        FnList<Integer> r3 = FnA1szLongestMonoSubsequence(xs3);
        r3.iforitm((i, x) -> System.out.print((i > 0 ? "," : "") + x));
        System.out.println();

        Integer[] a4 = new Integer[] {};
        FnA1sz<Integer> xs4 = new FnA1sz<Integer>(a4);
        FnList<Integer> r4 = FnA1szLongestMonoSubsequence(xs4);
        System.out.println("len=" + r4.length());

        Integer[] a5 = new Integer[] {3, 3, 3, 3};
        FnA1sz<Integer> xs5 = new FnA1sz<Integer>(a5);
        FnList<Integer> r5 = FnA1szLongestMonoSubsequence(xs5);
        r5.iforitm((i, x) -> System.out.print((i > 0 ? "," : "") + x));
        System.out.println();
    }
}
