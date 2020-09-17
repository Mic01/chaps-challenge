package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
    parseJson("src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Test Files/example_2.json");
    
    //Get json file and create the playback object
    Playback replay = new Playback(timeScale);
    
    //Return the replay
    return replay;
  }
  
  /**
   * Parse the json file
   *
   * @param filePath the file to parse
   */
  private void parseJson(String filePath) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(filePath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    scanner.useDelimiter(Regex.scanDelim);
    
    //Create the first parser object
    ParseJson parsed = new ParseJson("", null);
    
    //discard the opening '{'
    scanner.nextLine();
    parsed.parse(scanner);
  }
  
  /**
   * Save the current history into a json file.
   *
   * @param destination where the file is to be saved to.
   */
  public void saveReplay(String destination) {
  
  }
}
