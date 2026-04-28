//
// HX-2026-04-28: 50 points
//
/*
A description on Game-of-24 and an accompanying
demo can be found by visiting the following link:
https://github.com/githwxi/XATSHOME/tree/main/contrib/githwxi/pground/proj002%40250507/misc004
Please give a high-level description in English as to
how Game-of-24 can be solved using either DFS or BFS.
Your description should be given in a README file for
this assignment.
1. Please give a DFS-based implementation according to your
   description that should directly use the DFirstEnumerate method.
2. Please give a BFS-based implementation according to your
   description that should directly use the BFirstEnumerate method.
*/
//
import Library00.LnStrm.*;
import Library00.FnList.*;
import Library00.FnGtree.*;

class UnsupportedOpr
    extends RuntimeException {
    String opr;
    public UnsupportedOpr(String opr) {
	this.opr = opr;
    }
}

abstract class Term {
    public String tag = "Term";
    public abstract double eval();
    // eval() returns the value of the term
}

class TermInt extends Term {
    public int val;
    public TermInt(int val) {
	this.tag = "TermInt"; this.val = val;
    }
    public double eval() { return val; }
}

class TermOpr extends Term {
    public String opr;
    public Term arg1, arg2;
    public TermOpr(String opr0, Term arg1, Term arg2) {
	this.tag = "TermOpr";
	this.opr = opr0; this.arg1 = arg1; this.arg2 = arg2;
    }
    public double eval() {
	switch (opr) {
	  case "+":
	      return arg1.eval() + arg2.eval();
	  case "-":
	      return arg1.eval() - arg2.eval();
	  case "*":
	      return arg1.eval() * arg2.eval();
	  case "/":
	      return arg1.eval() / arg2.eval();
	}
	throw new UnsupportedOpr(     opr     );
    }
}

public class Quiz02_03 {
//
    /*
    High-level idea:
    Represent each search state as a multiset/list of Terms.
    A child state is created by picking two terms, combining them with
    an operator (+, -, *, / with both orders for - and /), and putting
    the result term back with the remaining terms.
    This shrinks the number of terms by 1 each step, so the tree is finite.
    A solved state is one remaining term whose value is approximately 24.
    BFS/DFS solving is done by calling BFirstEnumerate/DFirstEnumerate on
    the root state and filtering solved states.
    */

    private static boolean eq24(double x) {
        return Math.abs(x - 24.0) < 1e-9;
    }

    private static class Game24State implements FnGtree<Term> {
        Term[] ts;
        int n;

        Game24State(Term[] ts, int n) {
            this.ts = ts;
            this.n = n;
        }

        public Term value() {
            if (n == 1 && eq24(ts[0].eval())) return ts[0];
            return null;
        }

        private static Term[] restWithout(Term[] a, int n, int i, int j) {
            Term[] out = new Term[n - 2];
            int k = 0;
            for (int p = 0; p < n; p += 1) {
                if (p == i || p == j) continue;
                out[k] = a[p];
                k += 1;
            }
            return out;
        }

        private static Game24State extend(Term[] rest, Term t) {
            Term[] out = new Term[rest.length + 1];
            for (int i = 0; i < rest.length; i += 1) out[i] = rest[i];
            out[rest.length] = t;
            return new Game24State(out, out.length);
        }

        public FnList<FnGtree<Term>> children() {
            FnList<FnGtree<Term>> res = FnListSUtil.nil();
            if (n <= 1) return res;

            for (int i = 0; i < n; i += 1) {
                for (int j = i + 1; j < n; j += 1) {
                    Term a = ts[i];
                    Term b = ts[j];
                    Term[] rest = restWithout(ts, n, i, j);

                    res = FnListSUtil.cons(extend(rest, new TermOpr("+", a, b)), res);
                    res = FnListSUtil.cons(extend(rest, new TermOpr("*", a, b)), res);
                    res = FnListSUtil.cons(extend(rest, new TermOpr("-", a, b)), res);
                    res = FnListSUtil.cons(extend(rest, new TermOpr("-", b, a)), res);
                    if (Math.abs(b.eval()) > 1e-12) {
                        res = FnListSUtil.cons(extend(rest, new TermOpr("/", a, b)), res);
                    }
                    if (Math.abs(a.eval()) > 1e-12) {
                        res = FnListSUtil.cons(extend(rest, new TermOpr("/", b, a)), res);
                    }
                }
            }
            return res.reverse();
        }
    }

    private static Term[] initTerms(int n1, int n2, int n3, int n4) {
        return new Term[] {
            new TermInt(n1),
            new TermInt(n2),
            new TermInt(n3),
            new TermInt(n4)
        };
    }
//
    public LnStrm<Term> GameOf24_bfs_solve
	(int n1, int n2, int n3, int n4) {
	// Please find ALL the solutions of GameOf24
	// for the input n1, n2, n3, and n4
	// Each solution is represented as a Term
	// that evaluates to 24
	// Note that your solution should be based on
	// BFirstEnumerate implemented in Assign07_01
	Game24State root = new Game24State(initTerms(n1, n2, n3, n4), 4);
	return FnGtreeSUtil.BFirstEnumerate(root)
	    .filter0((tm) -> tm != null && eq24(tm.eval()));
    }
//
    public LnStrm<Term> GameOf24_dfs_solve
	(int n1, int n2, int n3, int n4) {
	// Please find ALL the solutions of GameOf24
	// for the input n1, n2, n3, and n4
	// Note that your solution should be based on
	// DFirstEnumerate implemented in Assign07_01
	Game24State root = new Game24State(initTerms(n1, n2, n3, n4), 4);
	return FnGtreeSUtil.DFirstEnumerate(root)
	    .filter0((tm) -> tm != null && eq24(tm.eval()));
    }
//
    // Please add minimal testing code for GameOf24_bfs_solve
    // Please add minimal testing code for GameOf24_dfs_solve
//
    private static void printSome(String tag, LnStrm<Term> xs, int limit) {
        LnStcn<Term> cs = xs.eval0();
        int i = 0;
        while (cs.consq() && i < limit) {
            System.out.println(tag + " solution#" + (i + 1) + " = " + cs.hd().eval());
            cs = cs.tl().eval0();
            i += 1;
        }
    }

    public static void main(String[] args) {
        Quiz02_03 q = new Quiz02_03();

        LnStrm<Term> bfs1 = q.GameOf24_bfs_solve(4, 4, 10, 10);
        LnStrm<Term> dfs1 = q.GameOf24_dfs_solve(4, 4, 10, 10);
        printSome("bfs(4,4,10,10)", bfs1, 5);
        printSome("dfs(4,4,10,10)", dfs1, 5);

        LnStrm<Term> bfs2 = q.GameOf24_bfs_solve(1, 3, 4, 6);
        LnStrm<Term> dfs2 = q.GameOf24_dfs_solve(1, 3, 4, 6);
        printSome("bfs(1,3,4,6)", bfs2, 5);
        printSome("dfs(1,3,4,6)", dfs2, 5);
    }

} // end of [public class Quiz02_03{...}]
