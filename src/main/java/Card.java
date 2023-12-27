
public class Card {

    private final int value;
    private final int suit;

    public Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getCardValue() {
        return this.value;
    }

    public int getCardSuit() {
        return this.suit;
    }

    public String toString() {
        return suitToChar() + " " + valueToString();
    }

    private String valueToString() {
        String valueString;
        if (value > 10) {
            valueString = switch (value) {
                case 11 -> "Jack";
                case 12 -> "Queen";
                case 13 -> "King";
                case 14 -> "Ace";
                default -> "";
            };
        } else {
            valueString = String.valueOf(value);
        }
        return valueString;
    }

    private char suitToChar() {
        char suitChar;
        suitChar = switch (suit) {
            case 1 -> '♥';
            case 2 -> '♦';
            case 3-> '♣';
            case 4 -> '♠';
            default -> ' ';
        };
        return suitChar;
    }
}
