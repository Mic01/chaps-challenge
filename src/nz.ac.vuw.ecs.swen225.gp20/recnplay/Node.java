package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;

/**
 * This class handles all of the nodes that will be used to run replays.
 */
public class Node {
  ArrayList<Level> levels = new ArrayList<>(); //todo shouldn't be object

  /**
   * Recursively build the map into an array of levels
   * @param map
   */
  public Node(LinkedTreeMap map) {
    parseLevels(map);
  }

  private void parseLevels( LinkedTreeMap map) {
    for (Object key: map.keySet()) {
      if (map.get(key) instanceof com.google.gson.internal.LinkedTreeMap) {
        Level newLevel = new Level(key.toString());

        //Parse the associated actions
        parseActions(newLevel, (LinkedTreeMap) map.get(key));

        levels.add(newLevel);
      }
    }
  }

  private void parseActions(Level newLevel, LinkedTreeMap actions) {
    for (Object key: actions.keySet()) {
      LinkedTreeMap actionParams = (LinkedTreeMap) actions.get(key);

      String character = new String();
      String action = new String();
      long time = 0;
      for (Object param: actionParams.keySet()) {
        if (param.toString().equals("Character")) character = actionParams.get(param).toString();
        else if (param.toString().equals("Action")) action = actionParams.get(param).toString();
        else time = (long) Double.parseDouble(actionParams.get(param).toString());
      }

      newLevel.addAction(character, action, time);
    }
  }
}
