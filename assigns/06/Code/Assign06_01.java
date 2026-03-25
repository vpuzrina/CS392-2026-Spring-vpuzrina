/*
 *  Array-based Quicksort
 */
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign06_01 {
    public static <T> void arrayQuickSort(T[] A, ToIntBiFunction<T,T> cmp) {
		Quicksort(A,0,A.length- 1, cmp);
    } 
	private static <T> void Quicksort(T[]A, int lo, int hi, ToIntBiFunction<T,T> cmp) {
		if(lo >= hi) return; 
		T pivot = A[lo];
		int lt=lo, i= lo +1, gt = hi;
		while (i <= gt) {
			int c = cmp.applyAsInt(A[i], pivot);
			if(c< 0) swap(A, lt++, i++); 
			else if (c > 0) swap(A,i, gt--);
			else i++;
		}
		Quicksort(A,lo, lt - 1, cmp);
		Quicksort(A, gt + 1, hi, cmp);

	}
	private static <T> void swap (T[] A, int i, int j) {
		T t = A[i];
		A[i] = A[j];
		A[j] = t;

	}

} // end of [public class Assign06_01{...}]
