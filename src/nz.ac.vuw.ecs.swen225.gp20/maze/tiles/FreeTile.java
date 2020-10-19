package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Treasure;

public class FreeTile extends Tile {
  private Item item;
  private static BufferedImage image;

  public FreeTile() {
    item = null;
  }

  public FreeTile(Item item) {
    this.item = item;
  }

  public FreeTile(Actor actor) {
    super(actor);
  }

  public Actor getActor() {
    return actor;
  }

  public boolean hasItem() {
    return item != null;
  }

  public Item getItem() {
    return item;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return actor instanceof Player || item == null;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    // Add any items on this tile to player inventory
    if (item != null && actor instanceof Player) {
      Player player = (Player) actor;
      if (item instanceof Treasure) {
        player.pickupTreasure();
        actor.getMaze().reduceTreasuresLeft();
      } else {
        player.pickup(item);
      }
      item = null;
    }

    actor.getMaze().setDisplayText("");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "floor.png"));
    }
    return image;
  }

  @Override
  public String toString() {
    return "Floor";
  }
}
