import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    public GuiGameView(GuiGame g) {
        game = g;
        this.setTitle(title);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void paint(Graphics g) {

        if (game.getState() == 0) {
            drawTitleScreen(g);
            return;
        }
        if (game.getState() == 1) {
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
        g.setColor(Color.YELLOW);
        Font f = new Font("Serif", Font.BOLD, 20);
        g.setFont(f);
        g.drawString(game.getPlayer().getPoints() + "", 50, 50);

    }

    public void drawText(Graphics g) {

    }

    public void drawTitleScreen(Graphics g) {
        g.drawImage(START_SCREEN, 0, 0, this);
    }

    public void drawInstructions(Graphics g) {
        g.drawImage(INSTRUCTIONS, 0, 0, this);
    }

}
