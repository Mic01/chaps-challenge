package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.IcePotion;

public class Ice extends Tile {
  private Thread animate;
  private boolean drawn = false;

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    if (actor.isPlayer() && !((Player) actor).isHolding(new IcePotion())) {
      drawn = false;

      animate = new Thread() {
        @Override
        public void run() {
          // Wait for image to update, so animation can be displayed
          synchronized (this) {
            while (!drawn) {
              try {
                this.wait();
              } catch (InterruptedException ignored) {
              }
            }
          }
          actor.move(direction);
        }
      };

      animate.start();
    }
    actor.getMaze().setDisplayText("");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (hasActor() && animate != null && drawn) {
      synchronized (animate) { animate.notifyAll(); }
    }
    drawn = true;
    return getImageProxy("ice");
  }

  @Override
  public String toString() {
    return "Ice";
  }
}
