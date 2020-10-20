package nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements.Node;

/**
 * This class is used to dispatch the replays on a separate thread
 * so that the players can move at the same time.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public abstract class Dispatch extends Thread {
  Node baseNode;
  double timeScale;
  boolean complete;
  ApplicationView application;

  /**
   * One of the default constructors.
   *
   * @param application the current application
   *
   * @param baseNode the node containing all of the replay actions.
   *
   * @param timeScale the speed of the replay.
   */
  public Dispatch(ApplicationView application, Node baseNode, double timeScale) {
    this.baseNode = baseNode;
    this.timeScale = timeScale;
    this.application = application;
  }

  /**
   * Default constructor
   */
  protected Dispatch() {
  }

  /**
   * Used to check if the thread has finished execution.
   *
   * @return boolean indicating thread status.
   */
  public Boolean isComplete() {
    return complete;
  }
}
