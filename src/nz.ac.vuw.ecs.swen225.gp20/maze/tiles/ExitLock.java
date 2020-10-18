package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;

public class ExitLock extends Tile {
  private final int treasuresNeeded;
  private BufferedImage imageClosed;
  private BufferedImage imageOpen;
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

  public boolean isVertical() {
    return vertical;
  }

  public boolean isOpen() {
    return open;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    if (open) return true;

    if (actor instanceof Player) {
      Player player = (Player) actor;
      return player.treasuresCollected() == treasuresNeeded;
    }

    return false;
  }

  @Override
  public void moveEvent(Actor actor, Actor.Direction direction) {
    open = true;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (imageClosed == null) {
      imageClosed = ImageIO.read(new File(imageDirectory +
              "gate_" + (vertical ? "vertical" : "horizontal") + ".png"));
      imageOpen = ImageIO.read(new File(imageDirectory +
              "lock_" + (vertical ? "vertical" : "horizontal") + "_open.png"));
    }
    return open ? imageOpen : imageClosed;
  }

  @Override
  public String toString() {
    return "Lock";
  }
}
