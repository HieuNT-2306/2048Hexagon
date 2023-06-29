package game;

import javax.swing.JPanel;

import gui.ConfigManager;
import gui.GuiScreen;
import gui.LeaderboardsPanel;
import gui.MainMenuPanel;
import gui.PlayPanel;
import gui.SettingsPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    public static int WIDTH = 600;
    public static int HEIGHT = 630;
    public static final Font mainfont = new Font("Bebas Neue Regular", Font.PLAIN, 28);
    private Thread game;
    private boolean running;
    private BufferedImage image;
    private long startTime;
    private long elapsed;
    private boolean set;
    private GuiScreen screen;


    public Game() {
        setFocusable(true);
        if (Setting.SIZE > 4 &&  Setting.SIZE < 7) {
            WIDTH += (Setting.SIZE- 4)*200;
        }
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(WIDTH , HEIGHT));
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        //board = new GameBoard(WIDTH/2 - GameBoard.BOARD_WIDTH/2, HEIGHT - GameBoard.BOARD_HEIGHT - 10);
        screen = GuiScreen.getInstance();
        screen.add("MENU", new MainMenuPanel());
        screen.add("PLAY", new PlayPanel(false));
        screen.add("NEW GAME", new PlayPanel(true));
        screen.add("LEADERBOARDS", new LeaderboardsPanel());
        screen.add("SETTINGS", new SettingsPanel());
        screen.setCurrentPanel("MENU");
        ConfigManager config = new ConfigManager();
        ConfigManager.saveSetting("SIZE", "4");
        ConfigManager.saveSetting("mainColor", "0xffffff");
        ConfigManager.saveSetting("ShiftColor_tile", "0x000000");
        ConfigManager.saveSetting("ShiftColor_board", "0x000000");
        ConfigManager.saveSetting("FPS", "60");
    }

    private void update() {
        screen.update();
        Keyboard.update();
    }

    private void render() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        Color mainColor = new Color(Setting.mainColor);
        g.setColor(mainColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        screen.render(g);
        g.dispose();
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    public void run() {
        int fps = 0, update = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / Setting.FPS;
        double then = System.nanoTime();
        double unprocessed = 0;
        // update queue
        while (running) {
            double now = System.nanoTime();
            boolean shouldRender = false;
            unprocessed += (now - then)/nsPerUpdate;
            then = now;
            while (unprocessed >= 1) {
                update++;
                update();
                unprocessed--;
                shouldRender = true;
            }
            // render
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // FPS Timer
            if (System.currentTimeMillis() - fpsTimer > 1000) {
                System.out.printf("%d fps, %d updates", fps, update);
                System.out.println();
                fps = 0;
                update = 0;
                fpsTimer += 1000;
            }
        }
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        game = new Thread(this, "2048 Game");
        game.start();
    }
    
    public synchronized void stop() {
        if (!running) return;
        running = false;
        System.exit(0);
    }
    public void keyPressed(KeyEvent a) {
        Keyboard.keyPressed(a);
    }

    public void keyReleased(KeyEvent a) {
        Keyboard.keyReleased(a);
    }

    public void keyTyped(KeyEvent a) {

    }
    public void mousePressed(MouseEvent a) {
        screen.mousePressed(a);
    }
    public void mouseEntered(MouseEvent a) {

    }
    public void mouseReleased(MouseEvent a) {
        screen.mouseReleased(a);
    }
    public void mouseDragged(MouseEvent a) {
        screen.mouseDragged(a);
    }
    public void mouseClicked(MouseEvent a) {

    }
    public void mouseMoved(MouseEvent a) {
        screen.mouseMoved(a);
    }
    public void mouseExited(MouseEvent a) {

    }
}
