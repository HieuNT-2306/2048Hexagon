package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.DrawUtils;
import game.Game;

public class MainMenuPanel extends GuiPanel {
    private Font titleFont = Game.mainfont.deriveFont(100f);
    private Font createrFont = Game.mainfont.deriveFont(24f);
    private String title = "2048";
    private String creator = "Nhom 15";
    private int buttonWidth = 200;
    private int buttonHeight = 50;
    private int spacing = 70;
    public MainMenuPanel() {
        super();
        GuiButton continueButton = new GuiButton(Game.WIDTH/ 2 - buttonWidth/ 2, 220, buttonWidth, buttonHeight);
        GuiButton newGameButton = new GuiButton(Game.WIDTH/ 2 - buttonWidth/ 2, continueButton.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton settingButton = new GuiButton(Game.WIDTH/ 2 - buttonWidth/ 2, newGameButton.getY() + spacing, buttonWidth , buttonHeight);
        GuiButton leaderBoardButton = new GuiButton(Game.WIDTH/ 2 - buttonWidth/ 2, settingButton.getY() + spacing, buttonWidth, buttonHeight);  
        GuiButton exitButton = new GuiButton(Game.WIDTH/ 2 - buttonWidth/ 2, leaderBoardButton.getY() + spacing,  buttonWidth, buttonHeight); 
        
        continueButton.setText("Continue");
        newGameButton.setText("New Game");
        settingButton.setText("Settings");
        leaderBoardButton.setText("Leaderboards");
        exitButton.setText("Exit");

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                GuiScreen.getInstance().setCurrentPanel("PLAY");
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                GuiScreen.getInstance().setCurrentPanel("NEW GAME");
            }
        });

        leaderBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                GuiScreen.getInstance().setCurrentPanel("LEADERBOARDS");
            }
        });

        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                GuiScreen.getInstance().setCurrentPanel("SETTINGS");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                System.exit(0);
            }
        });

        add(continueButton);
        add(newGameButton);
        add(leaderBoardButton);
        add(settingButton);
        add(exitButton);

    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.setFont(titleFont);
        g.setColor(Color.BLACK);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g)/2, 150);
        g.setFont(createrFont);
        g.drawString(creator, 20, Game.HEIGHT - 10);
    }
}
