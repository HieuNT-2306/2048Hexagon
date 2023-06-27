package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game.DrawUtils;
import game.Game;

public class GuiButton {
    private State currentState = State.RELEASED;
    private Rectangle clickBox;
    private ArrayList<ActionListener> actionListeners;
    private String text = "";
    private enum State{
        RELEASED,
        HOVER,
        PRESSED
    }
    private Color release_color;
    private Color hover_color;
    private Color press_color;
    private Font gui_font = Game.mainfont.deriveFont(22f);

    public GuiButton(int x, int y, int width, int height) {
        clickBox = new Rectangle(x, y, width, height);
        actionListeners = new ArrayList<ActionListener>();
        release_color = new Color(173, 177, 179);
        hover_color = new Color(150, 156, 158);
        press_color = new Color(111, 116, 117);
        //audio
    }


    public void update() {

    }   
    public void render(Graphics2D g) {
        if (currentState == State.RELEASED) {
            g.setColor(release_color);
            g.fill(clickBox);
        } else if (currentState == State.HOVER) {
            g.setColor(hover_color);
            g.fill(clickBox);
        } else {
            g.setColor(press_color);
            g.fill(clickBox);
        }
        g.setColor(Color.WHITE);
        g.setFont(gui_font);
        g.drawString(text, clickBox.x + clickBox.width/2 - DrawUtils.getMessageWidth(text, gui_font, g)/2 ,
                           clickBox.y + clickBox.height/2 + DrawUtils.getMessageHeight(text, gui_font, g)/2);
    }
    public void addActionListener(ActionListener l) {
        actionListeners.add(l);
    }
    public void mousePressed(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            currentState = State.PRESSED;
        }
    }
    public void mouseReleased(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            for (ActionListener al : actionListeners) {
                al.actionPerformed(null);
            }
            //audio
        }
        currentState = State.RELEASED;
    }
    public void mouseDragged(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            currentState = State.PRESSED;
        }
        else {
            currentState = State.RELEASED;
        }
    }
    public void mouseMoved(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            currentState = State.HOVER;
        }
        else {
            currentState = State.RELEASED;
        }
    }
    public int getX() {
        return clickBox.x;
    }
    public int getY() {
        return clickBox.y;
    }
    public int getWidth() {
        return clickBox.width;
    }
    public int getHeight() {
        return clickBox.height;
    }
    public void setText(String text) {
        this.text = text;
    }
}
