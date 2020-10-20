package nz.ac.vuw.ecs.swen225.gp20.recnplay.Interfaces;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;

/**
 * Interface for classes that run the replay.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public interface Play {
  public void play(ApplicationView application, double timeScale);
}
