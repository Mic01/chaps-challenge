package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Vent extends Tile {
  private final int xtarget;
  private final int ytarget;

  public Vent(int xtarget, int ytarget) {
    this.xtarget = xtarget;
    this.ytarget = ytarget;
  }

  public int getTargetX() {
    return xtarget;
  }

  public int getTargetY() {
    return ytarget;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    Preconditions.checkNotNull(actor, "Vent moveEvent is being given a null actor");
    actor.moveTo(xtarget, ytarget);
    actor.getMaze().setDisplayText("Travelled through a vent");
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
