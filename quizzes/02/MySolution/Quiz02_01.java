//
// HX-2026-04-28: 50 points
//
// This question tests your understanding
// of recursion and time analysis involving
// recursion.
// Given a sequence xs, a subsequence of xs
// can be represented as a list of integers
// (representing indices). For instance, given
// xs = "Hello", (0, 2, 4) refers to the subeqence
// "Hlo" (since xs[0] = 'H', xs[2] = 'l', and
// xs[4] = 'o'); (0, 3, 4) also refers to "Hlo".
// The subsequece (0, 2, 4) is to the left of
// the subsequece (0, 3, 4) as (0, 2, 4) is less
// than (0, 3, 4) according to the lexicographic
// ordering.
//
// Here you are asked to implement a function that
// finds the longest leftmost ascending subsequence
// of a given sequence.
// For instance, suppose xs = [1,2,1,2,3,1,2,3,4],
// the longest leftmost ascending subsequence of xs
// is represented by (0, 1, 3, 4, 7, 8) (which refers
// to [1,2,2,3,3,4] in xs).
//
// In order to receive 50 points, your implementation
// should be quadratic time, that is, O(n^2) time and
// you MUST give a brief explanation as to why it is so.
// Otherwise, a working solution receives at most 60%, that
// is, 30 points out of 50 points.
//
import Library00.FnList.*;
// Please see Library00/FnList for FnList.java
import Library00.FnA1sz.*;
// Please see Library00/FnA1sz for FnA1sz.java
public class Quiz02_01 {
    public static
	<T extends Comparable<T>>
	FnList<Integer> FnA1szLongestMonoSubsequence(FnA1sz<T> xs) {
	// HX-2025-11-19:
	// This method finds the leftmost longest ascending subsequence
	// of xs. Note that the returned list consists of the indices of
	// the elements of the subsequence.
	int n = xs.length();
	if (n == 0) return new FnList<Integer>();

	int[] bestFrom = new int[n];
	for (int i = n - 1; i >= 0; i -= 1) {
	    int best = 1;
	    T xi = xs.getAt(i);
	    for (int j = i + 1; j < n; j += 1) {
		if (xi.compareTo(xs.getAt(j)) <= 0) {
		    int cand = 1 + bestFrom[j];
		    if (cand > best) best = cand;
		}
	    }
	    bestFrom[i] = best;
	}

	int L = 0;
	for (int i = 0; i < n; i += 1) {
	    if (bestFrom[i] > L) L = bestFrom[i];
	}

	int[] picks = new int[L];
	int k = 0;
	int lastIndex = -1;
	T lastValue = null;
	int remaining = L;

	while (remaining > 0) {
	    for (int i = lastIndex + 1; i < n; i += 1) {
		if (bestFrom[i] < remaining) continue;
		T xi = xs.getAt(i);
		if (lastValue != null && lastValue.compareTo(xi) > 0) continue;
		picks[k] = i;
		k += 1;
		lastIndex = i;
		lastValue = xi;
		remaining -= 1;
		break;
	    }
	}

	FnList<Integer> ans = new FnList<Integer>();
	for (int i = L - 1; i >= 0; i -= 1) {
	    ans = new FnList<Integer>(picks[i], ans);
	}
	return ans;
    }
    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for FnA1szLongestMonoSubsequence
	Integer[] a1 = new Integer[] {1,2,1,2,3,1,2,3,4};
	FnA1sz<Integer> xs1 = new FnA1sz<Integer>(a1);
	FnList<Integer> r1 = FnA1szLongestMonoSubsequence(xs1);
	r1.iforitm((i, x) -> System.out.print((i > 0 ? "," : "") + x));
	System.out.println();

	Integer[] a2 = new Integer[] {5,4,3,2};
	FnA1sz<Integer> xs2 = new FnA1sz<Integer>(a2);
	FnList<Integer> r2 = FnA1szLongestMonoSubsequence(xs2);
	r2.iforitm((i, x) -> System.out.print((i > 0 ? "," : "") + x));
	System.out.println();

	// Complexity explanation:
	// bestFrom uses two nested loops over n -> O(n^2).
	// reconstruction scans forward at most n for each chosen index (<= n) -> O(n^2).
	// Total O(n^2) time.
	return /*void*/;
    }
} // end of [public class Quiz02_01{...}]
