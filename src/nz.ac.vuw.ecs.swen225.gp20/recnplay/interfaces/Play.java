package nz.ac.vuw.ecs.swen225.gp20.recnplay.interfaces;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;

/**
 * Interface for classes that run the replay.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public interface Play {
  void play(ApplicationView application, Playback playback);
}
