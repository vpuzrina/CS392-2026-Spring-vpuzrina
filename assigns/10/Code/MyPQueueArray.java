package Library.MyPQueue;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.Arrays;

public class MyPQueueArray<T> extends MyPQueueBase<T> {
    private static final int DEFAULT_CAPACITY= 10;
    private Object[] heap;
    private int size;
    private java.util.Comparator<? super T> comparator;
    
    public MyPQueueArray() {
        this(DEFAULT_CAPACITY, null);
    }
    public MyPQueueArray(java.util.Comparator<? super T> comparator ) {
        this(DEFAULT_CAPACITY, comparator);
    }
    public MyPQueueArray(int initialCapacity, java.util.Comparator<? super T> comparator){
        if (initialCapacity<= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        this.heap= new Object[initialCapacity];
        this.size = 0;
        this.comparator = comparator;

    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isFull() {
        return false;

    }
    @Override 
    public void enque$raw(T value) {
        if (value == null) {
            throw new NullPointerException("Cannot enqueue null value");

        }
        if (size >= heap.length) {

            resize();

        }
        heap[size] = value;
        size++;
        bubbleUp(size- 1);
    }
    @Override
    public T deque$raw() {
        if (isEmpty()) {
            throw new MyPQueueEmptyExn();
        }
        T root = (T) heap[0];
        size--;
        if (size > 0) {
            heap[0] = heap[size];
            heap[size] = null;
            bubbleDown(0);
        
        }else {
            heap[0] = null;
        }
        return root;
    }
    @Override
    public T top$raw() {
        if (isEmpty()) {
            throw new MyPQueueEmptyExn();
        }
        return (T) heap[0];
    }
    private void resize() {
        int newCapacity = heap.length * 2;
        heap = Arrays.copyOf(heap, newCapacity);
    }
    private void bubbleUp(int index) {
        while (index>0) {
            int parentIndex = (index - 1)/2;
            if (compare((T) heap[index], (T) heap[parentIndex]) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    private void bubbleDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;
            if (leftChild < size && compare((T) heap[leftChild], (T) heap[smallest] ) <0) {
                smallest= leftChild;
            }
            if (rightChild < size && compare((T) heap[rightChild], (T) heap[smallest]) <0) {
                smallest = rightChild;
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }

    }
    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<? super T>) a).compareTo(b);

        }
    }
    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    } 
    public boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < size && compare((T) heap[i], (T) heap[left]) > 0) {
                return false;
            }
            if (right < size && compare((T) heap[i], (T) heap[right]) >0) {
                return false;
            }
        }
        return true;
    }
    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i>0) sb.append(", ");
            sb.append(heap[i]);
        }
        sb.append("]");
        return sb.toString();
    }
    

}
