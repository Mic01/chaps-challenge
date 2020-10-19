package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements.Node;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads.Dispatch;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads.ReplayThread;

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
  public void load(String filePath) {
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
  public void play(ApplicationView application, double timeScale) {
    //create the dispatch thread
    dispatchThread = new ReplayThread(application, baseNode, timeScale);
    dispatchThread.start();
    while (!dispatchThread.isComplete()); //Wait until the thread is done
    System.out.println("Thread done");
  }
}
