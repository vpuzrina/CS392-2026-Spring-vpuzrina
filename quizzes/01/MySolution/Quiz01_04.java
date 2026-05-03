public class Quiz01_04 {
    public static
    <T extends Comparable<T>>
    LnList<T> LnListInsertSort(LnList<T> xs) {
        if (xs.nilq1() || xs.tl1().nilq1()) {
            return xs;
        }

        LnList<T> sorted = xs;
        xs = sorted.unlink();
        LnList<T> sortedLast = sorted;
        LnList<T> sortedSecondLast = null;

        while (xs.consq1()) {
            LnList<T> xnode = xs;
            xs = xs.unlink();

            if (xnode.hd1().compareTo(sortedLast.hd1()) >= 0) {
                sortedLast.link(xnode);
                sortedSecondLast = sortedLast;
                sortedLast = xnode;
                continue;
            }

            if (sortedSecondLast != null
                && xnode.hd1().compareTo(sortedSecondLast.hd1()) >= 0) {
                LnList<T> suffix = sortedSecondLast.unlink();
                sortedSecondLast.link(xnode);
                xnode.link(suffix);
                sortedSecondLast = xnode;
                continue;
            }

            if (xnode.hd1().compareTo(sorted.hd1()) < 0) {
                xnode.link(sorted);
                sorted = xnode;
                if (sortedSecondLast == null) {
                    sortedSecondLast = sorted;
                }
                continue;
            }

            LnList<T> prev = sorted;
            LnList<T> cur = sorted.tl1();
            while (cur.consq1() && cur.hd1().compareTo(xnode.hd1()) <= 0) {
                prev = cur;
                cur = cur.tl1();
            }
            LnList<T> suffix = prev.unlink();
            prev.link(xnode);
            xnode.link(suffix);
        }
        return sorted;
    }

    static class ParityInt implements Comparable<ParityInt> {
        int value;
        String tag;

        ParityInt(int value, String tag) {
            this.value = value;
            this.tag = tag;
        }

        public int compareTo(ParityInt other) {
            return Integer.compare(this.value % 2, other.value % 2);
        }

        public String toString() {
            return "(" + value + "," + tag + ")";
        }
    }

    public static void main(String[] args) {
        final int n = 1_000_000;
        LnList<Integer> xs = new LnList<Integer>();
        for (int k = n - 2; k >= 0; k -= 2) {
            xs = new LnList<Integer>(k, xs);
            xs = new LnList<Integer>(k + 1, xs);
        }
        long t0 = System.currentTimeMillis();
        LnList<Integer> ys = LnListInsertSort(xs);
        long t1 = System.currentTimeMillis();
        System.out.println("sorted 1m nearly sorted elements in " + (t1 - t0) + " ms");
        ys.iforitm1((i, x) -> {
            if (i < 10) System.out.print(x + " ");
        });
        System.out.println();

        LnList<ParityInt> ps =
            new LnList<ParityInt>(new ParityInt(5, "a"),
            new LnList<ParityInt>(new ParityInt(6, "b"),
            new LnList<ParityInt>(new ParityInt(8, "e"),
            new LnList<ParityInt>(new ParityInt(9, "c"),
            new LnList<ParityInt>(new ParityInt(5, "k"),
            new LnList<ParityInt>(new ParityInt(3, "w"),
            new LnList<ParityInt>()))))));
        LnList<ParityInt> sortedParity = LnListInsertSort(ps);
        sortedParity.foritm1(x -> System.out.print(x + " "));
        System.out.println();
    }
}
