package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class is responsible for creating, loading and saving replays.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Replay {

  private final ArrayList<Object> history = new ArrayList<Object>();
  String levelName;

  public Replay(String levelPath) {
    //Extract the level name
    String[] path = levelPath.split("/");
    this.levelName = path[path.length - 1].replace(".json", "");
  }

  /**
   * Add an action to the history.
   *
   * @param action the thing that we want to add to the stack.
   * @param <T> generic type.
   */
  public <T extends Object> void addAction(T action) {
    history.add(action);
  }

  /**
   * Save the current history into a json file.
   */
  public void saveReplay() {
    //Check to see the appropriate directory exists.
    if (!Files.isDirectory(Paths.get("src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Replays/"))) {
      System.out.println("The directory does not exist.");
      new File("src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Replays/").mkdir();
    } else {
      System.out.println("The directory exists");
    }

    System.out.println("Attempting to save the level.");

    //Create the file
    File replay = new File(
            "src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Replays/" + levelName + ".json");

    //Write the data to the file
    FileWriter writer = null;
    try {
      System.out.println("Level name is: " + levelName);

      writer = new FileWriter(replay, StandardCharsets.UTF_8);

      //Add the opening {
      writer.write("{\n");

      //Add the player moves
      writer.write("\t\"Player\" : {\n");

      for (int i = 0; i < history.size(); i++) {
        writer.write("\t\t\"" + i + "\" : \"" + history.get(i) + "\"");

        //Add a comma if needed
        if (i < history.size() - 1) {
          writer.write(",\n");
        } else {
          writer.write("\n");
        }
      }

      //Add the closing }
      writer.write("\t}\n}");
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
