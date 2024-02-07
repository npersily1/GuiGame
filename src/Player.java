import java.util.ArrayList;

public class Player {
    private Card[] hand;
    private int points;
    private String name;

    private String handName;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        handName = "";
        hand = new Card[2];
    }

    // constructor specified to make but is not used
//    public Player(String name, ArrayList<Card> oldHand ) {
//        this.name = name;
//        this.points = 0;
//        for (Card c : oldHand) {
//            hand.add(c);
//        }
//        handName = "";
//    }
//getters
    public String getHandName() {
        return handName;
    }

    public void setHandName(String handName) {
        this.handName = handName;
    }

    public Card[] getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    // Setter
    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int increment) {
        this.points += increment;
    }


    public String toString() {
        String info = name + " has " + points + " points \n" +
                name + "'s cards: ";

        for (Card c : hand) {
            info += "  " + c.toString() + "    ";
        }
        return info;
    }
}
