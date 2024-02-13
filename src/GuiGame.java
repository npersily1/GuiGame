import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class GuiGame implements MouseListener {
    public static final Button CONTINUE = new Button(293, 490, 414, 110),
            YES = new Button(717, 459, 108, 59),
            NO = new Button(847, 459, 108, 53);
    private Deck deck;
    private Player player;
    private Player dealer;
    private Card[] middle;
    private Scanner s;

    private GuiGameView window;
    private int state;

    private boolean yesNO;

    // Constructor
    public GuiGame(Player player, Player dealer) {

        state = 0;
        this.player = player;
        player.setPoints(110);
        this.dealer = dealer;
        this.createDeck();
        deck.shuffle();
        middle = new Card[3];
        s = new Scanner(System.in);
        window = new GuiGameView(this);
        this.window.addMouseListener(this);
    }

    public void play() {

        if (state == 2) {
            window.repaint();
            deal();
        } else if (state > 2 && state < 6) {
            window.repaint();
            player.addPoints(-5);
            middle[state].setRevealed(true);


        } else {
            window.repaint();


//            while (player.getPoints() > 0) {
//                //deal player cards
//
//
//                //betting loop
//                for (int i = 0; i < 4; i++) {
//                    window.repaint();
//                    player.addPoints(-5);
//                    this.printBoard(++counter);
//
//                    if (buttonClicked) {
//                        break;
//                    }
//                    if (!(i == 3)) {
//                        middle[i].setRevealed(true);
//                    } else {
//                        dealer.getHand()[0].setRevealed(true);
//                        dealer.getHand()[1].setRevealed(true);
//                    }
//                }
//
//                //end of game scenarios
//                boolean win = win();
//                printBoard(++counter);
//                if (!didFold && win) {
//                    player.addPoints(50);
//                    System.out.println("You win");
//                } else if (win) {
//                    System.out.println("You would would have won");
//                } else {
//                    System.out.println("You lost");
//                }
//                window.repaint();
//                if (!willContinue()) {
//                    break;
//                }
//                reset();
//            }
        }
    }

    //returns true if the player wants to continue playing
//    public boolean willContinue() {
//        System.out.println("Do you want to continue playing");
//        String willContinue = s.nextLine();
//        switch (willContinue) {
//            case ("y"): {
//                return true;
//            }
//            case ("Y"): {
//                return true;
//            }
//            case ("Yes"): {
//                return true;
//            }
//        }
//        state = 0;
//        return false;
//    }

    //returns true if the player won
    public boolean win() {
        Checker c = new Checker(middle, player, dealer);
        return c.won() == player;
    }

    //returns true if they want to fold
    public boolean willFold() {

        return false;
    }

    //prints board with neccesary elements hidden based off of counter
    public void printBoard(int counter) {

        if (counter == 5) {
            System.out.println(player + "  " + player.getHandName());

            this.printMiddle(counter);
            System.out.println("dealer's cards  " + dealer.getHand()[0] + "       " + dealer.getHand()[1] + " " + dealer.getHandName());
            return;
        }
        System.out.println(player);
        this.printMiddle(counter);
        System.out.println("dealer's cards  ___ ___");

    }

    //prints correct amount of middle three cards based on counter
    public void printMiddle(int counter) {
        System.out.println("Center Cards");
        if (counter == 1) {
            System.out.println("___    ___    ___");
        } else if (counter == 2) {
            for (int i = 0; i < 1; i++) {
                System.out.print(middle[i] + "  ");
            }
            System.out.println("    ___     ___");
        } else if (counter == 3) {
            for (int i = 0; i < 2; i++) {
                System.out.print(middle[i] + "   ");
            }

            System.out.println("___");
        } else {
            for (int i = 0; i < 3; i++) {
                System.out.print(middle[i] + "    ");
            }
            System.out.println();
        }
    }

    //deals use dealer and winner
    public void deal() {
        for (int i = 0; i < 2; i++) {

            player.getHand()[i] = deck.deal();
            player.getHand()[i].setRevealed(true);
            dealer.getHand()[i] = deck.deal();

            middle[i] = deck.deal();
        }
        middle[2] = deck.deal();

    }

    // creates complete 52 card deck with ace being a high value
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

    // checks if a number is greater than 10 and then returns corresponding special card name
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

    //resets hand middl3 and cardsleft
    public void reset() {
        for (int i = 0; i < 2; i++) {
            player.getHand()[i] = null;
            dealer.getHand()[i] = null;
            middle[i] = null;
        }
        middle[2] = null;
        deck.reset();
        deck.shuffle();
    }

    //prints intro
    public void printInstructions() {
        System.out.println("Welcome to Noah's version of Holdem");
        System.out.println("The main difference is that there is only three cards in the center");
        System.out.println("Each round if you do not fold then five points will be deducted from your total");
        System.out.println("If you win you will make double what is put in");
        System.out.println("Good Luck");
    }

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

        // For demo purposes only

    }

    @Override
    // # 8: Required of a MouseListener
    public void mouseReleased(MouseEvent e) {
        // For demo purposes only
        System.out.println("mouseReleased event handler executed.");
    }

    @Override
    // # 9: Required of a MouseListener
    public void mouseClicked(MouseEvent e) {

        if (state < 2) {
            if (CONTINUE.isClicked(e.getX(), e.getY())) {
                state += 1;
                play();
            }
        } else {
            if (YES.isClicked(e.getX(), e.getY())) {
                state++;
                play();
            } else if (NO.isClicked(e.getX(), e.getY())) {
                for (int i = 0; i < 3; i++) {
                    middle[i].setRevealed(true);
                }
                dealer.getHand()[0].setRevealed(true);
                dealer.getHand()[1].setRevealed(true);
                state = 6;
            }
        }

    }

    public void mouseEntered(MouseEvent e) {
        // For demo purposes only
        System.out.println("mouseEntered event handler executed.");
    }

    @Override
    // # 11: Required of a MouseListener
    public void mouseExited(MouseEvent e) {
        // For demo purposes only
        System.out.println("mouseExited event handler executed.");
    }

    //main function
    public static void main(String[] args) {
        // System.out.println("What is your name");
        Scanner input = new Scanner(System.in);
        Player player = new Player("player");
        Player dealer = new Player("dealer");
        GuiGame game = new GuiGame(player, dealer);
        //if yes on opening screen game.play;

    }
}
