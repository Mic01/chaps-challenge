package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class InfoTile extends FreeTile {
  private final String info;
  private static BufferedImage image;

  public InfoTile(String info) {
    this.info = info;
  }

  public InfoTile(String info, Actor actor) {
    super(actor);
    this.info = info;
  }

  public String getInfo() {
    return info;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return true;
  }

  @Override
  public BufferedImage getImage() throws IOException {
    if (image == null) image = ImageIO.read(new File(imageDirectory + "info.png"));
    return image;
  }

  @Override
  public String toString() {
    return "InfoTile";
  }
}
