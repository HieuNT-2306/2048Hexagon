package game;
import java.awt.event.KeyEvent;

public class Keyboard {
    public static boolean[] pressed = new boolean[256];
    public static boolean[] previous = new boolean[256];
    private Keyboard() {

    }
    public static void update() {
        for (int i = 0; i< 6; i++) {
            if (i == 0) previous[KeyEvent.VK_W] = pressed[KeyEvent.VK_W];
            if (i == 1) previous[KeyEvent.VK_E] = pressed[KeyEvent.VK_E];
            if (i == 2) previous[KeyEvent.VK_D] = pressed[KeyEvent.VK_D];
            if (i == 3) previous[KeyEvent.VK_X] = pressed[KeyEvent.VK_X];
            if (i == 4) previous[KeyEvent.VK_Z] = pressed[KeyEvent.VK_Z];
            if (i == 5) previous[KeyEvent.VK_A] = pressed[KeyEvent.VK_A];
        }
    }
    public static void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
    }
    public static void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
    }
    public static boolean typed(int keyEvent) {
        return !pressed[keyEvent] && previous[keyEvent]; 
    }
}
