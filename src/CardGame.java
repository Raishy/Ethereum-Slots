import java.awt.*;
import javax.swing.*;

/**
 * Created by nhasdarjian on 6/2/17.
 */

public class CardGame extends JPanel implements Game {

    public static final int HIGH_CARD = 1;
    public static final int PAIR = 2;
    public static final int TWO_PAIR = 3;
    public static final int THREE_OF_A_KIND = 4;
    public static final int STRAIGHT = 5;
    public static final int FLUSH = 6;
    public static final int FULL_HOUSE = 7;
    public static final int FOUR_OF_A_KIND = 8;
    public static final int STRAIGHT_FLUSH = 9;
    public static final int ROYAL_FLUSH = 10;

    public int[] result;
    public Image[] cards;
    public Hands[] hands;
    public int[][] board;
    public int win;

    public CardGame() {
        this.setup();
        this.setOpaque(false);
    }

    public void fetchImages(Image[] img) {
        this.cards = new Image[img.length];
        for (int i = 0; i < cards.length; i++) this.cards[i] = img[i];
    }

    public void setup() {

        this.result = new int[Main.ROWS];
        this.hands = new Hands[Main.ROWS];
        for (int i = 0; i < hands.length; i++) {
            hands[i] = new Hands();
        }
        this.setVisible(true);

    }

    public int[] roll(String texture) {


        System.out.println("I'm getting called!");
        return null;

    }


}
