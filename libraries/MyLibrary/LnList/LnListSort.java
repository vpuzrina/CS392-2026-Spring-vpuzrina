package MyLibrary.LnList;

import java.util.function.ToIntBiFunction;

public class LnListSort {
    public static <T extends Comparable<T>>
    LnList<T> insertSort(LnList<T> xs) {
        return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
    }

    public static <T>
    LnList<T> insertSort(LnList<T> xs, ToIntBiFunction<T, T> cmp) {
        if (xs.nilq1() || xs.tl1().nilq1()) {
            return xs;
        }
        LnList<T> sorted = xs;
        xs = sorted.unlink1();

        while (xs.consq1()) {
            LnList<T> xnode = xs;
            xs = xs.unlink1();

            if (cmp.applyAsInt(xnode.hd1(), sorted.hd1()) < 0) {
                xnode.link1(sorted);
                sorted = xnode;
            } else {
                LnList<T> prev = sorted;
                LnList<T> cur = sorted.tl1();
                while (cur.consq1() && cmp.applyAsInt(cur.hd1(), xnode.hd1()) <= 0) {
                    prev = cur;
                    cur = cur.tl1();
                }
                LnList<T> suffix = prev.unlink1();
                prev.link1(xnode);
                xnode.link1(suffix);
            }
        }
        return sorted;
    }
}
