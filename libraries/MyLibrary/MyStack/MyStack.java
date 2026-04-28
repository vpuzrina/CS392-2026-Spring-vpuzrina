package MyLibrary.MyStack;

import java.util.function.Consumer;
import java.util.function.BiConsumer;

interface MyStack<T> {
//
    int size();
//
    boolean isFull();
    boolean isEmpty();
//
    T top$raw();
    T top$opt();
    T top$exn() throws MyStackEmptyExn;
//
    T pop$raw();
    T pop$opt();
    T pop$exn() throws MyStackEmptyExn;
//
    void push$raw(T itm);
    void push$exn(T itm) throws MyStackFullExn;
    boolean push$opt(T itm);
//
    void System$out$print();
//
    void foritm(Consumer<? super T> work);
    void iforitm(BiConsumer<Integer, ? super T> work);
//
}
