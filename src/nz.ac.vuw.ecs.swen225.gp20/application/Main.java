package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;

public class Main {
    private static Main gameInstance;
    public String levelPath;
    public int currLevel = 1;


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
        new ApplicationView(this);
    }

    public void nextLevel(int currLevel){
        this.levelPath = "Level" + (currLevel+1) + ".json";
        this.currLevel = currLevel + 1;
        new ApplicationView(this);
    }

    public void restartLevel(int currLevel){
        this.currLevel = currLevel;
        new ApplicationView(this);
    }
}
