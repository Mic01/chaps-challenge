package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.IcePotion;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Conveyor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;

public class Player extends Actor {
  private final ArrayList<Item> inventory = new ArrayList<>();
  private int treasures = 0;
  private boolean isSliding = false;

  public Player(int xpos, int ypos, Maze maze) {
    super(xpos, ypos, maze);

    // Set this player as this Maze's player character
    maze.setPlayer(this);
  }

  /**
   * Add an item to Player's inventory.
   *
   * @param item the item being picked up
   */
  public void pickup(Item item) {
    inventory.add(item);
  }

  /**
   * Is Player holding the provided item.
   *
   * @param item is Player holding this item
   * @return boolean response for Player holding the provided item
   */
  public boolean isHolding(Item item) {
    for (Item heldItem : inventory) {
      if (heldItem.equals(item)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Increase the number of treasures Player has by 1.
   */
  public void pickupTreasure() {
    treasures++;
  }

  /**
   * Get the number of treasures the player has collected.
   *
   * @return number of treasures the player has collected
   */
  public int treasuresCollected() {
    return treasures;
  }

  /**
   * Get a unmodifiable list of items in Player's inventory.
   *
   * @return unmodifiable list of items
   */
  public List<Item> getInventory() {
    return Collections.unmodifiableList(inventory);
  }

  @Override
  public BufferedImage getImage(boolean moving) throws IOException {
    String type;

    if (!dead) {
      if (moving) {
        type = "walk";
      } else {
        type = "stand";
        frame = 0;
      }

      if (isOn(Water.class)) {
        type = "swim" + (moving ? "" : "_idle");
        frame %= 2;
      } else if (isOn(Conveyor.class) || (isOn(Ice.class) && !isHolding(new IcePotion()))) {
        type = "slide";
        if (isSliding) {
          frame = 1;
        } else {
          frame = 0;
          isSliding = true;
        }
      }

      if (!type.equals("slide")) {
        isSliding = false;
      }
    } else if (isOn(Water.class)) {
      type = "swim_dead";
    } else {
      type = "dead";
    }

    if (dead) {
      if (currentDirection == Direction.up) {
        currentDirection = Direction.left;
      } else if (currentDirection == Direction.down) {
        currentDirection = Direction.right;
      }
    }

    String path = "player/" + type + "_" + ((moving || type.equals("slide") || dead)
            ? (frame + "_") : "") + currentDirection;
    return getImageProxy(path);
  }

  @Override
  public String toString() {
    return "Player";
  }
}
