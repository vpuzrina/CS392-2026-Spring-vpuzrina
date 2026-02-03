package Library00.FnList;

import Library00.FnList.*;

public class FnListSUtil {
//
    public static<T>
	FnList<T> nil() {
	return new FnList<T>();
    }
    public static<T>
	FnList<T>
	cons(T x0, FnList<T> xs) {
	return new FnList<T>(x0, xs);
    }
//
    // HX: [length] is O(n)
    public static<T>
	int length(FnList<T> xs) {
	int res = 0;
	while (true) {
	    if (xs.nilq()) break;
	    res += 1; xs = xs.tl();
	}
	return res;
    }
//
    public static<T>
	FnList<T> reverse(FnList<T> xs) {
	FnList<T> ys;
	ys = nil();
	while (!xs.nilq()) {
	    ys = cons(xs.hd(), ys); xs = xs.tl();
	}
	return ys;
    }    
} // end of [public class FnListSUtil{...}]
