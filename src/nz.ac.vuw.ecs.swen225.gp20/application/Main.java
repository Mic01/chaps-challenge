package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;

public class Main {
    private static Main gameInstance;
    public String levelPath;
    public int currLevel = 1;
    private ApplicationView game;

    private Main() {
    }

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        gameInstance = new Main();
        gameInstance.setup();
    }

    private void setup() {
        new SetupView(this);
        this.game = new ApplicationView(this, false);
    }

    public void nextLevel(int currLevel){
        if(currLevel < 2) {
            this.levelPath = "levels/Level" + (currLevel + 1) + ".json";
            this.currLevel = currLevel + 1;
        }
        else{
            this.currLevel = currLevel;
        }
        this.game.disposeWindow();
        this.game = new ApplicationView(this, false);
    }

    public void restartLevel(int currLevel){
        this.currLevel = currLevel;
        this.game.disposeWindow();
        this.game = new ApplicationView(this, false);
    }

    public void loadReplayLevel(String replayDir, int currLevel){
        this.currLevel = currLevel;
        this.game.disposeWindow();
        this.game = new ApplicationView(this, true, replayDir);
    }

    public void loadSave(String savePath){
        this.levelPath = savePath;
        this.game.disposeWindow();
        this.game = new ApplicationView(this, false);
    }
}
