import java.awt.*;

/**
 * Created by nhasdarjian on 6/2/17.
 */

public interface Game {

    int ROWS = Main.ROWS;
    int COLS = Main.COLS;

    void fetchImages(Image[] i);
    int[] roll(String texture);
    void setup();
}
