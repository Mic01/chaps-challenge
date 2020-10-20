package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;

public class ExitLock extends Tile {
  private final int treasuresNeeded;
  private final boolean vertical;
  private boolean open;

  /**
   * A locked door that can only be opened once a certain number of treasures have been collected.
   *
   * @param treasuresNeeded the number of treasures that need to be picked up to open this door
   * @param vertical whether this door uses a vertical or horizontal graphic
   * @param open whether this door has been opened or not
   */
  public ExitLock(int treasuresNeeded, boolean vertical, boolean open) {
    Preconditions.checkArgument(treasuresNeeded >= 0,
            "Treasures needed for ExitLock cannot be negative");
    this.treasuresNeeded = treasuresNeeded;
    this.vertical = vertical;
    this.open = open;
    // Can only be open on creation if treasures needed is 0
    assert !open || treasuresNeeded == 0;
  }

  public int getTreasuresNeeded() {
    return treasuresNeeded;
  }

  public boolean isVertical() {
    return vertical;
  }

  public boolean isOpen() {
    return open;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    Preconditions.checkNotNull(actor, "ExitLock isTraversable is being given a null actor");

    if (open) {
      return true;
    }

    if (actor.isPlayer()) {
      Player player = (Player) actor;
      return player.treasuresCollected() == treasuresNeeded;
    }
    return false;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    Preconditions.checkNotNull(actor, "ExitLock moveEvent is being given a null actor");
    open = true;
    actor.getMaze().setDisplayText("Exit door unlocked");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return open ? getImageProxy("lock_" + (vertical ? "vertical" : "horizontal") + "_open") :
            getImageProxy("gate_" + (vertical ? "vertical" : "horizontal"));
  }

  @Override
  public String toString() {
    return "Lock";
  }
}
