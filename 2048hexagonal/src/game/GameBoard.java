package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class GameBoard {
    public static int ROWS = Setting.SIZE;
    public static int COLS = Setting.SIZE;
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

    private int score = 0;
    private int highScore = 0;
    private Font scoreFont;
    // sound
    private long elapsedMS;
    private long fastestMS;
    private long startTime;
    private String formattedTime = "00:00:000";

    // saving
    private String saveDataPath;
    private String fileName = "SaveData";

    public static int SPACING = 10; // khoảng cách các ô trong bảng
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    public GameBoard(int X, int Y) {
        try {
            saveDataPath = GameBoard.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        scoreFont = Game.mainfont.deriveFont(24f);
        this.X = X;
        this.Y = Y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        startTime = System.nanoTime();
        loadHighScore();
        createBoardImage();
        start();
    }

    private void createSaveData() {
        try {
            File file = new File(saveDataPath, fileName);

            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + 0);
            writer.newLine();
            writer.write("" + Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatTime(long milisec) {
        String formattedTime;
        String hourFormat = "";
        int hours = (int) (milisec / 3600000);
        if (hours >= 1) {
            milisec -= hours * 3600000;
            if (hours < 10) {
                hourFormat = "0" + hours;
            } else {
                hourFormat = "" + hours;
            }
            hourFormat += ":";
        }; 

        String minuteFormat = "";
        int minutes = (int) (milisec / 60000);
        if (minutes >= 1) {
            milisec -= minutes * 60000;
            if (minutes < 10) {
                minuteFormat = "0" + minutes;
            } else {
                minuteFormat = "" + minutes;
            }
        }
        else {
            minuteFormat = "00";
        }

        String secondFormat = "";
        int seconds = (int) (milisec / 1000);
        if (seconds >= 1) {
            milisec -= seconds * 60000;
            if (seconds < 10) {
                secondFormat = "0" + seconds;
            } else {
                secondFormat = "" + seconds;
            }
        }
        else {
            secondFormat = "00";
        }

        String milliFormat = "";
        if (milisec > 99) {
            milliFormat = "" + milisec;
        }
        else if (milisec > 9) {
            milliFormat = "0" + milisec;
        }
        else {
            milliFormat = "00" + milisec;
        }

        formattedTime = hourFormat + minuteFormat + ":" + secondFormat;
        return formattedTime;
    }

    private void loadHighScore() {
        try {
            File f = new File(saveDataPath, fileName);
            if (!f.isFile()) {
                createSaveData();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            highScore = Integer.parseInt(reader.readLine());
            fastestMS = Long.parseLong(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHighScore() {
        FileWriter output = null;
        try {
            File f = new File(saveDataPath, fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write("" + highScore);
            writer.newLine();

            if (elapsedMS <= fastestMS && win) {
                writer.write("" + elapsedMS);
            } else {
                writer.write("" + fastestMS);
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBoardImage() {
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        Color BoardColor = new Color(0x444444 + Setting.ShiftColor_board);
        g.setColor(BoardColor);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        Color wallColor = new Color(0xbcbcbc + Setting.ShiftColor_board);
        g.setColor(wallColor);

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
                if (current == null)
                    continue;
                current.render(gr);
            }
        }

        g.drawImage(finalBoard, X, Y, null);
        gr.dispose();

        g.setColor(Color.LIGHT_GRAY);
        g.setFont(scoreFont);
        g.drawString("" + score, 30, 40);
        g.setColor(Color.red);
        g.drawString("Best: " + highScore, Game.WIDTH - DrawUtils.getMessageWidth("Best: " + highScore, scoreFont, g) - 20, 40);
        g.setColor(Color.black);
        g.drawString("Time: " + formattedTime, 30, 90);
        g.setColor(Color.red);
        g.drawString("Fastest: " + formatTime(1000000), Game.WIDTH - DrawUtils.getMessageWidth("Fastest" + formatTime(fastestMS), scoreFont, g) - 30, 90);

    }

    public void update() {
        if (!win && !lose) {
            if (hasStarted) {
                elapsedMS = (System.nanoTime() - startTime) / 1000000;
                formattedTime = formatTime(elapsedMS);
            }
            else {
                startTime = System.nanoTime();
            }
        }
        checkKeys();

        if (score > highScore) {
            highScore = score;
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < ROWS; col++) {
                Tile current = board[row][col];
                if (current == null)
                    continue;
                current.update();
                resetPosition(current, row, col);
                if (current.getValue() == WinValue) {
                    win = true;
                }
            }
        }
    }

    private void resetPosition(Tile current, int row, int col) {
        if (current == null)
            return;
        int x = getTileX(col);
        int y = getTileY(row);
        int distX = current.getX() - x;
        int distY = current.getY() - y;

        if (Math.abs(distX) < Tile.SLIDE_SPEED) {
            current.setX(current.getX() - distX);
        }

        if (Math.abs(distY) < Tile.SLIDE_SPEED) {
            current.setY(current.getY() - distY);
        }

        if (distX < 0) {
            current.setX(current.getX() + Tile.SLIDE_SPEED);
        }
        if (distY < 0) {
            current.setY(current.getY() + Tile.SLIDE_SPEED);
        }
        if (distX > 0) {
            current.setX(current.getX() - Tile.SLIDE_SPEED);
        }
        if (distY > 0) {
            current.setY(current.getY() - Tile.SLIDE_SPEED);
        }
    }

    public boolean move(int row, int col, int horizontalDir, int verticalDir, Direction dir) {
        boolean canMove = false;
        Tile current = board[row][col];
        if (current == null)
            return false;
        boolean move = true;
        int newCol = col;
        int newRow = row;
        while (move) {
            newCol += horizontalDir;
            newRow += verticalDir;
            if (checkOutOfBound(dir, newRow, newCol))
                break;
            if (board[newRow][newCol] == null) {
                board[newRow][newCol] = current;
                board[newRow - verticalDir][newCol - horizontalDir] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                canMove = true;
            } else if (board[newRow][newCol].getValue() == current.getValue()
                    && board[newRow][newCol].canCombine()) {
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
                canMove = true;
                board[newRow - verticalDir][newCol - horizontalDir] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                board[newRow][newCol].setCombineAnimation(true);
                // them diem

                score += board[newRow][newCol].getValue();
            } else {
                move = false;
            }
        }

        return canMove;
    }

    private boolean checkOutOfBound(Direction dir, int newRow, int newCol) {
        if (dir == Direction.LEFT) {
            return newCol < 0;
        } else if (dir == Direction.RIGHT) {
            return newCol > COLS - 1;
        } else if (dir == Direction.UP) {
            return newRow < 0;
        } else if (dir == Direction.DOWN) {
            return newRow > ROWS - 1;
        }
        return false;
    }

    public void moveTiles(Direction dir) {
        boolean canMove = false;
        int horizontalDir = 0;
        int verticalDir = 0;
        // sang trai
        if (dir == Direction.LEFT) {
            horizontalDir = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    } else
                        move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        }
        // sang phai
        else if (dir == Direction.RIGHT) {
            horizontalDir = 1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLS - 1; col >= 0; col--) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    } else
                        move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        }
        // len tren
        else if (dir == Direction.UP) {
            verticalDir = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    } else
                        move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        }
        // xuong duoi
        else if (dir == Direction.DOWN) {
            verticalDir = 1;
            for (int row = ROWS - 1; row >= 0; row--) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDir, verticalDir, dir);
                    } else
                        move(row, col, horizontalDir, verticalDir, dir);
                }
            }
        } else {
            System.out.println(dir + " is Not a valid move");
        }
        // update
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null)
                    continue;
                current.setCanCombine(true);
            }
        }

        // spawn
        if (canMove) {
            spawmRandom();
            checkDeath();
        }
    }

    private void checkDeath() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == null)
                    return;
                if (checkSurroundingTiles(row, col, board[row][col]))
                    return;
            }
        }
        lose = true;
        if (score >= highScore) highScore = score;
        setHighScore();
    }

    private boolean checkSurroundingTiles(int row, int col, Tile current) {
        if (row > 0) {
            Tile check = board[row - 1][col];
            if (check == null)
                return true;
            if (current.getValue() == check.getValue())
                return true;
        }
        if (row < ROWS - 1) {
            Tile check = board[row + 1][col];
            if (check == null)
                return true;
            if (current.getValue() == check.getValue())
                return true;
        }
        if (col > 0) {
            Tile check = board[row][col - 1];
            if (check == null)
                return true;
            if (current.getValue() == check.getValue())
                return true;
        }
        if (col < COLS - 1) {
            Tile check = board[row][col + 1];
            if (check == null)
                return true;
            if (current.getValue() == check.getValue())
                return true;
        }
        return false;
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