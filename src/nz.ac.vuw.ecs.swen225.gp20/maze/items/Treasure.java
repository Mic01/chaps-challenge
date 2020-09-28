package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Treasure extends Item {
  @Override
  public BufferedImage getImage() throws IOException {
    return ImageIO.read(new File(imageDirectory + "chip.png"));
  }
}
