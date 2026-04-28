package MyLibrary.FnStrn;

import java.util.function.Predicate;
import java.util.function.BiPredicate;

public class FnStrn {

    char root[];

    public FnStrn() {
	root = null;
    }
    public FnStrn(char[] cs) {
	int ln = cs.length;
	root = new char[ln];
	for (int i = 0; i < ln; i += 1) {
	    root[i] = cs[i];
	}
    }
    public FnStrn(String cs) {
	int ln = cs.length();
	root = new char[ln];
	for (int i = 0; i < ln; i += 1) {
	    root[i] = cs.charAt(i);
	}
    }

    public int length() {
	return root.length;
    }

    public char getAt(int index) {
	return root[index];
    }

    public String toString() {
        return new String(root);
    }

    public static FnStrn concat(FnStrn s1, FnStrn s2) {
        int n1 = s1.length();
        int n2 = s2.length();
        char[] out = new char[n1 + n2];
        for (int i = 0; i < n1; i += 1) out[i] = s1.getAt(i);
        for (int i = 0; i < n2; i += 1) out[n1 + i] = s2.getAt(i);
        return new FnStrn(out);
    }

    public static boolean forall(FnStrn s, Predicate<Character> pred) {
        for (int i = 0; i < s.length(); i += 1) {
            if (!pred.test(s.getAt(i))) return false;
        }
        return true;
    }

    public static boolean iforall(FnStrn s, BiPredicate<Integer, Character> pred) {
        for (int i = 0; i < s.length(); i += 1) {
            if (!pred.test(i, s.getAt(i))) return false;
        }
        return true;
    }
}
