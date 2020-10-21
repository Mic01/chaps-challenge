package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
  public String levelPath;
  public int currLevel = 1;
  private ApplicationView game;
  public Font deface = Font.createFont(Font.TRUETYPE_FONT,
          new File("assets/fonts/deface.otf")).deriveFont(14f);


  private Main() throws IOException, FontFormatException {
  }

  /**
   * Main function, starts the game.
   *
   * @param args - args.
   * @throws IOException - IOException.
   * @throws FontFormatException - FontFormatException.
   */
  public static void main(String... args) throws IOException, FontFormatException {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    env.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/deface.otf")));
    Main gameInstance = new Main();
    File f = new File("./tmp_save.txt");
    if(!f.isFile()) {
      gameInstance.setup();
    }
    else{
      gameInstance.currLevel = SmallSave.loadFile(f);
      gameInstance.levelPath = "levels/Level" + gameInstance.currLevel + ".json";
      gameInstance.quickLoad();
    }
  }

  private void setup() {
    new SetupView(this);
    this.game = new ApplicationView(this, false);
  }

  private void quickLoad(){
    this.game = new ApplicationView(this, false);
  }

  /**
   * Advances the game to the next level.
   *
   * @param currLevel - The ID of the current level before advancing.
   */
  public void nextLevel(int currLevel) {
    if (currLevel < 2) {
      this.levelPath = "levels/Level" + (currLevel + 1) + ".json";
      this.currLevel = currLevel + 1;
    } else {
      this.currLevel = currLevel;
    }
    this.game.disposeWindow();
    this.game = new ApplicationView(this, false);
  }

  /**
   * Restarts the current level.
   *
   * @param currLevel - The ID of the currently active level.
   */
  public void restartLevel(int currLevel) {
    this.currLevel = currLevel;
    this.game.disposeWindow();
    this.game = new ApplicationView(this, false);
  }

  /**
   * Loads a replay of a level.
   *
   * @param replayDir - Location of the replay file.
   * @param currLevel - The ID of the currently active level.
   */
  public void loadReplayLevel(String replayDir, int currLevel) {
    this.currLevel = currLevel;
    this.game.disposeWindow();
    this.game = new ApplicationView(this, true, replayDir);
  }

  /**
   * Loads a save file, also loading the correct level.
   *
   * @param savePath - Path of the save file being loaded.
   */
  public void loadSave(String savePath) {
    this.levelPath = savePath;
    this.game.disposeWindow();
    String[] splitFileName = savePath.split("\\.(?=[^.]+$)");
    char levelId = splitFileName[0].charAt(splitFileName[0].length() - 1);
    this.currLevel = Character.getNumericValue(levelId);
    this.game = new ApplicationView(this, false);
  }
}
