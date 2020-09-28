package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Key extends Item {
  private final String colour;
  private BufferedImage image;

  public Key(String colour) {
    this.colour = colour.toLowerCase();
  }

  public String getColour() {
    return colour;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Key) {
      Key otherKey = (Key) obj;
      return this.colour.equals(otherKey.colour);
    }
    return false;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "key_" + colour + ".png"));
    }
    return image;
  }
}
