import java.util.*;

public class Game {

    private final MyQueue<Card> cardDeck1;
    private final MyQueue<Card> cardDeck2;

    private List<Card> faceUpCards1;
    private List<Card> faceUpCards2;

    private int currentMove;
    private boolean isDispute;
    private boolean isDraw;
    private int moveWinner;
    private int gameWinner;

    public Game() {
        cardDeck1 = new MyQueue<>();
        cardDeck2 = new MyQueue<>();

        faceUpCards1 = new LinkedList<>();
        faceUpCards2 = new LinkedList<>();

        currentMove = 0;
        isDispute = false;
        isDraw = false;
        moveWinner = 0;
        gameWinner = 0;

        dealCards();
    }

    public MyQueue<Card> getCardDeck1() {
        return new MyQueue<>(cardDeck1);
    }
    public MyQueue<Card> getCardDeck2() {
        return new MyQueue<>(cardDeck2);
    }
    public List<Card> getFaceUpCards1() {
        return new LinkedList<>(faceUpCards1);
    }
    public List<Card> getFaceUpCards2() {
        return new LinkedList<>(faceUpCards2);
    }
    public boolean isDispute() {
        return isDispute;
    }
    public boolean isDraw() {
        return isDraw;
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

    private List<Card> getAllCards() {
        List<Card> newCommonDeck = new ArrayList<>(36);
        for (char suit : new char[]{'♥', '♦', '♣', '♠'}) {
            for (int i = 6; i < 15; i++) {
                newCommonDeck.add(new Card(i, suit));
            }
        }
        return newCommonDeck;
    }

    public void makeMove() {
        currentMove++;

        if (moveWinner == 1) {
            cardDeck1.addAll(fromCardCollectionToMyQueue(faceUpCards2));
            cardDeck1.addAll(fromCardCollectionToMyQueue(faceUpCards1));
            faceUpCards1 = new LinkedList<>();
            faceUpCards2 = new LinkedList<>();

        } else if (moveWinner == 2) {
            cardDeck2.addAll(fromCardCollectionToMyQueue(faceUpCards1));
            cardDeck2.addAll(fromCardCollectionToMyQueue(faceUpCards2));
            faceUpCards1 = new LinkedList<>();
            faceUpCards2 = new LinkedList<>();
        }

        faceUpCards1.add(0, cardDeck1.poll());
        faceUpCards2.add(0, cardDeck2.poll());

        int resultOfCompareCards = compareCards(faceUpCards1.get(0), faceUpCards2.get(0));
        if (resultOfCompareCards == 0) {
            isDispute = true;
            moveWinner = 0;
            if (cardDeck1.isEmpty() && cardDeck2.isEmpty()) {
                isDraw = true;
            } else if (cardDeck1.isEmpty()) {
                moveWinner = 1;
            } else if (cardDeck2.isEmpty()) {
                moveWinner = 2;
            }
        } else if (resultOfCompareCards == 1) {
            isDispute = false;
            moveWinner = 1;
            if (cardDeck2.isEmpty()) {
                gameWinner = 1;
            }
        } else if (resultOfCompareCards == 2) {
            isDispute = false;
            moveWinner = 2;
            if (cardDeck1.isEmpty()) {
                gameWinner = 2;
            }
        }
    }

    private int compareCards(Card card1, Card card2) {

        if (card1.getCardValue() == 6 && card2.getCardValue() == 14) {
            return 1;
        } else if (card1.getCardValue() == 14 && card2.getCardValue() == 6) {
            return 2;
        } else {
            if (card1.getCardValue() > card2.getCardValue()) {
                return 1;
            } else if (card1.getCardValue() < card2.getCardValue()) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    private void dealCards() {
        Random random = new Random();
        List<Card> allCardsList = getAllCards();
        for (int i = 0; i < 6; i++) {
            cardDeck1.offer(allCardsList.remove(random.nextInt(allCardsList.size())));
            cardDeck2.offer(allCardsList.remove(random.nextInt(allCardsList.size())));
        }
    }

    public String[] fromCardMyQueueToStringArray(MyQueue<Card> cards) {
        String[] newArr = new String[cards.size()];
        int i = 0;
        for (Card card : cards) {
            newArr[i++] = card.toString();
        }
        return newArr;
    }

    public String[] fromCardListToStringArray(List<Card> cards) {
        String[] newArr = new String[cards.size()];
        int i = 0;
        for (Card card : cards) {
            newArr[i++] = card.toString();
        }
        return newArr;
    }

    public MyQueue<Card> fromCardCollectionToMyQueue(Collection<Card> cardList) {
        MyQueue<Card> cardsQueue = new MyQueue<>();
        for (Card card : cardList) {
            cardsQueue.addLast(card);
        }
        return cardsQueue;
    }
}