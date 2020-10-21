package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.elements.Node;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.threads.Dispatch;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.threads.ReplayThread;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.threads.WaitThread;

/**
 * Load a replay file and dispatch the actions.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Playback {
  Node baseNode;
  Dispatch dispatchThread;
  boolean pause = false;
  boolean step = false;
  boolean running = false;
  boolean isDone;
  private double speed = 1.0;

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
   * @param application the current application
   */
  public void play(ApplicationView application) {
    //Check if the replay is already running.
    if (!running) {
      running = true;

      //create the dispatch thread
      dispatchThread = new ReplayThread(application, baseNode, this);
      dispatchThread.start();

      //Create a thread that is used to wait for the replay to finish
      WaitThread wait = new WaitThread(dispatchThread);
      wait.start();
    } else {
      System.out.println("Replay is already active");
    }
  }

  /**
   * Pause the replay.
   */
  public void pause() {
    System.out.println("Attempting to pause the replay.");
    this.pause = true;
    this.running = false;
    dispatchThread.suspend();
  }

  /**
   * Resume the replay.
   */
  public void resume(ApplicationView application) {
    System.out.println("Resuming replay");
    this.pause = false;

    //Check to see if the thread has been started before
    if (dispatchThread != null) {
      this.running = true;
      dispatchThread.resume();
    } else {
      this.running = false;
      play(application);
    }
  }

  /**
   * Check if the replay is currently paused.
   *
   * @return boolean indicating status.
   */
  public boolean isPaused() {
    return this.pause;
  }

  /**
   * Step through the replay.
   */
  public void step(Boolean step, ApplicationView application) {
    this.step = step;

    if (step) {
      resume(application);
    }
  }

  /**
   * check if stepping.
   */
  public boolean isStep() {
    return step;
  }

  /**
   * Check if the replay is running.
   *
   * @return the running status.
   */
  public boolean isRunning() {
    return this.running;
  }

  /**
   * Get the current replay speed.
   *
   * @return the replay speed;
   */
  public double getCurrentSpeed() {
    return speed;
  }

  /**
   * Set the replay speed.
   *
   * @param speed the new speed
   */
  public void setReplaySpeed(double speed) {
    this.speed = speed;
  }

  /**
   * Mark the replay as complete.
   */
  public void markDone() {
    isDone = true;
  }

  /**
   * check if the replay is complete.
   */
  public boolean isDone() {
    return isDone;
  }
}
