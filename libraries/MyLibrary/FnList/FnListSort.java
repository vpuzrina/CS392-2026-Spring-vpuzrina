package MyLibrary.FnList;

import java.util.function.ToIntBiFunction;

public class FnListSort {
    public static <T extends Comparable<T>>
    FnList<T> insertSort(FnList<T> xs) {
        return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
    }

    public static <T>
    FnList<T> insertSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
        FnList<T> sorted = new FnList<T>();
        while (!xs.nilq()) {
            sorted = insertOne(sorted, xs.hd(), cmp);
            xs = xs.tl();
        }
        return sorted;
    }

    private static <T>
    FnList<T> insertOne(FnList<T> ys, T x, ToIntBiFunction<T, T> cmp) {
        if (ys.nilq() || cmp.applyAsInt(x, ys.hd()) < 0) {
            return new FnList<T>(x, ys);
        }
        return new FnList<T>(ys.hd(), insertOne(ys.tl(), x, cmp));
    }
}
