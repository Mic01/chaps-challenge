package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class NullTile extends Tile {
  private static final Random random = new Random();

  @Override
  public boolean isTraversable(Actor actor) {
    return false;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return ImageIO.read(new File(imageDirectory + "empty_" + random.nextInt(5) + ".png"));
  }

  @Override
  public String toString() {
    return "Null";
  }
}
