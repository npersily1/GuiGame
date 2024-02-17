import javax.swing.*;
import java.awt.*;

// Noah Persily GuiGame
public class GuiGameView extends JFrame {

    public static final String title = "HoldEm";
    public static final Image MAIN_SCREEN = new ImageIcon("Resources/MainScreen.jpg").getImage(),
            START_SCREEN = new ImageIcon("Resources/GuiGameTitle.jpg").getImage(),
            INSTRUCTIONS = new ImageIcon("Resources/Instructions.jpg").getImage();

    public static final int WINDOW_WIDTH = 1000,
            WINDOW_HEIGHT = 600,
            X_PADDING = (WINDOW_WIDTH - (Card.CARD_WIDTH * 3)) / 2,
            Y_PADDING = 40;

    private GuiGame game;
    // Basic constructor
    public GuiGameView(GuiGame g) {
        game = g;
        this.setTitle(title);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void paint(Graphics g) {
        // Draws background based on state
        if (game.getState() == GuiGame.MAIN_SCREEN) {
            drawTitleScreen(g);
            return;
        }
        if (game.getState() == GuiGame.INSTRUCTIONS_SCREEN) {
            drawInstructions(g);
            return;
        }
        g.drawImage(MAIN_SCREEN, 0, 0, this);

        //Draws player and dealer cards
        int x = X_PADDING;
        int gameY = WINDOW_HEIGHT - Y_PADDING - Card.CARD_HEIGHT + getInsets().top;
        for (int i = 0; i < 2; i++) {
            game.getPlayer().getHand()[i].draw(g, x, gameY, this);
            game.getDealer().getHand()[i].draw(g, x, Y_PADDING, this);
            x += Card.CARD_WIDTH * 2;

        }
        // Paints center cards
        x = (3 * Card.CARD_WIDTH + 2 * Card.CARD_WIDTH) / 2;
        for (int i = 0; i < 3; i++) {
            game.getMiddle()[i].draw(g, x, WINDOW_HEIGHT / 2 - (Card.CARD_HEIGHT / 2), this);
            x += Card.CARD_WIDTH * 2;
        }
        // Draws score
        g.setColor(Color.YELLOW);
        Font f = new Font("Serif", Font.BOLD, 20);
        g.setFont(f);
        g.drawString(game.getPlayer().getPoints() + "", 100, 75);

        drawText(g);

    }

    public void drawText(Graphics g) {
        // If the game is still in play, display "Do you Fold"
        if(game.getState() < GuiGame.END_PHASE) {
            g.drawString("Do you fold", 800, 400);
        }
        //I f the game is over because they never folded
        else if(game.getState() == GuiGame.END_PHASE) {
            // Display the result and prompt to continue
            if(game.win()) {
                g.drawString("You Won", 800, 400);
                g.drawString("Do you want to continue", 750, 450);
                game.addScore();
            }
            else {
                g.drawString("You Lost", 800, 400);
                g.drawString("Do you want to continue", 750, 450);
            }
        }
        // If they folded
        else if(game.getState() == GuiGame.FOLD_PHASE) {
            // Display the result and prompt to continue
            if(game.win()) {
                g.drawString("You would have Won", 800, 400);
                g.drawString("Do you want to continue", 750, 450);
            }
            else {
                g.drawString("You Lost", 800, 400);
                g.drawString("Do you want to continue", 750, 450);
            }
        }
    }
    // Methods to draw background screen
    public void drawTitleScreen(Graphics g) {
        g.drawImage(START_SCREEN, 0, 0, this);
    }

    public void drawInstructions(Graphics g) {
        g.drawImage(INSTRUCTIONS, 0, 0, this);
    }

}
