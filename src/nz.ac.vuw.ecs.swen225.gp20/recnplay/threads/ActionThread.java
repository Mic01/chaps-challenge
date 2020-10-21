package nz.ac.vuw.ecs.swen225.gp20.recnplay.threads;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;

/**
 * This class handles calling the move method in application.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class ActionThread extends Dispatch {
  final int move;

  /**
  * Create a new action thread.
  *
  * @param application the current application.
  *
  * @param move the type of move to perform.
  */
  public ActionThread(ApplicationView application, int move) {
    this.move = move;
    this.application = application;
  }

  /**
   * Make the move.
   */
  @Override
  public synchronized void run() {
    application.playerMovement(move, true);
  }
}
