package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;

/**
 * This class handles all of the nodes that will be used to run replays.
 */
public class Node {
  String key;
  ArrayList<Object> values = new ArrayList<>(); //todo shouldn't be object

  /**
   * Recursively build the set of objects.
   *
   * @param map the map
   */
  public Node(LinkedTreeMap map) {
    for (Object key: map.keySet()) {
      //Add the key
      this.key = key.toString();

      if (map.get(key) instanceof com.google.gson.internal.LinkedTreeMap) {
        values.add(new Node((LinkedTreeMap) map.get(key)));
      }
    }
  }

  /**
   * Get the key.
   *
   * @return String containing the key
   */
  public String getKey() {
    return this.key;
  }

  /**
   * Get the values.
   *
   * @return Arraylist containing all of the values
   */
  public ArrayList<Object> getValues() {
    return this.values;
  }
}
