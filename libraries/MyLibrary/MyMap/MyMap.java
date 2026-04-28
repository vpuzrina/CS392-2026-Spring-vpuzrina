package MyLibrary.MyMap;

import java.util.function.BiConsumer;

public interface MyMap<K, V> {
    int size();
    boolean isEmpty();
    boolean isFull();

    V getOrNull(K key);
    V getOrExn(K key) throws MyMapNoKeyExn;

    V put(K key, V val);
    void putNew(K key, V val);

    V remove(K key);
    V removeOrExn(K key) throws MyMapNoKeyExn;

    void forEach(BiConsumer<? super K, ? super V> work);
}
