//
// HX-2026-03-04: 30 points
// This one may seem easy but can be time-consuming
// if you use a brute-force approach.
// Hint: Try to think about implementing bubble-sort
// without recursion
//
public class Quiz01_03 {
    private static <T extends Comparable<T>> void cmpSwap(T[] A, int i, int j) {
        if (A[i].compareTo(A[j]) > 0) {
            T temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
    }

    private static <T extends Comparable<T>> void bubblePass(T[] A) {
        cmpSwap(A, 0, 1);
        cmpSwap(A, 1, 2);
        cmpSwap(A, 2, 3);
        cmpSwap(A, 3, 4);
        cmpSwap(A, 4, 5);
        cmpSwap(A, 5, 6);
        cmpSwap(A, 6, 7);
        cmpSwap(A, 7, 8);
        cmpSwap(A, 8, 9);
        cmpSwap(A, 9, 10);
        cmpSwap(A, 10, 11);
        cmpSwap(A, 11, 12);
        cmpSwap(A, 12, 13);
        cmpSwap(A, 13, 14);
        cmpSwap(A, 14, 15);
        cmpSwap(A, 15, 16);
        cmpSwap(A, 16, 17);
        cmpSwap(A, 17, 18);
        cmpSwap(A, 18, 19);
    }

    public static
    <T extends Comparable<T>>
    T[] sort20WithNoRecursion(
        T x00, T x01, T x02, T x03, T x04, T x05, T x06, T x07, T x08, T x09,
        T x10, T x11, T x12, T x13, T x14, T x15, T x16, T x17, T x18, T x19
    ) {
        @SuppressWarnings("unchecked")
        T[] A = (T[]) java.lang.reflect.Array.newInstance(x00.getClass(), 20);
        A[0]  = x00; A[1]  = x01; A[2]  = x02; A[3]  = x03; A[4]  = x04;
        A[5]  = x05; A[6]  = x06; A[7]  = x07; A[8]  = x08; A[9]  = x09;
        A[10] = x10; A[11] = x11; A[12] = x12; A[13] = x13; A[14] = x14;
        A[15] = x15; A[16] = x16; A[17] = x17; A[18] = x18; A[19] = x19;
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        bubblePass(A);
        return A;
    }

    public static void main(String[] args) {
        Integer[] A1 =
            sort20WithNoRecursion(
                8, 15, 3, 4, 9, 15, 6, 8, 17, 2,
                19, 1, 12, 7, 11, 20, 14, 5, 10, 13
            );
        for (Integer x : A1) {
            System.out.println(x + " ");
        }
        System.out.println();
        String[] A2 =
            sort20WithNoRecursion(
                "d", "f", "r", "e", "w", "x", "k", "a", "m", "b",
                "p", "z", "h", "c", "q", "n", "u", "g", "y", "t"
            );
        for (String x : A2) {
            System.out.println(x + " ");
        }
        System.out.println();
    }
}
