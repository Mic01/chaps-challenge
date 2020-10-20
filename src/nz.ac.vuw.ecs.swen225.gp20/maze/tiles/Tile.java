package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public abstract class Tile {
  private static final HashMap<String, BufferedImage> images = new HashMap<>();
  protected Actor actor;

  public Tile() { }

  public Tile(Actor actor) {
    addActor(actor);
  }

  /**
   * Add an actor to this tile.
   *
   * @param actor the actor to add to this tile
   */
  public void addActor(Actor actor) {
    Preconditions.checkNotNull(actor, "A null actor is being added to a Tile");

    // Players should only be added to tiles if they are traversable
    Preconditions.checkArgument(isTraversable(actor),
            "An actor is trying to walk onto a non-traversable tile");

    // If a Player walks onto a tile with another Actor already on it, kill the player
    if (hasActor() && (actor.isPlayer() || this.actor.isPlayer())) {
      if (actor.isPlayer()) {
        actor.die();
      } else {
        this.actor.die();
      }
    } else {
      this.actor = actor;
    }
  }

  /**
   * Remove the actor from this tile.
   */
  public void removeActor() {
    actor = null;
  }

  /**
   * Does this tile have an actor.
   *
   * @return whether this tile has an actor
   */
  public boolean hasActor() {
    return actor != null;
  }

  /**
   * Are Actors allowed to move across this tile?.
   *
   * @param actor the actor trying to move across this tile
   * @return whether the actor is allowed to move across this tile
   */
  public abstract boolean isTraversable(Actor actor);

  /**
   * Trigger any events as a result of moving onto this tile.
   */
  public abstract void moveEvent(Actor actor, Actor.Direction direction);

  /**
   * Get this tile's image.
   *
   * @return a BufferedImage of this tile
   * @throws IOException thrown if the file cannot be found for the tile
   */
  public abstract BufferedImage getImage() throws IOException;

  /**
   * Load image from file and act as a virtual proxy -
   * storing images loaded for first time in map so they can be loaded faster.
   *
   * @param imageName name of the png file inside "assets/tiles/"
   * @return the loaded image
   * @throws IOException thrown if the file cannot be found
   */
  protected BufferedImage getImageProxy(String imageName) throws IOException {
    Preconditions.checkNotNull(imageName, "Tile image is being loaded with a null string");
    Preconditions.checkArgument(imageName.length() > 0,
            "Tile image is being loaded with an empty string");

    BufferedImage image;
    if (images.containsKey(imageName)) {
      image = images.get(imageName);
    } else {
      image = ImageIO.read(new File("assets/tiles/" + imageName + ".png"));
      images.put(imageName, image);
    }

    return image;
  }

  @Override
  public abstract String toString();
}
