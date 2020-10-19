package nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements.Node;

/**
 * This class is used to dispatch the replays on a separate thread so that the players can move at the same time.
 */
public abstract class Dispatch extends Thread {
  Node baseNode;
  double timeScale;
  boolean complete;
  ApplicationView application;

  public Dispatch(ApplicationView application, Node baseNode, double timeScale) {
    this.baseNode = baseNode;
    this.timeScale = timeScale;
    this.application = application;
  }

  public Dispatch (ApplicationView application) {
    this.application = application;
  }

  protected Dispatch() {
  }

  public Boolean isComplete() {
    return complete;
  }
}
