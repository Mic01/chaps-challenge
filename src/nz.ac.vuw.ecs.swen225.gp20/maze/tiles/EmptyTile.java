package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class EmptyTile extends Tile {
  @Override
  public boolean isTraversable(Actor actor) {
    return false;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return null;
  }
}
