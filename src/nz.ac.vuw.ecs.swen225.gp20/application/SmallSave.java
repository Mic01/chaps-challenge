package nz.ac.vuw.ecs.swen225.gp20.application;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SmallSave {

  public SmallSave(){
  }

  /**
   * Loads a basic save file, telling the program what level to load.
   *
   * @param f - File to load.
   * @return - Integer representing the level to be loaded.
   * @throws FileNotFoundException - Throws an exception if the file isn't found.
   */
  public static int loadFile(File f) throws IOException {
    Scanner findLevel = new Scanner(f, StandardCharsets.UTF_8);
    int level = 1;
    while (findLevel.hasNextInt()) {
      level = findLevel.nextInt();
    }
    return level;
  }

  /**
   * Saves the current active or next unfinished level.
   *
   * @param level - Integer representing the current active or next unfinished level.
   * @throws IOException - Throws exception on IO failure.
   */
  public static void saveFile(int level) throws IOException {
    File save = new File("./tmp_save.txt");
    if (save.isFile()) {
      if (!save.delete()) {
        System.out.println("Whoops, this file didn't load!");
      }
    }
    FileOutputStream outputStream = new FileOutputStream(save);
    OutputStreamWriter output = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    output.write("" + level);
    output.close();
  }

  /**
   * Removes the save file.
   */
  public static void removeFile() {
    File save = new File("./tmp_save.txt");
    if (save.isFile()) {
      if (!save.delete()) {
        System.out.println("Whoops, something went wrong here!");
      }
    }
  }
}
