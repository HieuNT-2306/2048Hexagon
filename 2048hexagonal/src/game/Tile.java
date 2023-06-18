package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int SLIDE_SPEED = 20;
    public static final int ARC_WIDTH = 15;
    public static final int ARC_HEIGHT = 15;

    private int value;
    private BufferedImage tileImage;
    private Color background_color;
    private Color text_color;
    private int x, y;
    private Font tileFont;

    private void drawImage() {
        Graphics2D g = (Graphics2D) tileImage.getGraphics();
        if (value == 2) {
            background_color = new Color(0xe9e9e9);
            text_color = new Color(0x000000);
        }
        if (value == 4) {
            background_color = new Color(0xe6daab);
            text_color = new Color(0x000000);
        }
        if (value == 8) {
            background_color = new Color(0xf79d3d);
            text_color = new Color(0xffffff);
        }
        if (value == 16) {
            background_color = new Color(0xf28007);
            text_color = new Color(0xffffff);
        }
        if (value == 32) {
            background_color = new Color(0xf55e3d);
            text_color = new Color(0xffffff);
        }
        if (value == 64) {
            background_color = new Color(0xff0000);
            text_color = new Color(0xffffff);
        }
        if (value == 128) {
            background_color = new Color(0xe9de84);
            text_color = new Color(0xffffff);
        }
        if (value == 256) {
            background_color = new Color(0xf6e873);
            text_color = new Color(0xffffff);
        }
        if (value == 512) {
            background_color = new Color(0xf5e455);
            text_color = new Color(0xffffff);
        }
        if (value == 1024) {
            background_color = new Color(0xffe400);
            text_color = new Color(0xffffff);
        }
        if (value == 2048) {
            background_color = new Color(0xe6daab);
            text_color = new Color(0xffffff);
        }
        else {
            background_color = Color.white;
            text_color = Color.black;
        }
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(background_color);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(text_color);
        if (value <= 64) {
            tileFont = Font.getFont(Font.SANS_SERIF).deriveFont(36f);
        }
        else {
            tileFont = Font.getFont(Font.SANS_SERIF);
        }
        g.setFont(tileFont);
        int drawX = WIDTH/2 - DrawUtils.getMessageWidth("" + value, tileFont, g) /2;
        int drawY = HEIGHT/2 - DrawUtils.getMessageHeight("" + value, tileFont, g) /2;
        g.drawString("" + value, drawX, drawY);
        g.dispose();
    }

    public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        drawImage();
    }

}
