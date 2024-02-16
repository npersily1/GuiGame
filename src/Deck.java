import java.util.ArrayList;

public class Deck {
    //Noah Persily GuiGame
    private final ArrayList<Card> deck;
    private int cardsLeft;
    // Defaults constructor
    public Deck(int[] point, String[] suit, String[] rank) {
        deck = new ArrayList<Card>();
        int count = 5;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                deck.add(new Card(point[i], suit[j], rank[i], count++));
            }
        }
        cardsLeft = deck.size();
    }

    // Returns true if is empty
    public boolean isEmpty() {
        return !(cardsLeft > 0);
    }

    // Getter
    public int getCardsLeft() {
        return cardsLeft;
    }

    // Deals a single card
    public Card deal() {
        if (deck.isEmpty()) {
            return null;
        }
        if (cardsLeft == 0) {
            return null;
        }
        return deck.get(--cardsLeft);

    }

    // Random shuffle
    public void shuffle() {
        for (int i = cardsLeft - 1; i >= 0; i--) {
            int r = (int) (Math.random() * i);
            Card temp = deck.get(i);
            deck.set(i, deck.get(r));
            deck.set(r, temp);

        }

    }

    // Resets deck
    public void reset() {
        cardsLeft = 52;
        for (int i = 0; i < 52; i++) {
            deck.get(i).setRevealed(false);
        }
    }
}
