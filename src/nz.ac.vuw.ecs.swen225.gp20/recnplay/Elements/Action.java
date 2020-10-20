package nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Interfaces.Play;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Interfaces.Save;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads.ActionThread;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads.Dispatch;

/**
 * This class controls the characters that are used for replays and the time between moves.
 *
 * @author Luke Hawinkels: hawinkluke
 */

public class Action implements Play, Save {
  long timeSinceLastMove;
  String character;
  String action;

  /**
   * This class handles the processing of actions.
   *
   * @param character the character that performed the action
   *
   * @param action the action that was performed
   *
   * @param time the time between the previous action and this one
   */
  public Action(String character, String action, long time) {
    this.character = character;
    this.action = action;
    this.timeSinceLastMove = time;
  }

  /**
   * Return the action in word form.
   *
   * @return a String representation of the action that was taken
   */
  public String writeHistory() {
    StringBuilder toReturn = new StringBuilder();
    toReturn.append("\t\t\t\"Character\": " + "\"" + character + "\",\n");
    toReturn.append("\t\t\t\"Action\": " + "\"" + action + "\",\n");
    toReturn.append("\t\t\t\"Time\": " + timeSinceLastMove + "\n");
    return toReturn.toString();
  }

  /**
   * Go through the game and pass the appropriate actions back to the application.
   *
   * @param application the current application object
   *
   * @param timeScale the speed of the replay
   */
  @Override
  public void play(ApplicationView application, double timeScale, Playback playback) {
    try {
      //Wait until the required time has elapsed
      Thread.sleep(calcWaitTime(timeSinceLastMove, timeScale));
//      System.out.println("Time Scale: " + timeScale);
//      System.out.println("Waited: " + calcWaitTime(timeSinceLastMove, timeScale));
//      System.out.println("Original wait time: " + timeSinceLastMove);
//      System.out.println("Character: " + character);
//      System.out.println("Action: " + action);
//      System.out.println();

      if (character.equals("player")) {
        //Create a new thread and execute the move
        Dispatch movePlayer = new ActionThread(application, calcMove(action));
        movePlayer.start();
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Calculate how long to wait.
   *
   * @param timeSinceLastMove how long to wait in real time
   *
   * @param timeScale how much to speed up by
   *
   * @return the modified waiting time
   */
  private long calcWaitTime(long timeSinceLastMove, double timeScale) {
    if (timeScale <= 1) {
      return timeSinceLastMove;
    } else if (timeScale > 5) {
      return (long) (timeSinceLastMove / (2 * 5));
    }
    return (long) (timeSinceLastMove / (2 * timeScale));
  }

  /**
   * Calculate the appropriate int corresponding the the play move.
   *
   * @return the correct int
   */
  private int calcMove(String action) {
    if (action.equals("moveUp")) {
      return 1;
    } else if (action.equals("moveDown")) {
      return 2;
    } else if (action.equals("moveLeft")) {
      return 3;
    } else {
      return 4;
    }
  }
}
