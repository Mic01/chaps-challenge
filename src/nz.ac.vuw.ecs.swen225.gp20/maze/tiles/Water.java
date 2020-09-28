package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Water extends FreeTile {
  private static BufferedImage image;

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "water.png"));
    }
    return image;
  }

  @Override
  public String toString() {
    return "Water";
  }
}
