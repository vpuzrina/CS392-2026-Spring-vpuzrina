//
// HX: 20 points
//

import MyLibrary.FnA1sz.*;

public class Quiz01_01 {
    public static
    <T extends Comparable<T>>
    int FnA1szBinarySearch(FnA1sz<T> A, T key) {
        int lo = 0;
        int hi = A.length() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            T v = A.getAt(mid);
            int c = v.compareTo(key);
            if (c == 0) return mid;
            if (c < 0) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Integer[] a1 = new Integer[] {1, 3, 3, 7};
        FnA1sz<Integer> A1 = new FnA1sz<>(a1);
        System.out.println(FnA1szBinarySearch(A1, 0));
        System.out.println(FnA1szBinarySearch(A1, 1));
        System.out.println(FnA1szBinarySearch(A1, 3));
        System.out.println(FnA1szBinarySearch(A1, 7));
        System.out.println(FnA1szBinarySearch(A1, 99));
        System.out.println(FnA1szBinarySearch(A1, 4));

        String[] a2 = new String[] {"a", "b", "b", "d"};
        FnA1sz<String> A2 = new FnA1sz<>(a2);
        System.out.println(FnA1szBinarySearch(A2, "0"));
        System.out.println(FnA1szBinarySearch(A2, "v"));
        System.out.println(FnA1szBinarySearch(A2, "a"));
        System.out.println(FnA1szBinarySearch(A2, "r"));
        System.out.println(FnA1szBinarySearch(A2, "p"));
        System.out.println(FnA1szBinarySearch(A2, "u"));
        return;
    }
}
