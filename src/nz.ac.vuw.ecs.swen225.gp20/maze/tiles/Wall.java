package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Wall extends Tile {
  private static BufferedImage image;

  @Override
  public boolean isTraversable(Actor actor) {
    return false;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    throw new UnsupportedOperationException("Walls should not be able to be moved onto");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("wall");
  }

  @Override
  public String toString() {
    return "Wall";
  }
}