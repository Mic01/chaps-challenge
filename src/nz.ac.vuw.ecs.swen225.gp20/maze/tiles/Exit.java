package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
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
    Preconditions.checkNotNull(actor, "Exit moveEvent is being given a null actor");
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
