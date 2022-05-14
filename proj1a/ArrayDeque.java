public class ArrayDeque<T> {
    private int first;
    private int last;
    private T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = 0;
        last = 0;
    }

    public void addLast(T item) {
        if ((last + 1) % items.length == first) {
            reCapacity();
        }
        items[last] = item;
        last = ++last % items.length;
    }

    public void addFirst(T item) {
        if ((last + 1) % items.length == first) {
            reCapacity();
        }
        first = (first - 1 + items.length) % items.length;
        items[first] = item;
    }

    public int size() {
        return (last - first + items.length) % items.length;
    }

    private void reCapacity() {
        T[] newItems = (T[]) new Object[items.length * 2];
        for (int i = 0; i < size(); i++) {
            newItems[i] = items[(first + i) % items.length];
        }
        int size = size();
        first = 0;
        last = size;
        items = newItems;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T temp = items[first];
        items[first] = null;
        first = ++first % items.length;
        if (size() > 16 && size() < items.length * 0.25) {
            minusCapacity();
        }
        return temp;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        last = (last - 1 + items.length) % items.length;
        T temp = items[last];
        items[last] = null;
        if (size() > 16 && size() < items.length * 0.25) {
            minusCapacity();
        }
        return temp;
    }

    public boolean isEmpty() {
        return first == last;
    }

    private void minusCapacity() {
        int newCapacity = (int) (items.length * 0.25);
        T[] newItems = (T[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            newItems[i] = items[(first + i) % items.length];
        }
        int size = size();
        first = 0;
        last = size;
        items = newItems;
    }

    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        return items[(first + index) % items.length];
    }

    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(items[(first + i) % items.length]);
            System.out.print(" ");
        }
    }

    /*public static void main(String[] args) {
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<>();
        System.out.println(ArrayDeque.size());
        ArrayDeque.addFirst(0);
        System.out.println(ArrayDeque.size());
        ArrayDeque.addLast(1);
        System.out.println(ArrayDeque.size());
        ArrayDeque.addFirst(2);
        System.out.println(ArrayDeque.size());
        ArrayDeque.addFirst(3);
        System.out.println(ArrayDeque.size());
        ArrayDeque.addFirst(4);
        System.out.println(ArrayDeque.size());
        ArrayDeque.addFirst(5);
        System.out.println(ArrayDeque.size());
        ArrayDeque.addFirst(7);
        ArrayDeque.addLast(8);
        System.out.println(ArrayDeque.size());
    }*/

}
