package recnplay;

import java.util.ArrayList;

/**
 * This class is responsible for creating, loading and saving replays.
 *
 * @author Luke Hawinkels: hawinkluke
 */
public class Replay {
  private ArrayList history = new ArrayList();
  Playback replay;
  
  /**
   * Add an action to the history.
   *
   * @param action the thing that we want to add to the stack.
   * @param <T> generic type.
   */
  public <T extends Object> void addAction(T action) {
    history.add(action);
  }
  
  /**
   * Load the replay from a json file.
   *
   * @param filePath the file to load.
   * @param timeScale The initial speed of the replay.
   * @return the playback object
   */
  public Playback loadReplay(String filePath, int timeScale) {
    //Get json file and create the playback object
    Playback replay = new Playback(timeScale);
    
    //Return the replay
    return replay;
  }
  
  /**
   * Save the current history into a json file.
   *
   * @param destination where the file is to be saved to.
   */
  public void saveReplay(String destination) {
  
  }
}
