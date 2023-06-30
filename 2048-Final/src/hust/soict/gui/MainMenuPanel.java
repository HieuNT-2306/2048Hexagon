package hust.soict.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hust.soict.game.DrawUtils;
import hust.soict.game.Game;
import hust.soict.game.Setting;

public class MainMenuPanel extends GuiPanel {

	private Font titleFont = Game.main.deriveFont(Font.BOLD, 100f);
	private Font subtitleFont = Game.main.deriveFont(Font.ITALIC, 40f);
	private Font creatorFont = Game.main.deriveFont(Font.ITALIC, 24f);
	private String title = "2 0 4 8";
	private String subtitle = Setting.SIZE + "x" + Setting.SIZE;
	private String creator = "By Nhom HTHL (Nhom 22)";
	private int buttonWidth = 220;
	
	public MainMenuPanel() {
		super();
		GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, 60);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Play");
			}
		});
		playButton.setText("Continue");
		add(playButton);
		GuiButton newGameButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 300, buttonWidth, 60);
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("NewGames");
			}
		});
		newGameButton.setText("New Game");
		add(newGameButton);
		
		GuiButton scoresButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 380, buttonWidth, 60);
		scoresButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Leaderboards");
			}
		});
		scoresButton.setText("Scores");
		add(scoresButton);

		GuiButton settingsButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 460, buttonWidth, 60);
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Settings");
			}
		});
		settingsButton.setText("Setting");
		add(settingsButton);
		
		GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 540, buttonWidth, 60);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitButton.setText("Quit");
		add(quitButton);
	}

	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setFont(titleFont);
		g.setColor(Color.BLUE);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
		g.setColor(Color.MAGENTA);
		g.setFont(subtitleFont);
		g.drawString(subtitle, Game.WIDTH / 2 - DrawUtils.getMessageWidth(subtitle, subtitleFont, g) / 2, 180);
		g.setColor(Color.BLACK);
		g.setFont(creatorFont);
		g.drawString(creator, 20, Game.HEIGHT - 10);
	}
}
