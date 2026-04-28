package MyLibrary.MyMap;

import java.util.function.BiConsumer;

public class MyMapHashProbe<K, V> implements MyMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V val;
        boolean deleted;
        Entry(K key, V val, boolean deleted) {
            this.key = key;
            this.val = val;
            this.deleted = deleted;
        }
    }

    private Entry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public MyMapHashProbe() {
        table = (Entry<K, V>[]) new Entry[97];
        size = 0;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), table.length);
    }

    private int find(K key) {
        int h = hash(key);
        for (int i = 0; i < table.length; i += 1) {
            int j = (h + i * i) % table.length;
            Entry<K, V> e = table[j];
            if (e == null) return -1;
            if (!e.deleted && e.key.equals(key)) return j;
        }
        return -1;
    }

    private int probe(K key) {
        int h = hash(key);
        int del = -1;
        for (int i = 0; i < table.length; i += 1) {
            int j = (h + i * i) % table.length;
            Entry<K, V> e = table[j];
            if (e == null) return (del >= 0 ? del : j);
            if (e.deleted) {
                if (del < 0) del = j;
            } else if (e.key.equals(key)) {
                return j;
            }
        }
        return del;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == table.length;
    }

    public V getOrNull(K key) {
        int i = find(key);
        return (i >= 0 ? table[i].val : null);
    }

    public V getOrExn(K key) throws MyMapNoKeyExn {
        V v = getOrNull(key);
        if (v == null) throw new MyMapNoKeyExn();
        return v;
    }

    public V put(K key, V val) {
        int i = probe(key);
        if (i < 0) throw new MyMapFullExn();
        Entry<K, V> e = table[i];
        if (e != null && !e.deleted && e.key.equals(key)) {
            V old = e.val;
            table[i] = new Entry<K, V>(key, val, false);
            return old;
        }
        table[i] = new Entry<K, V>(key, val, false);
        size += 1;
        return null;
    }

    public void putNew(K key, V val) {
        int i = probe(key);
        if (i < 0) throw new MyMapFullExn();
        table[i] = new Entry<K, V>(key, val, false);
        size += 1;
    }

    public V remove(K key) {
        int i = find(key);
        if (i < 0) return null;
        V old = table[i].val;
        table[i] = new Entry<K, V>(null, null, true);
        size -= 1;
        return old;
    }

    public V removeOrExn(K key) throws MyMapNoKeyExn {
        V v = remove(key);
        if (v == null) throw new MyMapNoKeyExn();
        return v;
    }

    public void forEach(BiConsumer<? super K, ? super V> work) {
        for (int i = 0; i < table.length; i += 1) {
            Entry<K, V> e = table[i];
            if (e != null && !e.deleted) {
                work.accept(e.key, e.val);
            }
        }
    }
}
