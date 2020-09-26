package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Water extends FreeTile {
  @Override
  public BufferedImage getImage() throws IOException {
    return ImageIO.read(new File(imageDirectory + "water.png"));
  }

  @Override
  public String toString() {
    return "Water";
  }
}
