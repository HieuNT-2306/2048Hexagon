package gui;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GuiPanel  {
    private ArrayList<GuiButton> buttons;
    private ArrayList<GuiSlider> sliders;
    public GuiPanel() {
        buttons = new ArrayList<GuiButton>();
        sliders = new ArrayList<GuiSlider>();
    }

    public void update() {
        for (GuiButton b : buttons) {
            b.update();
        }
        for (GuiSlider s : sliders) {
            s.update();
        }
    }
    public void render(Graphics2D g) {
        for (GuiButton b : buttons) {
            b.render(g);
        }
        for (GuiSlider s: sliders) {
            s.render(g);
        }
    }

    public void add(GuiButton button) {
        buttons.add(button);
    }

    public void remove(GuiButton button) {
        buttons.remove(button);
    }

    public void add(GuiSlider slider) {
        sliders.add(slider);
    }

    public void remove(GuiSlider slider) {
        sliders.remove(slider);
    }
    public void mousePressed(MouseEvent e) {
        for (GuiButton b : buttons) {
            b.mousePressed(e);
        }
    }
    public void mouseReleased(MouseEvent e) {
        for (GuiButton b : buttons) {
            b.mouseReleased(e);
        }
         for (GuiSlider s : sliders) {
            s.mouseReleased(e);
        }
    }
    public void mouseDragged(MouseEvent e) {
        for (GuiButton b : buttons) {
            b.mouseDragged(e);
        }
        for (GuiSlider s : sliders) {
            s.mouseDragged(e);
        }
    }
    public void mouseMoved(MouseEvent e) {
        for (GuiButton b : buttons) {
            b.mouseMoved(e);
        }
        for (GuiSlider s : sliders) {
            s.mouseMoved(e);
        }
    }
}
