package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.util.Scanner;

class ParseJson {
  private String name;
  private Object value;
  
  /**
   * Create a new parse Json object
   *
   * @param name name of the value
   * @param value the value itself
   */
  public ParseJson(String name, Object value) {
    this.name = name;
    this.value = value;
  }
  
  public void parse(Scanner scanner) {
    while (scanner.hasNext()) {
      if (scanner.hasNext(Regex.name)) {
        System.out.println("Name found");
      }
      System.out.println(scanner.next());
    }
  }
}
