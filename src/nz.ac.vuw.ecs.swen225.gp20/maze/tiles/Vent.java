package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Vent extends Tile {
  private static BufferedImage image;
  private final int xTarget;
  private final int yTarget;

  public Vent(int xTarget, int yTarget) {
    this.xTarget = xTarget;
    this.yTarget = yTarget;
  }

  public int getTargetX() {
    return xTarget;
  }

  public int getTargetY() {
    return yTarget;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    actor.moveTo(xTarget, yTarget);
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("vent");
  }

  @Override
  public String toString() {
    return "Vent";
  }
}
