package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;

public class ExitLock extends Tile {
  private final int treasuresNeeded;
  private static BufferedImage image;
  private final boolean vertical;
  private boolean open;

  public ExitLock(int treasuresNeeded, boolean vertical, boolean open) {
    this.treasuresNeeded = treasuresNeeded;
    this.vertical = vertical;
    this.open = open;
  }

  public int getTreasuresNeeded() {
    return treasuresNeeded;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    if (actor instanceof Player) {
      Player player = (Player) actor;
      return player.treasuresCollected() == treasuresNeeded;
    }
    return false;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) {
      image = ImageIO.read(new File(imageDirectory + "gate_" + (vertical ? "vertical" : "horizontal") + ".png"));
    }
    return image;
  }

  @Override
  public String toString() {
    return "Lock";
  }
}
