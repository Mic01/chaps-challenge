package nz.ac.vuw.ecs.swen225.gp20.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SmallSave {

  public SmallSave(){
  }

  public static int loadFile(File f) throws FileNotFoundException {
    Scanner findLevel = new Scanner(f);
    int level = 1;
    while(findLevel.hasNextInt()){
      level = findLevel.nextInt();
    }
    return level;
  }

  public static void saveFile(int level) throws IOException {
    File save = new File("./tmp_save.txt");
    if(save.isFile()){
      save.delete();
    }
    FileWriter output = new FileWriter(save, false);
    output.write("" + level);
    output.close();
  }

  public static void removeFile() {
    File save = new File("./tmp_save.txt");
    if(save.isFile()){
      save.delete();
    }
  }
}
