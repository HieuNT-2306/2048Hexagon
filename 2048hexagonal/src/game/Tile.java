package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile {
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;
	public static final int SLIDE_SPEED = 30;
	public static final int ARC_WIDTH = 15;
	public static final int ARC_HEIGHT =15;

    private int value;

	private BufferedImage tileImage;
    private Color background_color;
    private Color text_color;
    private int x, y;
    private Font tileFont;
	private Point slideTo;
	private boolean canCombine;

	public void setValue(int value) {
		this.value = value;
	}

	public boolean canCombine() {
		return canCombine;
	}

	public void setCanCombine(boolean canCombine) {
		this.canCombine = canCombine;
	}

	public Point getSlideTo() {
		return slideTo;
	}

	public void setSlideTo(Point slideTo) {
		this.slideTo = slideTo;
	}

    public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
		this.slideTo
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        drawImage();
    }

    private void drawImage() {
		Graphics2D g = (Graphics2D) tileImage.getGraphics();
		if (value == 2) {
			background_color = new Color(0xe9e9e9);
			text_color = new Color(0x000000);
		}
		else if (value == 4) {
			background_color = new Color(0xe6daab);
			text_color = new Color(0x000000);
		}
		else if (value == 8) {
			background_color = new Color(0xf79d3d);
			text_color = new Color(0xffffff);
		}
		else if (value == 16) {
			background_color = new Color(0xf28007);
			text_color = new Color(0xffffff);
		}
		else if (value == 32) {
			background_color = new Color(0xf55e3b);
			text_color = new Color(0xffffff);
		}
		else if (value == 64) {
			background_color = new Color(0xff0000);
			text_color = new Color(0xffffff);
		}
		else if (value == 128) {
			background_color = new Color(0xe9de84);
			text_color = new Color(0xffffff);
		}
		else if (value == 256) {
			background_color = new Color(0xf6e873);
			text_color = new Color(0xffffff);
		}
		else if (value == 512) {
			background_color = new Color(0xf5e455);
			text_color = new Color(0xffffff);
		}
		else if (value == 1024) {
			background_color = new Color(0xf7e12c);
			text_color = new Color(0xffffff);
		}
		else if (value == 2048) {
			background_color = new Color(0xffe400);
			text_color = new Color(0xffffff);
		}
		else if(value == 0){
			background_color = Color.lightGray;
			text_color = Color.black;
		}
		else{
			background_color = new Color(0x000000);
			text_color = new Color(0xffffff);
		}
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(background_color);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);

		g.setColor(text_color);

		if (value <= 64) {
			tileFont = Game.mainfont.deriveFont(36f);
			g.setFont(tileFont);
		}
		else {
			tileFont = Game.mainfont;
			g.setFont(tileFont);
		}

		int drawX = WIDTH / 2 - DrawUtils.getMessageWidth("" + value, tileFont, g) / 2;
		int drawY = HEIGHT / 2 + DrawUtils.getMessageHeight("" + value, tileFont, g) / 2;
		g.drawString("" + value, drawX, drawY);
		g.dispose();
	}
    
    public void update() {

    }
    public void render(Graphics2D g) {
        g.drawImage(tileImage, x,y, null);
    }

    public int getValue() {
        return value;
    }

}
