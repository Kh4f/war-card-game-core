package io.github.kh4f;

import java.util.*;

public class Game {

    private final Deck deck1;
    private final Deck deck2;
    private final Deck shownCards1;
    private final Deck shownCards2;

    private int currentMove;
    private boolean isCardDispute;
    private boolean isGameDraw;
    private int moveWinner;
    private int gameWinner;

    public Game() {
        deck1 = new Deck();
        deck2 = new Deck();
        shownCards1 = new Deck();
        shownCards2 = new Deck();

        currentMove = 0;
        isCardDispute = false;
        isGameDraw = false;
        moveWinner = 0;
        gameWinner = 0;

        dealCards();
    }

    public Deck getDeck1() {
        return new Deck(deck1);
    }
    public Deck getDeck2() {
        return new Deck(deck2);
    }
    public Deck getShownCards1() {
        return new Deck(shownCards1);
    }
    public Deck getShownCards2() {
        return new Deck(shownCards2);
    }
    public boolean isCardDispute() {
        return isCardDispute;
    }
    public boolean isGameDraw() {
        return isGameDraw;
    }
    public int getMoveWinner() {
        return moveWinner;
    }
    public int getGameWinner() {
        return gameWinner;
    }
    public int getCurrentMove() {
        return currentMove;
    }

    private Deck getCommonDeck() {
        Deck deck = new Deck();
        int[] suits = {1, 2, 3, 4};
        for (int suit : suits) {
            for (int i = 6; i < 15; i++) {
                deck.addLast(new Card(i, suit));
            }
        }
        return deck;
    }

    private void dealCards(){
        Random random = new Random();
        Deck commonDeck = getCommonDeck();

        int playerDeckSize = 5;
        for (int i = 0; i < playerDeckSize; i++) {
            try {
                deck1.addLast(commonDeck.poll(random.nextInt(commonDeck.size())));
                deck2.addLast(commonDeck.poll(random.nextInt(commonDeck.size())));
            } catch (DoublyLinkedList.DoublyLinkedListException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void makeMove() throws DoublyLinkedList.DoublyLinkedListException {
        currentMove++;

        if (moveWinner == 1) {
            deck1.addAll(shownCards2);
            deck1.addAll(shownCards1);
            shownCards1.clear();
            shownCards2.clear();

        } else if (moveWinner == 2) {
            deck2.addAll(shownCards1);
            deck2.addAll(shownCards2);
            shownCards1.clear();
            shownCards2.clear();
        }

        shownCards1.addFirst(deck1.poll());
        shownCards2.addFirst(deck2.poll());

        int resultOfCompareCards = compareCards(shownCards1.getFirst(), shownCards2.getFirst());
        if (resultOfCompareCards == 0) {
            isCardDispute = true;
            moveWinner = 0;
            if (deck1.isEmpty() && deck2.isEmpty()) {
                isGameDraw = true;
            } else if (deck1.isEmpty()) {
                moveWinner = 1;
            } else if (deck2.isEmpty()) {
                moveWinner = 2;
            }
        } else if (resultOfCompareCards == 1) {
            isCardDispute = false;
            moveWinner = 1;
            if (deck2.isEmpty()) {
                gameWinner = 1;
            }
        } else if (resultOfCompareCards == 2) {
            isCardDispute = false;
            moveWinner = 2;
            if (deck1.isEmpty()) {
                gameWinner = 2;
            }
        }
    }

    private int compareCards(Card card1, Card card2) {
        if (card1.value() == 6 && card2.value() == 14) {
            return 1;
        } else if (card1.value() == 14 && card2.value() == 6) {
            return 2;
        } else {
            if (card1.value() > card2.value()) {
                return 1;
            } else if (card1.value() < card2.value()) {
                return 2;
            } else {
                return 0;
            }
        }
    }
}