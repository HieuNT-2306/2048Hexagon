package game;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class DrawUtils {
    private DrawUtils() {}
    public static int getMessageWidth(String message, Font font, Graphics2D g){
        g.setFont(font);
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(message, g);
        return (int)bounds.getWidth();
    }
    public static int getMessageHeight(String message, Font font, Graphics2D g){
        g.setFont(font);
        if (message.length() == 0) return 0;
        TextLayout tl = new TextLayout(message, font, g.getFontRenderContext());
        return (int)tl.getBounds().getHeight();     
    }
    public static String formatTime(long milisec) {
        String formattedTime;
        String hourFormat = "";
        int hours = (int) (milisec / 3600000);
        if (hours >= 1) {
            milisec -= hours * 3600000;
            if (hours < 10) {
                hourFormat = "0" + hours;
            } else {
                hourFormat = "" + hours;
            }
            hourFormat += ":";
        }; 

        String minuteFormat = "";
        int minutes = (int) (milisec / 60000);
        if (minutes >= 1) {
            milisec -= minutes * 60000;
            if (minutes < 10) {
                minuteFormat = "0" + minutes;
            } else {
                minuteFormat = "" + minutes;
            }
        }
        else {
            minuteFormat = "00";
        }

        String secondFormat = "";
        int seconds = (int) (milisec / 1000);
        if (seconds >= 1) {
            milisec -= seconds * 60000;
            if (seconds < 10) {
                secondFormat = "0" + seconds;
            } else {
                secondFormat = "" + seconds;
            }
        }
        else {
            secondFormat = "00";
        }

        String milliFormat = "";
        if (milisec > 99) {
            milliFormat = "" + milisec;
        }
        else if (milisec > 9) {
            milliFormat = "0" + milisec;
        }
        else {
            milliFormat = "00" + milisec;
        }

        formattedTime = hourFormat + minuteFormat + ":" + secondFormat;
        return formattedTime;
    }
}
