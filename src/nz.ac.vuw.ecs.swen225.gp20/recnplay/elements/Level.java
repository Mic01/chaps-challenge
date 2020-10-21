package nz.ac.vuw.ecs.swen225.gp20.recnplay.elements;

import java.util.ArrayList;
import java.util.Objects;
import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.interfaces.Play;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.interfaces.Save;


/**
 * This class holds the levels as they are saved.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Level implements Play, Save {
  private final ArrayList<Action> actions = new ArrayList<Action>();
  String levelName;

  /**
   * Create a new level.
   *
   * @param levelName the name of this level
   */
  public Level(String levelName) {
    this.levelName = levelName;
  }
  
  /**
   * Add an action to this level.
   *
   * @param character the character to move
   *
   * @param action action to add.
   *
   * @param time the time between this move and the last one
   */
  public void addAction(String character, String action, long time) {
    actions.add(new Action(character, action, time));
  }

  /**
   * Return the level in word form.
   *
   * @return a string representation of the level
   */
  public String writeHistory() {
    StringBuilder toReturn = new StringBuilder();
    //Add the level name
    toReturn.append("\t\"" + levelName + "\" : {\n");
    
    //Add the player moves
    for (int i = 0; i < actions.size(); i++) {
      toReturn.append("\t\t\"" + i + "\": {\n");
      toReturn.append(actions.get(i).writeHistory());
      if (i < actions.size() - 1) {
        toReturn.append("\t\t},\n");
      } else {
        toReturn.append("\t\t}\n");
      }
    }

    //Add the closing }
    toReturn.append("\t}");
    return toReturn.toString();
  }

  /**
   * Compare this level to a different level.
   *
   * @param otherLevel the level to compare.
   *
   * @return indication of object equality.
   */
  @Override
  public boolean equals(Object otherLevel) {
    if (this == otherLevel) {
      return true;
    }
    if (otherLevel == null || getClass() != otherLevel.getClass()) {
      return false;
    }
    Level level = (Level) otherLevel;
    return Objects.equals(actions, level.actions) && Objects.equals(levelName, level.levelName);
  }

  /**
   * IntelliJ generated hashcode.
   *
   * @return the hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(actions, levelName);
  }

  /**
   * Get the number of actions that have been performed on this level.
   *
   * @return the size of the actions array
   */
  public int actionCount() {
    return actions.size();
  }

  /**
   * Go through each of the actions in this level and call the associated play method.
   *
   * @param application the current application.
   *
   * @param playback used to control the playback speed.
   */
  @Override
  public void play(ApplicationView application, Playback playback) {
    //System.out.println("Next level: " + levelName);
    for (Action action : actions) {
      action.play(application, playback);

      if (playback.isStep()) {
        playback.step(false, application);
        playback.pause();
      }
    }

    playback.markDone();
    application.stopTimers();

    //display popup
    application.makeReplayDialog();

    //System.out.println("Replay complete");
  }
}
