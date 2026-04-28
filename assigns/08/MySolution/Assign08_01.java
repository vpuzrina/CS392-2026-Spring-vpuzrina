import Library00.FnTuple.*;
import Library00.LnList.*;
import Library00.LnStrm.*;
import Library00.MyMap00.*;

public class Assign08_01<V>
    implements MyMap00<String, V> {

    private LnList<FnTupl2<String, V>> table[];
    private int size;
    @SuppressWarnings("unchecked")
    public Assign08_01() {
        table= (LnList<FnTupl2<String, V>>[]) new LnList[97];
        for(int i= 0; i < table.length; i+= 1) {
            table[i] = new LnList<FnTupl2<String, V>> ();
        }
        size =0;
    }
    private int hash(String key) {
        return Math.floorMod(key.hashCode(), table.length);
    }
    public int size() {
        return size;
    }
    public boolean isFull() {
        return false;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    private LnStrm<FnTupl2<String, V>> bucketToStrm(LnList<FnTupl2<String, V>> xs, LnStrm<FnTupl2<String, V>> tail) {
        if (xs.nilq1()) return tail;
        FnTupl2<String, V> kv = xs.hd1();
        return new LnStrm<FnTupl2<String, V>>(
            () -> new LnStcn<FnTupl2<String, V>>(kv, bucketToStrm(xs.tl1(), tail))
        );
    }
    private LnStrm<FnTupl2<String, V>> keyvalFromIndex(int i) {
        if (i >= table.length) return new LnStrm<FnTupl2<String, V>>();
        LnStrm<FnTupl2<String, V>> rest = keyvalFromIndex(i + 1);
        return bucketToStrm(table[i], rest);
    }
    public LnStrm<FnTupl2<String, V>> keyval_strmize() {
        return keyvalFromIndex(0);
    }
    public V search$old(String key) {
        return search$opt(key);
    }
    public V search$exn(String key) {
        V res = search$opt(key);
        if(res== null) throw new MyMap00NoKeyExn();
        return res;
    }
    public V search$opt(String key) {
        LnList<FnTupl2<String,V>> xs=table[hash(key)];
        while(xs.consq1()) {
            FnTupl2<String,V> kv= xs.hd1();
            if (kv.sub0.equals(key)) return kv.sub1;
            xs =xs.tl1();
        }
        return null;
    }
    public V insert$opt(String key, V val) {
        int i = hash(key);
        LnList<FnTupl2<String, V>> xs= table[i];
        LnList<FnTupl2<String, V>> ys = new LnList<FnTupl2<String, V>>();
        while (xs.consq1()) {
            FnTupl2<String,V> kv = xs.hd1();
            if (kv.sub0.equals(key)) {
                V old = kv.sub1;
                ys=new LnList<FnTupl2<String,V>>(new FnTupl2<String,V>(key, val), ys);
                xs=xs.tl1();
                ys.reverse1();
                ys.append1(xs);
                table[i]=ys;
                return old;
            }
            ys= new LnList<FnTupl2<String,V>>(kv,ys);
            xs= xs.tl1();
        }
        table[i] = new LnList<FnTupl2<String, V>>(new FnTupl2<String,V>(key, val), table[i]);
        size+=1;
        return null;
    }
    public void insert$new(String key, V val) {
        table[hash(key)] = new LnList<FnTupl2<String, V>>(new FnTupl2<String, V>(key, val), table[hash(key)]);
        size +=1;

    }
    public V remove$old(String key) {
        return remove$opt(key);

    }
    public V remove$exn(String key) {
        V res = remove$opt(key);
        if(res == null) throw new MyMap00NoKeyExn();
        return res;
    }
    public V remove$opt(String key) {
        int i=hash(key);
        LnList<FnTupl2<String,V>> xs=table[i];
        LnList<FnTupl2<String,V>> ys=new LnList<FnTupl2<String,V>>();
        while (xs.consq1()) {
            FnTupl2<String, V> kv = xs.hd1();
            if(kv.sub0.equals(key)) {
                V old = kv.sub1;
                xs = xs.tl1();
                ys.reverse1();
                ys.append1(xs);
                table[i] = ys;
                size -= 1;
                return old;
            }
            ys = new LnList<FnTupl2<String, V>>(kv, ys);
            xs = xs.tl1();
        }
        return null;
    }
    public void foritm(java.util.function.BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i< table.length; i+=1) {
            LnList<FnTupl2<String, V>> xs= table[i];
            while (xs.consq1()) {
                FnTupl2<String, V> kv = xs.hd1();
                work.accept(kv.sub0, kv.sub1);
                xs= xs.tl1();
            }
        }
    }

}
