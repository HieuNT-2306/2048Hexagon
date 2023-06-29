package game;

import java.io.*;

public class ScoreManager {
    // diem hien tai
    private int currentScore;
    private int currentTopScore;
    private long time;
    private long startingTime;
    private long bestTime;
    private int[] board = new int[Setting.SIZE * Setting.SIZE];

    // file
    private String filePath;
    private String temp = "TEMP.tmp";
    private GameBoard gBoard;

    // boolean
    private boolean newGame;

    public ScoreManager(GameBoard gBoard){
        this.gBoard = gBoard;
        filePath = new File("").getAbsolutePath();
    }

    public void reset(){
        File f = new File(filePath, temp);
        if(f.isFile()){
            f. delete();
        }
        newGame = true;
        startingTime = 0;
        currentScore = 0;
        time = 0;
    }

    private void createFile(){
        FileWriter output = null;
        newGame = true;
        try{
            File f = new File(filePath, temp);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + 0);  // diem hien tai
            writer.newLine();
            writer.write("" + 0); // diem cao nhat
            writer.newLine();
            writer.write("" + 0); // time hien tai
            writer.newLine();
            writer.write("" + 0); // time cao nhat
            writer.newLine();
            for(int row = 0; row < Setting.SIZE; row++){
                for(int col = 0; col < Setting.SIZE; col++){
                    if(row == Setting.SIZE-1 && col == Setting.SIZE-1){
                        writer.write("" + 0);
                    }
                    else writer.write(0 + "-");
                }
            }
            writer.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveGame(){
        FileWriter output = null;
        if(newGame)
            newGame = false;
        try{
            File f = new File(filePath, temp);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + currentScore);
            writer.newLine();
            writer.write("" + currentTopScore);
            writer.newLine();
            writer.write("" + time);
            writer.newLine();
            writer.write("" + bestTime);
            writer.newLine();
            for(int row = 0; row < Setting.SIZE; row++){
                for(int col = 0; col < Setting.SIZE; col++){
                    int location = row * Setting.SIZE + col;
                    Tile tile = gBoard.getBoard()[row][col];
                    this.board[location] = tile != null ? tile.getValue() : 0;
                    if(row == Setting.SIZE-1 && col == Setting.SIZE-1){
                        writer.write("" + board[location]);
                    }
                    else writer.write(board[location] + "-");
                }
            }
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadGame(){
        try{
            File f =new File(filePath, temp);
            if(!f.isFile()){
                createFile();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            currentScore = Integer.parseInt(reader.readLine());
            currentTopScore = Integer.parseInt(reader.readLine());
            time = Long.parseLong(reader.readLine());
            startingTime = time; // cai nay de khi pause game se luu lai time gan nhat dang choi do
            bestTime = Long.parseLong(reader.readLine());

            String[] board = reader.readLine().split("-");
            for(int i=0; i<board.length; i++){
                this.board[i] = Integer.parseInt(board[i]);
            }

            reader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentTopScore() {
        return currentTopScore;
    }

    public void setCurrentTopScore(int currentTopScore) {
        this.currentTopScore = currentTopScore;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time + startingTime;
    }

    public long getBestTime() {
        return bestTime;
    }

    public void setBestTime(long bestTime) {
        this.bestTime = bestTime;
    }

    public int[] getBoard() {
        return board;
    }

    public boolean newGame() {
        return newGame;
    }
}
