import java.awt.*;
import java.util.Stack;
import javax.swing.*;

/**
 * Created by nhasdarjian on 6/2/17.
 */

public class SlotGame extends JPanel implements Game {

    public int[] board;                 // stores state of board
    public Tile[] boxes;                // stores containers on board
    public Image[] tiles;               // stores possible results (all icons)
    public Stack<Integer> result;       // stores overlay/calculation values
    public int stackSize, win;
    public int[][] matches;

    public SlotGame(Image[] i) {

        this.setup();
        this.fetchImages(i);
        this.roll("");
        this.setOpaque(false);

    }

    public void setup() {

        this.board = new int[ROWS * COLS];
        this.boxes = new Tile[board.length];
        this.result = new Stack<>();
        this.setLayout(new GridLayout(ROWS,COLS,10,10));

        for (int j = 0; j < board.length; j++) {
            boxes[j] = new Tile();
            this.add(this.boxes[j]);
        }

    }
    public void fetchImages(Image[] img) {

        this.tiles = new Image[img.length];
        for (int i = 0; i < img.length; i++) {
            this.tiles[i] = img[i];
        }

    }

    public int[] roll(String texture) {

        for (int i = 0; i < board.length; i++) {
                board[i] = (int) (Math.random() * tiles.length);

                this.boxes[i].setImage(tiles[board[i]]);
                //if (i % COLS == 4) System.out.println();
        }
        //System.out.println();

        if (texture.equals("Billiards")) {
            //System.out.println(board.length);
            for (int j = 0; j < board.length; j++) {
                if (board[j] > 7) board[j] = 2;
                else board[j] = 1;
                System.out.print(board[j] + " ");
                if (j % 5 == 4) System.out.println();
            }
        }
        updateMatches();

        this.repaint();

        return slotLogic();
    }

    private int[] slotLogic() {
        win = 0;
        int res;
        int[] val;
        stackSize = 0;

        for (int i = 0; i < matches.length; i++) {
            res = checkEqual(matches[i]);
            if (res != -1) {
                result.push(i);
                stackSize++;
                win += res;
            }
        }

        // System.out.println("Lines: " + stackSize);
        val = new int[stackSize];
        for(int i = 0; i < stackSize; i++) {
            val[i] = result.pop();
        }
        return val;
    }

    private int checkEqual(int[] chk) {
        int c = 0;
        int res = 0;
        for (int i : chk) {
            if (i != 0) {
                if (c == 0) c = i;
                if (i != c) return -1;
                res++;
            }
        }
        return res;
    }

    public void updateMatches() {
        this.matches = new int[][] {
                { board[0],  board[1],  board[2],  board[3],  board[4]  },  // 0
                { board[5],  board[6],  board[7],  board[8],  board[9]  },  // 1
                { board[10], board[11], board[12], board[13], board[14] },  // 2
                { board[0],  board[4],  board[6],  board[8],  board[12] },  // 3
                { board[2],  board[6],  board[8],  board[10], board[14] },  // 4
                { board[5],  board[7],  board[9],  board[11], board[13] },  // 5
                { board[0],  board[2],  board[4],  board[6],  board[8]  },  // 6
                { board[1],  board[3],  board[5],  board[7],  board[9]  },  // 7
                { board[6],  board[8],  board[10], board[12], board[14] },  // 8
                { board[3],  board[5],  board[7],  board[9],  board[11] },  // 9
                { board[1],  board[5],  board[7],  board[9],  board[13] },  // 10
                { board[4],  board[6],  board[7],  board[8],  board[10] },  // 11
                { board[0],  board[6],  board[7],  board[8],  board[14] },  // 12
                { board[3],  board[4],  board[7],  board[10], board[11] },  // 13
                { board[0],  board[1],  board[7],  board[13], board[14] },  // 14
                { board[4],  board[8],  board[10], board[11], board[12] },  // 15
                { board[0],  board[6],  board[12], board[13], board[14] },  // 16
                { board[0],  board[1],  board[2],  board[8],  board[14] },  // 17
                { board[2],  board[3],  board[4],  board[6],  board[10] },  // 18
                { board[5],  board[9],  board[11], board[12], board[13] },  // 19
                { board[0],  board[4],  board[6],  board[7],  board[8]  },  // 20
                { board[1],  board[2],  board[3],  board[5],  board[9]  },  // 21
                { board[6],  board[7],  board[8],  board[10], board[14] },  // 22
                { board[5],  board[6],  board[8],  board[9],  board[12] },  // 23
                { board[0],  board[1],  board[3],  board[4],  board[7]  },  // 24
                { board[2],  board[5],  board[6],  board[8],  board[9]  },  // 25
                { board[7],  board[10], board[11], board[13], board[14] },  // 26
                { board[5],  board[7],  board[8],  board[9],  board[11] },  // 27
                { board[5],  board[6],  board[7],  board[9],  board[13] },  // 28
                { board[0],  board[2],  board[3],  board[4],  board[6]  },  // 29
                { board[0],  board[1],  board[2],  board[4],  board[8]  },  // 30
                { board[1],  board[5],  board[7],  board[8],  board[9]  },  // 31
                { board[3],  board[5],  board[6],  board[7],  board[9]  },  // 32
                { board[6],  board[10], board[12], board[13], board[14] },  // 33
                { board[8],  board[10], board[11], board[12], board[14] }   // 34
        };
    }


    private class Tile extends JPanel {

        protected Image panelImg;

        public Tile() {
            super();
            this.setOpaque(false);
        }

        public void setImage(Image i) {
            this.panelImg = i;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(panelImg, 0, 0, this.getWidth(), this.getHeight(), this);
        }

    }

}