//
// HX-2026-04-28: 50 points
// A partial implementation of
// randomized doubly linked binary search tree
// 30 points for reroot and 20 points for insert
//
public class Quiz02_06 {
    Node root = null;
    public class Node {
	int key; // key stored in the node
	int size; // size of the tree rooted as the node
	Node parent; // parent of the node
	Node lchild; // left-child of the node
	Node rchild; // right-child of the node
    }
    private Node mkNode(int key) {
	Node n = new Node();
	n.key = key;
	n.size = 1;
	n.parent = null;
	n.lchild = null;
	n.rchild = null;
	return n;
    }
    private int sz(Node n) {
	return (n == null ? 0 : n.size);
    }
    private void pull(Node n) {
	if (n != null) n.size = 1 + sz(n.lchild) + sz(n.rchild);
    }
    private void pullUp(Node n) {
	while (n != null) {
	    pull(n);
	    n = n.parent;
	}
    }
    private void rotateLeft(Node x) {
	Node y = x.rchild;
	if (y == null) return;
	Node p = x.parent;
	Node b = y.lchild;
	y.parent = p;
	if (p == null) root = y;
	else if (p.lchild == x) p.lchild = y;
	else p.rchild = y;
	y.lchild = x;
	x.parent = y;
	x.rchild = b;
	if (b != null) b.parent = x;
	pull(x);
	pull(y);
	if (p != null) pullUp(p);
    }
    private void rotateRight(Node x) {
	Node y = x.lchild;
	if (y == null) return;
	Node p = x.parent;
	Node b = y.rchild;
	y.parent = p;
	if (p == null) root = y;
	else if (p.lchild == x) p.lchild = y;
	else p.rchild = y;
	y.rchild = x;
	x.parent = y;
	x.lchild = b;
	if (b != null) b.parent = x;
	pull(x);
	pull(y);
	if (p != null) pullUp(p);
    }
    private Node kth(Node n, int k) {
	while (n != null) {
	    int ls = sz(n.lchild);
	    if (k < ls) {
		n = n.lchild;
	    } else if (k == ls) {
		return n;
	    } else {
		k = k - ls - 1;
		n = n.rchild;
	    }
	}
	return null;
    }
    private boolean isBST(Node n, Integer lo, Integer hi) {
	if (n == null) return true;
	if (lo != null && n.key <= lo) return false;
	if (hi != null && n.key >= hi) return false;
	return isBST(n.lchild, lo, n.key) && isBST(n.rchild, n.key, hi);
    }
    private int inorderCollect(Node n, int[] out, int i) {
	if (n == null) return i;
	i = inorderCollect(n.lchild, out, i);
	out[i] = n.key;
	i += 1;
	i = inorderCollect(n.rchild, out, i);
	return i;
    }
    private boolean hasParentConsistency(Node n, Node p) {
	if (n == null) return true;
	if (n.parent != p) return false;
	return hasParentConsistency(n.lchild, n) && hasParentConsistency(n.rchild, n);
    }
    public void reroot() {
	// HX-2025-11-20: 30 points
	// [reroot] picks a node RANDOMLY and
	// uses rotations to turn this picked node
	// into the root of a new binary search tree
	// (containing the same set of keys)
	if (root == null) return;
	int n = root.size;
	if (n <= 1) return;
	int k = (int)(Math.random() * n);
	Node x = kth(root, k);
	while (x != null && x.parent != null) {
	    if (x.parent.lchild == x) {
		rotateRight(x.parent);
	    } else {
		rotateLeft(x.parent);
	    }
	}
	if (root != null) pullUp(root);
    }
    public boolean insert(int key) {
	// HX-2025-11-20: 20 points
	// If key is in the tree stored at [root],
	// [insert] does no nothing and just returns false
	// If key is not in the tree stored at [root],
	// the key is inserted as a leaf node and the new
	// tree is still a binary search tree and [insert]
	// returns true (to indicate insertion is done).
	if (root == null) {
	    root = mkNode(key);
	    return true;
	}
	Node curr = root;
	Node prev = null;
	while (curr != null) {
	    prev = curr;
	    if (key == curr.key) return false;
	    curr = (key < curr.key ? curr.lchild : curr.rchild);
	}
	Node n = mkNode(key);
	n.parent = prev;
	if (key < prev.key) prev.lchild = n;
	else prev.rchild = n;
	pullUp(prev);
	return true;
    }
    public static void main (String[] args) {
	// Please add minimal testing code for reroot()
	// Please add minimal testing code for insert()
	Quiz02_06 t = new Quiz02_06();
	System.out.println("insert 5: " + t.insert(5));
	System.out.println("insert 3: " + t.insert(3));
	System.out.println("insert 7: " + t.insert(7));
	System.out.println("insert 2: " + t.insert(2));
	System.out.println("insert 4: " + t.insert(4));
	System.out.println("insert 6: " + t.insert(6));
	System.out.println("insert 8: " + t.insert(8));
	System.out.println("duplicate 4: " + t.insert(4));

	Quiz02_06 one = new Quiz02_06();
	one.insert(10);
	one.reroot();
	System.out.println("single reroot root=10: " + (one.root != null && one.root.key == 10));

	int n0 = t.root.size;
	int[] before = new int[n0];
	t.inorderCollect(t.root, before, 0);
	t.reroot();
	int[] after = new int[n0];
	t.inorderCollect(t.root, after, 0);
	boolean same = true;
	for (int i = 0; i < n0; i += 1) if (before[i] != after[i]) same = false;
	System.out.println("reroot keeps inorder keys: " + same);
	System.out.println("reroot keeps BST: " + t.isBST(t.root, null, null));
	System.out.println("parent links consistent: " + t.hasParentConsistency(t.root, null));
	return /*void*/;
    }
}
