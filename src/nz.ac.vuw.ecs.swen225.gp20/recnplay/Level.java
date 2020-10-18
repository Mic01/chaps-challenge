package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.recnplay.Interfaces.Play;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Interfaces.Save;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class holds the levels as they are saved
 */
public class Level implements Play, Save {
  private final ArrayList<Action> actions = new ArrayList<Action>();
  String levelName;
  
  Level(String levelName) {
    this.levelName = levelName;
  }
  
  /**
   * Add an action to this level
   * @param action action to add
   */
  public void addAction(String character, String action, long time) {
    actions.add(new Action(character, action, time));
  }
  
  public String writeHistory() {
    StringBuilder toReturn = new StringBuilder();
    //Add the level name
    toReturn.append("\t\"" + levelName + "\" : {\n");
    
    //Add the player moves
    for (int i = 0; i < actions.size(); i++) {
      toReturn.append("\t\t\"" + i + "\": {\n");
      toReturn.append(actions.get(i).writeHistory());
      if (i < actions.size() - 1) toReturn.append("\t\t},\n");
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
    return Objects.equals(actions, level.actions) &&
            Objects.equals(levelName, level.levelName);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(actions, levelName);
  }
  
  public int actionCount() {
    return actions.size();
  }

  /**
   * Play all of the actions
   */
  public void play() {
    System.out.println("Next level: " + levelName);
    for (Action action: actions) {
      action.play();
    }
  }
}
