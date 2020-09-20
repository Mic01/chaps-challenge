package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * This class is responsible for creating, loading and saving replays.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Replay {
  private ArrayList history = new ArrayList();
  Playback replay;
  
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
   * Load the replay from a json file.
   *
   * @param filePath the file to load.
   * @param timeScale The initial speed of the replay.
   * @return the playback object
   */
  public Playback loadReplay(String filePath, int timeScale) {
    parseJson("src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Test Files/testJson.json");

    //Get json file and create the playback object
    Playback replay = new Playback(timeScale);

    //Return the replay
    return replay;
  }

  /**
   * Parse the json file.
   *
   * @param filePath the file to parseObject
   */
  private void parseJson(String filePath) {
    File file = new File("src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Test Files/testJson.json");
    FileReader toRead = null;
    try {
      toRead = new FileReader(file, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    LinkedTreeMap map = new Gson().fromJson(toRead, LinkedTreeMap.class);

    System.out.println("Built map");

    //Make the required objects
    Node baseNode = new Node(map);

    System.out.println("Built nodes");

  }
  
  /**
   * Save the current history into a json file.
   *
   * @param destination where the file is to be saved to.
   */
  public void saveReplay(String destination) {
  
  }
}
