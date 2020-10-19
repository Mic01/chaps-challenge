package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements.Level;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class is responsible for creating, loading and saving replays.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Replay {
  ApplicationView application;
  String levelName;
  Level currentLevel;
  long startTime = System.nanoTime();
  long endTime = System.nanoTime();
  ArrayList<Level> levelHistory = new ArrayList<>();

  public Replay(ApplicationView application) {
    this.application = application;

    //Extract the level name
    String[] path = application.getLevelPath().split("/");
    this.levelName = path[path.length - 1].replace(".json", "");
    currentLevel = new Level(levelName);
  }

  //NOTE this is primarily here for testing purposes
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
      currentLevel.addAction(character, action, endTime-startTime);
      startTime = System.currentTimeMillis();
    }
  }
  
  public void levelUp(String levelName) {
    this.levelName = levelName;
    levelHistory.add(currentLevel);
    currentLevel = new Level(levelName);
  }

  /**
   * Save the current history into a json file.
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
        if (i < levelHistory.size() - 1) writer.write(",\n");
        else writer.write("\n");
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
