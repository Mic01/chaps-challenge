package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Treasure extends Item {
  private static BufferedImage image;

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "chip.png"));
    }
    return image;
  }
}
