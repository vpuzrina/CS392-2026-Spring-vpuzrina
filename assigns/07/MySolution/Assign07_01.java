import Library00.LnStrm.*;

import java.util.function.ToIntBiFunction;

public class Assign07_01 {
//
    private static <T>
    LnStrm<T> rebuildStrm(LnStcn<T> cxs) {
        return new LnStrm<T>(() -> cxs);
    }

    private static <T>
    LnStrm<LnStrm<T>> rebuildOuter(LnStcn<LnStrm<T>> cxss) {
        return new LnStrm<LnStrm<T>>(() -> cxss);
    }

    private static <T>
    LnStrm<LnStrm<T>> skipEmptyFront(LnStrm<LnStrm<T>> fxss) {
        return new LnStrm<LnStrm<T>>(
            () -> {
                LnStcn<LnStrm<T>> cxss = fxss.eval0();
                while (cxss.consq()) {
                    LnStrm<T> fs = cxss.hd();
                    LnStcn<T> cxs = fs.eval0();
                    if (cxs.consq()) {
                        LnStrm<T> s1 = rebuildStrm(cxs);
                        return new LnStcn<LnStrm<T>>(s1, cxss.tl());
                    }
                    cxss = cxss.tl().eval0();
                }
                return new LnStcn<LnStrm<T>>();
            }
        );
    }

    private static <T>
    LnStrm<LnStrm<T>> insertOrdered
    (LnStrm<T> fs, LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
        LnStcn<T> cfs = fs.eval0();
        if (cfs.nilq()) return fxss;
        LnStrm<T> s1 = rebuildStrm(cfs);
        return new LnStrm<LnStrm<T>>(
            () -> {
                LnStcn<LnStrm<T>> cxss = fxss.eval0();
                if (cxss.nilq()) {
                    return new LnStcn<LnStrm<T>>(s1, new LnStrm<LnStrm<T>>());
                }
                LnStrm<T> h = cxss.hd();
                LnStcn<T> ch = h.eval0();
                if (ch.nilq()) {
                    return insertOrdered(s1, cxss.tl(), cmpr).eval0();
                }
                LnStrm<T> h1 = rebuildStrm(ch);
                if (cmpr.applyAsInt(cfs.hd(), ch.hd()) <= 0) {
                    return new LnStcn<LnStrm<T>>(s1, rebuildOuter(new LnStcn<LnStrm<T>>(h1, cxss.tl())));
                } else {
                    return new LnStcn<LnStrm<T>>(h1, insertOrdered(s1, cxss.tl(), cmpr));
                }
            }
        );
    }

    public static<T>
	LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T,T> cmpr) {
        LnStrm<LnStrm<T>> nxss = skipEmptyFront(fxss);
        return new LnStrm<T>(
            () -> {
                LnStcn<LnStrm<T>> cxss = nxss.eval0();
                if (cxss.nilq()) return new LnStcn<T>();
                LnStrm<T> s0 = cxss.hd();
                LnStcn<T> c0 = s0.eval0();
                T x0 = c0.hd();
                LnStrm<T> t0 = c0.tl();
                LnStrm<LnStrm<T>> rest = cxss.tl();
                LnStrm<LnStrm<T>> fxss2 = insertOrdered(t0, rest, cmpr);
                return new LnStcn<T>(x0, mergeLnStrm(fxss2, cmpr));
            }
        );
    }
}


//

 // end of [public class Assign07_01{...}]

