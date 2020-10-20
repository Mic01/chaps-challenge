package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Conveyor extends Tile {
  private final Actor.Direction moveDirection;
  private boolean drawn = false;
  private Thread animate;

  public Conveyor(Actor.Direction direction) {
    Preconditions.checkNotNull(direction, "Conveyor is being created with null direction");
    this.moveDirection = direction;
  }

  public Actor.Direction getDirection() {
    return moveDirection;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    Preconditions.checkNotNull(actor, "Conveyor moveEvent is being given a null actor");
    Preconditions.checkNotNull(direction, "Conveyor moveEvent is being given a null direction");
    drawn = false;

    // Wait for image to update, so animation can be displayed
    animate = new Thread() {
      @Override
      public void run() {
        synchronized (this) {
          while (!drawn) {
            try {
              this.wait();
            } catch (InterruptedException ignored) {
              // Do nothing
            }
          }
        }
        actor.move(moveDirection);
      }
    };

    animate.start();
    actor.getMaze().setDisplayText("");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    // Unfreeze movement as image as been updated
    if (hasActor() && animate != null && drawn) {
      synchronized (animate) {
        animate.notifyAll();
      }
    }
    drawn = true;
    return getImageProxy("turbo_" + moveDirection);
  }

  @Override
  public String toString() {
    return "Conveyor";
  }
}
