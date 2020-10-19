package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;

public class LockedDoor extends Tile {
  private final String colour;
  private final boolean vertical;
  private boolean open;

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

  public boolean isOpen() {
    return open;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    if (open) return true;

    if (actor.isPlayer()) {
      Player player = (Player) actor;
      return player.isHolding(new Key(colour));
    }
    return false;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    open = true;
    actor.getMaze().setDisplayText("Door unlocked");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("lock_" + (vertical ? "vertical" : "horizontal") + "_" + (open ? "open" : colour));
  }

  @Override
  public String toString() {
    return "Door";
  }
}
