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

    private static <T> FnList<Decor<T>> decorate(FnList<T> xs) {
        FnList<Decor<T>> rev = new FnList<Decor<T>>();
        int idx = 0;
        FnList<T> it = xs;
        while (!it.nilq()) {
            rev = new FnList<Decor<T>>(new Decor<T>(it.hd(), idx), rev);
            it = it.tl();
            idx += 1;
        }
        return reverse(rev);
    }

    private static <T> FnList<T> undecorate(FnList<Decor<T>> ds) {
        FnList<T> rev = new FnList<T>();
        while (!ds.nilq()) {
            rev = new FnList<T>(ds.hd().item, rev);
            ds = ds.tl();
        }
        return reverse(rev);
    }

    public static <T>
    FnList<T> someRevStableSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
        FnList<Decor<T>> decorated = decorate(xs);
        ToIntBiFunction<Decor<T>, Decor<T>> strictCmp =
            (a, b) -> {
                int c = cmp.applyAsInt(a.item, b.item);
                if (c != 0) return c;
                return Integer.compare(b.idx, a.idx);
            };
        FnList<Decor<T>> sortedDecor = someSort(decorated, strictCmp);
        return undecorate(sortedDecor);
    }
}
