//
// HX-2026-04-28: 30 points
// (plus up to 20 bonus points)
// This is more of a theory problem
// than a programming one.
//
import Library00.LnStrm.*;
//
public class Quiz02_04 {
    public static class AVLnode {
	int key;
	AVLnode lchild;
	AVLnode rchild;
    }
    private static AVLnode generatedRoot = null;
    private static int generatedHeight = 0;

    private static int checkAVLHeight(AVLnode avl) {
	if (avl == null) return 0;
	int hl = checkAVLHeight(avl.lchild);
	if (hl < 0) return -1;
	int hr = checkAVLHeight(avl.rchild);
	if (hr < 0) return -1;
	if (hl - hr > 1 || hr - hl > 1) return -1;
	return 1 + (hl > hr ? hl : hr);
    }

    private static long[] minNodesUpTo(int h) {
	long[] mn = new long[h + 1];
	mn[0] = 0L;
	if (h >= 1) mn[1] = 1L;
	for (int i = 2; i <= h; i += 1) {
	    mn[i] = 1L + mn[i - 1] + mn[i - 2];
	}
	return mn;
    }

    private static long maxNodes(int h) {
	if (h >= 62) return Long.MAX_VALUE / 4;
	return (1L << h) - 1L;
    }

    private static long clamp(long x, long lo, long hi) {
	if (x < lo) return lo;
	if (x > hi) return hi;
	return x;
    }

    private static AVLnode buildShape(int n, int h, long[] mn) {
	if (n == 0) return null;
	AVLnode r = new AVLnode();
	if (h <= 1) return r;

	int[][] opts = new int[][] {
	    {h - 1, h - 2},
	    {h - 2, h - 1},
	    {h - 1, h - 1}
	};
	long m = n - 1L;
	for (int t = 0; t < opts.length; t += 1) {
	    int hl = opts[t][0];
	    int hr = opts[t][1];
	    long minL = mn[hl], minR = mn[hr];
	    long maxL = maxNodes(hl), maxR = maxNodes(hr);
	    if (m < minL + minR || m > maxL + maxR) continue;
	    long nl = clamp(m - minR, minL, maxL);
	    long nr = m - nl;
	    r.lchild = buildShape((int) nl, hl, mn);
	    r.rchild = buildShape((int) nr, hr, mn);
	    return r;
	}
	r.lchild = buildShape(n - 1, h - 1, mn);
	r.rchild = null;
	return r;
    }

    private static int assignInorderKeys(AVLnode r, int next) {
	if (r == null) return next;
	next = assignInorderKeys(r.lchild, next);
	r.key = next;
	next += 1;
	next = assignInorderKeys(r.rchild, next);
	return next;
    }

    private static int treeHeight(AVLnode r) {
	if (r == null) return 0;
	int hl = treeHeight(r.lchild);
	int hr = treeHeight(r.rchild);
	return 1 + (hl > hr ? hl : hr);
    }
    //
    // HX: 10 points for this one
    // HX: If your implementation only
    // visit each node in [avl] at most once,
    // then it will be rewarded with some bonus
    // points (up to 20 bonus points).
    // For instance, if you compute the size of
    // height of a tree, then you have already
    // visited each node once.
    //
    public static boolean isAVL (AVLnode avl) {
	// HX: Please implement a function that
	// tests whether a given AVLnode is a valid
	// AVL tree. If it is unclear what an
	// AVL tree, you can readily find it on-line
	// Note that you are not asked to check if avl is
	// a binary search tree in this case.
	return checkAVLHeight(avl) >= 0;
    }
    //
    // HX: 20 points
    // This is largely about understanding AVL trees.
    // Please explain BRIEFLY as to why the generated AVL is
    // of maximal height (not minimal height). Note that this
    // is different from what is asked in Quiz02_05.
    //
    public static boolean genAVLBST() {
	// Please genenerate a binary search RBT that
	// contains exactly 1 million keys: 0, 1, 2, ..., 999999
	// such that the height of this tree is maximal (that is,
	// as large as possible). What is this height? Please give
	// a brief explanation on your implementation strategy.
	final int n = 1_000_000;
	int h = 1;
	while (true) {
	    long[] mn = minNodesUpTo(h + 1);
	    if (mn[h + 1] > n) break;
	    h += 1;
	}
	long[] mn = minNodesUpTo(h);
	generatedRoot = buildShape(n, h, mn);
	assignInorderKeys(generatedRoot, 0);
	generatedHeight = treeHeight(generatedRoot);
	// Brief explanation:
	// For AVL height h, minimal nodes satisfy M(h)=1+M(h-1)+M(h-2).
	// The maximal possible height for fixed n is largest h with M(h)<=n.
	// We build an AVL shape at that h using exactly n nodes, then assign
	// keys in-order to get a BST on 0..999999.
	return isAVL(generatedRoot);
    }
    public static void main (String[] args) {
	// Please add minimal testing code for isRBT()
	// Please add minimal testing code for genAVLBST()
	AVLnode empty = null;
	System.out.println("empty: " + isAVL(empty));

	AVLnode single = new AVLnode();
	single.key = 1;
	System.out.println("single: " + isAVL(single));

	AVLnode b = new AVLnode();
	b.key = 2;
	b.lchild = new AVLnode();
	b.lchild.key = 1;
	b.rchild = new AVLnode();
	b.rchild.key = 3;
	System.out.println("balanced: " + isAVL(b));

	AVLnode bad = new AVLnode();
	bad.key = 3;
	bad.lchild = new AVLnode();
	bad.lchild.key = 2;
	bad.lchild.lchild = new AVLnode();
	bad.lchild.lchild.key = 1;
	System.out.println("bad-balance: " + isAVL(bad));

	boolean ok = genAVLBST();
	System.out.println("generated-avl: " + ok);
	System.out.println("generated-height: " + generatedHeight);
	return /*void*/;
    }
}
