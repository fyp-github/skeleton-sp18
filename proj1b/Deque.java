public interface Deque<T> {
    void addFirst(T value);
    void addLast(T value);
    boolean isEmpty();
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
}
