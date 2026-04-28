package MyLibrary.MyMap;

import MyLibrary.FnTuple.*;
import MyLibrary.LnList.*;
import java.util.function.BiConsumer;

public class MyMapHashChain<K, V> implements MyMap<K, V> {
    private LnList<FnTupl2<K, V>>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public MyMapHashChain() {
        table = (LnList<FnTupl2<K, V>>[]) new LnList[97];
        for (int i = 0; i < table.length; i += 1) {
            table[i] = new LnList<FnTupl2<K, V>>();
        }
        size = 0;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), table.length);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return false;
    }

    public V getOrNull(K key) {
        LnList<FnTupl2<K, V>> xs = table[hash(key)];
        while (xs.consq1()) {
            FnTupl2<K, V> kv = xs.hd1();
            if (kv.sub0.equals(key)) return kv.sub1;
            xs = xs.tl1();
        }
        return null;
    }

    public V getOrExn(K key) throws MyMapNoKeyExn {
        V v = getOrNull(key);
        if (v == null) throw new MyMapNoKeyExn();
        return v;
    }

    public V put(K key, V val) {
        int i = hash(key);
        LnList<FnTupl2<K, V>> xs = table[i];
        LnList<FnTupl2<K, V>> ys = new LnList<FnTupl2<K, V>>();
        while (xs.consq1()) {
            FnTupl2<K, V> kv = xs.hd1();
            if (kv.sub0.equals(key)) {
                V old = kv.sub1;
                ys = new LnList<FnTupl2<K, V>>(new FnTupl2<K, V>(key, val), ys);
                xs = xs.tl1();
                ys.reverse1();
                ys.append1(xs);
                table[i] = ys;
                return old;
            }
            ys = new LnList<FnTupl2<K, V>>(kv, ys);
            xs = xs.tl1();
        }
        table[i] = new LnList<FnTupl2<K, V>>(new FnTupl2<K, V>(key, val), table[i]);
        size += 1;
        return null;
    }

    public void putNew(K key, V val) {
        table[hash(key)] = new LnList<FnTupl2<K, V>>(new FnTupl2<K, V>(key, val), table[hash(key)]);
        size += 1;
    }

    public V remove(K key) {
        int i = hash(key);
        LnList<FnTupl2<K, V>> xs = table[i];
        LnList<FnTupl2<K, V>> ys = new LnList<FnTupl2<K, V>>();
        while (xs.consq1()) {
            FnTupl2<K, V> kv = xs.hd1();
            if (kv.sub0.equals(key)) {
                V old = kv.sub1;
                xs = xs.tl1();
                ys.reverse1();
                ys.append1(xs);
                table[i] = ys;
                size -= 1;
                return old;
            }
            ys = new LnList<FnTupl2<K, V>>(kv, ys);
            xs = xs.tl1();
        }
        return null;
    }

    public V removeOrExn(K key) throws MyMapNoKeyExn {
        V v = remove(key);
        if (v == null) throw new MyMapNoKeyExn();
        return v;
    }

    public void forEach(BiConsumer<? super K, ? super V> work) {
        for (int i = 0; i < table.length; i += 1) {
            LnList<FnTupl2<K, V>> xs = table[i];
            while (xs.consq1()) {
                FnTupl2<K, V> kv = xs.hd1();
                work.accept(kv.sub0, kv.sub1);
                xs = xs.tl1();
            }
        }
    }
}
