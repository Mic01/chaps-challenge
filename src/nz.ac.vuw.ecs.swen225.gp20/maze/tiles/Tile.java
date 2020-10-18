package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public abstract class Tile {
  protected static final String imageDirectory = "assets/tiles/";

  public abstract boolean isTraversable(Actor actor);

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
