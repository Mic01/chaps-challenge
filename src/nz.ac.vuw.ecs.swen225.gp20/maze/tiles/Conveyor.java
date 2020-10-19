package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Conveyor extends Tile {
  private BufferedImage image;
  private Actor.Direction moveDirection;

  Conveyor(Actor.Direction direction) {
    this.moveDirection = direction;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {

  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) image = ImageIO.read(new File(imageDirectory + "turbo_" + moveDirection + ".png"));
    return image;
  }

  @Override
  public String toString() {
    return "Conveyor";
  }
}
