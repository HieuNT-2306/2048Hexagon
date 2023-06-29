package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Graphics2D;
import game.DrawUtils;
import game.Game;
import game.Setting;

public class SettingsPanel extends GuiPanel {

	private int buttonWidth = 60;
	private int backButtonWidth = 220;
	private int buttonSpacing = 20;
	private int buttonY = 120;
	private int buttonHeight = 50;
	private int shiftX = 80;
	private int settingBoardX =  shiftX;
	private int settingBoardY = buttonY + 40 ;
	private int leftX = Game.WIDTH / 2 - buttonWidth / 2 - buttonWidth - buttonSpacing + shiftX;
	private int rightX = Game.WIDTH / 2 + buttonWidth / 2 + buttonSpacing + shiftX;
	private int middleX = Game.WIDTH / 2 - buttonWidth / 2 + shiftX;


	private String title = "Settings";
	private Font titleFont = Game.mainfont.deriveFont(48f);
	private Font settingFont = Game.mainfont.deriveFont(25f);

	// setting
	private static int size = Setting.SIZE;
	private static int fps = Setting.FPS;
	private static int shiftColor_tile = Setting.ShiftColor_tile;
	private static int ShiftColor_board = Setting.ShiftColor_board;
	private static int mainColor = Setting.mainColor;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if(size < 4 ) size = 6;
		if(size > 6) size = 4;
		SettingsPanel.size = size;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		if (fps < 60)
			fps = 120;
		if (fps > 120)
			fps = 60;
		SettingsPanel.fps = fps;
	}

	public int getShiftColor_tile() {
		return shiftColor_tile;
	}

	public void setShiftColor_tile(int shiftColor_tile) {
		SettingsPanel.shiftColor_tile = shiftColor_tile;
	}

	public int getShiftColor_board() {
		return ShiftColor_board;
	}

	public void setShiftColor_board(int shiftColor_board) {
		SettingsPanel.ShiftColor_board = shiftColor_board;
	}

	public int getMainColor() {
		return mainColor;
	}

	public void setMainColor(int mainColor) {
		SettingsPanel.mainColor = mainColor;
	}

	public SettingsPanel() {
		super();

		GuiButton incrementFPSButton = new GuiButton(rightX, buttonY, buttonWidth, buttonHeight);
		incrementFPSButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFps(fps + 10);
			}
		});
		incrementFPSButton.setText(">");
		add(incrementFPSButton);

		GuiButton decrementFPSButton = new GuiButton(leftX, buttonY, buttonWidth, buttonHeight);
		decrementFPSButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFps(fps - 10);
			}
		});
		decrementFPSButton.setText("<");
		add(decrementFPSButton);

		GuiButton incrementSizeButton = new GuiButton(rightX, incrementFPSButton.getY() + 70, buttonWidth, buttonHeight);
		incrementSizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(size + 1);
			}
		});
		incrementSizeButton.setText(">");
		add(incrementSizeButton);

		GuiButton decrementSizeButton = new GuiButton(leftX, decrementFPSButton.getY() + 70, buttonWidth, buttonHeight);
		decrementSizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSize(size + 1);
			}
		});
		decrementSizeButton.setText("<");
		add(decrementSizeButton);



		GuiButton backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2 + 120, 500, backButtonWidth, 60);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("MENU");
			}
		});
		backButton.setText("Back");
		add(backButton);
		GuiButton confirmSettingButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2 - 120, 500, backButtonWidth, 60);
		confirmSettingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SettingsPanel.fps == Setting.FPS
				&& SettingsPanel.size == Setting.SIZE
				&& SettingsPanel.ShiftColor_board == Setting.ShiftColor_board
				&& SettingsPanel.shiftColor_tile == Setting.ShiftColor_tile
				&& SettingsPanel.mainColor == Setting.mainColor) {
					GuiScreen.getInstance().setCurrentPanel("MENU");
				}
				else {
					ConfigManager.saveSetting("SIZE", Integer.toString(SettingsPanel.size));
					ConfigManager.saveSetting("FPS", Integer.toString(SettingsPanel.fps));
				}
			}
		});
		confirmSettingButton.setText("Confirm");
		add(confirmSettingButton);

	}
	private void drawSettingBoards(Graphics2D g){
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("FPS:");
		strings.add("Size:");
		strings.add("Main color");
		strings.add("Tile color");
		strings.add("Board color:");
		g.setColor(Color.black);
		g.setFont(settingFont);
		for(int i = 0; i < strings.size(); i++){
			String s =  strings.get(i);
			g.drawString(s, settingBoardX, settingBoardY + i * 70);
		}
	}
	private void drawValue(Graphics2D g) {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(" "+ Integer.toString(fps) + " ");
		strings.add(" "+ Integer.toString(size) + "  ");
		strings.add("None");
		strings.add("None");
		strings.add("None");
		g.setColor(Color.black);
		g.setFont(settingFont);
		for(int i = 0; i < strings.size(); i++){
			String s =  strings.get(i);
			g.drawString(s, middleX, settingBoardY + i * 70);
		}
	}

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.setColor(Color.black);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2,
				DrawUtils.getMessageHeight(title, titleFont, g) + 40);
		drawSettingBoards(g);
		drawValue(g);
	}

}
