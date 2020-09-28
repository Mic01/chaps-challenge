package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Item {
  protected static final String imageDirectory = "assets/tiles/";

  /**
   * Get this item's image.
   *
   * @return a BufferedImage of this item
   * @throws IOException thrown if the file cannot be found for the item
   */
  public abstract BufferedImage getImage() throws IOException;
}
