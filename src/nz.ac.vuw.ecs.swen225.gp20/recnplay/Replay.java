package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.elements.Level;

/**
 * This class is responsible for creating, loading and saving replays.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Replay {
  String levelName;
  Level currentLevel;
  long startTime = System.nanoTime();
  long endTime = System.nanoTime();
  final ArrayList<Level> levelHistory = new ArrayList<>();

  /**
   * Create a new replay object.
   *
   * @param application the current application.
   */
  public Replay(ApplicationView application) {
    //Extract the level name
    String[] path = application.getLevelPath().split("/");
    this.levelName = path[path.length - 1].replace(".json", "");
    currentLevel = new Level(levelName);
  }

  /**
   * Constructor to create a replay.
   * Primarily used for static testing.
   *
   * @param levelPath the path to the current level
   */
  public Replay(String levelPath) {
    //Extract the level name
    String[] path = levelPath.split("/");
    this.levelName = path[path.length - 1].replace(".json", "");
    currentLevel = new Level(levelName);
  }

  /**
   * Add an action to the history.
   *
   * @param action the thing that we want to add to the stack.
   */
  public void addAction(String action, String character) {
    if (currentLevel.actionCount() == 0) {
      currentLevel.addAction(character, action, (long) 0.0);
      startTime = System.currentTimeMillis();
    } else {
      endTime = System.currentTimeMillis();
      currentLevel.addAction(character, action, endTime - startTime);
      startTime = System.currentTimeMillis();
    }
  }

  /**
   * Add a new level to the json file.
   *
   * @param levelName the name of the new level.
   */
  public void levelUp(String levelName) {
    this.levelName = levelName;
    levelHistory.add(currentLevel);
    currentLevel = new Level(levelName);
  }

  /**
   * Save the current history into a json file.
   *
   * @param replay return the file to be used to save the replay.
   */
  public void saveReplay(File replay) {
    if (!levelHistory.contains(currentLevel)) {
      levelHistory.add(currentLevel);
    }

    System.out.println("Attempting to save the level.");

    //Write the data to the file
    FileWriter writer = null;
    try {
      System.out.println("Last level name is: " + levelName);

      writer = new FileWriter(replay, StandardCharsets.UTF_8);

      //Add the opening {
      writer.write("{\n");

      for (int i = 0; i < levelHistory.size(); i++) {
        writer.write(levelHistory.get(i).writeHistory());
        if (i < levelHistory.size() - 1) {
          writer.write(",\n");
        } else {
          writer.write("\n");
        }
      }

      //Add the closing }
      writer.write("\n}");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
