public class Player {
    //Noah Persily GuiGame
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

    // Getters and setters
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
