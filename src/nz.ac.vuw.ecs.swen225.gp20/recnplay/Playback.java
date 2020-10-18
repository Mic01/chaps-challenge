package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Load a replay file and dispatch the actions
 */
public class Playback {
  Node baseNode;
  Dispatch dispatchThread;

  /**
   * Load the replay from a json file.
   *
   * @param filePath the file to load.
   * @param timeScale The initial speed of the replay.
   * @return the playback object
   */
  public void load(String filePath, int timeScale) {
    parseJson(filePath);
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
    baseNode = new Node(map);

    System.out.println("Built nodes");
  }

  /**
   * Send the actions back and load levels when required.
   * @param timeScale
   */
  public void play(double timeScale) {
    //create the dispatch thread
    dispatchThread = new Dispatch(baseNode, timeScale);
    dispatchThread.start();
  }
}

/**
 * This class is used to dispatch the replays on a seperate thread so that the players can move at the same time.
 */
class Dispatch extends Thread {
  Node baseNode;
  double timeScale;

  public Dispatch(Node baseNode, double timeScale) {
    this.baseNode = baseNode;
    this.timeScale = timeScale;
  }

  @Override
  public synchronized void start() {
    baseNode.play();
  }
}
