public class Quiz01_02 {
    private static void merge(Integer[] A, Integer[] tmp, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            if (A[i] <= A[j]) {
                tmp[k] = A[i];
                i += 1;
            } else {
                tmp[k] = A[j];
                j += 1;
            }
            k += 1;
        }
        while (i <= mid) {
            tmp[k] = A[i];
            i += 1;
            k += 1;
        }
        while (j <= hi) {
            tmp[k] = A[j];
            j += 1;
            k += 1;
        }
        for (int t = lo; t <= hi; t += 1) {
            A[t] = tmp[t];
        }
    }

    private static void mergeSort(Integer[] A, Integer[] tmp, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(A, tmp, lo, mid);
        mergeSort(A, tmp, mid + 1, hi);
        merge(A, tmp, lo, mid, hi);
    }

    private static int lowerBound(Integer[] A, int key) {
        int lo = 0;
        int hi = A.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (A[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    private static int upperBound(Integer[] A, int key) {
        int lo = 0;
        int hi = A.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (A[mid] <= key) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    private static int countOccurrences(Integer[] A, int key) {
        return upperBound(A, key) - lowerBound(A, key);
    }

    // Complexity: O(n^2 log n) time, O(n) space
    public static boolean solve_3prod(Integer[] A) {
        int n = A.length;
        if (n < 3) return false;

        Integer[] B = new Integer[n];
        for (int i = 0; i < n; i += 1) {
            B[i] = A[i];
        }
        Integer[] tmp = new Integer[n];
        mergeSort(B, tmp, 0, n - 1);

        for (int i = 0; i < n; i += 1) {
            for (int j = i + 1; j < n; j += 1) {
                long prodL = (long) A[i] * (long) A[j];
                if (prodL < Integer.MIN_VALUE || prodL > Integer.MAX_VALUE) continue;
                int prod = (int) prodL;
                int total = countOccurrences(B, prod);
                int used = 0;
                if (A[i] == prod) used += 1;
                if (A[j] == prod) used += 1;
                if (total - used >= 1) return true;
            }
        }
        return false;
    }

    public static void main(String[] argv) {
        System.out.println(solve_3prod(new Integer[] {1, 2, 3}));
        System.out.println(solve_3prod(new Integer[] {1, 0, 1}));
        System.out.println(solve_3prod(new Integer[] {4, 7, 3}));
        System.out.println(solve_3prod(new Integer[] {-1, -2, 3}));
        System.out.println(solve_3prod(new Integer[] {1, -2, 3}));
        System.out.println(solve_3prod(new Integer[] {2, 3, 6}));
        System.out.println(solve_3prod(new Integer[] {1, 1, 1}));
        System.out.println(solve_3prod(new Integer[] {2, 5, 4, 10, 3}));
        System.out.println(solve_3prod(new Integer[] {-2, -3, 6, 1}));
    }
}
