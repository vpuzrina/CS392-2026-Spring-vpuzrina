//
// HX-2026-04-09: 50 points
// A partial implementation of
// randomized doubly linked binary search tree
// 20 points for insert and 30 points for remove
//
public class Quiz09_01 {
    Node root = null;
	public class Node {
		int key, size;
		Node parent, lchild, rchild;
		Node (int key) {this.key = key; this.size = 1;}
	
    }
    public boolean insert(int key) {
		if (root == null) {root= new Node(key); return true;}
		Node curr= root, parent = null;
		while (curr != null) {
			parent = curr;
			if(key == curr.key) return false;
			curr =(key < curr.key) ? curr.lchild : curr.rchild;
		}
		Node newNode = new Node(key);
		newNode.parent = parent;
		if (key< parent.key) parent.lchild = newNode;
		else parent.rchild = newNode;
		for (Node p = newNode; p != null; p = p.parent)
			p.size= 1+ (p.lchild != null ? p.lchild.size : 0)+ (p.rchild != null ? p.rchild.size : 0);
		return true;
    }
    public boolean remove(int key) {
		Node target= find(key);
		if (target == null) return false;
		delete(target);
		return true;
    }
	private Node find(int key) {
		Node curr = root;
		while (curr != null && curr.key != key)
			curr = (key < curr.key) ? curr.lchild : curr.rchild;
		return curr;
	}
	private void delete(Node n) {
		if (n.lchild ==null || n.rchild == null) {
			Node child = (n.lchild != null) ? n.lchild : n.rchild;
			if (n.parent == null) root = child;
			else if (n== n.parent.lchild) n.parent.lchild = child;
			else n.parent.rchild = child;
			if (child != null) child.parent = n.parent;
			for (Node p= n.parent; p != null; p = p.parent)
				p.size = 1 + (p.lchild != null ? p.lchild.size : 0) + (p.rchild != null ? p.rchild.size : 0);
		} else {
			Node succ= n.rchild;
			while (succ.lchild != null) succ = succ.lchild;
			n.key = succ.key;
			delete(succ);
		}
	}
    public static void main (String[] args) {
		Quiz09_01 t = new Quiz09_01();
		System.out.println(t.insert(5));
		System.out.println(t.insert(3));
		System.out.println(t.insert(7));
		System.out.println(t.insert(5));
		System.out.println(t.insert(5));
		System.out.println(t.insert(3));
    }
}
