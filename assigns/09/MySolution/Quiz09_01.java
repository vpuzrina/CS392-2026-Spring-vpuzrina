//
// HX-2026-04-09: 50 points
// Doubly linked binary search tree (insert / remove)
//
public class Quiz09_01 {
    Node root = null;

    public class Node {
        int key;
        int size;
        Node parent;
        Node lchild;
        Node rchild;

        Node(int key) {
            this.key = key;
            this.size = 1;
        }
    }

    public boolean insert(int key) {
        if (root == null) {
            root = new Node(key);
            return true;
        }
        Node curr = root;
        Node parent = null;
        while (curr != null) {
            parent = curr;
            if (key == curr.key) return false;
            curr = (key < curr.key) ? curr.lchild : curr.rchild;
        }
        Node newNode = new Node(key);
        newNode.parent = parent;
        if (key < parent.key) parent.lchild = newNode;
        else parent.rchild = newNode;
        for (Node p = newNode; p != null; p = p.parent) {
            p.size = 1
                + (p.lchild != null ? p.lchild.size : 0)
                + (p.rchild != null ? p.rchild.size : 0);
        }
        return true;
    }

    public boolean remove(int key) {
        Node target = find(key);
        if (target == null) return false;
        delete(target);
        return true;
    }

    private Node find(int key) {
        Node curr = root;
        while (curr != null && curr.key != key) {
            curr = (key < curr.key) ? curr.lchild : curr.rchild;
        }
        return curr;
    }

    private void refreshSize(Node n) {
        for (Node p = n; p != null; p = p.parent) {
            p.size = 1
                + (p.lchild != null ? p.lchild.size : 0)
                + (p.rchild != null ? p.rchild.size : 0);
        }
    }

    private void delete(Node n) {
        if (n.lchild == null || n.rchild == null) {
            Node child = (n.lchild != null) ? n.lchild : n.rchild;
            if (n.parent == null) {
                root = child;
            } else if (n == n.parent.lchild) {
                n.parent.lchild = child;
            } else {
                n.parent.rchild = child;
            }
            if (child != null) child.parent = n.parent;
            refreshSize(n.parent);
        } else {
            Node succ = n.rchild;
            while (succ.lchild != null) succ = succ.lchild;
            n.key = succ.key;
            delete(succ);
        }
    }

    private static int nodeCount(Node n) {
        if (n == null) return 0;
        return 1 + nodeCount(n.lchild) + nodeCount(n.rchild);
    }

    private static void inorderCollect(Node n, int[] buf, int[] ix) {
        if (n == null) return;
        inorderCollect(n.lchild, buf, ix);
        buf[ix[0]++] = n.key;
        inorderCollect(n.rchild, buf, ix);
    }

    private static void assertSorted(Quiz09_01 t, int[] expectedKeysInOrder) {
        int n = nodeCount(t.root);
        if (n != expectedKeysInOrder.length) throw new AssertionError("size mismatch");
        int[] buf = new int[n];
        int[] ix = new int[] {0};
        inorderCollect(t.root, buf, ix);
        for (int i = 0; i < n; i++) {
            if (buf[i] != expectedKeysInOrder[i]) throw new AssertionError("order mismatch at " + i);
        }
    }

    private static void assertSizes(Quiz09_01 t) {
        checkSizes(t.root);
    }

    private static int checkSizes(Node n) {
        if (n == null) return 0;
        int ls = checkSizes(n.lchild);
        int rs = checkSizes(n.rchild);
        int expect = 1 + ls + rs;
        if (n.size != expect) throw new AssertionError("bad size at key " + n.key);
        return expect;
    }

    public static void main(String[] args) {
        Quiz09_01 t = new Quiz09_01();
        System.out.println(t.insert(5));
        System.out.println(t.insert(3));
        System.out.println(t.insert(7));
        System.out.println(t.insert(5));
        System.out.println(t.insert(5));
        System.out.println(t.insert(3));

        Quiz09_01 t2 = new Quiz09_01();
        int[] vals = {5, 3, 7, 2, 4, 6, 8};
        for (int v : vals) t2.insert(v);
        assertSorted(t2, new int[] {2, 3, 4, 5, 6, 7, 8});
        assertSizes(t2);

        t2.remove(3);
        assertSorted(t2, new int[] {2, 4, 5, 6, 7, 8});
        assertSizes(t2);

        t2.remove(7);
        assertSorted(t2, new int[] {2, 4, 5, 6, 8});
        assertSizes(t2);

        t2.remove(5);
        assertSorted(t2, new int[] {2, 4, 6, 8});
        assertSizes(t2);

        Quiz09_01 t3 = new Quiz09_01();
        for (int i = 1; i <= 10; i++) t3.insert(i);
        for (int i = 1; i <= 10; i++) {
            if (!t3.remove(i)) throw new AssertionError("remove " + i);
            assertSizes(t3);
        }
        if (t3.root != null) throw new AssertionError("expected empty");

        Quiz09_01 t4 = new Quiz09_01();
        t4.insert(42);
        t4.remove(42);
        if (t4.root != null) throw new AssertionError("single remove");

        System.out.println("Quiz09_01 checks ok");
    }
}
