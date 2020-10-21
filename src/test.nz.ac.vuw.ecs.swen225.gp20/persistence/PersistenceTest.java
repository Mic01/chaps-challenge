package test.nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.IcePotion;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Treasure;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.WaterPotion;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Conveyor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Exit;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.ExitLock;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InfoTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.LockedDoor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.NullTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Vent;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Wall;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;
import levels.EnemyOne;
import levels.EnemyThree;
import levels.EnemyTwo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PersistenceTest {
  private final String fileName = "src/levels/PersistenceTest";
  // =================================================================
  // Tests
  // =================================================================

  @Test
  public void test_Load_01_Wall() {
    int x = 5;
    int y = 5;
    // Creating json wall tile object
    saveFile("{\"tile\": \"Wall\",\"x\": "+x+",\"y\": "+y+"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof Wall);
  }

  @Test
  public void test_Load_02_FreeTile() {
    int x = 6;
    int y = 3;
    // Creating json free tile object with an empty slot
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Null\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
  }

  @Test
  public void test_Load_03_FreeTile() {
    int x = 5;
    int y = 36;
    // Creating json free tile object with a treasure item
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Chip\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getItem() instanceof Treasure);
  }

  @Test
  public void test_Load_04_FreeTile() {
    int x = 35;
    int y = 22;
    // Creating json free tile object with a green key item
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Key\",\"colour\": \"Green\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getItem() instanceof Key);
    assertEquals(((Key)((FreeTile)maze.getTile(x,y)).getItem()).getColour(),"green");
  }

  @Test
  public void test_Load_05_FreeTile() {
    int x = 11;
    int y = 23;
    // Creating json free tile object with a IcePotion
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Shoe\",\"element\": \"Ice\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getItem() instanceof IcePotion);
  }

  @Test
  public void test_Load_06_FreeTile() {
    int x = 5;
    int y = 44;
    // Creating json free tile object with a WaterPotion
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Shoe\",\"element\": \"Water\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getItem() instanceof WaterPotion);
  }

  @Test
  public void test_Load_07_LockedDoor() {
    int x = 6;
    int y = 3;
    // Creating json vertical Locked Door tile object
    saveFile("{\"tile\": \"Door\",\"x\": "+x+",\"y\": "+y+",\"colour\": \"Red\",\"vertical\": true,\"open\": false}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof LockedDoor);
    assertEquals(((LockedDoor)maze.getTile(x,y)).getColour(),"red");
    assertTrue(((LockedDoor)maze.getTile(x,y)).isVertical());
    assertFalse(((LockedDoor)maze.getTile(x,y)).isOpen());
  }

  @Test
  public void test_Load_08_ExitLock() {
    int x = 35;
    int y = 55;
    // Creating json vertical ExitLock tile object
    saveFile("{\"tile\": \"Lock\",\"x\": "+x+",\"y\": "+y+",\"chips\": 1,\"colour\": \"Red\",\"vertical\": true,\"open\": false}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof ExitLock);
    assertEquals(((ExitLock)maze.getTile(x,y)).getTreasuresNeeded(),1);
    assertTrue(((ExitLock)maze.getTile(x,y)).isVertical());
    assertFalse(((ExitLock)maze.getTile(x,y)).isOpen());
  }

  @Test
  public void test_Load_09_Exit() {
    int x = 12;
    int y = 25;
    // Creating json vertical Exit tile object and time limit
    saveFile("{\"tile\": \"End\",\"x\": "+x+",\"y\": "+y+",\"time\": 120}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof Exit);
    assertEquals(maze.getTimeLimit(),120);
  }

  @Test
  public void test_Load_10_Water() {
    int x = 42;
    int y = 4;
    // Creating json water tile object
    saveFile("{\"tile\": \"Water\",\"x\": "+x+",\"y\": "+y+"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof Water);
  }

  @Test
  public void test_Load_11_Ice() {
    int x = 2;
    int y = 45;
    // Creating json ice tile object
    saveFile("{\"tile\": \"Ice\",\"x\": "+x+",\"y\": "+y+"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof Ice);
  }

  @Test
  public void test_Load_12_InfoTile() {
    int x = 4;
    int y = 34;
    // Creating json info tile object
    saveFile("{\"tile\": \"Info\",\"x\": "+x+",\"y\": "+y+",\"text\": \"Howdy\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof InfoTile);
    assertEquals(((InfoTile)maze.getTile(x,y)).getInfo(),"Howdy");
  }

  @Test
  public void test_Load_13_Conveyor() {
    int x = 12;
    int y = 16;
    // Creating json conveyor tile object with direction up
    saveFile("{\"tile\": \"Conveyor\",\"x\": "+x+",\"y\": "+y+",\"direction\": \"up\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof Conveyor);
    assertEquals(((Conveyor)maze.getTile(x,y)).getDirection().toString(),"up");
  }

  @Test
  public void test_Load_14_Vent() {
    int x = 31;
    int y = 19;
    // Creating json Vent tile object
    saveFile("{\"tile\": \"Vent\",\"x\": "+x+",\"y\": "+y+",\"x1\": 5,\"y1\": 2}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof Vent);
    assertEquals(((Vent)maze.getTile(x,y)).getTargetX(),5);
    assertEquals(((Vent)maze.getTile(x,y)).getTargetY(),2);
  }

  @Test
  public void test_Load_15_Player() {
    int x = 38;
    int y = 29;
    // Creating json player on freeTile tile object with empty inventory
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Player\",\"inventory\": []}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getActor() instanceof Player);
    assertTrue(((Player)((FreeTile)maze.getTile(x,y)).getActor()).getInventory().isEmpty());
  }

  @Test
  public void test_Load_16_Player() {
    int x = 38;
    int y = 29;
    // Creating json player on freeTile tile object with an item in there inventory
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Player\",\"inventory\": [{\"slot\": \"Item\",\n" +
            "\t\t\t\"type\": \"Shoe\",\n" +
            "\t\t\t\"element\": \"Ice\"}]}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getActor() instanceof Player);
    assertEquals(((Player)((FreeTile)maze.getTile(x,y)).getActor()).getInventory().size(),1);
  }

  @Test
  public void test_Load_17_Enemy() {
    int x = 22;
    int y = 9;
    // Creating json enemy type one on freeTile tile object facing left
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Enemy\",\"type\": \"One\",\"direction\": \"left\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getActor() instanceof EnemyOne);
    assertEquals(((AutoActor)((FreeTile)maze.getTile(x,y)).getActor()).getCurrentDirection().toString(), "left");
  }

  @Test
  public void test_Load_18_Enemy() {
    int x = 44;
    int y = 5;
    // Creating json enemy type two on freeTile tile object facing down
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Enemy\",\"type\": \"Two\",\"direction\": \"down\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getActor() instanceof EnemyTwo);
    assertEquals(((AutoActor)((FreeTile)maze.getTile(x,y)).getActor()).getCurrentDirection().toString(), "down");
  }

  @Test
  public void test_Load_19_Enemy() {
    int x = 31;
    int y = 10;
    // Creating json enemy type three on freeTile tile object facing right
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Enemy\",\"type\": \"Three\",\"direction\": \"right\"}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)maze.getTile(x,y)).getActor() instanceof EnemyThree);
    assertEquals(((AutoActor)((FreeTile)maze.getTile(x,y)).getActor()).getCurrentDirection().toString(), "right");
  }

  @Test
  public void test_Load_20_Floor() {
    int x = 40;
    int y = 40;
    // Creating json enemy type three on freeTile tile object facing right
    saveFile("{\"tile\": \"Inside\",\"x1\": "+x+",\"y1\": "+y+",\"x2\": 50,\"y2\": 50}");
    Maze maze = new Maze(fileName);
    assertTrue(maze.getTile(x,y) instanceof FreeTile);
  }

  @Test
  public void test_Save_01_Wall() {
    int x = 5;
    int y = 5;
    // Creating json wall tile object
    saveFile("{\"tile\": \"Wall\",\"x\": "+x+",\"y\": "+y+"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof Wall);


  }

  @Test
  public void test_Save_02_FreeTile() {
    int x = 6;
    int y = 3;
    // Creating json free tile object with an empty slot
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Null\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
  }

  @Test
  public void test_Save_03_FreeTile() {
    int x = 5;
    int y = 36;
    // Creating json free tile object with a treasure item
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Chip\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getItem() instanceof Treasure);
  }

  @Test
  public void test_Save_04_FreeTile() {
    int x = 35;
    int y = 22;
    // Creating json free tile object with a green key item
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Key\",\"colour\": \"Green\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getItem() instanceof Key);
    assertEquals(((Key)((FreeTile)mazeSave.getTile(x,y)).getItem()).getColour(),"green");
  }

  @Test
  public void test_Save_05_FreeTile() {
    int x = 11;
    int y = 23;
    // Creating json free tile object with a IcePotion
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Shoe\",\"element\": \"Ice\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getItem() instanceof IcePotion);
  }

  @Test
  public void test_Save_06_FreeTile() {
    int x = 5;
    int y = 44;
    // Creating json free tile object with a WaterPotion
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Item\",\"type\": \"Shoe\",\"element\": \"Water\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getItem() instanceof WaterPotion);
  }

  @Test
  public void test_Save_07_LockedDoor() {
    int x = 6;
    int y = 3;
    // Creating json vertical Locked Door tile object
    saveFile("{\"tile\": \"Door\",\"x\": "+x+",\"y\": "+y+",\"colour\": \"Red\",\"vertical\": true,\"open\": false}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof LockedDoor);
    assertEquals(((LockedDoor)mazeSave.getTile(x,y)).getColour(),"red");
    assertTrue(((LockedDoor)mazeSave.getTile(x,y)).isVertical());
    assertFalse(((LockedDoor)mazeSave.getTile(x,y)).isOpen());
  }

  @Test
  public void test_Save_08_ExitLock() {
    int x = 35;
    int y = 55;
    // Creating json vertical ExitLock tile object
    saveFile("{\"tile\": \"Lock\",\"x\": "+x+",\"y\": "+y+",\"chips\": 1,\"colour\": \"Red\",\"vertical\": true,\"open\": false}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof ExitLock);
    assertEquals(((ExitLock)mazeSave.getTile(x,y)).getTreasuresNeeded(),1);
    assertTrue(((ExitLock)mazeSave.getTile(x,y)).isVertical());
    assertFalse(((ExitLock)mazeSave.getTile(x,y)).isOpen());
  }

  @Test
  public void test_Save_09_Exit() {
    int x = 12;
    int y = 25;
    // Creating json vertical Exit tile object and time limit
    saveFile("{\"tile\": \"End\",\"x\": "+x+",\"y\": "+y+",\"time\": 120}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof Exit);
    assertEquals(mazeSave.getTimeLimit(),120);
  }

  @Test
  public void test_Save_10_Water() {
    int x = 42;
    int y = 4;
    // Creating json water tile object
    saveFile("{\"tile\": \"Water\",\"x\": "+x+",\"y\": "+y+"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof Water);
  }

  @Test
  public void test_Save_11_Ice() {
    int x = 2;
    int y = 45;
    // Creating json ice tile object
    saveFile("{\"tile\": \"Ice\",\"x\": "+x+",\"y\": "+y+"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof Ice);
  }

  @Test
  public void test_Save_12_InfoTile() {
    int x = 4;
    int y = 34;
    // Creating json info tile object
    saveFile("{\"tile\": \"Info\",\"x\": "+x+",\"y\": "+y+",\"text\": \"Howdy\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof InfoTile);
    assertEquals(((InfoTile)mazeSave.getTile(x,y)).getInfo(),"Howdy");
  }

  @Test
  public void test_Save_13_Conveyor() {
    int x = 12;
    int y = 16;
    // Creating json conveyor tile object with direction up
    saveFile("{\"tile\": \"Conveyor\",\"x\": "+x+",\"y\": "+y+",\"direction\": \"up\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof Conveyor);
    assertEquals(((Conveyor)maze.getTile(x,y)).getDirection().toString(),"up");
  }

  @Test
  public void test_Save_14_Vent() {
    int x = 31;
    int y = 19;
    // Creating json Vent tile object
    saveFile("{\"tile\": \"Vent\",\"x\": "+x+",\"y\": "+y+",\"x1\": 5,\"y1\": 2}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof Vent);
    assertEquals(((Vent)mazeSave.getTile(x,y)).getTargetX(),5);
    assertEquals(((Vent)mazeSave.getTile(x,y)).getTargetY(),2);
  }

  @Test
  public void test_Save_15_Player() {
    int x = 38;
    int y = 29;
    // Creating json player on freeTile tile object with empty inventory
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Player\",\"inventory\": []}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getActor() instanceof Player);
    assertTrue(((Player)((FreeTile)mazeSave.getTile(x,y)).getActor()).getInventory().isEmpty());
  }

  @Test
  public void test_Save_16_Player() {
    int x = 38;
    int y = 29;
    // Creating json player on freeTile tile object with an item in there inventory
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Player\",\"inventory\": [{\"slot\": \"Item\",\n" +
            "\t\t\t\"type\": \"Shoe\",\n" +
            "\t\t\t\"element\": \"Ice\"}]}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getActor() instanceof Player);
    assertEquals(((Player)((FreeTile)mazeSave.getTile(x,y)).getActor()).getInventory().size(),1);
  }

  @Test
  public void test_Save_17_Enemy() {
    int x = 22;
    int y = 9;
    // Creating json enemy type one on freeTile tile object facing left
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Enemy\",\"type\": \"One\",\"direction\": \"left\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getActor() instanceof EnemyOne);
    assertEquals(((AutoActor)((FreeTile)mazeSave.getTile(x,y)).getActor()).getCurrentDirection().toString(), "left");
  }

  @Test
  public void test_Save_18_Enemy() {
    int x = 44;
    int y = 5;
    // Creating json enemy type two on freeTile tile object facing down
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Enemy\",\"type\": \"Two\",\"direction\": \"down\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getActor() instanceof EnemyTwo);
    assertEquals(((AutoActor)((FreeTile)mazeSave.getTile(x,y)).getActor()).getCurrentDirection().toString(), "down");
  }

  @Test
  public void test_Save_19_Enemy() {
    int x = 31;
    int y = 10;
    // Creating json enemy type three on freeTile tile object facing right
    saveFile("{\"tile\": \"Floor\",\"x\": "+x+",\"y\": "+y+",\"slot\": \"Enemy\",\"type\": \"Three\",\"direction\": \"right\"}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
    assertTrue(((FreeTile)mazeSave.getTile(x,y)).getActor() instanceof EnemyThree);
    assertEquals(((AutoActor)((FreeTile)mazeSave.getTile(x,y)).getActor()).getCurrentDirection().toString(), "right");
  }

  @Test
  public void test_Save_20_Floor() {
    int x = 40;
    int y = 40;
    // Creating json enemy type three on freeTile tile object facing right
    saveFile("{\"tile\": \"Inside\",\"x1\": "+x+",\"y1\": "+y+",\"x2\": 50,\"y2\": 50}");
    Maze maze = new Maze(fileName);
    maze.save(fileName);
    Maze mazeSave = new Maze(fileName);
    assertTrue(mazeSave.getTile(x,y) instanceof FreeTile);
  }




  /**
   *
   * @param output
   */
  public static void saveFile(String output) {
    File file = new File("src/levels/PersistenceTest");
    OutputStreamWriter writer;
    try {
      writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
      writer.write(output);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();

    }
  }
}
