
public class MyQueue<T> extends MyLinkedList<T> implements SimpleQueue<T> {

    public MyQueue(MyQueue<T> newQueue) {
        addAll(newQueue);
    }

    public MyQueue() {}

    @Override
    public void offer(T value) {
        this.addLast(value);
    }

    @Override
    public T poll() {
        T value = this.get(0);
        this.removeFirst();
        return value;
    }

    @Override
    public T peek() {
        return this.get(0);
    }

    @Override
    public void addAll(MyQueue<T> newQueue) {
        for (T t : newQueue) {
            addLast(t);
        }
    }
}
