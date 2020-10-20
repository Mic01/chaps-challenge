package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;

public class LockedDoor extends Tile {
  private final String colour;
  private final boolean vertical;
  private boolean open;

  /**
   * A coloured locked door that can only be opened if
   * the player has a key of the same colour as this door.
   *
   * @param colour the colour of this door
   * @param vertical whether this door uses a vertical or horizontal graphic
   * @param open whether this door has been opened or not
   */
  public LockedDoor(String colour, boolean vertical, boolean open) {
    Preconditions.checkNotNull(colour, "A LockedDoor is being provided with a null colour string");
    Preconditions.checkArgument(colour.length() > 0,
            "LockedDoor is being provided with an empty colour string");
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
    Preconditions.checkNotNull(actor, "LockedDoor isTraversable is being given a null actor");
    if (open) {
      return true;
    }

    if (actor.isPlayer()) {
      Player player = (Player) actor;
      return player.isHolding(new Key(colour));
    }
    return false;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    Preconditions.checkNotNull(actor, "LockedDoor moveEvent is being given a null actor");
    open = true;
    actor.getMaze().setDisplayText("Door unlocked");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("lock_" + (vertical ? "vertical" : "horizontal")
            + "_" + (open ? "open" : colour));
  }

  @Override
  public String toString() {
    return "Door";
  }
}
