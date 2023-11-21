import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T>{

    public static class MyLinkedListException extends Exception {
        public MyLinkedListException(String message) {
            super(message);
        }
    }

    private static class ListNode<T> {
        public T value;
        public ListNode<T> next;

        public ListNode(T value, ListNode<T> next) {
            this.value = value;
            this.next = next;
        }

        public ListNode(T value) {
            this(value, null);
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public ListNode<T> getNext() {
            return next;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }
    }

    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    public MyLinkedList() {}

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void addFirst(T value) {
        head = new ListNode<>(value, head);
        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(T value) {
        if (isEmpty()) {
            addFirst(value);
            return;
        }
        ListNode<T> newNode = new ListNode<>(value);
        tail.setNext(newNode);
        tail = newNode;
        size++;
    }

    public void add(int index, T value) {
        if (index == 0) {
            addFirst(value);
            return;
        } else if (index == size()) {
            addLast(value);
            return;
        }
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        ListNode<T> newNode = new ListNode<>(value);
        tail.setNext(newNode);
        tail = newNode;
        size++;
    }

    private ListNode<T> getNode(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be grater or equal 0");
        }
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException(String.format("Index must be below %d", size()));
        }
        int counter = 0;
        ListNode<T> current = head;
        while (current != null && counter < index) {
            counter++;
            current = current.getNext();
        }
        if (current == null) {
            throw new NullPointerException("List corrupted exception");
        }
        return current;
    }

    public void removeFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        head = head.getNext();
        size--;
    }

    public void removeLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        tail = getNode(size() - 2);
        tail.setNext(null);
        size--;
    }

    public void remove(int index){
        if (index == 0) {
            removeFirst();
            return;
        } else if (index == (size() - 1)) {
            removeLast();
            return;
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be grater or equal 0");
        }
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException(String.format("Index must be below %d", size()));
        }
        ListNode<T> prev = getNode(index - 1);
        ListNode<T> curr = prev.getNext();
        prev.next = curr.next;
    }

    public T get(int index) {
        return getNode(index).getValue();
    }

    public String toString() {
        ListNode<T> currNode = head;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (currNode != null) {
            sb.append(currNode.getValue());
            if (currNode.getNext() != null) {
                sb.append(", ");
            }
            currNode = currNode.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public String[] toStringArray() {
        String[] newAr = new String[size()];

        ListNode<T> currNode = head;
        int index = 0;
        while (currNode != null) {
            newAr[index] = String.valueOf(currNode.getValue());
            currNode = currNode.getNext();
            index += 1;
        }

        return newAr;
    }

    @Override
    public Iterator<T> iterator() {
        class MyLinkedListIterator implements Iterator<T> {
            ListNode<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T value = curr.value;
                curr = curr.next;
                return value;
            }
        }

        return new MyLinkedListIterator();
    }
}
