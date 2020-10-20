package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements.Node;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads.Dispatch;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads.ReplayThread;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads.WaitThread;

/**
 * Load a replay file and dispatch the actions.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Playback {
  Node baseNode;
  Dispatch dispatchThread;
  boolean pause = false;

  /**
   * Load the replay from a json file.
   *
   * @param filePath the file to load.
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
   *
   * @param timeScale the replay speed
   */
  public void play(ApplicationView application, double timeScale) {
    //create the dispatch thread
    dispatchThread = new ReplayThread(application, baseNode, timeScale, this);
    dispatchThread.start();

    //Create a thread that is used to wait for the replay to finish
    WaitThread wait = new WaitThread(dispatchThread);
    wait.start();
  }

  /**
   * Pause the replay.
   */
  public void pause() {
    System.out.println("Attempting to pause the replay.");
    this.pause = true;
    dispatchThread.suspend();
  }

  /**
   * Resume the replay.
   */
  public void resume() {
    System.out.println("Resuming replay");
    this.pause = false;
    dispatchThread.resume();
  }

  /**
   * Check if the replay is currently paused.
   *
   * @return boolean indicating status.
   */
  public boolean isPaused() {
    return this.pause;
  }
}
