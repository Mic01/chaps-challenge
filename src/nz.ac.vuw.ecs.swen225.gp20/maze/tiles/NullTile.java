package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class NullTile extends Tile {
  private static final Random random = new Random();
  private BufferedImage image;

  @Override
  public boolean isTraversable(Actor actor) {
    return false;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    throw new UnsupportedOperationException("NullTiles should not be able to be moved onto");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("empty_" + random.nextInt(5));
  }

  @Override
  public String toString() {
    return "Null";
  }
}
