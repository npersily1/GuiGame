import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GuiGameView extends JFrame implements MouseListener {


    public static final String title = "HoldEm";

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
        Image startScreen = new ImageIcon("Resources/GuiGameTitle.jpg").getImage();
        g.drawImage(startScreen, 0, 0, this);
    }

    public void drawInstructions(Graphics g) {

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

        // For demo purposes only

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
}
