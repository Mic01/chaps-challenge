package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;

public class LockedDoor extends Tile {
  private final String colour;
  private final boolean vertical;
  private boolean open;
  private BufferedImage image;

  public LockedDoor(String colour, boolean vertical, boolean open) {
    this.colour = colour.toLowerCase();
    this.vertical = vertical;
    this.open = open;
  }

  public String getColour() {
    return colour;
  }

  public boolean isVertical() {
    return vertical;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    if (open) return true;

    if (actor instanceof Player) {
      Player player = (Player) actor;
      return player.isHolding(new Key(colour));
    }

    return false;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    open = true;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "lock_"
              + (vertical ? "vertical" : "horizontal") + "_" + (open ? "open" : colour) + ".png"));
    }
    return image;
  }

  @Override
  public String toString() {
    return "Door";
  }
}
