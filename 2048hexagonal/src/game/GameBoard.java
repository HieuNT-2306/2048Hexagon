package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameBoard {
    public static int ROWS = 6;
    public static int COLS = 6;
    public static int WinValue = 2048;
    private final int startTiles = 2; // số ô vuông ban đầu
    private Tile[][] board;
    private boolean lose;
    private boolean win;
    private BufferedImage gameBoard;
    private BufferedImage finalBoard;
    private int X;
    private int Y;
    private boolean hasStarted;

    public static int SPACING = 10; // khoảng cách các ô trong bảng
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    public GameBoard(int X, int Y) {
        this.X = X;
        this.Y = Y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        createBoardImage();
        start();
    }

    public void createBoardImage() {
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.LIGHT_GRAY);

        // vẽ các ô vuông có góc bo tròn
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int x = SPACING + SPACING * j + Tile.WIDTH * j;
                int y = SPACING + SPACING * i + Tile.HEIGHT * i;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
            }
        } 
    }

    private void start() {
        for (int i = 0; i < startTiles; i++) {
            spawmRandom();
        }
    }

    private void spawmRandom() {
        Random random = new Random();
        boolean notValid = true;
        while (notValid) {
            int location = random.nextInt(ROWS * COLS);
            int row = location / ROWS;
            int col = location / COLS;
            Tile current = board[row][col];
            if (current == null) {
                int value = random.nextInt(10) < 5 ? 2 : 4;
                Tile tile = new Tile(value, getTileX(col), getTileY(row));
                board[row][col] = tile;
                notValid = false;
            }
        }
    }

    public int getTileX(int col) {
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row) {
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }

    public void render(Graphics2D g) {
        Graphics2D gr = (Graphics2D) finalBoard.getGraphics();
        gr.drawImage(gameBoard, 0, 0, null);
        // vẽ tile
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.render(gr);
            }
        }

        g.drawImage(finalBoard, X, Y, null);
        gr.dispose();
    }

    public void update() {
        checkKeys();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < ROWS; col++) {
                Tile current = board[row][col];
                if (current == null)
                    continue;
                current.update();
                // resetposition
                if (current.getValue() == WinValue) {
                    win = true;
                }
            }
        }
    }

    public void checkKeys() {
        if (Keyboard.typed(KeyEvent.VK_LEFT)) {
            // di chuyển ô sang trái
            if (!hasStarted)
                hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_RIGHT)) {
            // di chuyển ô sang phải
            if (!hasStarted)
                hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_UP)) {
            // di chuyển ô lên
            if (!hasStarted)
                hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_DOWN)) {
            // di chuyển ô xuống
            if (!hasStarted)
                hasStarted = true;
        }
    }
}