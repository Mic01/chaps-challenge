package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Exit extends Tile {
  private static BufferedImage image;

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "finish.png"));
    }
    return image;
  }

  @Override
  public String toString() {
    return "End";
  }
}
