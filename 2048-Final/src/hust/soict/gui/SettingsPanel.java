package hust.soict.gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Graphics2D;
import hust.soict.game.DrawUtils;
import hust.soict.game.Game;
import hust.soict.game.Setting;

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
	private int alpha = 0;

	private String title = "Settings";
	private Font titleFont = Game.main.deriveFont(48f);
	private Font settingFont = Game.main.deriveFont(25f);
	private Font confirmFont = Game.main.deriveFont(25f);


	// setting
	public static int size = Setting.SIZE;
	public static int fps = Setting.FPS;
	public static int shiftColor_tile = Setting.ShiftColor_tile;
	public static int ShiftColor_board = Setting.ShiftColor_board;
	public static int mainColor = Setting.mainColor;

	private int color_tile_index;
	private int color_board_index;
	private int color_main_index;
	public static int[] ColorMap = {0x000000, 0x000088, 0x0000ff, 0x008800, 0xffffff};


	private int findIndex(int targetValue) {
		int index = -1;
		for (int i = 0; i < ColorMap.length; i++) {
            if (ColorMap[i] == targetValue) {
                index = i;
                break;
            }
        }
		return index;
	}


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
		color_board_index = findIndex(ShiftColor_board);
		color_tile_index = findIndex(shiftColor_tile);
		color_main_index = findIndex(mainColor);
		System.out.println(color_board_index + " " + color_main_index + " " + color_tile_index);

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

		GuiButton incrementMainColorButton = new GuiButton(rightX, incrementSizeButton.getY() + 70, buttonWidth, buttonHeight);
		incrementMainColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color_main_index++;
				if (color_main_index > ColorMap.length - 1) color_main_index = 0;
				setMainColor(ColorMap[color_main_index]);
			}
		});
		incrementMainColorButton.setText(">");
		add(incrementMainColorButton);

		GuiButton decrementMainColorButton = new GuiButton(leftX, decrementSizeButton.getY() + 70, buttonWidth, buttonHeight);
		decrementMainColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color_main_index--;
				if (color_main_index < 0) color_main_index = ColorMap.length - 1;
				setMainColor(ColorMap[color_main_index]);
			}
		});
		decrementMainColorButton.setText("<");
		add(decrementMainColorButton);

		GuiButton incrementTileColorButton = new GuiButton(rightX, incrementMainColorButton.getY() + 70, buttonWidth, buttonHeight);
		incrementTileColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color_tile_index++;
				if (color_tile_index > ColorMap.length - 1) color_tile_index = 0;
				setShiftColor_tile(ColorMap[color_tile_index]);
			}
		});
		incrementTileColorButton.setText(">");
		add(incrementTileColorButton);

		GuiButton decrementTileColorButton = new GuiButton(leftX, decrementMainColorButton.getY() + 70, buttonWidth, buttonHeight);
		decrementTileColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color_tile_index--;
				if (color_tile_index < 0) color_tile_index = ColorMap.length - 1;
				setShiftColor_tile(ColorMap[color_tile_index]);
			}
		});
		decrementTileColorButton.setText("<");
		add(decrementTileColorButton);

		GuiButton incrementBoardColorButton = new GuiButton(rightX, incrementTileColorButton.getY() + 70, buttonWidth, buttonHeight);
		incrementBoardColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color_board_index++;
				if (color_board_index > ColorMap.length - 1) color_board_index = 0;
				setShiftColor_board(ColorMap[color_board_index]);
			}
		});
		incrementBoardColorButton.setText(">");
		add(incrementBoardColorButton);

		GuiButton decrementBoardColorButton = new GuiButton(leftX, decrementTileColorButton.getY() + 70, buttonWidth, buttonHeight);
		decrementBoardColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color_board_index--;
				if (color_board_index < 0) color_board_index = ColorMap.length - 1;
				setShiftColor_board(ColorMap[color_board_index]);
			}
		});
		decrementBoardColorButton.setText("<");
		add(decrementBoardColorButton);





		GuiButton backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2 + 120, 500, backButtonWidth, 60);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Menu");
			}
		});


		backButton.setText("Back");
		add(backButton);

		GuiButton confirmSettingButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2 - 120, 500, backButtonWidth, 60);
		confirmSettingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Confirm settings");
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
		g.setColor(Color.black);
		g.setFont(settingFont);
		for(int i = 0; i < strings.size(); i++){
			String s =  strings.get(i);
			g.drawString(s, middleX, settingBoardY + i * 70);
		}
		//Main color
		g.setColor(new Color(SettingsPanel.mainColor));
		g.fillRect(middleX, settingBoardY + 100, buttonWidth, buttonHeight);
		g.setColor(Color.BLACK);
		g.drawRect(middleX, settingBoardY + 100, buttonWidth, buttonHeight);
		//Tile color
		g.setColor(new Color(SettingsPanel.shiftColor_tile + 0xe9e9e9));
		g.fillRect(middleX, settingBoardY + 170, buttonWidth, buttonHeight);
		g.setColor(Color.BLACK);
		g.drawRect(middleX, settingBoardY + 170, buttonWidth, buttonHeight);
		//Board color
		g.setColor(new Color(SettingsPanel.ShiftColor_board + 0x444444));
		g.fillRect(middleX, settingBoardY + 240, buttonWidth, buttonHeight);
		g.setColor(Color.BLACK);
		g.drawRect(middleX, settingBoardY + 240, buttonWidth, buttonHeight);
	}
	public void drawConfirmSettingBoard(Graphics2D g) {
		String confirmMessage1 = "Do you want to save the setting?";
		String confirmMessage2 =  "(The game must be restarted)?";
        g.setColor(new Color(222, 222, 222, alpha));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.ORANGE);
        g.setFont(confirmFont);
        g.drawString(confirmMessage1, Game.WIDTH/ 2 - DrawUtils.getMessageWidth(confirmMessage1, confirmFont, g) /2, 250);
		g.drawString(confirmMessage2, Game.WIDTH/ 2 - DrawUtils.getMessageWidth(confirmMessage2, confirmFont, g) /2, 300);
    }

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2,
				DrawUtils.getMessageHeight(title, titleFont, g) + 40);
		drawSettingBoards(g);
		drawValue(g);
		super.render(g);
	}

}
