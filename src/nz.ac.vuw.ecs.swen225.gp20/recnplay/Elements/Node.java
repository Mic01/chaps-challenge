package nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements;

import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;
import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Interfaces.Play;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;

/**
 * This class handles all of the nodes that will be used to run replays.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Node implements Play {
  ArrayList<Level> levels = new ArrayList<>(); //todo shouldn't be object

  /**
   * Recursively build the map into an array of levels.
   *
   * @param map the map that has resulted from the parsed json file
   */
  public Node(LinkedTreeMap map) {
    parseLevels(map);
  }

  /**
   * Parse the levels from the map.
   *
   * @param map Linked tree map, as a result of gson parsing the json file
   */
  private void parseLevels(LinkedTreeMap map) {
    for (Object key : map.keySet()) {
      if (map.get(key) instanceof com.google.gson.internal.LinkedTreeMap) {
        Level newLevel = new Level(key.toString());

        //Parse the associated actions
        parseActions(newLevel, (LinkedTreeMap) map.get(key));

        levels.add(newLevel);
      }
    }
  }

  /**
   * Parse the actions contained within each level.
   *
   * @param newLevel the level that the actions belong to.
   *
   * @param actions map containing the actions parsed from the json file.
   */
  private void parseActions(Level newLevel, LinkedTreeMap actions) {
    for (Object key : actions.keySet()) {
      LinkedTreeMap actionParams = (LinkedTreeMap) actions.get(key);

      String character = "";
      String action = "";
      long time = 0;
      for (Object param : actionParams.keySet()) {
        if (param.toString().equals("Character")) {
          character = actionParams.get(param).toString();
        } else if (param.toString().equals("Action")) {
          action = actionParams.get(param).toString();
        } else {
          time = (long) Double.parseDouble(actionParams.get(param).toString());
        }
      }

      newLevel.addAction(character, action, time);
    }
  }


  /**
   * Go through the array of levels and play them.
   *
   * @param application the current application.
   */
  @Override
  public void play(ApplicationView application, Playback playback) {
    for (Level level : levels) {
      level.play(application, playback);
    }
  }
}
