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

    public Game() throws Deck.DeckException {
        this(new Deck(), new Deck());
        dealCards();
    }

    public Game(Deck filledDeck1, Deck filledDeck2) {
        deck1 = filledDeck1;
        deck2 = filledDeck2;
        shownCards1 = new Deck();
        shownCards2 = new Deck();

        currentMove = 0;
        isCardDispute = false;
        isGameDraw = false;
        moveWinner = 0;
        gameWinner = 0;
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
        Deck commonDeck = new Deck();
        int[] suits = {1, 2, 3, 4};
        for (int suit : suits) {
            for (int i = 6; i < 15; i++) {
                commonDeck.putCardOnBottom(new Card(i, suit));
            }
        }
        return commonDeck;
    }

    private void dealCards() throws Deck.DeckException {
        Random random = new Random();
        Deck commonDeck = getCommonDeck();

        int playerDeckSize = 5;
        for (int i = 0; i < playerDeckSize; i++) {
            deck1.putCardOnBottom(commonDeck.takeCard(random.nextInt(commonDeck.size())));
            deck2.putCardOnBottom(commonDeck.takeCard(random.nextInt(commonDeck.size())));
        }
    }

    public void makeMove() throws Deck.DeckException {
        currentMove++;

        if (moveWinner == 1) {
            deck1.putAllFromOtherDeckToBottom(shownCards2);
            deck1.putAllFromOtherDeckToBottom(shownCards1);
            shownCards1.clearDeck();
            shownCards2.clearDeck();

        } else if (moveWinner == 2) {
            deck2.putAllFromOtherDeckToBottom(shownCards1);
            deck2.putAllFromOtherDeckToBottom(shownCards2);
            shownCards1.clearDeck();
            shownCards2.clearDeck();
        }

        shownCards1.putCardOnTop(deck1.takeTopCard());
        shownCards2.putCardOnTop(deck2.takeTopCard());

        int resultOfCompareCards = compareCards(shownCards1.checkTopCard(), shownCards2.checkTopCard());;

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