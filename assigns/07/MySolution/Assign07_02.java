import Library00.LnStrm.*;
import Library00.FnTuple.*;

public class Assign07_02 {
    private static long cubeSum(FnTupl2<Integer, Integer> p) {
        long x = p.sub0.longValue();
        long y = p.sub1.longValue();
        return x * x * x + y * y * y;
    }

    private static LnStrm<FnTupl2<Integer, Integer>>
    pairsFrom(int x, int y) {
        return new LnStrm<FnTupl2<Integer, Integer>>(
            () -> new LnStcn<FnTupl2<Integer, Integer>>(
                new FnTupl2<Integer, Integer>(x, y),
                pairsFrom(x, y + 1)
            )
        );
    }

    private static LnStrm<LnStrm<FnTupl2<Integer, Integer>>>
    pairStreamsFrom(int x) {
        return new LnStrm<LnStrm<FnTupl2<Integer, Integer>>>(
            () -> new LnStcn<LnStrm<FnTupl2<Integer, Integer>>>(
                pairsFrom(x, x),
                pairStreamsFrom(x + 1)
            )
        );
    }

    private static LnStrm<FnTupl2<Integer, Integer>>
    skipSameCubeSum(LnStrm<FnTupl2<Integer, Integer>> fps, long sum0) {
        return new LnStrm<FnTupl2<Integer, Integer>>(
            () -> {
                LnStcn<FnTupl2<Integer, Integer>> cps = fps.eval0();
                while (cps.consq() && cubeSum(cps.hd()) == sum0) {
                    cps = cps.tl().eval0();
                }
                return cps;
            }
        );
    }

    private static LnStrm<Integer>
    ramanujanFromPairs(LnStrm<FnTupl2<Integer, Integer>> fps) {
        return new LnStrm<Integer>(
            () -> {
                LnStcn<FnTupl2<Integer, Integer>> c1 = fps.eval0();
                if (c1.nilq()) return new LnStcn<Integer>();
                long s1 = cubeSum(c1.hd());
                LnStrm<FnTupl2<Integer, Integer>> rest = c1.tl();
                while (true) {
                    LnStcn<FnTupl2<Integer, Integer>> c2 = rest.eval0();
                    if (c2.nilq()) return new LnStcn<Integer>();
                    long s2 = cubeSum(c2.hd());
                    if (s2 == s1) {
                        LnStrm<FnTupl2<Integer, Integer>> next = skipSameCubeSum(c2.tl(), s1);
                        return new LnStcn<Integer>((int) s1, ramanujanFromPairs(next));
                    }
                    s1 = s2;
                    rest = c2.tl();
                }
            }
        );
    }

    public static
	LnStrm<Integer>
	ramanujanNumbers() {
	return ramanujanFromPairs(cubeSumOrderedIntegerPairs());
    }

    public static
	LnStrm<
	  FnTupl2<Integer,Integer>>
	cubeSumOrderedIntegerPairs() {
	return Assign07_01.mergeLnStrm(
            pairStreamsFrom(1),
            (p1, p2) -> {
                long s1 = cubeSum(p1);
                long s2 = cubeSum(p2);
                if (s1 < s2) return -1;
                if (s1 > s2) return 1;
                if (p1.sub0.intValue() < p2.sub0.intValue()) return -1;
                if (p1.sub0.intValue() > p2.sub0.intValue()) return 1;
                return Integer.compare(p1.sub1.intValue(), p2.sub1.intValue());
            }
        );
    }

    private static void printFirstPairs(int n) {
        LnStrm<FnTupl2<Integer, Integer>> fps = cubeSumOrderedIntegerPairs();
        for (int i = 0; i < n; i += 1) {
            LnStcn<FnTupl2<Integer, Integer>> cps = fps.eval0();
            if (cps.nilq()) break;
            System.out.println(cps.hd().toString());
            fps = cps.tl();
        }
    }

    private static void printFirstRamanujan(int n) {
        LnStrm<Integer> frs = ramanujanNumbers();
        for (int i = 0; i < n; i += 1) {
            LnStcn<Integer> crs = frs.eval0();
            if (crs.nilq()) break;
            System.out.println(crs.hd().toString());
            frs = crs.tl();
        }
    }

    public static void main(String[] args) {
	printFirstPairs(20);
        printFirstRamanujan(10);
	return;
    }

} // end of [public class Assign07_02{...}]

