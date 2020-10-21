package nz.ac.vuw.ecs.swen225.gp20.recnplay.threads;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.elements.Node;

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
   * @param playback used to control play-pause functionality.
   */
  public ReplayThread(ApplicationView application, Node baseNode, Playback playback) {
    super(application, baseNode, playback);
  }

  /**
   * Run the replay.
   */
  @Override
  public synchronized void run() {
    System.out.println("Started a new replay thread.");
    baseNode.play(application, playback);
    complete = true;
  }
}
