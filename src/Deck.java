import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> deck;
    private int cardsLeft;

    public Deck(int[] point, String[] suit, String[] rank) {
        deck = new ArrayList<Card>();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                deck.add(new Card(point[i], suit[j], rank[i], 4 * ((i -1) + j)));
            }
        }
        cardsLeft = deck.size();
    }

    // Returns true if is empty
    public boolean isEmpty() {
        return !(cardsLeft > 0);
    }

    //getter
    public int getCardsLeft() {
        return cardsLeft;
    }

    // deals
    public Card deal() {
        if (deck.isEmpty()) {
            return null;
        }
        if (cardsLeft == 0) {
            return null;
        }
        return deck.get(--cardsLeft);

    }

    //random shuffle
    public void shuffle() {
        for (int i = cardsLeft - 1; i >= 0; i--) {
            int r = (int) (Math.random() * i);
            Card temp = deck.get(i);
            deck.set(i, deck.get(r));
            deck.set(r, temp);

        }

    }

    //reset deck
    public void reset() {
        cardsLeft = 52;
        for (int i = 0; i < 52; i++) {
            deck.get(i).setRevealed(false);
        }
    }
}
