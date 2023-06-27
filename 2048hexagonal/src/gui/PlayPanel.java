package gui;

import game.Game;
import game.GameBoard;
import game.ScoreManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ActionMap;

public class PlayPanel extends GuiPanel {
    private GameBoard board;
    private BufferedImage info;
    private ScoreManager scores;
    private Font scoreFont;
    private String timeStringF;
    private String bestTimeStringF;

    //Game over
    private GuiButton tryAgain;
    private GuiButton mainMenu;
    private GuiButton screenShot;
    private int smallButtonWidth = 160;
    private int spacing = 20;
    private int largeButtonWidth = smallButtonWidth*2 +20;
    private int buttonHeight;
    private boolean added;
    private int alpha;
    private Font gameOverFont;
    private boolean screenshot;

    public PlayPanel() {
        scoreFont = Game.mainfont.deriveFont(24f);
        gameOverFont = Game.mainfont.deriveFont(70f);
        board = new GameBoard(Game.WIDTH/ 2 - GameBoard.BOARD_WIDTH/ 2, Game.HEIGHT - GameBoard.BOARD_HEIGHT - 20);
        scores = board.getScores();
        info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);
        mainMenu = new GuiButton(Game.WIDTH/2 - largeButtonWidth/ 2, 450, largeButtonWidth, buttonHeight);
        tryAgain = new GuiButton(mainMenu.getX(), mainMenu.getY() - spacing - buttonHeight, smallButtonWidth, buttonHeight);
        screenShot = new GuiButton(tryAgain.getX() + tryAgain.getWidth() + spacing, tryAgain.getY(), smallButtonWidth, buttonHeight);

        mainMenu.setText("Main Menu");
        tryAgain.setText("Try Again");
        screenShot.setText("Screenshot");

        tryAgain.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                board.getScores().reset();
                board.reset();
                alpha = 0;
                remove(tryAgain);
                remove(mainMenu);
                remove(screenShot);

                added = false;
            }
        });

        screenShot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenshot = true;
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("MENU");
            }
        });
    }

}
