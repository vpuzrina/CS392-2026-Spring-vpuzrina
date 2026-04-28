package MyLibrary.MyBST;

public class MyBST<K extends Comparable<K>, V> {
    private Node root;

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
        }
    }

    public int size() {
        return (root == null ? 0 : root.size);
    }

    public boolean isEmpty() {
        return root == null;
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
        if (cmp < 0) parent.lchild = n; else parent.rchild = n;
        refreshSize(n);
        return null;
    }

    public V remove(K key) {
        Node n = find(key);
        if (n == null) return null;
        V old = n.val;
        delete(n);
        return old;
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

    private void refreshSize(Node n) {
        for (Node p = n; p != null; p = p.parent) {
            p.size = 1
                + (p.lchild != null ? p.lchild.size : 0)
                + (p.rchild != null ? p.rchild.size : 0);
        }
    }

    private void delete(Node n) {
        if (n.lchild == null || n.rchild == null) {
            Node child = (n.lchild != null ? n.lchild : n.rchild);
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
            n.val = succ.val;
            delete(succ);
        }
    }
}
