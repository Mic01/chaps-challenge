package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Exit extends Tile {
  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    actor.getMaze().setFinished();
    actor.getMaze().setDisplayText("You have reached the Exit!");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("finish");
  }

  @Override
  public String toString() {
    return "End";
  }
}
