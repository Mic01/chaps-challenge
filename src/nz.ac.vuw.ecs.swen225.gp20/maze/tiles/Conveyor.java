package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Conveyor extends Tile {
  private final Actor.Direction moveDirection;

  public Conveyor(Actor.Direction direction) {
    this.moveDirection = direction;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    actor.move(moveDirection);
    actor.getMaze().setDisplayText("");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("turbo_" + moveDirection);
  }

  @Override
  public String toString() {
    return "Conveyor";
  }
}
