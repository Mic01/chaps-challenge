package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

public abstract class Actor {
  protected int xpos;
  protected int ypos;
  private int xposPrev;
  private int yposPrev;
  protected final Maze maze;
  protected FreeTile currentTile;
  protected int frame = 0;
  private final HashMap<String, BufferedImage> images = new HashMap<>();
  protected Direction currentDirection = Direction.down;

  public enum Direction {
    up, right, down, left
  }

  /**
   * A moving creature, typically the player or an enemy creature.
   *
   * @param xpos the xpos to start this actor at
   * @param ypos the ypos to start this actor at
   * @param maze the maze this actor is being made on
   */
  public Actor(int xpos, int ypos, Maze maze) {
    this.xpos = xpos;
    this.ypos = ypos;
    this.xposPrev = xpos;
    this.yposPrev = ypos;
    this.maze = maze;

    maze.addActor(this);
  }

  /**
   * Initialise required variables for this actor that were unavailable
   * during its construction.
   */
  public void setup() {
    currentTile = (FreeTile) maze.getTile(xpos, ypos);
  }

  /**
   * Attempt to move this actor up.
   *
   * @return whether this actor successfully moved up or not
   */
  public boolean moveUp() {
    currentDirection = Direction.up;
    if (moveTo(xpos, ypos - 1)) {
      xposPrev = xpos;
      yposPrev = ypos;
      ypos--;
      return true;
    }
    return false;
  }

  /**
   * Attempt to move this actor down.
   *
   * @return whether this actor successfully moved down or not
   */
  public boolean moveDown() {
    currentDirection = Direction.down;
    if (moveTo(xpos, ypos + 1)) {
      xposPrev = xpos;
      yposPrev = ypos;
      ypos++;
      return true;
    }
    return false;
  }

  /**
   * Attempt to move this actor left.
   *
   * @return whether this actor successfully moved left or not
   */
  public boolean moveLeft() {
    currentDirection = Direction.left;
    if (moveTo(xpos - 1, ypos)) {
      yposPrev = ypos;
      xposPrev = xpos;
      xpos--;
      return true;
    }
    return false;
  }

  /**
   * Attempt to move this actor right.
   *
   * @return whether this actor successfully moved right or not
   */
  public boolean moveRight() {
    currentDirection = Direction.right;
    if (moveTo(xpos + 1, ypos)) {
      yposPrev = ypos;
      xposPrev = xpos;
      xpos++;
      return true;
    }
    return false;
  }

  /**
   * Move this actor to the tile provided.
   *
   * @param x x position of tile to move to
   * @param y y position of tile to move to
   * @return whether the move was successful
   */
  private boolean moveTo(int x, int y) {
    Tile newTile = maze.getTile(x, y);
    if (newTile.isTraversable(this)) {

      /* If tile other than a free tile is traversable then it must be a door
      or lock that can be opened and turned into a standard free tile */
      if (!(newTile instanceof FreeTile)) {
        maze.setTile(x, y, new FreeTile(this));
      } else {
        ((FreeTile) newTile).addActor(this);
      }

      currentTile.removeActor();
      currentTile = (FreeTile) maze.getTile(x, y);
      return true;
    }
    return false;
  }

  /**
   * Make this actor use the next frame in its animation.
   */
  public void nextFrame() {
    frame = (frame + 1) % 4;
  }

  /**
   * Get this actor's image.
   *
   * @return a BufferedImage of this actor
   * @throws IOException thrown if the file cannot be found for the actor
   */
  public abstract BufferedImage getImage(boolean moving) throws IOException;

  /**
   * Load image from file and act as a virtual proxy -
   * storing images loaded for first time in map so they can be loaded faster
   *
   * @param filepath path to the image, starting inside "assets/actors/"
   * @return the loaded image
   * @throws IOException thrown if the file cannot be found
   */
  protected BufferedImage getImageProxy(String filepath) throws IOException {
    BufferedImage image;
    if (images.containsKey(filepath)) {
      image = images.get(filepath);
    } else {
      image = ImageIO.read(new File("assets/actors/" + filepath + ".png"));
      images.put(filepath, image);
    }

    nextFrame();
    return image;
  }

  public int getX() {
    return xpos;
  }

  public int getPrevX() {
    return xposPrev;
  }

  public int getY() {
    return ypos;
  }

  public int getPrevY() {
    return yposPrev;
  }

  public FreeTile getCurrentTile() {
    return currentTile;
  }

  @Override
  public abstract String toString();
}
