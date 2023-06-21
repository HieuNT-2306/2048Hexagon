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

    public boolean move(int row, int col, int horizontalDir, int verticalDir, Direction dir) {
        boolean canMove = false;
        Tile current = board[row][col];
        if (current == null) return false;
        boolean move = true;
        int newCol = col;
        int newRow = row;
        while (move) {
            newCol += horizontalDir;
            newRow += verticalDir;
            if(checkOutOfBound(dir, newRow, newCol)) break;
            if(board[newRow][newCol] == null) {
                board[newRow][newCol] = current;
                board[newRow - verticalDir][newCol - horizontalDir] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
            }
            else if(board[newRow][newCol].getValue() == current.getValue() 
            && board[newRow][newCol].canCombine()) {
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue()*2);
                canMove = true;
                board[newRow - verticalDir][newCol - horizontalDir] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                //board[newRow][newCol].setCombineAnimation(true);
                //them diem
            }
            else {
                move = false;
            }
        }

        return canMove;
    }
    private boolean checkOutOfBound(Direction dir, int newRow, int newCol) {
        if(dir == Direction.LEFT) {
            return newCol < 0;
        }
        else if(dir == Direction.RIGHT) {
            return newCol > COLS - 1;
        }
        else if(dir == Direction.UP) {
            return newRow < 0;
        }
        else if (dir == Direction.DOWN) {
            return newRow > ROWS -1;
        }
        return false;
    }

    public void moveTiles(Direction dir) {
        boolean canMove = false;
        int horizontalDir = 0;
        int verticalDir = 0;
        //sang trai
        if (dir == Direction.LEFT) {
            horizontalDir = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    }
                    else move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        }
        //sang phai
        else if (dir == Direction.RIGHT) {
            horizontalDir = 1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLS - 1 ; col >= 0; col--) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    }
                    else move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        }
        //len tren
        else if (dir == Direction.UP) {
            verticalDir = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    }
                    else move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        }
        //xuong duoi
        else if (dir == Direction.DOWN) {
            verticalDir = 1;
            for (int row = ROWS -1; row >= 0; row--) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    }
                    else move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        }
        else {
            System.out.println(dir + " is Not a valid move");
        }
        //update 
        for (int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.setCanCombine(true);
            }
        }

        //spawn
        if (canMove) {
            spawmRandom();
            //kiem tra chet
        }
    }

    public void checkKeys() {
        if (Keyboard.typed(KeyEvent.VK_LEFT)) {
            moveTiles(Direction.LEFT);
            if (!hasStarted)
                hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_RIGHT)) {
            // di chuyển ô sang phải
            moveTiles(Direction.RIGHT);
            if (!hasStarted)
                hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_UP)) {
            // di chuyển ô lên
            moveTiles(Direction.UP);
            if (!hasStarted)
                hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_DOWN)) {
            // di chuyển ô xuống
            moveTiles(Direction.DOWN);
            if (!hasStarted)
                hasStarted = true;
        }
    }
}