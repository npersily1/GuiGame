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
        //fills arrays with hands and middle
        for (int i = 0; i < 2; i++) {

            this.p1[i] = p1.getHand()[i];
            this.p2[i] = p2.getHand()[i];

        }
        for (int i = 2; i < 5; i++) {
            this.p1[i] = middle[i - 2];
            this.p2[i] = middle[i - 2];
        }
    }

    //returns player who won
    public Player won() {
        //assigns value to each players score
        p1score = assign(p1);
        p2score = assign(p2);
        //sets hand name based on score
        p1ref.setHandName(this.getHandName(p1score));
        p2ref.setHandName(this.getHandName(p2score));
        //account for all cases
        if (p1score > p2score) {
            return p1ref;
        } else if (p1score < p2score) {
            return p2ref;
        } else {
            return tie();
        }
    }

    //returns player who won using tiebreak rules
    public Player tie() {

        while (getHighCard(p1) == getHighCard(p2)) {
            p1[indexOf(p1, getHighCard(p1))] = null;
            p2[indexOf(p2, getHighCard(p2))] = null;
        }
        if (getHighCard(p1) > getHighCard(p2)) {
            return p1ref;
        }
        return p2ref;

    }

    //index of for arrays
    public int indexOf(Card[] hand, int point) {
        for (int i = 0; i < 5; i++) {
            if (hand[i].getPoint() == point) {
                return i;
            }
        }
        return 0;
    }

    // returns hand name based off of score
    public String getHandName(int score) {
        if (COMBOS[score / 100].equals("High Card")) {
            return "High Card " + GuiGame.checkRoyal(score % 100);
        }

        return COMBOS[score / 100] + " with a high card " + GuiGame.checkRoyal(score % 100);
    }

    //returns the point value for a hand
    public int assign(Card[] hand) {
        //set temp equal to all hands from rarest to least
        //if they have that hand return temp
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

    //returns score for a hand with a flush 0 if they don't have one
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

    //gets highcard point value in any hand
    public int getHighCard(Card[] hand) {

        int max = hand[0].getPoint();
        for (int i = 1; i < 5; i++) {
            if (max < hand[i].getPoint()) {
                max = hand[i].getPoint();
            }
        }
        return max;
    }

    //returns point value for a hand with a straight 0 if they dont have it
    public int straight(Card[] hand) {
        //put card points into array of ints
        int[] vals = new int[5];
        for (int i = 0; i < 5; i++) {
            vals[i] = hand[i].getPoint();
        }
        //sort
        Arrays.sort(vals);
        //check if each value + one is equal to the next value
        for (int i = 0; i < 4; i++) {
            if (vals[i] + 1 != vals[i + 1]) {
                return 0;
            }
        }
        return STRAIGHT + getHighCard(hand);
    }

    //returns point value if they have three or four of a kind
    public int numPair(Card[] hand) {
        //for the first three cards of a hand
        for (int i = 0; i < 3; i++) {
            int counter = 0;
            //for each card after card that is currently being iterated on
            for (int j = i; j < 5; j++) {
                //if the cards are the same rank
                if (hand[i].getPoint() == hand[j].getPoint()) {
                    counter++;
                }
            }
            //check to see if there is four or three of a kind
            if (counter == 3) {
                return THREE + hand[i].getPoint();
            }
            if (counter == 4) {
                return FOUR + hand[i].getPoint();
            }
        }
        return 0;
    }

    //returns point value for a pair
    //takes into account two pair possibility
    public int getPair(Card[] hand) {
        //representing point values of pairs
        int pair1 = 0;
        int pair2 = 0;
        //for each card
        for (int i = 0; i < 4; i++) {
            //for each card after current card
            for (int j = i + 1; j < 5; j++) {
                //if the 2 cards being iterated on have the same point value set pair 1 and break
                if (pair1 == 0 && hand[i].getPoint() == hand[j].getPoint()) {
                    pair1 = hand[j].getPoint();
                    break;
                }
                //if he 2 cards being iterated on have the same point value set pair 2 and break
                if (hand[i].getPoint() == hand[j].getPoint()) {
                    pair2 = hand[j].getPoint();
                    break;
                }
            }
        }
        //if there was a pair return the points for the greater one
        if (pair1 != 0) {
            return PAIR + Math.max(pair1, pair2);
        }
        return 0;
    }

    //returns pair value as long it is not equal to other
    //used for full house
    public int getFullPair(Card[] hand, int other) {
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

    //returns points if they have a full house
    public int getFullHouse(Card[] hand) {
        //sets two componenets of a full house
        int three = numPair(hand);
        int two = getFullPair(hand, three % 100);
        //if they have a full house
        if (two / 100 == 1)
            if (three / 100 == 2) {
                return FULLHOUSE + three % 100;
            }
        return 0;
    }

    //returns point value if they have a straight flush
    public int getStraightFlush(Card[] hand) {
        //sets values for flush and straight flush
        int flush = flush(hand);
        int straight = straight(hand);
        //if they have a straight flush
        if (flush / 100 == 5)
            if (straight / 100 == 6) {
                //not redundant because of truncation
                return STRAIGHTFLUSH + straight % 100;
            }
        return 0;
    }
}
