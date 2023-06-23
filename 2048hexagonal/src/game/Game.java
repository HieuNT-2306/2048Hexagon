package game;

import javax.swing.JPanel;

import gui.GuiScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    public static int WIDTH = 500;
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
        if (GameBoard.COLS > 4 &&  GameBoard.COLS < 7) {
            WIDTH += (GameBoard.COLS - 4)*200;
        }
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(WIDTH , HEIGHT));
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        //board = new GameBoard(WIDTH/2 - GameBoard.BOARD_WIDTH/2, HEIGHT - GameBoard.BOARD_HEIGHT - 10);
        screen = GuiScreen.getInstance();
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
