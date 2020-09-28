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
  String levelName;

  public Replay (String levelName) {
    this.levelName = levelName;
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
   * Parse the json file.
   *
   * @param filePath the file to parseObject
   */
  private void parseJson(String filePath) {
    File file = new File(filePath);
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
