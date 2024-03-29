import java.util.Arrays;

public class Checker {
    //constants
    public final static String[] COMBOS = {"High Card", "Pair", "Three of a Kind", "Full House", "Four of a Kind", "Flush", "Straight", "Straight Flush"};
    public static final int PAIR = 100;
    public static final int THREE = 200;
    public static final int FULLHOUSE = 300;
    public static final int FOUR = 400;
    public static final int FLUSH = 500;
    public static final int STRAIGHT = 600;
    public static final int STRAIGHTFLUSH = 700;

    Card[] p1;
    int p1score;
    Card[] p2;
    private Player p1ref;
    private Player p2ref;
    int p2score;

    public Checker(Card[] middle, Player p1, Player p2) {
        p1score = 0;
        p2score = 0;
        this.p1 = new Card[5];
        this.p2 = new Card[5];
        p1ref = p1;
        p2ref = p2;
        // Fills arrays with hands and middle
        this.p1[0] = p1.getHand()[0];
        this.p1[1] = p1.getHand()[1];
        this.p1[2] = middle[0];
        this.p1[3] = middle[1];
        this.p1[4] = middle[2];

        this.p2[0] = p2.getHand()[0];
        this.p2[1] = p2.getHand()[1];
        this.p2[2] = middle[0];
        this.p2[3] = middle[1];
        this.p2[4] = middle[2];
    }

    // Returns player who won
    public Player won() {
        // Assigns value to each players score
        p1score = assign(p1);
        p2score = assign(p2);
        // Sets hand name based on score
        p1ref.setHandName(this.getHandName(p1score));
        p2ref.setHandName(this.getHandName(p2score));
        // Account for all cases
        if (p1score > p2score) {
            return p1ref;
        } else if (p1score < p2score) {
            return p2ref;
        } else {
            return tie();
        }
    }

    // Returns player who won using tiebreak rules
    public Player tie() {
        // While they still have the same high card
        while (getHighCard(p1) == getHighCard(p2)) {
            // Change that card to a new card worth 0
            p1[indexOf(p1, getHighCard(p1))] = new Card(0, null, null, 0);
            p2[indexOf(p2, getHighCard(p2))] = new Card(0, null, null, 0);
        }
        // Return the winner
        if (getHighCard(p1) > getHighCard(p2)) {
            return p1ref;
        }
        return p2ref;

    }

    // Index of for arrays
    public int indexOf(Card[] hand, int point) {
        for (int i = 0; i < 5; i++) {
            if (hand[i].getPoint() == point) {
                return i;
            }
        }
        return 0;
    }

    // Eeturns hand name based off of score
    public String getHandName(int score) {
        if (COMBOS[score / 100].equals("High Card")) {
            return "High Card " + GuiGame.checkRoyal(score % 100);
        }

        return COMBOS[score / 100] + " with a high card " + GuiGame.checkRoyal(score % 100);
    }

    // Returns the point value for a hand
    public int assign(Card[] hand) {
        // Set temp equal to all hands from rarest to least
        // If they have that hand return temp
        int temp = getStraightFlush(hand);
        if (temp / 100 != 0) {
            return temp;
        }
        temp = straight(hand);
        if (temp / 100 != 0) {
            return temp;
        }
        temp = flush(hand);
        if (temp / 100 != 0) {
            return temp;
        }

        temp = getFullHouse(hand);
        if (temp / 100 != 0) {
            return temp;
        }
        temp = numPair(hand);
        if (temp / 100 != 0) {
            return temp;
        }
        temp = getPair(hand);
        if (temp / 100 != 0) {
            return temp;
        } else {
            return getHighCard(hand);
        }
    }

    // Returns score for a hand with a flush, 0 if they don't have one
    public int flush(Card[] hand) {
        //for every card but the last
        for (int i = 0; i < 4; i++) {
            //if they have a different suit
            if (!(hand[i].getSuit().equals(hand[i + 1].getSuit()))) {
                return 0;
            }
        }
        return FLUSH + getHighCard(hand);
    }

    // Gets highcard point value in any hand
    public int getHighCard(Card[] hand) {

        int max = 0;
        for (int i = 0; i < hand.length; i++) {
            if (max < hand[i].getPoint()) {
                max = hand[i].getPoint();
            }
        }
        return max;
    }

    // Returns point value for a hand with a straight 0 if they dont have it
    public int straight(Card[] hand) {
        //put card points into array of ints
        int[] vals = new int[5];
        for (int i = 0; i < 5; i++) {
            vals[i] = hand[i].getPoint();
        }
        //sort
        Arrays.sort(vals);
        //check if each value plus one is equal to the next value
        for (int i = 0; i < 4; i++) {
            if (vals[i] + 1 != vals[i + 1]) {
                return 0;
            }
        }
        return STRAIGHT + getHighCard(hand);
    }

    // Returns point value if they have three or four of a kind
    public int numPair(Card[] hand) {
        // For the first three cards of a hand
        for (int i = 0; i < 3; i++) {
            int counter = 0;
            // For each card after card that is currently being iterated on
            for (int j = i; j < 5; j++) {
                // If the cards are the same rank
                if (hand[i].getPoint() == hand[j].getPoint()) {
                    counter++;
                }
            }
            // Check to see if there is four or three of a kind
            if (counter == 3) {
                return THREE + hand[i].getPoint();
            }
            if (counter == 4) {
                return FOUR + hand[i].getPoint();
            }
        }
        return 0;
    }

    // Returns point value for a pair
    //Takes into account two different pairs
    public int getPair(Card[] hand) {
        // Representing point values of pairs
        int pair1 = 0;
        int pair2 = 0;
        // For each card
        for (int i = 0; i < 4; i++) {
            // For each card after current card
            for (int j = i + 1; j < 5; j++) {
                // If the 2 cards being iterated on have the same point value set pair 1 and break
                if (pair1 == 0 && hand[i].getPoint() == hand[j].getPoint()) {
                    pair1 = hand[j].getPoint();
                    break;
                }
                // Uf he 2 cards being iterated on have the same point value set pair 2 and break
                if (hand[i].getPoint() == hand[j].getPoint()) {
                    pair2 = hand[j].getPoint();
                    break;
                }
            }
        }
        // Uf there was a pair return the points for the greater one
        if (pair1 != 0) {
            return PAIR + Math.max(pair1, pair2);
        }
        return 0;
    }

    // Returns pair value as long it is not equal to other
    // Should only be used by full house method so it should be private
    private int getFullPair(Card[] hand, int other) {
        int pair1 = 0;
        int pair2 = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (pair1 == 0 && hand[i].getPoint() == hand[j].getPoint() && hand[j].getPoint() != other) {
                    pair1 = hand[j].getPoint();
                    break;
                }
                if (hand[i].getPoint() == hand[j].getPoint() && hand[j].getPoint() != other) {
                    pair2 = hand[j].getPoint();
                    break;
                }
            }
        }
        if (pair1 != 0) {
            return PAIR + Math.max(pair1, pair2);
        }
        return 0;
    }

    // Returns points if they have a full house
    public int getFullHouse(Card[] hand) {
        // Sets two componenets of a full house
        int three = numPair(hand);
        int two = getFullPair(hand, three % 100);
        //if they have a full house
        if (two / 100 == 1)
            if (three / 100 == 2) {
                return FULLHOUSE + three % 100;
            }
        return 0;
    }

    // Returns point value if they have a straight flush
    public int getStraightFlush(Card[] hand) {
        // Sets values for flush and straight flush
        int flush = flush(hand);
        int straight = straight(hand);
        // If they have a straight flush
        if (flush / 100 == 5)
            if (straight / 100 == 6) {
                return STRAIGHTFLUSH + straight % 100;
            }
        return 0;
    }
}
