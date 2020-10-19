package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WaterPotion extends Item {
  private BufferedImage image;

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "water_potion.png"));
    }
    return image;
  }

  @Override
  public String toString() {
    return "Shoe";
  }
}
