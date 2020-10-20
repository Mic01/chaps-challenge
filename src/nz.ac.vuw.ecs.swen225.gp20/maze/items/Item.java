package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Item {
  private BufferedImage image;

  /**
   * Get this item's image.
   *
   * @return a BufferedImage of this item
   * @throws IOException thrown if the file cannot be found for the item
   */
  public abstract BufferedImage getImage() throws IOException;

  /**
   * Load image from file and act as a virtual proxy -
   * storing images when loaded for first time so they can be loaded faster.
   *
   * @param imageName name of the image file inside "assets/tiles/"
   * @return the loaded image
   * @throws IOException thrown if the file cannot be found
   */
  protected BufferedImage getImageProxy(String imageName) throws IOException {
    Preconditions.checkNotNull(imageName, "Item image is being loaded with a null string");
    Preconditions.checkArgument(imageName.length() > 0,
            "Item image is being loaded with an empty string");

    if (image == null) {
      image = ImageIO.read(new File("assets/tiles/" + imageName));
    }
    return image;
  }

  @Override
  public abstract String toString();

  @Override
  public abstract boolean equals(Object obj);
}
