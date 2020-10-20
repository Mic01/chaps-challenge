package nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements.Node;

/**
 * This class handles the the replay.
 * It runs the replay on a new thread and allows
 * for a thread to be created for each new action
 * when required.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class ReplayThread extends Dispatch {

  /**
   * Create the new thread.
   *
   * @param application the current application.
   *
   * @param baseNode the node that holds all of the levels and actions
   *
   * @param timeScale hoe fast should the replay be
   */
  public ReplayThread(ApplicationView application, Node baseNode, double timeScale) {
    super(application, baseNode, timeScale);
  }

  /**
   * Run the replay.
   */
  @Override
  public synchronized void start() {
    baseNode.play(application, timeScale);
    complete = true;
  }
}
