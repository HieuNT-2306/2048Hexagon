package hust.soict.game;

import hust.soict.gui.ConfigManager;

public class Setting {
    public static int SIZE = Integer.parseInt(ConfigManager.getSetting("SIZE"));
    public static int FPS = Integer.parseInt(ConfigManager.getSetting("FPS"));
    public static int ShiftColor_tile = Integer.decode(ConfigManager.getSetting("ShiftColor_tile"));
    public static int mainColor = Integer.decode(ConfigManager.getSetting("mainColor"));
    public static int ShiftColor_board = Integer.decode(ConfigManager.getSetting("ShiftColor_board"));

}
