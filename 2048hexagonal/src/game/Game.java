package game;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 800;
    public static final Font mainfont = new Font("Bebas Neue Regular", Font.PLAIN, 28);
    private Thread game;
    private boolean running;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private long startTime;
    private long elapsed;
    private boolean set;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
    }

    private void update() {

    }

    private void render() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.dispose();
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    public void run() {
        int fps = 0, update = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 10000000000.0 / 60;
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

    }

    public void keyReleased(KeyEvent a) {

    }

    public void keyTyped(KeyEvent a) {

    }
}
