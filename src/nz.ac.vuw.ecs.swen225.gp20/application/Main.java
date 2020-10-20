package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    private static Main gameInstance;
    public String levelPath;
    public int currLevel = 1;
    private ApplicationView game;
    public Font deface = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/deface.otf")).deriveFont(14f);


    private Main() throws IOException, FontFormatException {
    }

    public static void main(String... args) throws IOException, FontFormatException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        env.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/deface.otf")));
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
        String[] splitFileName = savePath.split("\\.(?=[^\\.]+$)");
        char levelID = splitFileName[0].charAt(splitFileName[0].length() - 1);
        this.currLevel = Character.getNumericValue(levelID);
        this.game = new ApplicationView(this, false);
    }
}
