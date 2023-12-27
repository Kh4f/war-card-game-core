package io.github.kh4f;

public class Deck extends DoublyLinkedList<Card> {
    public Deck(Deck newDeck) {
        super(newDeck);
    }

    public Deck() {
        super();
    }
}
