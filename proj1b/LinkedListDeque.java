public class LinkedListDeque<T> implements Deque<T>{
    private Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node<T>();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.value);
            //if(p.next != sentinel)
            System.out.print(" ");
            p = p.next;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T temp = (T) sentinel.next.value;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return temp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T temp = (T) sentinel.prev.value;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return temp;
    }

    @Override
    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        Node p = sentinel.next;
        while (p != sentinel) {
            if (index == 0) {
                return (T) p.value;
            }
            index--;
            p = p.next;
        }
        return null;
    }

    public T getRecursive(int index) {
        Node p = sentinel.next;
        return recur(p, index);
    }

    private T recur(Node node, int index) {
        if (node == sentinel) {
            return null;
        }
        if (index == 0) {
            return (T) node.value;
        }
        return recur(node.next, index - 1);
    }
    private static class Node<T> {
        T value;
        Node prev;
        Node next;

        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Node() {
        }
    }

    /*public static void main(String[] args) {
        LinkedListDeque<Object> linkedListDeque = new LinkedListDeque<>();
        linkedListDeque.addLast(1);
        linkedListDeque.addLast(2);
        linkedListDeque.addLast(3);
        linkedListDeque.addFirst(4);
        linkedListDeque.addFirst(5);
        linkedListDeque.addFirst(6);
        *//*linkedListDeque.removeLast();
        linkedListDeque.removeFirst();*//*

        linkedListDeque.printDeque();
        System.out.println(linkedListDeque.get(6));
        System.out.println(linkedListDeque.getRecursive(0));

    }*/
}
