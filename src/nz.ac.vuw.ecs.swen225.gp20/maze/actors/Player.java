package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;

public class Player extends Actor {
  private final ArrayList<Item> inventory = new ArrayList<>();
  private int treasures = 0;
  private static final HashMap<String, BufferedImage> images = new HashMap<>();

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
    BufferedImage image;

    String type;
    if (moving) {
      type = "walk";
      if (currentTile instanceof Water) {
        type = "swim";
        frame %= 2;
      } else if (currentTile instanceof Ice) {
        type = "slide";
        frame %= 2;
      }
    } else {
      type = "stand";
      frame = 0;
    }

    /* Stores loaded images in a map with lazy initialisation and acts as a virtual proxy */
    String key = type + (moving ? frame : "") + orientation;
    if (images.containsKey(key)) {
      image = images.get(key);
    } else {
      image = ImageIO.read(new File(
              imageDirectory + "red/" + type + "_"
                      + (moving ? (frame + "_") : "") + orientation + ".png"));
      images.put(key, image);
    }

    nextFrame();

    return image;
  }
}
