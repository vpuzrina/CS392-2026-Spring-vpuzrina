import Library00.FnList.*;
import Library00.LnStrm.*;
import Library00.FnTuple.*;

public class Final_02 {

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

    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize2() {
        LnStrm<FnList<Character>> ws = Final_01.pg2701_word$strmize();
        LnStcn<FnList<Character>> c = ws.eval0();
        FnList<FnList<Character>> revAcc = FnListSUtil.nil();
        int n = 0;
        while (c.consq()) {
            revAcc = FnListSUtil.cons(c.hd(), revAcc);
            n += 1;
            c = c.tl().eval0();
        }
        FnList<FnList<Character>> wordList = FnListSUtil.reverse(revAcc);

        @SuppressWarnings("unchecked")
        FnList<Character>[] A1 = (FnList<Character>[])(new FnList[n]);
        int i = 0;
        FnList<FnList<Character>> it = wordList;
        while (it.consq()) {
            A1[i] = it.hd();
            i += 1;
            it = it.tl();
        }

        MyLibrary.FnA1sz.FnA1sz<FnList<Character>> wrap =
            new MyLibrary.FnA1sz.FnA1sz<FnList<Character>>(A1);
        MyLibrary.FnA1sz.FnA1szSort.quickSort(
            wrap, (w1, w2) -> compareLists(w1, w2)
        );

        FnList<FnTupl2<FnList<Character>, Integer>> revL2 = FnListSUtil.nil();
        int idx = 0;
        while (idx < n) {
            FnList<Character> w = A1[idx];
            int j = idx + 1;
            while (j < n && compareLists(A1[j], w) == 0) {
                j += 1;
            }
            int count = j - idx;
            revL2 = FnListSUtil.cons(
                new FnTupl2<FnList<Character>, Integer>(w, count), revL2
            );
            idx = j;
        }
        FnList<FnTupl2<FnList<Character>, Integer>> L2 = FnListSUtil.reverse(revL2);

        int m = L2.length();
        @SuppressWarnings("unchecked")
        FnTupl2<FnList<Character>, Integer>[] A2 =
            (FnTupl2<FnList<Character>, Integer>[])(new FnTupl2[m]);
        int k = 0;
        FnList<FnTupl2<FnList<Character>, Integer>> jt = L2;
        while (jt.consq()) {
            A2[k] = jt.hd();
            k += 1;
            jt = jt.tl();
        }

        MyLibrary.FnA1sz.FnA1sz<FnTupl2<FnList<Character>, Integer>> wrap2 =
            new MyLibrary.FnA1sz.FnA1sz<FnTupl2<FnList<Character>, Integer>>(A2);
        MyLibrary.FnA1sz.FnA1szSort.quickSort(
            wrap2,
            (p1, p2) -> {
                int cc = Integer.compare(p2.sub1, p1.sub1);
                if (cc != 0) return cc;
                return compareLists(p1.sub0, p2.sub0);
            }
        );

        FnList<FnTupl2<FnList<Character>, Integer>> sorted = FnListSUtil.nil();
        for (int q = m - 1; q >= 0; q -= 1) {
            sorted = FnListSUtil.cons(A2[q], sorted);
        }
        return sorted;
    }

    public static void main(String[] args) {
        FnList<FnTupl2<FnList<Character>, Integer>> L = pg2701_word$count$listize2();
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
