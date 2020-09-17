package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;

public class ExitLock extends Tile {
  private final int treasuresNeeded;

  public ExitLock(int treasuresNeeded) {
    this.treasuresNeeded = treasuresNeeded;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    if (actor instanceof Player) {
      Player player = (Player) actor;
      return player.treasuresCollected() == treasuresNeeded;
    }
    return false;
  }
}
