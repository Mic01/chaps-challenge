package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class InfoTile extends Tile {
  private final String info;

  /**
   * Tile that displays set text to user when Player steps on it.
   *
   * @param info text to display to user
   */
  public InfoTile(String info) {
    Preconditions.checkNotNull(info, "InfoTile is being given null info string");
    Preconditions.checkArgument(info.length() > 0, "InfoTile is being given empty info string");
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
  public void moveEvent(Actor actor, Actor.Direction direction) {
    Preconditions.checkNotNull(actor, "InfoTile moveEvent is being given a null actor");
    actor.getMaze().setDisplayText(info);
  }

  @Override
  public BufferedImage getImage() throws IOException {
    return getImageProxy("info");
  }

  @Override
  public String toString() {
    return "Info";
  }
}
