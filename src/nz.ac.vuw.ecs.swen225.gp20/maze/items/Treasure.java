package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Treasure extends Item {
  private static final Random random = new Random();

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("task_" + random.nextInt(3) + ".png");
  }

  @Override
  public String toString() {
    return "Chip";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Treasure;
  }
}
