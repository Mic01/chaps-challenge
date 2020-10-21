package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class IcePotion extends Item {
  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("ice_potion.png");
  }

  @Override
  public String toString() {
    return "Shoe";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof IcePotion;
  }
}
