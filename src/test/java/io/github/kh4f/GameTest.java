package io.github.kh4f;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Game game;
    Deck deck1;
    Deck deck2;

    @BeforeEach
    public void setUp() {
        deck1 = new Deck();
        deck2 = new Deck();
    }

    @Test
    public void player1WinsMove() throws Deck.DeckException {
        deck1.putCardOnBottom(new Card(13, 1));
        deck1.putCardOnBottom(new Card(10, 2));

        deck2.putCardOnBottom(new Card(8, 4));
        deck2.putCardOnBottom(new Card(11, 1));

        game = new Game(deck1, deck2);
        game.makeMove();

        assertEquals(1, game.getCurrentMove());
        assertFalse(game.isCardDispute());
        assertFalse(game.isGameDraw());
        assertEquals(1, game.getMoveWinner());
        assertEquals(0, game.getGameWinner());
    }

    @Test
    public void player2WinsMove() throws Deck.DeckException {
        deck1.putCardOnBottom(new Card(6, 1));
        deck1.putCardOnBottom(new Card(10, 2));

        deck2.putCardOnBottom(new Card(8, 4));
        deck2.putCardOnBottom(new Card(11, 1));

        game = new Game(deck1, deck2);
        game.makeMove();

        assertEquals(1, game.getCurrentMove());
        assertFalse(game.isCardDispute());
        assertFalse(game.isGameDraw());
        assertEquals(2, game.getMoveWinner());
        assertEquals(0, game.getGameWinner());
    }

    @Test
    public void isCardDispute() throws Deck.DeckException {
        deck1.putCardOnBottom(new Card(6, 1));
        deck1.putCardOnBottom(new Card(10, 2));

        deck2.putCardOnBottom(new Card(6, 1));
        deck2.putCardOnBottom(new Card(11, 1));

        game = new Game(deck1, deck2);
        game.makeMove();

        assertEquals(1, game.getCurrentMove());
        assertTrue(game.isCardDispute());
        assertFalse(game.isGameDraw());
        assertEquals(0, game.getMoveWinner());
        assertEquals(0, game.getGameWinner());
    }

    @Test
    public void player1WinsGame() throws Deck.DeckException {
        deck1.putCardOnBottom(new Card(6, 1));
        deck1.putCardOnBottom(new Card(13, 2));

        deck2.putCardOnBottom(new Card(6, 1));
        deck2.putCardOnBottom(new Card(11, 1));

        game = new Game(deck1, deck2);
        game.makeMove();
        game.makeMove();

        assertEquals(2, game.getCurrentMove());
        assertFalse(game.isCardDispute());
        assertFalse(game.isGameDraw());
        assertEquals(1, game.getMoveWinner());
        assertEquals(1, game.getGameWinner());
    }

    @Test
    public void player2WinsGame() throws Deck.DeckException {
        deck1.putCardOnBottom(new Card(6, 1));
        deck1.putCardOnBottom(new Card(11, 2));

        deck2.putCardOnBottom(new Card(6, 1));
        deck2.putCardOnBottom(new Card(13, 1));

        game = new Game(deck1, deck2);
        game.makeMove();
        game.makeMove();

        assertEquals(2, game.getCurrentMove());
        assertFalse(game.isCardDispute());
        assertFalse(game.isGameDraw());
        assertEquals(2, game.getMoveWinner());
        assertEquals(2, game.getGameWinner());
    }

    @Test
    public void isGameDraw() throws Deck.DeckException {
        deck1.putCardOnBottom(new Card(6, 1));
        deck1.putCardOnBottom(new Card(11, 2));

        deck2.putCardOnBottom(new Card(6, 1));
        deck2.putCardOnBottom(new Card(11, 1));

        game = new Game(deck1, deck2);
        game.makeMove();
        game.makeMove();

        assertEquals(2, game.getCurrentMove());
        assertTrue(game.isCardDispute());
        assertTrue(game.isGameDraw());
        assertEquals(0, game.getMoveWinner());
        assertEquals(0, game.getGameWinner());
    }

    @Test
    public void sixBeatsAceRule() throws Deck.DeckException {
        deck1.putCardOnBottom(new Card(6, 1));

        deck2.putCardOnBottom(new Card(14, 1));

        game = new Game(deck1, deck2);
        game.makeMove();

        assertEquals(1, game.getCurrentMove());
        assertFalse(game.isCardDispute());
        assertFalse(game.isGameDraw());
        assertEquals(1, game.getMoveWinner());
        assertEquals(1, game.getGameWinner());
    }
}
