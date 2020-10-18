package nz.ac.vuw.ecs.swen225.gp20.recnplay;

/**
 * This class controls the characters that are used for replays and the time between moves.
 */

public class Action {
  long timeSinceLastMove;
  String character;
  String action;
  
  
  public Action(String character, String action, long time) {
    this.character = character;
    this.action = action;
    this.timeSinceLastMove = time;
  }
  
  public String writeHistory() {
    StringBuilder toReturn = new StringBuilder();
    toReturn.append("\t\t\t\"Character\": " + "\"" + character + "\",\n");
    toReturn.append("\t\t\t\"Action\": " + "\"" + action + "\",\n");
    toReturn.append("\t\t\t\"Time\": " + timeSinceLastMove + "\n");
    return toReturn.toString();
  }
}
