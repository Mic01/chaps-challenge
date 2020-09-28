package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ice extends FreeTile {
  private static BufferedImage image;

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
