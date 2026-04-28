package MyLibrary.FnA1sz;

import MyLibrary.FnList.*;

public class FnA1sz<T> {
    private T[] root;

    public FnA1sz(T[] xs) {
        root = xs;
    }

    public FnA1sz(FnList<T> xs) {
        int i = 0;
        int n = xs.length();
        root = (T[]) (new Object[n]);
        while (!xs.nilq()) {
            root[i] = xs.hd();
            i += 1;
            xs = xs.tl();
        }
    }

    public T getAt(int i) {
        return root[i];
    }

    public void setAt(int i, T x) {
        root[i] = x;
    }

    public int length() {
        return root.length;
    }
}
