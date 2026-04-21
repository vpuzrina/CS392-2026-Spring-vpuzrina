import Library00.LnStrm.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign07_01 {
//
    public static<T>
	LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T,T> cmpr) {
        if (fxss.isEmpty()) return LnStrm.empty();
        LnStrm<T> minStream = null;
        T minVal = null;
        LnStrm<LnStrm<T>> rest = fxss;
        while (!rest.isEmpty()) {
            LnStrm<T> s = rest.head(); 
            if (!s.isEmpty()) {
                T val = s.head();
                if (minStream == null || cmpr.applyAsInt(val, minVal) < 0)
{
                    minVal = val;
                    minStream = s;
                }

            }
            rest = rest.tail();
        }
        if (minStream == null) return LnStrm.empty();
        final T headVal = minVal;
        final LnStrm<LnStrm<T>> newStreams = rebuild(fxss, minStream);
        return LnStrm.cons(headVal, () -> mergeLnStrm(newStreams, cmpr));
    }
    private static <T> 
    LnStrm<LnStrm<T>> rebuild(LnStrm<LnStrm<T>> fxss, LnStrm<T> target) {
        if (fxss.isEmpty()) return LnStrm.empty();
        if (fxss.head() == target) {
            LnStrm<T> tail = fxss.head().tail();
            return tail.isEmpty() ? fxss.tail() : LnStrm.cons(tail, fxss::tail);

        }
        return LnStrm.cons(fxss.head(), () -> rebuild(fxss.tail(), target));
    }
}


//

 // end of [public class Assign07_01{...}]

