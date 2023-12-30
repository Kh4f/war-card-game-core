package io.github.kh4f;

import java.util.Arrays;
import java.util.Iterator;

public class Deck implements Iterable<Card> {

    public static class DeckException extends Exception {
        public DeckException(String message) {
            super(message);
        }
    }

    private Node head;
    private Node tail;
    private int size;

    private static class Node {
        private Card value;
        private Node prev;
        private Node next;

        public Node(Card value) {
            this(value, null, null);
        }
        public Node(Card value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Card getValue() {
            return value;
        }
        public void setValue(Card value) {
            this.value = value;
        }
        public Node getPrev() {
            return prev;
        }
        public Node getNext() {
            return next;
        }
    }

    public Deck() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Deck(Deck otherDeck) {
        for (Card card : otherDeck) {
            putCardOnBottom(card);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void checkEmpty() throws DeckException {
        if (isEmpty()) throw new DeckException("Deck is empty");
    }

    public Card checkTopCard() throws DeckException {
        checkEmpty();
        return head.value;
    }

    public void putCardOnTop(Card card) {
        Node newNode = new Node(card);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void putCardOnBottom(Card card) {
        if (isEmpty()) {
            putCardOnTop(card);
            return;
        }
        tail.next = new Node(card, tail, null);
        tail = tail.next;
        size++;
    }

    public void putAllFromOtherDeckToBottom(Deck otherDeck3) {
        for (Card card : otherDeck3) {
            putCardOnBottom(card);
        }
    }

    public Card takeTopCard() throws DeckException {
        checkEmpty();
        Card value = head.value;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return value;
    }

    public void clearDeck() throws DeckException {
        while (!isEmpty()) {
            takeTopCard();
        }
    }

    public Card takeCard(int index) throws DeckException {
        checkEmpty();

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be grater or equal 0");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index must be below %d", size));
        }
        int counter = 0;
        Node current = head;
        while (counter++ < index) {
            current = current.next;
        }

        return current.value;
    }

    public String toString() {
        return Arrays.toString(toStringArray());
    }

    public String[] toStringArray() {
        String[] newAr = new String[size()];

        Node currNode = head;
        int index = 0;
        while (currNode != null) {
            newAr[index] = String.valueOf(currNode.getValue());
            currNode = currNode.getNext();
            index += 1;
        }

        return newAr;
    }

    public Iterator<Card> iterator() {
        class DeckListIterator implements Iterator<Card> {
            Node curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Card next() {
                Card card = curr.value;
                curr = curr.next;
                return card;
            }
        }

        return new DeckListIterator();
    }
}
