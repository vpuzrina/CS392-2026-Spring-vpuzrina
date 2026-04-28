import Library00.FnTuple.*;
import Library00.LnStrm.*;
import Library00.MyMap00.*;

public class Assign08_02<V>
    implements MyMap00<String, V> {

    private FnTupl2<String, V> table[];
    private int size;
    private static final String DEL = new String("$DEL$");
    @SuppressWarnings("unchecked")
    public Assign08_02() {
        table = (FnTupl2<String, V>[]) new FnTupl2[97];
        size = 0;
    }
    private int hash(String key) {
        return Math.floorMod(key.hashCode(), table.length);
    }
    private int find(String key) {
        int h = hash(key);
        for (int i = 0; i <table.length; i+=1) {
            int j = (h+ i*i) % table.length;
            FnTupl2<String, V> kv= table[j];
            if(kv == null) return -1;
            if (kv.sub0 != DEL && kv.sub0.equals(key)) return j;
        }
        return -1;
    }
    private int probe(String key) {
        int h=hash(key);
        int del= -1;
        for (int i=0; i < table.length; i +=1) {
            int j = (h+i*i) % table.length;
            FnTupl2<String, V> kv= table[j];
            if (kv== null) return (del>= 0 ? del : j);
            if (kv.sub0==DEL) {
                if (del < 0) del= j;
            } else if(kv.sub0.equals(key)) {
                return j;
            }
        }
        return del;

    }
    private LnStrm<FnTupl2<String, V>> keyvalFromIndex(int i) {
        if (i >= table.length) return new LnStrm<FnTupl2<String, V>>();
        FnTupl2<String, V> kv = table[i];
        LnStrm<FnTupl2<String, V>> rest = keyvalFromIndex(i + 1);
        if (kv == null || kv.sub0 == DEL) return rest;
        return new LnStrm<FnTupl2<String, V>>(
            () -> new LnStcn<FnTupl2<String, V>>(kv, rest)
        );
    }
    public int size() {
        return size;
    }
    public boolean isFull() {
        return size == table.length;
    
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public LnStrm<FnTupl2<String, V>> keyval_strmize(){
        return keyvalFromIndex(0);
    }
    public V search$old(String key) {
        return search$opt(key);
    }
    public V search$exn(String key) {
        V v = search$opt(key);
        if (v == null) throw new MyMap00NoKeyExn();
        return v;
    }
    public V search$opt(String key) {
        int i =find(key);
        return (i>=0 ? table[i].sub1 : null);
    }
    public V insert$opt(String key, V val) {
        int i= probe(key);
        if (i < 0) throw new MyMap00FullExn();
        FnTupl2<String, V> kv = table[i];
        if (kv !=null && kv.sub0 != DEL && kv.sub0.equals(key)) {
            V old = kv.sub1;
            table[i] = new FnTupl2<String, V>(key, val);
            return old;
        }
        table[i] = new FnTupl2<String, V>(key,val);
        size += 1;
        return null;
    }
    public void insert$new(String key, V val) {
        int i = probe(key);
        if (i < 0) throw new MyMap00FullExn();
        table[i] = new FnTupl2<String, V>(key, val);
        size +=1;
    }
    public V remove$old(String key) {
        return remove$opt(key);
    }
    public V remove$exn(String key) {
        V v = remove$opt(key);
        if (v == null) throw new MyMap00NoKeyExn();
        return v;
    }
    public V remove$opt(String key) {
        int i = find(key);
        if(i < 0) return null;
        V old = table[i].sub1;
        table[i] = new FnTupl2<String, V> (DEL, null);
        size -= 1;
        return old;
    }
    public void foritm(java.util.function.BiConsumer<? super String,? super V> work) {
        for (int i = 0; i < table.length; i += 1) {
            FnTupl2<String, V> kv = table[i];
            if (kv !=null && kv.sub0 != DEL) {
                work.accept(kv.sub0, kv.sub1);
            }
        }
    }
}
