package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.WaterPotion;

public class Water extends Tile {
  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    if (actor.isPlayer() && !((Player) actor).isHolding(new WaterPotion())) {
      actor.die();
    }
    actor.getMaze().setDisplayText("");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("water");
  }

  @Override
  public String toString() {
    return "Water";
  }
}
