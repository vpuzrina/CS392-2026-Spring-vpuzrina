package MyLibrary.FnStrn;

import java.util.function.Predicate;
import java.util.function.BiPredicate;

public class FnStrnSUtil {
    public static FnStrn concat(FnStrn s1, FnStrn s2) {
        return FnStrn.concat(s1, s2);
    }
    public static boolean forall(FnStrn s, Predicate<Character> pred) {
        return FnStrn.forall(s, pred);
    }
    public static boolean iforall(FnStrn s, BiPredicate<Integer, Character> pred) {
        return FnStrn.iforall(s, pred);
    }
}
