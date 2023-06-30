package hust.soict.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import hust.soict.gui.ConfirmSettingPanel;
import hust.soict.gui.GuiScreen;
import hust.soict.gui.LeaderboardsPanel;
import hust.soict.gui.MainMenuPanel;
import hust.soict.gui.PlayPanel;
import hust.soict.gui.SettingsPanel;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = GameBoard.BOARD_WIDTH + 140;
	public static final int HEIGHT = 660;
	public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);
	private Thread game;
	private boolean running;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	private GuiScreen screen;
	
	public Game() {
		
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		screen = GuiScreen.getInstance();
		screen.add("Menu", new MainMenuPanel());
		screen.add("Play", new PlayPanel(false));
		screen.add("Leaderboards", new LeaderboardsPanel());
		screen.add("NewGames", new PlayPanel(true));
		screen.add("Settings", new SettingsPanel());
		screen.add("Confirm settings", new ConfirmSettingPanel());
		screen.setCurrentPanel("Menu");

		// ConfigManager.saveSetting("SIZE", "4");
		// ConfigManager.saveSetting("FPS", "60");
		// ConfigManager.saveSetting("mainColor", "0xffffff");
		// ConfigManager.saveSetting("ShiftColor_tile", "0x000000");
		// ConfigManager.saveSetting("ShiftColor_board", "0x000000");
	}

	private void update() {
		screen.update();
		Keys.update();
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

	@Override
	public void run() {
		int fps = 0, updates = 0;
		long fpsTimer = System.currentTimeMillis();
		double nsPerUpdate = 1000000000.0 / Setting.FPS;

		// last update time in nanoseconds
		double then = System.nanoTime();
		double unprocessed = 0;

		while (running) {

			boolean shouldRender = false;

			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;

			// Update queue
			while (unprocessed >= 1) {

				// update
				updates++;
				update();
				unprocessed--;
				shouldRender = true;
			}

			// Render
			if (shouldRender) {
				fps++;
				render();
				shouldRender = false;
			}
			else {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// FPS timer
			if (System.currentTimeMillis() - fpsTimer > 1000) {
				System.out.printf("%d fps %d updates", fps, updates);
				System.out.println("");
				fps = 0;
				updates = 0;
				fpsTimer += 1000;
			}
		}
	}

	public synchronized void start() {
		if (running) return;
		running = true;
		game = new Thread(this, "game");
		game.start();
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		System.exit(0);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keys.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keyReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		screen.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);
	}
}
