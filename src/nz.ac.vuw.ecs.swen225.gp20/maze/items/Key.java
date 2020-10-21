package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Key extends Item {
  private final String colour;

  /**
   * Key used to open locked doors.
   *
   * @param colour colour of key
   */
  public Key(String colour) {
    Preconditions.checkNotNull(colour, "A Key is being provided with a null colour string");
    Preconditions.checkArgument(colour.length() > 0,
            "A Key is being provided with an empty colour string");

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
