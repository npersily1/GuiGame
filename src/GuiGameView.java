import javax.swing.*;
import java.awt.*;

public class GuiGameView extends JFrame {

    public static final String title = "HoldEm";

    public static final int WINDOW_WIDTH = 1000,
            WINDOW_HEIGHT = 600,
            CARD_WIDTH = 100,
            CARD_HEIGHT = 176,
            X_PADDING = 162,
            BETWEEN_CARDS = 76,
            Y_PADDING = 40;

    private GuiGame game;

    public GuiGameView(GuiGame g) {
        game = g;
        this.setTitle(title);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (game.getState() == 0) {
            drawTitleScreen(g);
            return;
        }
        if (game.getState() == 1) {
            drawInstructions(g);
            return;
        }

        //Draws player and dealer cards
        int x = X_PADDING;
        int gameY = WINDOW_HEIGHT - Y_PADDING;
        for (int i = 0; i < 2; i++) {
            game.getPlayer().getHand()[i].draw(g, x, gameY, this);
            game.getDealer().getHand()[i].draw(g, x, Y_PADDING, this);
            x += CARD_WIDTH + BETWEEN_CARDS;

        }
        // Paints center cards
        x = WINDOW_WIDTH - (3 * CARD_WIDTH + 2 * BETWEEN_CARDS) / 2;
        for (int i = 0; i < 3; i++) {
            game.getMiddle()[i].draw(g, x, WINDOW_HEIGHT / 2 - (Card.CARD_HEIGHT / 2), this);
            x += CARD_WIDTH + BETWEEN_CARDS;
        }
    }

    public void drawTitleScreen(Graphics g) {

    }
    public void drawInstructions(Graphics g) {

    }
}
