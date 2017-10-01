
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.imageio.*;
import javax.swing.*;

/**
 * Created by nhasdarjian on 5/26/17.
 */

public class Main {

    public static final int MAX_WIDTH = 1920;
    public static final int BUFFER_SPACE = 90;                              // top and bottom spaces
    protected static final int ROWS = 3;
    protected static final int COLS = 5;
    protected static final float WINNING_CONSTANT = 0.25f;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Main());
    }

    public JFrame window;                       // the entire game window
    public JPanel[] elements;                   // the different sections of the game window (top, middle, bottom)
    public JPanel gameBoard;                   // the "view" of the game
    //public JPanel squeezeL, squeezeR;

    public Image bg;                            // the background image for the midsection
    public Image[] tiles, overlays;             // tile icons and overlays for pay lines

    public JButton spin;                        // when pressed, spins rollers
    public JComboBox textures, modes;            // selection chooses game type
    public JTextField currentFunds, winnings;
    public JButton betUp, betDown;
    public JTextField betAmount;
    public JPanel money, settings, bets;
    public String[] folders, gameModes;         // list of themes/game types

    public CardGame c;
    public SlotGame s;
    public JPanel o;

    public int[] result;
    public float funds, bet, won;
    public Gambling g;

    public Main() {

        g = new Gambling();
        funds = (float) g.getFunds();
        initializeWindow();
        initializeHeader();
        updateImageFiles(textures.getSelectedItem().toString()); // requires header to be initialized

        initializeGameBoard();
        this.result = new int[0];
        initializeFooter();
        addListeners();

        window.add(elements[0], BorderLayout.PAGE_START);
        window.add(elements[1], BorderLayout.CENTER);
        window.add(elements[2], BorderLayout.PAGE_END);

        /*
        squeezeL = new JPanel();
        squeezeL.setBackground(Color.DARK_GRAY);
        squeezeR = new JPanel();
        squeezeR.setBackground(Color.DARK_GRAY);
        this.window.add(squeezeL,BorderLayout.LINE_START);
        this.window.add(squeezeR,BorderLayout.LINE_END);
        */

        window.setVisible(true);

    }

    public void initializeWindow() {

        this.window = new JFrame("CS 480 Blockchain Slot Machine");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLayout(new BorderLayout());
        this.window.setMinimumSize(new Dimension(800, 600));

        elements = new JPanel[3];   // containers for each section of the window

    }

    public void initializeHeader() {

        elements[0] = new JPanel(); // top of window
        elements[0].setBackground(Color.DARK_GRAY);
        elements[0].setPreferredSize(new Dimension(MAX_WIDTH, BUFFER_SPACE));
        elements[0].setLayout(new BorderLayout());

        money = new JPanel();
        money.setLayout(new BoxLayout(money, BoxLayout.PAGE_AXIS));
        money.setOpaque(false);
        currentFunds = new JTextField("$" + String.format("%.2f", funds));
        currentFunds.setFont(new Font(currentFunds.getFont().getName(), Font.BOLD, 30));
        currentFunds.setColumns(6);
        currentFunds.setHorizontalAlignment(JTextField.RIGHT);
        currentFunds.setOpaque(false);
        currentFunds.setForeground(Color.WHITE);
        currentFunds.setEditable(false);

        winnings = new JTextField("$0.00");
        winnings.setHorizontalAlignment(JTextField.RIGHT);
        winnings.setOpaque(false);
        winnings.setForeground(Color.GREEN);
        winnings.setEditable(false);

        money.add(currentFunds);
        money.add(winnings);

        settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.PAGE_AXIS));
        settings.setOpaque(false);

        folders = new String[3];
        folders[0] = "Emoji";
        folders[1] = "Billiards";
        folders[2] = "Rick and Morty";
        textures = new JComboBox(folders);



        gameModes = new String[2];
        gameModes[0] = "Slots";
        gameModes[1] = "Cards";
        modes = new JComboBox(gameModes);

        settings.add(Box.createVerticalGlue());
        settings.add(this.textures);
        settings.add(Box.createVerticalGlue());
        settings.add(this.modes);
        settings.add(Box.createVerticalGlue());

        elements[0].add(settings, BorderLayout.LINE_END);
        elements[0].add(money, BorderLayout.LINE_START);


    }

    public void initializeGameBoard() {

        gameBoard = new JPanel();
        gameBoard.setPreferredSize(new Dimension(854, 480));
        gameBoard.setMinimumSize(gameBoard.getPreferredSize());
        gameBoard.setLayout(new CardLayout());
        gameBoard.setOpaque(false);

        elements[1] = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, window.getWidth(), window.getHeight(),this);
            }
        };

        s = new SlotGame(this.tiles);
        c = new CardGame();
        o = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image[] img = selectOverlays(overlays);
                for (Image i : img) {
                    g.drawImage(i,0,0,gameBoard.getWidth(),gameBoard.getHeight(),this);
                    this.repaint();
                }
            }
        };
        o.setOpaque(false);

        gameBoard.add(s);
        gameBoard.add(c);
        gameBoard.add(o);
        c.setVisible(false);
        s.setVisible(true); // slots is initial state
        o.setVisible(true);
        gameBoard.setComponentZOrder(o,0);
        gameBoard.setComponentZOrder(s, 1);
        gameBoard.setComponentZOrder(c, 1);

        elements[1].add(gameBoard);

    }

    public void initializeFooter() {

        elements[2] = new JPanel();
        elements[2].setPreferredSize(new Dimension(MAX_WIDTH, BUFFER_SPACE));
        elements[2].setBackground(Color.DARK_GRAY);
        //elements[2].setLayout(new BoxLayout(elements[2], BoxLayout.LINE_AXIS));

        bets = new JPanel();
        bets.setLayout(new BoxLayout(bets, BoxLayout.LINE_AXIS));
        bets.setOpaque(false);

        bet = 2.5f;
        betDown = new JButton("LESS");
        betAmount = new JTextField();
        betAmount.setEditable(false);
        betAmount.setText(String.format("$" + "%.2f", bet));
        betAmount.setColumns(10);
        betUp = new JButton("MORE");

        bets.add(betDown);
        bets.add(betAmount);
        bets.add(betUp);

        spin = new JButton("SPIN");
        spin.setPreferredSize(new Dimension(100, BUFFER_SPACE - 10));

        elements[2].add(Box.createHorizontalGlue());
        elements[2].add(bets);
        elements[2].add(Box.createHorizontalGlue());
        elements[2].add(spin);
        elements[2].add(Box.createHorizontalGlue());

    }

    public void addListeners() {

        betDown.addActionListener((ActionEvent) -> {
            if (bet > 0.5f) bet -= 0.50;
            betAmount.setText(String.format("$" + "%.2f", bet));
        });
        betUp.addActionListener((ActionEvent) -> {
            bet += 0.50;
            betAmount.setText(String.format("$" + "%.2f", bet));
        });
        spin.addActionListener((ActionEvent) -> this.roll());

        textures.addActionListener((ActionEvent) -> updateGameWindow(true));
        modes.addActionListener((ActionEvent) -> updateGameMode(modes.getSelectedItem().toString()));

        window.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                updateGameWindow(false);
            }
        });

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateGameWindow(false);
            }
        });

        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) spin.doClick();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
            }
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

    }

    public void updateGameMode(String s) { // model
        switch (s) {

            case "Slots":
                this.textures.setEnabled(true);
                updateGameWindow(true);
                this.s.setVisible(true);
                this.c.setVisible(false);
                this.o.setVisible(false);
                break;

            case "Cards":
                this.textures.setEnabled(false);
                updateGameWindow(true);
                this.c.setVisible(true);
                this.s.setVisible(false);
                this.o.setVisible(false);
                break;

        }

    }

    public void updateGameWindow(boolean settings) { // view

        int h = this.window.getContentPane().getHeight() - (BUFFER_SPACE * 2) - 10;
        int w = (16 * h) / 9;
        if (w > this.window.getWidth()) this.window.setSize(new Dimension(w, h + (BUFFER_SPACE * 2) + 20));

        switch (modes.getSelectedItem().toString()) {

            case "Slots":
                gameBoard.setPreferredSize(new Dimension(w, h));
                if (settings) {
                    updateImageFiles(textures.getSelectedItem().toString());
                    s.fetchImages(tiles);
                    s.roll(textures.getSelectedItem().toString());
                    o.setVisible(false);
                }
                break;

            case "Cards":
                gameBoard.setPreferredSize(new Dimension(4 * w/5, h));
                if (settings) {
                    updateImageFiles("Cards");
                    c.fetchImages(tiles);
                    c.roll("");
                    o.setVisible(false);
                }
                break;

        }

        gameBoard.revalidate();

    }

    public Image[] selectOverlays(Image[] ovls) {
        Image[] select = new Image[result.length];
        for (int i = 0; i < result.length; i++) {
            select[i] = ovls[result[i]];
        }
        return select;
    }


    public void updateImageFiles(String folder) { // model
        Scanner s;
        int count;
        try {

            s = new Scanner(getClass().getResourceAsStream("/assets/" + folder + "/textures.meta"));
            count = s.nextInt();
            System.out.println("Loading " + count + " images...");

            this.tiles = new Image[count];
            this.bg = ImageIO.read(getClass().getResource("/assets/" + folder + "/bg.jpg"));

            count = s.nextInt();
            System.out.println("Loading " + count + " overlays...");
            this.overlays = new Image[count];

        } catch (Exception e) { System.out.println("Error loading theme data"); }

        for (int i = 0; i < tiles.length; i++) {
            try {
                tiles[i] = ImageIO.read(getClass().getResource("/assets/" + folder + "/tiles/" + i + ".png"));
            } catch (Exception e) { System.out.println("Failed to load " + i + ".png (tile)"); }
            window.repaint();
        }

        for (int j = 0; j < overlays.length; j++) {
            try {
                overlays[j] = ImageIO.read(getClass().getResource("/assets/" + folder + "/overlays/" + String.format("%03d", j) + ".png"));
            } catch (Exception e) { System.out.println("Failed to load " + String.format("%03d", j) + ".png (overlay)"); }
        }


    }

    public void roll() { // called on button press
        funds = (float) g.getFunds();
        if (funds < bet) {
            JOptionPane.showMessageDialog(window, "Insufficient funds.\nPlease add additional funds via Metamask.");
            return;
        }
        float starting = funds;
        g.removeFunds(bet + "");
        funds -= bet;

        switch (modes.getSelectedItem().toString()) {

            case "Slots":
                this.result = s.roll(textures.getSelectedItem().toString()); // roll the SlotGame
                this.won = s.win;
                updateGameWindow(false);
                break;

            case "Cards":
                this.result = c.roll(""); // roll the CardGame
                this.won = c.win;
                updateGameWindow(false);
                break;

        }
        g.addFunds(won + "");
        funds += won;
        String ch = "";
        if(starting > funds){
            ch = "-";
            starting = starting - funds;
            winnings.setForeground(Color.RED);
        }
        else{
            ch = "+";
            starting = funds - starting;
            winnings.setForeground(Color.GREEN);
        }
        won *= bet * WINNING_CONSTANT;
        
        if (textures.getSelectedItem().toString().equals("Billiards")) won *= WINNING_CONSTANT;
        winnings.setText(ch + " $" + String.format("%.2f",starting ));
        currentFunds.setText("$" + String.format("%.2f", funds));
        o.setVisible(true);

    }



}
