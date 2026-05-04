import Library00.FnList.*;
import Library00.LnStrm.*;
import Library00.FnTuple.*;

import java.util.function.ToIntBiFunction;

public class Final_04 {

    private static int compareLists(FnList<Character> a, FnList<Character> b) {
        while (a.consq() && b.consq()) {
            int c = Character.compare(a.hd(), b.hd());
            if (c != 0) return c;
            a = a.tl();
            b = b.tl();
        }
        if (a.nilq() && b.nilq()) return 0;
        return a.nilq() ? -1 : 1;
    }

    private static String wordToString(FnList<Character> w) {
        StringBuilder sb = new StringBuilder();
        w.foritm(ch -> sb.append(ch));
        return sb.toString();
    }

    private static FnList<Character> stringToFnList(String s) {
        FnList<Character> r = FnListSUtil.<Character>nil();
        for (int i = s.length() - 1; i >= 0; i -= 1) {
            r = FnListSUtil.cons(s.charAt(i), r);
        }
        return r;
    }

    private static <T>
    FnList<T> mergeRuns(FnList<T> xs, FnList<T> ys, ToIntBiFunction<T, T> cmp) {
        FnList<T> rev = FnListSUtil.<T>nil();
        while (xs.consq() && ys.consq()) {
            if (cmp.applyAsInt(xs.hd(), ys.hd()) <= 0) {
                rev = FnListSUtil.cons(xs.hd(), rev);
                xs = xs.tl();
            } else {
                rev = FnListSUtil.cons(ys.hd(), rev);
                ys = ys.tl();
            }
        }
        while (xs.consq()) {
            rev = FnListSUtil.cons(xs.hd(), rev);
            xs = xs.tl();
        }
        while (ys.consq()) {
            rev = FnListSUtil.cons(ys.hd(), rev);
            ys = ys.tl();
        }
        return FnListSUtil.reverse(rev);
    }

    private static <T>
    FnList<T> mergeSortIter(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
        int n = xs.length();
        if (n <= 1) return xs;
        @SuppressWarnings("unchecked")
        FnList<T>[] runs = (FnList<T>[]) new FnList[n];
        int i = 0;
        FnList<T> cur = xs;
        while (cur.consq()) {
            runs[i] = FnListSUtil.cons(cur.hd(), FnListSUtil.<T>nil());
            i += 1;
            cur = cur.tl();
        }
        int len = n;
        while (len > 1) {
            int newLen = (len + 1) / 2;
            int j = 0;
            while (j < newLen) {
                int idx = 2 * j;
                if (idx + 1 < len) {
                    runs[j] = mergeRuns(runs[idx], runs[idx + 1], cmp);
                } else {
                    runs[j] = runs[idx];
                }
                j += 1;
            }
            len = newLen;
        }
        return runs[0];
    }

    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize4() {
        Final_04Map<String, Integer> map = new Final_04Map<String, Integer>();

        LnStrm<FnList<Character>> ws = Final_01.pg2701_word$strmize();
        LnStcn<FnList<Character>> c = ws.eval0();
        while (c.consq()) {
            String key = wordToString(c.hd());
            Integer prev = map.getOrNull(key);
            map.put(key, prev == null ? 1 : prev + 1);
            c = c.tl().eval0();
        }

        FnList<FnTupl2<String, Integer>> kvList = map.keyval$listize();

        FnList<FnTupl2<FnList<Character>, Integer>> WNS =
            FnListSUtil.<FnTupl2<FnList<Character>, Integer>>nil();
        FnList<FnTupl2<String, Integer>> it = kvList;
        while (it.consq()) {
            FnTupl2<String, Integer> p = it.hd();
            WNS = FnListSUtil.cons(
                new FnTupl2<FnList<Character>, Integer>(stringToFnList(p.sub0), p.sub1),
                WNS
            );
            it = it.tl();
        }

        return mergeSortIter(
            WNS,
            (p1, p2) -> {
                int cc = Integer.compare(p2.sub1, p1.sub1);
                if (cc != 0) return cc;
                return compareLists(p1.sub0, p2.sub0);
            }
        );
    }

    public static void main(String[] args) {
        FnList<FnTupl2<FnList<Character>, Integer>> L = pg2701_word$count$listize4();
        FnList<FnTupl2<FnList<Character>, Integer>> it = L;
        int i = 0;
        while (it.consq() && i < 100) {
            FnTupl2<FnList<Character>, Integer> p = it.hd();
            StringBuilder sb = new StringBuilder();
            p.sub0.foritm(ch -> sb.append(ch));
            System.out.println(p.sub1 + "\t" + sb.toString());
            it = it.tl();
            i += 1;
        }
    }
}
