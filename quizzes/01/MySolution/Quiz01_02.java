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

    private static boolean contains(Integer[] A, int key) {
        int lo = 0;
        int hi = A.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (A[mid] == key) return true;
            if (A[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return false;
    }

    public static boolean solve_3prod(Integer[] A) {
        int n = A.length;
        if (n == 0) return false;

        Integer[] B = new Integer[n];
        for (int i = 0; i < n; i += 1) {
            B[i] = A[i];
        }
        Integer[] tmp = new Integer[n];
        mergeSort(B, tmp, 0, n - 1);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int prod = A[i] * A[j];
                if (contains(B, prod)) return true;
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
    }
}
