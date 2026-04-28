//
// HX: 30 points
//
import MyLibrary.FnList.*;
import java.util.function.ToIntBiFunction;

public class Quiz01_05 {
    private static <T> FnList<T> reverse(FnList<T> xs) {
        FnList<T> ys = new FnList<T>();
        while (!xs.nilq()) {
            ys = new FnList<T>(xs.hd(), ys);
            xs = xs.tl();
        }
        return ys;
    }

    private static <T> FnList<T> insertStable(FnList<T> ys, T x, ToIntBiFunction<T, T> cmp) {
        if (ys.nilq() || cmp.applyAsInt(x, ys.hd()) < 0) {
            return new FnList<T>(x, ys);
        }
        return new FnList<T>(ys.hd(), insertStable(ys.tl(), x, cmp));
    }

    public static <T>
    FnList<T> someSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
        FnList<T> sorted = new FnList<T>();
        while (!xs.nilq()) {
            sorted = insertStable(sorted, xs.hd(), cmp);
            xs = xs.tl();
        }
        return sorted;
    }

    private static class Decor<T> {
        T item;
        int idx;
        Decor(T item, int idx) {
            this.item = item;
            this.idx = idx;
        }
    }

    public static <T>
    FnList<T> someRevStableSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
        FnList<Decor<T>> decorated = new FnList<Decor<T>>();
        int idx = 0;
        FnList<T> it = xs;
        while (!it.nilq()) {
            decorated = new FnList<Decor<T>>(new Decor<T>(it.hd(), idx), decorated);
            it = it.tl();
            idx += 1;
        }

        FnList<Decor<T>> sortedDecor = someSort(
            decorated,
            (a, b) -> {
                int c = cmp.applyAsInt(a.item, b.item);
                if (c != 0) return c;
                return Integer.compare(b.idx, a.idx);
            }
        );

        FnList<T> outRev = new FnList<T>();
        while (!sortedDecor.nilq()) {
            outRev = new FnList<T>(sortedDecor.hd().item, outRev);
            sortedDecor = sortedDecor.tl();
        }
        return reverse(outRev);
    }
}
