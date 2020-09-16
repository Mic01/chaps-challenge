package recnplay;

/**
 * This class is responsible for playing replays that have been
 * loaded from a json file.
 *
 * @author Luke Hawinkels: hawinkluke
 */

class Playback {
  private int timeScale;
  
  /**
   * This class is responsible for playing the replay.
   *
   * @param timeScale the speed at which to play.
   */
  public Playback(int timeScale) {
  }
  
  /**
   * Start to play the replay.
   */
  public void play() {
    //This should play on its own thread, such the the user 
    // can play/pause/rewind without having to wait for the thread.
  }
  
  /**
   * Pause the replay.
   */
  public void pause() {
  }
  
  /**
   * Resume the replay.
   */
  public void resume() {
  }
  
  /**
   * Make the replay faster.
   */
  public void increaseSpeed() {
  }
  
  /**
   * Make the replay slower.
   */
  public void decreaseSpeed() {
  }
}
