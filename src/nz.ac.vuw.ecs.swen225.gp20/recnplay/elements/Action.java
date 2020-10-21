package nz.ac.vuw.ecs.swen225.gp20.recnplay.elements;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.interfaces.Play;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.interfaces.Save;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.threads.ActionThread;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.threads.Dispatch;

/**
 * This class controls the characters that are used for replays and the time between moves.
 *
 * @author Luke Hawinkels: hawinkluke
 */

public class Action implements Play, Save {
  final long timeSinceLastMove;
  final String character;
  final String action;

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
    String toReturn = "\t\t\t\"Character\": " + "\"" + character + "\",\n" +
            "\t\t\t\"Action\": " + "\"" + action + "\",\n" +
            "\t\t\t\"Time\": " + timeSinceLastMove + "\n";
    return toReturn;
  }

  /**
   * Go through the game and pass the appropriate actions back to the application.
   *
   * @param application the current application object
   *
   * @param playback the current playback object
   */
  @Override
  public void play(ApplicationView application, Playback playback) {
    try {
      //Wait until the required time has elapsed
      System.out.println("Waited: " + timeSinceLastMove / playback.getCurrentSpeed());
      System.out.println("Time Scale: " + playback.getCurrentSpeed());
      System.out.println("Original wait time: " + timeSinceLastMove);
      System.out.println("Character: " + character);
      System.out.println("Action: " + action);
      System.out.println();

      Thread.sleep((long) (timeSinceLastMove / playback.getCurrentSpeed()));

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
