import Library00.FnList.*;
import Library00.FnTuple.*;

public class Final_04Map<K extends Comparable<K>, V> {

    private Node root = null;

    private class Node {
        K key;
        V val;
        int size;
        Node parent;
        Node lchild;
        Node rchild;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.size = 1;
            this.parent = null;
            this.lchild = null;
            this.rchild = null;
        }
    }

    public int size() {
        return (root == null ? 0 : root.size);
    }

    public boolean isEmpty() {
        return root == null;
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

    private Node find(K key) {
        Node curr = root;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp == 0) return curr;
            curr = (cmp < 0 ? curr.lchild : curr.rchild);
        }
        return null;
    }

    public V getOrNull(K key) {
        Node n = find(key);
        return (n == null ? null : n.val);
    }

    public V put(K key, V val) {
        if (root == null) {
            root = new Node(key, val);
            return null;
        }
        Node curr = root;
        Node parent = null;
        int cmp = 0;
        while (curr != null) {
            parent = curr;
            cmp = key.compareTo(curr.key);
            if (cmp == 0) {
                V old = curr.val;
                curr.val = val;
                return old;
            }
            curr = (cmp < 0 ? curr.lchild : curr.rchild);
        }
        Node n = new Node(key, val);
        n.parent = parent;
        if (cmp < 0) parent.lchild = n;
        else parent.rchild = n;
        pullUp(n);
        return null;
    }

    public void reroot() {
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

    public FnList<FnTupl2<K, V>> keyval$listize() {
        int n = size();
        if (n == 0) return FnListSUtil.<FnTupl2<K, V>>nil();
        Object[] stack = new Object[n];
        int sp = 0;
        Node node = root;
        FnList<FnTupl2<K, V>> acc = FnListSUtil.<FnTupl2<K, V>>nil();
        while (node != null || sp > 0) {
            while (node != null) {
                stack[sp] = node;
                sp += 1;
                node = node.rchild;
            }
            sp -= 1;
            @SuppressWarnings("unchecked")
            Node top = (Node) stack[sp];
            acc = FnListSUtil.cons(new FnTupl2<K, V>(top.key, top.val), acc);
            node = top.lchild;
        }
        return acc;
    }
}
