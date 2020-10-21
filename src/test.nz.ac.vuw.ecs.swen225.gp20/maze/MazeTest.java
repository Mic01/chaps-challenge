package test.nz.ac.vuw.ecs.swen225.gp20.maze;

import java.io.IOException;
import java.util.List;
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
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Vent;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Wall;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;
import org.junit.jupiter.api.Test;

public class MazeTest {
  @Test
  public void testLevel1Creation() {
    Maze maze = new Maze("src/levels/Level1.json");

    assert maze.getPlayer() != null;
    assert maze.getActors() != null;
    assert maze.getAutoActors() != null;
    assert maze.getDisplayText() != null;
    assert !maze.isFinished();
  }

  @Test
  public void testLevel2Creation() {
    Maze maze = new Maze("src/levels/Level2.json");

    assert maze.getPlayer() != null;
    assert maze.getActors() != null;
    assert maze.getAutoActors() != null;
    assert maze.getDisplayText() != null;
    assert !maze.isFinished();
  }

  @Test
  public void testMazeSave() {
    Maze maze = new Maze("src/levels/Level1.json");
    maze.save("testSave.json");
    maze = new Maze("src/levels/Level2.json");
    maze.save("testSave.json");
  }

  @Test
  public void testPlayerMove() {
    Maze maze = new Maze("src/levels/Level1.json");
    Player player = maze.getPlayer();
    int xorigin = player.getX();
    int yorigin = player.getY();

    assert player.move(Actor.Direction.up);
    assert player.getY() == yorigin - 1 && player.getX() == xorigin
            && player.getPrevY() == yorigin && player.getPrevX() == xorigin;
    assert player.move(Actor.Direction.down);

    assert player.move(Actor.Direction.left);
    assert player.getY() == yorigin && player.getX() == xorigin - 1
            && player.getPrevY() == yorigin && player.getPrevX() == xorigin;
    assert player.move(Actor.Direction.right);

    assert player.move(Actor.Direction.down);
    assert player.getY() == yorigin + 1 && player.getX() == xorigin
            && player.getPrevY() == yorigin && player.getPrevX() == xorigin;
    assert player.move(Actor.Direction.up);

    assert player.move(Actor.Direction.right);
    assert player.getY() == yorigin && player.getX() == xorigin + 1
            && player.getPrevY() == yorigin && player.getPrevX() == xorigin;
    assert player.move(Actor.Direction.left);
  }

  @Test
  public void testPlayerDeadMove() {
    Maze maze = new Maze("src/levels/Level1.json");
    Player player = maze.getPlayer();
    player.die();

    assert !player.move(Actor.Direction.up);
    assert !player.move(Actor.Direction.down);
    assert !player.move(Actor.Direction.left);
    assert !player.move(Actor.Direction.right);
    assert !player.moveUp();
    assert !player.moveDown();
    assert !player.moveLeft();
    assert !player.moveRight();
  }

  @Test
  public void testPlayerImages() {
    Maze maze = new Maze("src/levels/Level1.json");
    Player player = maze.getPlayer();
    try {
      player.moveDown();
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      player.moveLeft();
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      player.moveRight();
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      player.moveUp();
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
    } catch (IOException e) {
      // An image wasn't found
      assert false;
    }
  }

  @Test
  public void testTileImages() {
    try {
      assert new Conveyor(Actor.Direction.down).getImage() != null;
      assert new Exit().getImage() != null;
      assert new ExitLock(2, true, false).getImage() != null;
      assert new ExitLock(2, false, false).getImage() != null;
      assert new ExitLock(0, true, true).getImage() != null;
      assert new ExitLock(0, false, true).getImage() != null;
      assert new FreeTile().getImage() != null;
      assert new Ice().getImage() != null;
      assert new InfoTile("test").getImage() != null;
      assert new LockedDoor("blue", true, false).getImage() != null;
      assert new LockedDoor("blue", false, false).getImage() != null;
      assert new LockedDoor("blue", true, true).getImage() != null;
      assert new LockedDoor("blue", false, true).getImage() != null;
      assert new NullTile().getImage() != null;
      assert new NullTile().toString() != null;
      assert new Vent(5, 5).getImage() != null;
      assert new Wall().getImage() != null;
      assert new Water().getImage() != null;
      // Test proxy
      assert new Water().getImage() != null;
    } catch (IOException e) {
      // An image wasn't found
      assert false;
    }
  }

  @Test
  public void testItemImages() {
    try {
      assert new Treasure().getImage() != null;
      assert new IcePotion().getImage() != null;
      assert new WaterPotion().getImage() != null;
      assert new Key("blue").getImage() != null;
      // Test proxy
      assert new WaterPotion().getImage() != null;
    } catch (IOException e) {
      // An image wasn't found
      assert false;
    }
  }

  @Test
  public void testItems() {
    assert !new Treasure().equals(new WaterPotion());
    assert new Treasure().equals(new Treasure());
    assert !new IcePotion().equals(new Treasure());
    assert new IcePotion().equals(new IcePotion());
    assert !new WaterPotion().equals(new Treasure());
    assert new WaterPotion().equals(new WaterPotion());
    assert !new Key("blue").equals(new Treasure());
    assert !new Key("blue").equals(new Key("green"));
    assert new Key("blue").equals(new Key("blue"));
    new Key("green").hashCode();
  }

  @Test
  public void testLevel1() {
    Maze maze = new Maze("src/levels/Level1.json");
    Player player = maze.getPlayer();
    assert maze.getTreasuresLeft() == ((ExitLock) maze.getTile(29, 26)).getTreasuresNeeded();

    for (int i = 0; i < 8; i++) {
      player.moveUp();
    }
    player.moveLeft();
    player.moveLeft();
    for (int i = 0; i < 4; i++) {
      player.moveRight();
    }
    for (int i = 0; i < 6; i++) {
      player.moveDown();
    }
    for (int i = 0; i < 4; i++) {
      player.moveLeft();
    }
    player.moveDown();
    player.moveDown();
    for (int i = 0; i < 10; i++) {
      player.moveLeft();
    }
    for (int i = 0; i < 4; i++) {
      player.moveUp();
    }
    player.moveLeft();
    player.moveLeft();
    for (int i = 0; i < 4; i++) {
      player.moveRight();
    }
    player.moveLeft();
    player.moveLeft();
    for (int i = 0; i < 4; i++) {
      player.moveUp();
    }
    for (int i = 0; i < 8; i++) {
      player.moveDown();
    }
    for (int i = 0; i < 6; i++) {
      player.moveRight();
    }
    for (int i = 0; i < 4; i++) {
      player.moveUp();
    }
    player.moveDown();
    player.moveUp();
    player.moveUp();
    player.moveUp();

    assert maze.isFinished();
  }

  @Test
  public void testPreAndPostConditions() {
    Maze maze = new Maze("src/levels/Level1.json");
    // Preconditions
    try {
      new Wall().moveEvent(maze.getPlayer(), Actor.Direction.up);
    } catch (UnsupportedOperationException e) {
      // Test passed
    }
    try {
      new NullTile().moveEvent(maze.getPlayer(), Actor.Direction.up);
    } catch (UnsupportedOperationException e) {
      // Test passed
    }
    try {
      maze.setTimeLimit(-1);
    } catch (IllegalArgumentException e) {
      // Test passed
    }
    try {
      new Key("");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
    try {
      maze.setTreasuresLeft(-1);
    } catch (IllegalArgumentException e) {
      // Test passed
    }
    try {
      new InfoTile("");
    } catch (IllegalArgumentException e) {
      // Test passed
    }
    try {
      new LockedDoor("", true, true);
    } catch (IllegalArgumentException e) {
      // Test passed
    }
    try {
      new ExitLock(-1, true, true);
    } catch (IllegalArgumentException e) {
      // Test passed
    }

    // Post assertions
    try {
      maze.setTreasuresLeft(2);
    } catch (AssertionError e) {
      // Test passed
    }
    try {
      for (int i = 0; i < 20; i++) {
        maze.reduceTreasuresLeft();
      }
    } catch (AssertionError e) {
      // Test passed
    }
    try {
      for (int i = 0; i < 20; i++) {
        new ExitLock(2, true, true);
      }
    } catch (AssertionError e) {
      // Test passed
    }
  }

  // TestLevel.json Tests
  // player at 8, 8

  @Test
  public void testAutoActors() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    List<AutoActor> actors = maze.getAutoActors();
    for (int i = 0; i < 10; i++) {
      for (AutoActor actor : actors) {
        actor.autoMove();
      }
    }
  }

  @Test
  public void testPlayerWallInteractions() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    // Walk into wall
    assert !player.moveLeft();
    assert player.moveTo(7,7);
    assert !player.moveDown();
    assert player.moveTo(7,9);
    assert !player.moveUp();
  }

  @Test
  public void testPlayerExitLockInteractions() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    // Walk into ExitLock without enough treasures
    assert player.moveUp();
    assert player.moveLeft();
    assert player.moveUp();
    assert !player.moveLeft();
  }

  @Test
  public void testPlayerTreasureInteractions() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    // Pickup treasure
    assert player.moveTo(7, 7);
    assert player.treasuresCollected() == 0;
    assert player.moveLeft();
    assert player.treasuresCollected() == 1;
  }

  @Test
  public void testPlayerVentInteraction() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    // Vent
    assert player.moveTo(8,7);
    assert player.moveUp();
    assert !player.isOn(Vent.class);
  }

  @Test
  public void testPlayerConveyorInteraction() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    // Conveyor
    assert player.moveTo(9,9);
    assert player.moveDown();
    assert player.isOn(Conveyor.class);
    // Animation pause test
    try {
      maze.getTile(player.getX(), player.getY()).getImage();
      maze.getTile(player.getX(), player.getY()).getImage();
    } catch (IOException e) {
      // Ignore
    }
  }

  @Test
  public void testPlayerIceInteraction() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    // Ice
    assert player.moveTo(8,9);
    assert player.moveDown();
    assert player.isOn(Ice.class);
    // Animation pause test
    try {
      maze.getTile(player.getX(), player.getY()).getImage();
      maze.getTile(player.getX(), player.getY()).getImage();
    } catch (IOException e) {
      // Ignore
    }

    // Ice with potion
    assert player.moveTo(7,9);
    assert player.moveDown();
    assert player.moveRight();
  }

  @Test
  public void testPlayerWaterInteraction() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    // Water
    assert player.moveTo(5, 9);
    assert player.moveDown();
    assert player.isDead();

    // Dead images water
    try {
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
    } catch (IOException e) {
      assert false;
    }

    maze = new Maze("src/levels/TestLevel.json");
    player = maze.getPlayer();

    // Water with potion
    assert player.moveTo(6, 9);
    assert player.moveDown();
    assert player.moveLeft();
    assert !player.isDead();
    // Images water
    try {
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
      assert player.getImage(true) != null;
    } catch (IOException e) {
      assert false;
    }
  }

  @Test
  public void testPlayerDeath1() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    Player player = maze.getPlayer();

    assert player.moveDown();
    for (int i = 0; i < 2; i++) {
      assert player.moveLeft();
    }
    assert !player.isDead();
    assert player.moveUp();
    assert player.isDead();

    // Dead images
    try {
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
      assert player.getImage(false) != null;
      assert player.getImage(true) != null;
    } catch (IOException e) {
      assert false;
    }
  }

  @Test
  public void testPlayerDeath2() {
    Maze maze = new Maze("src/levels/TestLevel.json");
    List<AutoActor> autoActors = maze.getAutoActors();
    Player player = maze.getPlayer();

    assert player.moveTo(5, 8);
    for (AutoActor autoActor : autoActors) {
      autoActor.moveLeft();
    }
    assert player.isDead();
    assert !player.moveDown();
  }
}
