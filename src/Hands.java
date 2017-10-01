import javax.swing.*;
import java.awt.*;

/**
 * Created by nhasdarjian on 6/3/17.
 */

public class Hands extends JPanel {

    private Image[] img;
    private Card[] c;

    public Hands() {
        super();
        c = new Card[5];
    }

    public void fetchImages(Image[] images) {
        this.img = new Image[images.length];
        for (int i = 0; i < img.length; i++) this.img[i] = images[i];

    }

    private class Card extends JPanel {

        protected Image panelImg;
        protected int val;

        public Card() {
            super();
            this.setOpaque(false);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(panelImg, 5, 5, this.getWidth() - 5, this.getHeight() - 5, this);
        }

    }

}
