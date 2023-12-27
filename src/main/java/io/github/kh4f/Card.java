package io.github.kh4f;

public record Card(int value, int suitID) {

    /*private final int value;
    private final int suitID;

    public Card(int value, int suitID) {
        this.value = value;
        this.suitID = suitID;
    }

    public int getValue() {
        return this.value;
    }
    public int getSuitID() {
        return this.suitID;
    }*/

    public String toString() {
        return suitToChar() + " " + valueToString();
    }

    private String valueToString() {
        String valueAsString;
        if (value > 10) {
            valueAsString = switch (value) {
                case 11 -> "Jack";
                case 12 -> "Queen";
                case 13 -> "King";
                case 14 -> "Ace";
                default -> "";
            };
        } else {
            valueAsString = String.valueOf(value);
        }
        return valueAsString;
    }

    private char suitToChar() {
        char suitChar;
        suitChar = switch (suitID) {
            case 1 -> '♥';
            case 2 -> '♦';
            case 3 -> '♣';
            case 4 -> '♠';
            default -> ' ';
        };
        return suitChar;
    }
}
