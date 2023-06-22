package game;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

	private boolean beginningAnimation = true;
	private double scaleFirst = 0.1;
	private BufferedImage beginningImage;

	private boolean combineAnimation = false;
	private BufferedImage combineImage;
	private double scaleCombine = 1.3;
	private boolean canCombine = true;

	public void setValue(int value) {
		this.value = value;
		drawImage();
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

    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isCombineAnimation() {
		return combineAnimation;
	}

	public void setCombineAnimation(boolean combineAnimation) {
		this.combineAnimation = combineAnimation;
		if(combineAnimation)
			scaleCombine = 1.2;
	}

	public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
		this.slideTo = new Point(x, y);
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		beginningImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		combineImage = new BufferedImage(WIDTH*2, HEIGHT*2, BufferedImage.TYPE_INT_ARGB);
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
		if(beginningAnimation){
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH/2 - scaleFirst * WIDTH/2, HEIGHT/2 - scaleFirst * HEIGHT/2);
			transform.scale(scaleFirst, scaleFirst);
			Graphics2D g2d = (Graphics2D) beginningImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setColor(new Color(0, 0, 0, 0));
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.drawImage(tileImage, transform, null);
			scaleFirst += 0.1;
			g2d.dispose();
			if(scaleFirst >= 1)
				beginningAnimation = false;
		}
		else if(combineAnimation){
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH/2 - scaleCombine * WIDTH/2, HEIGHT/2 - scaleCombine * HEIGHT/2);
			transform.scale(scaleCombine, scaleCombine);
			Graphics2D g2d = (Graphics2D) combineImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setColor(new Color(0, 0, 0, 0));
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.drawImage(tileImage, transform, null);
			scaleCombine -= 0.05;
			g2d.dispose();
			if(scaleCombine <= 1)
				combineAnimation = false;
		}
    }
    public void render(Graphics2D g) {
		if(beginningAnimation){
			g.drawImage(beginningImage, x, y, null);
		}
		else if(combineAnimation){
			g.drawImage(combineImage, (int)(x + WIDTH/2 - scaleCombine * WIDTH/2),
					(int)(y + HEIGHT/2 - scaleCombine * HEIGHT/2), null);
		}
        else
			g.drawImage(tileImage, x,y, null);
    }

    public int getValue() {
        return value;
    }

}
