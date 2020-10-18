package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Treasure extends Item {
  private static final Random random = new Random();
  private BufferedImage image;

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "task_" + random.nextInt(3) + ".png"));
    }
    return image;
  }

  @Override
  public String toString() {
    return "Treasure";
  }
}
