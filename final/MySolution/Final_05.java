import Library00.LnList.*;
import Library00.FnList.*;
import Library.MyPQueue.MyPQueueArray;

import java.util.function.ToIntBiFunction;

public class Final_05 {

    private static class Entry<T> {
        LnList<T> list;
        int srcIdx;
        Entry(LnList<T> list, int srcIdx) {
            this.list = list;
            this.srcIdx = srcIdx;
        }
    }

    public static<T> LnList<T>
    LnList_n$way$merge(LnList<T> xss[], ToIntBiFunction<T,T> cmp) {
        java.util.Comparator<Entry<T>> entryCmp = (a, b) -> {
            int c = cmp.applyAsInt(a.list.hd1(), b.list.hd1());
            if (c != 0) return c;
            return Integer.compare(a.srcIdx, b.srcIdx);
        };
        MyPQueueArray<Entry<T>> pq = new MyPQueueArray<Entry<T>>(entryCmp);
        for (int i = 0; i < xss.length; i += 1) {
            if (xss[i] != null && xss[i].consq1()) {
                pq.enque$raw(new Entry<T>(xss[i], i));
            }
        }
        LnList<T> result = new LnList<T>();
        LnList<T> tail = null;
        while (!pq.isEmpty()) {
            Entry<T> e = pq.deque$raw();
            LnList<T> head = e.list;
            LnList<T> rest = head.unlink1();
            if (tail == null) {
                result = head;
                tail = head;
            } else {
                tail.link1(head);
                tail = head;
            }
            if (rest.consq1()) {
                e.list = rest;
                pq.enque$raw(e);
            }
        }
        return result;
    }

    private static <T>
    LnList<T> sortRec(LnList<T> xs, ToIntBiFunction<T, T> cmp) {
        int n = xs.length1();
        if (n <= 1) return xs;
        int k = Math.min(n, 100);
        @SuppressWarnings("unchecked")
        LnList<T>[] parts = (LnList<T>[]) new LnList[k];
        int base = n / k;
        int rem = n % k;
        LnList<T> cur = xs;
        for (int i = 0; i < k; i += 1) {
            int csize = base + (i < rem ? 1 : 0);
            LnList<T> part = new LnList<T>();
            LnList<T> partTail = null;
            for (int j = 0; j < csize; j += 1) {
                LnList<T> head = cur;
                LnList<T> rest = cur.unlink1();
                cur = rest;
                if (partTail == null) {
                    part = head;
                    partTail = head;
                } else {
                    partTail.link1(head);
                    partTail = head;
                }
            }
            parts[i] = part;
        }
        for (int i = 0; i < k; i += 1) {
            parts[i] = sortRec(parts[i], cmp);
        }
        return LnList_n$way$merge(parts, cmp);
    }

    public static<T>
    FnList<T>
    LnList_mergeSort$100way(LnList<T> xs, ToIntBiFunction<T,T> cmp) {
        LnList<T> sorted = sortRec(xs, cmp);
        FnList<T> rev = FnListSUtil.<T>nil();
        while (sorted.consq1()) {
            rev = FnListSUtil.cons(sorted.hd1(), rev);
            sorted = sorted.tl1();
        }
        return FnListSUtil.reverse(rev);
    }

    public static void main(String[] args) {
        final int N = 1_000_000;

        LnList<Integer> xs = new LnList<Integer>();
        LnList<Integer> tail = null;
        for (int i = 0; i < N; i += 1) {
            LnList<Integer> node = new LnList<Integer>(i, new LnList<Integer>());
            if (tail == null) {
                xs = node;
                tail = node;
            } else {
                tail.link1(node);
                tail = node;
            }
        }

        ToIntBiFunction<Integer, Integer> parityCmp = (a, b) -> (a % 2) - (b % 2);

        long t0 = System.currentTimeMillis();
        FnList<Integer> sorted = LnList_mergeSort$100way(xs, parityCmp);
        long t1 = System.currentTimeMillis();
        System.out.println("sort time: " + (t1 - t0) + " ms");

        System.out.print("first 10:");
        FnList<Integer> it = sorted;
        for (int k = 0; k < 10 && it.consq(); k += 1) {
            System.out.print(" " + it.hd());
            it = it.tl();
        }
        System.out.println();

        FnList<Integer> it2 = sorted;
        for (int k = 0; k < N - 10 && it2.consq(); k += 1) {
            it2 = it2.tl();
        }
        System.out.print("last 10:");
        while (it2.consq()) {
            System.out.print(" " + it2.hd());
            it2 = it2.tl();
        }
        System.out.println();

        boolean ok = true;
        int total = 0;
        FnList<Integer> it3 = sorted;
        while (it3.consq()) {
            int expected = (total < N / 2) ? (2 * total) : (2 * (total - N / 2) + 1);
            if (it3.hd() != expected) { ok = false; break; }
            total += 1;
            it3 = it3.tl();
        }
        if (total != N) ok = false;
        System.out.println("stable parity sort: " + (ok ? "PASS" : "FAIL"));
    }
}
