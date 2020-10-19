package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Key extends Item {
  private final String colour;

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
  public BufferedImage getImage() throws IOException {
    return getImageProxy("key_" + colour + ".png");
  }

  @Override
  public String toString() {
    return "Key";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Key) {
      Key otherKey = (Key) obj;
      return this.colour.equals(otherKey.colour);
    }
    return false;
  }
}
