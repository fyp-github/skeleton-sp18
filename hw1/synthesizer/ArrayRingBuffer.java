package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = last = 0;
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[nextLast()] = x;
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        int oldFirst = nextFirst();
        fillCount--;
        return rb[oldFirst];
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];
    }

    private int nextLast() {
        int oldLast = last;
        last = (last + 1) % capacity;
        return oldLast;
    }
    private int nextFirst() {
        int oldFirst = first;
        first = (first + 1) % capacity;
        return oldFirst;
    }

    public Iterator<T> iterator() {
        return new MyIteration();
    }

    private class MyIteration implements Iterator<T> {
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < fillCount;
        }

        @Override
        public T next() {
            T returnValue =  rb[(first + count) % capacity];
            count++;
            return returnValue;
        }
    }
}
