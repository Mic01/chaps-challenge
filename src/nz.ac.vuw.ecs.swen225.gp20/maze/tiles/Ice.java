package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.IcePotion;

public class Ice extends Tile {
  private static BufferedImage image;

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    if (actor instanceof Player && !((Player) actor).isHolding(new IcePotion())) {
      actor.move(direction);
    }
    actor.getMaze().setDisplayText("");
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) image = ImageIO.read(new File(imageDirectory + "ice.png"));
    return image;
  }

  @Override
  public String toString() {
    return "Ice";
  }
}
