import MyLibrary.FnList.*;
    
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign05_01 {

    public static
	<T extends Comparable<T>>
	FnList<T> insertSort(FnList<T> xs) {
	return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
    }
//
    public static<T> FnList<T>
	insertSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
		FnList <T> leftRev = FnList.<T>nil(); 
		FnList<T> right = FnList.<T>nil(); 
		while (!FnList.isNil(xs)) { 
			T x = xs.head(); 
			xs = xs.tail(); 
			while (!FnList.isNil(leftRev)&& cmp.applyAsInt(x, leftRev.head())<0) { 
				T a = leftRev.head(); 
				leftRev= leftRev.tail(); 
				right= FnList.cons(a,right);
			} 
			leftRev =FnList.cons(x,leftRev); 
			while (!FnList.isNil(right)) {
				T a = right.head(); 
				right = right.tail(); 
				leftRev= FnList.cons(a, leftRev); 

			} 

		} 
		FnList<T> res = FnList.<T>nil(); 
		while (!FnList.isNil(leftRev)) {
			res = FnList.cons(leftRev.head(), res); 
			leftRev= leftRev.tail(); 

		} 
		return res; 
    }

    public static void main(String[] args) {
		final int n=1_000_000; 
		FnList<Integer>xs=FnList.<Integer>nil();
		int k = n - 2; 
		while(k>=0) { 
			xs = FnList.cons(k,xs);
			xs = FnList.cons(k + 1, xs); 
			k -= 2; 

		} 
		long t0 = System.currentTimeMillis();
		FnList<Integer>ys = insertSort(xs); 
		long t1 = System.currentTimeMillis(); 
		System.err.println("Sorted"+n + " items in  " +(t1-t0)+ " ms ");
		int i =0;
		FnList<Integer> cur= ys; 
		while (i<10 && !FnList.isNil(cur)) { 
			System.err.println(cur.head() + (i== 9 ? "\n": " ")); 
			cur = cur.tail();
			i++; 
		}

    }

} // end of [public class Assign05_01{...}]
