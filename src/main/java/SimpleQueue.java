
public interface SimpleQueue<T> {
    void offer(T value);

    T poll();

    T peek();

    void addAll(MyQueue<T> newQueue);
}