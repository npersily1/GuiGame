import javax.swing.*;
import java.awt.*;


public class Card {
    //
    public static final Image BACK = new ImageIcon("Resources/Cards/back.png").getImage();
    public static final int CARD_HEIGHT = 150,
                            CARD_WIDTH = 100;
    private int point;
    private String suit;
    private String rank;
    private Image image;
    private boolean isRevealed;

    public Card(int point, String suit, String rank, int cardNumber) {
        this.point = point;
        this.suit = suit;
        this.rank = rank;
        this.isRevealed = false;
        if(!(cardNumber == 0)) {
            image = new ImageIcon("Resources/Cards/" + cardNumber + ".png").getImage();
        }
    }

    //getters
    public int getPoint() {
        return point;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    //setters
    public void setPoint(int point) {
        this.point = point;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public void draw(Graphics g, int x, int y, GuiGameView window) {
        if (isRevealed) {
            g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, window);
            return;
        }
        g.drawImage(BACK, x, y, CARD_WIDTH, CARD_HEIGHT, window);
    }
}
