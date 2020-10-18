package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class holds the levels as they are saved
 */
public class Level {
  private final ArrayList<Action> history = new ArrayList<Action>();
  String levelName;
  
  Level(String levelName) {
    this.levelName = levelName;
  }
  
  /**
   * Add an action to this level
   * @param action action to add
   */
  public void addAction(String character, String action, long time) {
    history.add(new Action(character, action, time));
  }
  
  public String writeHistory() {
    StringBuilder toReturn = new StringBuilder();
    //Add the level name
    toReturn.append("\t\"" + levelName + "\" : {\n");
    
    //Add the player moves
    for (int i = 0; i < history.size(); i++) {
      toReturn.append("\t\t\"" + i + "\": {\n");
      toReturn.append(history.get(i).writeHistory());
      if (i < history.size() - 1) toReturn.append("\t\t},\n");
      else toReturn.append("\t\t}\n");
    }

    //Add the closing }
    toReturn.append("\t}");
    return toReturn.toString();
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Level level = (Level) o;
    return Objects.equals(history, level.history) &&
            Objects.equals(levelName, level.levelName);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(history, levelName);
  }
  
  public int actionCount() {
    return history.size();
  }
}
