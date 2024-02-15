import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class GuiGame implements MouseListener {
    public static final Button CONTINUE = new Button(293, 490, 414, 110),
            NO = new Button(717, 459, 108, 53),
            YES = new Button(847, 459, 108, 59);
    public static final int MAIN_SCREEN = 0,
            INSTRUCTIONS_SCREEN = 1, FIRST_PHASE = 2, END_PHASE = 6, FOLD_PHASE = 7;
    private Deck deck;
    private Player player;
    private Player dealer;
    private Card[] middle;
    private GuiGameView window;
    private int state;

    // Constructor
    public GuiGame(Player player, Player dealer) {

        state = 0;

        this.player = player;
        player.setPoints(110);
        this.dealer = dealer;
        this.createDeck();
        deck.shuffle();
        middle = new Card[3];
        window = new GuiGameView(this);
        this.window.addMouseListener(this);
    }

    public void play() {
        // This case goes to instructions screen and prints deck
        if (state == INSTRUCTIONS_SCREEN) {
            window.repaint();
            deal();
        } else if (state > INSTRUCTIONS_SCREEN && state < END_PHASE) {
            // Draws screen then reveals a card
            window.repaint();
            player.addPoints(-5);

        } else {
            reveal();
            window.repaint();
        }
    }

    // Sets all cards in play to be revealed
    public void reveal() {
        for (int i = 0; i < 2; i++) {
            middle[i].setRevealed(true);
            dealer.getHand()[i].setRevealed(true);
        }
        middle[2].setRevealed(true);
    }

    // Returns true if the player won
    public boolean win() {
        Checker c = new Checker(middle, player, dealer);
        return c.won() == player;
    }

    // Deals user, dealer and middle
    public void deal() {
        for (int i = 0; i < 2; i++) {

            player.getHand()[i] = deck.deal();
            player.getHand()[i].setRevealed(true);
            dealer.getHand()[i] = deck.deal();

            middle[i] = deck.deal();
        }
        middle[2] = deck.deal();

    }

    // Creates complete 52 card deck with ace being a high value
    public void createDeck() {
        String[] ranks = new String[13];
        int[] points = new int[13];
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        for (int i = 0; i < 13; i++) {
            String rank = checkRoyal(i + 2);
            ranks[i] = rank;
            points[i] = i + 2;
        }
        deck = new Deck(points, suits, ranks);
    }

    // Checks if a number is greater than 10 and then returns corresponding special card name
    public static String checkRoyal(int num) {
        if (num > 1 && num < 11)
            return "" + num;
        if (num == 11)
            return "Jack";
        if (num == 12)
            return "Queen";
        if (num == 13)
            return "King";
        else
            return "Ace";

    }

    // Resets hands, middle, and reshuffles the deck, and then deals
    public void reset() {
        for (int i = 0; i < 2; i++) {
            player.getHand()[i].setRevealed(false);
            player.getHand()[i] = null;
            dealer.getHand()[i].setRevealed(false);
            dealer.getHand()[i] = null;
            middle[i].setRevealed(false);
            middle[i] = null;
        }
        middle[2].setRevealed(false);
        middle[2] = null;
        deck.reset();
        deck.shuffle();
        this.deal();
        state = FIRST_PHASE;
        window.repaint();
    }
    public void addScore() {
        player.setPoints(player.getPoints() + 20);
    }

    // Various getters and setters for the front end
    public Player getPlayer() {
        return player;
    }

    public Player getDealer() {
        return dealer;
    }

    public int getState() {
        return state;
    }

    public Card[] getMiddle() {
        return middle;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (state < FIRST_PHASE) {
            if (CONTINUE.isClicked(e.getX(), e.getY())) {
                System.out.println("continue clickedd");
                state++;
                play();
            }
        } else {
            if (NO.isClicked(e.getX(), e.getY())) {
                System.out.println("no clickedd");
                if(state < END_PHASE - 1) {
                    middle[state - FIRST_PHASE].setRevealed(true);

                }
                state++;
                if (state >= FOLD_PHASE) {
                    state = MAIN_SCREEN;
                }

                play();
            } else if (YES.isClicked(e.getX(), e.getY())) {
                if (state >= END_PHASE) {
                    reset();
                    return;
                }
                System.out.println("yes clickedd");
                reveal();
                state = FOLD_PHASE;
                window.repaint();
            }
        }
    }

    @Override
    // # 8: Required of a MouseListener
    public void mouseReleased(MouseEvent e) {
        // For demo purposes only
        //   System.out.println("mouseReleased event handler executed.");
    }

    @Override
    // # 9: Required of a MouseListener
    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        // For demo purposes only
        // System.out.println("mouseEntered event handler executed.");
    }

    @Override
    // # 11: Required of a MouseListener
    public void mouseExited(MouseEvent e) {
        // For demo purposes only
        //  System.out.println("mouseExited event handler executed.");
    }

    //main function
    public static void main(String[] args) {

        Player player = new Player("player");
        Player dealer = new Player("dealer");
        GuiGame game = new GuiGame(player, dealer);
        //if yes on opening screen game.play;

    }
}
