package hust.soict.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hust.soict.game.DrawUtils;
import hust.soict.game.Game;
import hust.soict.game.Setting;

public class ConfirmSettingPanel extends GuiPanel {
    private int smallButtonWidth = 160;
	private int largeButtonWidth = smallButtonWidth*2 +20;
    private int alpha = 112;
	private int ConfirmPanelspacing = 20;
    private Font confirmFont = Game.main.deriveFont(Font.BOLD,30f);
    private int buttonHeight = 50;
    private GuiButton YesButton;
    private GuiButton NoButton;
    private String title = "Confirm settings";
	private Font titleFont = Game.main.deriveFont(48f);

    public ConfirmSettingPanel() {
        YesButton = new GuiButton(Game.WIDTH/2 - largeButtonWidth/ 2, 450 - ConfirmPanelspacing - buttonHeight, smallButtonWidth, buttonHeight);
		NoButton = new GuiButton(YesButton.getX() + YesButton.getWidth() + ConfirmPanelspacing, YesButton.getY(), smallButtonWidth, buttonHeight);
		YesButton.setText("YES (Restart)");
		NoButton.setText("NO");

		YesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SettingsPanel.fps == Setting.FPS
				&& SettingsPanel.size == Setting.SIZE
				&& SettingsPanel.ShiftColor_board == Setting.ShiftColor_board
				&& SettingsPanel.shiftColor_tile == Setting.ShiftColor_tile
				&& SettingsPanel.mainColor == Setting.mainColor) {
					GuiScreen.getInstance().setCurrentPanel("Menu");
				}
				else {
					ConfigManager.saveSetting("SIZE", Integer.toString(SettingsPanel.size));
					ConfigManager.saveSetting("FPS", Integer.toString(SettingsPanel.fps));
                    ConfigManager.saveSetting("mainColor", Integer.toString(SettingsPanel.mainColor));
                    ConfigManager.saveSetting("ShiftColor_tile", Integer.toString(SettingsPanel.shiftColor_tile));
                    ConfigManager.saveSetting("ShiftColor_board", Integer.toString(SettingsPanel.ShiftColor_board));
					System.exit(0);
				}
			}
		});
		NoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("settings");
			}
		});

        add(YesButton);
        add(NoButton);
    }

    public void drawConfirmSettingBoard(Graphics2D g) {
		String confirmMessage1 = "Do you want to save the setting?";
		String confirmMessage2 =  "(The game must be restarted)?";
		String confirmMessage3 =  "(And you will lose all your progess!!)";
        g.setColor(new Color(222, 222, 222, alpha));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.ORANGE);
        g.setFont(confirmFont);
        g.drawString(confirmMessage1, Game.WIDTH/ 2 - DrawUtils.getMessageWidth(confirmMessage1, confirmFont, g) /2, 250);
		g.drawString(confirmMessage2, Game.WIDTH/ 2 - DrawUtils.getMessageWidth(confirmMessage2, confirmFont, g) /2, 300);
		g.drawString(confirmMessage3, Game.WIDTH/ 2 - DrawUtils.getMessageWidth(confirmMessage3, confirmFont, g) /2, 350);
    }
    @Override
    public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2,
		DrawUtils.getMessageHeight(title, titleFont, g) + 40);
        drawConfirmSettingBoard(g);
		super.render(g);
	}
    
}
