package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import game.DrawUtils;

public class GuiSlider {
    private State currentState = State.RELEASED;
    private Rectangle sliderBox;
    private float minValue;
    private float maxValue;
    private float value;
    private float handleSize;
    private boolean isDragging;
    private ActionListener onChangeListener;
    private Color trackColor;
    private Color handleColor;
    private Font guiFont;

    private enum State {
        RELEASED,
        HOVER,
        PRESSED
    }

    public GuiSlider(float minValue, float maxValue, float defaultValue, int x, int y, int width, int height) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = defaultValue;

        sliderBox = new Rectangle(x, y, width, height);
        handleSize = height;

        trackColor = new Color(173, 177, 179);
        handleColor = new Color(111, 116, 117);

        guiFont = new Font("Arial", Font.PLAIN, 12);
    }

    public void setValue(float newValue) {
        if (newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        } else {
            value = newValue;
        }

        if (onChangeListener != null) {
            onChangeListener.actionPerformed(null);
        }
    }

    public float getValue() {
        return value;
    }

    public void setOnChangeListener(ActionListener listener) {
        this.onChangeListener = listener;
    }

    public void mousePressed(MouseEvent e) {
        if (sliderBox.contains(e.getPoint())) {
            currentState = State.PRESSED;
            isDragging = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isDragging) {
            isDragging = false;
        }
        currentState = State.RELEASED;
    }

    public void mouseDragged(MouseEvent e) {
        if (isDragging) {
            float ratio = (e.getX() - sliderBox.x) / (float) sliderBox.width;
            float newValue = minValue + ratio * (maxValue - minValue);
            setValue(newValue);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (sliderBox.contains(e.getPoint())) {
            currentState = State.HOVER;
        } else {
            currentState = State.RELEASED;
        }
    }
    public void update() {

    }   

    public void render(Graphics2D g) {
        g.setColor(trackColor);
        g.fill(sliderBox);

        g.setColor(handleColor);
        int handleX = sliderBox.x + (int) ((value - minValue) / (maxValue - minValue) * sliderBox.width);
        int handleY = sliderBox.y + sliderBox.height / 2 - (int) (handleSize / 2);
        g.fillRect(handleX, handleY, (int) handleSize, (int) handleSize);

        // Display value text
        g.setFont(guiFont);
        g.setColor(Color.WHITE);
        String valueText = String.format("%.2f", value);
        int textX = sliderBox.x + sliderBox.width / 2 - DrawUtils.getMessageWidth(valueText, guiFont, g) / 2;
        int textY = sliderBox.y - DrawUtils.getMessageHeight(valueText, guiFont, g) - 5;
        g.drawString(valueText, textX, textY);
    }
}