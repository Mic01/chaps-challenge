package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public abstract class Tile {
  protected static final String imageDirectory = "assets/tiles/";
  protected Actor actor;

  public Tile() { }

  public Tile(Actor actor) {
    addActor(actor);
  }

  public abstract boolean isTraversable(Actor actor);

  /**
   * Add an actor to this tile.
   *
   * @param actor the actor to add to this tile
   */
  public void addActor(Actor actor) {
    // Players should only be added to tiles if they are traversable
    Preconditions.checkArgument(isTraversable(actor));

    //todo: deal with another actor on this tile already
    this.actor = actor;
  }

  /**
   * Remove the actor from this tile.
   */
  public void removeActor() {
    actor = null;
  }

  public boolean hasActor() {
    return actor != null;
  }

  /**
   * Trigger any events as a result of moving onto this tile
   */
  public abstract void moveEvent(Actor actor, Actor.Direction direction);

  /**
   * Get this tile's image.
   *
   * @return a BufferedImage of this tile
   * @throws IOException thrown if the file cannot be found for the tile
   */
  public abstract BufferedImage getImage() throws IOException;

  @Override
  public abstract String toString();
}
