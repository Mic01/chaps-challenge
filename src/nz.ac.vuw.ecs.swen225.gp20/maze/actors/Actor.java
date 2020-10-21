package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;

public abstract class Actor {
  protected int xpos;
  protected int ypos;
  private int xposPrev;
  private int yposPrev;
  protected final Maze maze;
  protected Tile currentTile;
  protected int frame = 0;
  private final HashMap<String, BufferedImage> images = new HashMap<>();
  protected Direction currentDirection = Direction.down;
  protected boolean dead = false;

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
    Preconditions.checkNotNull(maze, "Null Maze passed to Actor");

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
    Preconditions.checkElementIndex(xpos, maze.getWidth(),
          xpos + " is not within the width of the Maze - " + maze.getWidth()
                  + ": Actor constructor");
    Preconditions.checkElementIndex(ypos, maze.getHeight(),
            ypos + " is not within the height of the Maze - " + maze.getHeight()
                    + ": Actor constructor");

    currentTile = maze.getTile(xpos, ypos);

    assert currentTile.isTraversable(this);
  }

  /**
   * Attempt to move this actor up.
   *
   * @return whether this actor successfully moved up or not
   */
  public boolean moveUp() {
    // Prevent movement if the actor is dead
    /* Also prevent manual movement commands from the user if
    the Player is being moved automatically by Ice or Conveyors */
    if (dead || isPlayer() && ((Player) this).isSliding()
            && !Thread.currentThread().getStackTrace()[3].getMethodName().equals("run")) {
      return false;
    }
    currentDirection = Direction.up;
    if (moveTo(xpos, ypos - 1)) {
      currentTile.moveEvent(this, currentDirection);
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
    // Prevent movement if the actor is dead
    /* Also prevent manual movement commands from the user if
    the Player is being moved automatically by Ice or Conveyors */
    if (dead || isPlayer() && ((Player) this).isSliding()
            && !Thread.currentThread().getStackTrace()[3].getMethodName().equals("run")) {
      return false;
    }
    currentDirection = Direction.down;
    if (moveTo(xpos, ypos + 1)) {
      currentTile.moveEvent(this, currentDirection);
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
    // Prevent movement if the actor is dead
    /* Also prevent manual movement commands from the user if
    the Player is being moved automatically by Ice or Conveyors */
    if (dead || isPlayer() && ((Player) this).isSliding()
            && !Thread.currentThread().getStackTrace()[3].getMethodName().equals("run")) {
      return false;
    }
    currentDirection = Direction.left;
    if (moveTo(xpos - 1, ypos)) {
      currentTile.moveEvent(this, currentDirection);
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
    // Prevent movement if the actor is dead
    /* Also prevent manual movement commands from the user if
    the Player is being moved automatically by Ice or Conveyors */
    if (dead || isPlayer() && ((Player) this).isSliding()
            && !Thread.currentThread().getStackTrace()[3].getMethodName().equals("run")) {
      return false;
    }
    currentDirection = Direction.right;
    if (moveTo(xpos + 1, ypos)) {
      currentTile.moveEvent(this, currentDirection);
      return true;
    }
    return false;
  }

  /**
   * Move this actor in the provided direction.
   *
   * @param direction direction to move in
   * @return whether the move was successful
   */
  public boolean move(Direction direction) {
    if (dead) {
      return false;
    }
    switch (direction) {
      case up: return moveUp();
      case down: return moveDown();
      case left: return moveLeft();
      case right: return moveRight();
      default: return false;
    }
  }

  /**
   * Move this actor to the tile provided.
   *
   * @param x x position of tile to move to
   * @param y y position of tile to move to
   * @return whether the move was successful
   */
  public boolean moveTo(int x, int y) {
    Preconditions.checkElementIndex(xpos, maze.getWidth(),
            xpos + " is not within the width of the Maze - " + maze.getWidth()
                    + ": Actor moveTo method");
    Preconditions.checkElementIndex(ypos, maze.getHeight(),
            ypos + " is not within the height of the Maze - " + maze.getHeight()
                    + ": Actor moveTo method");

    if (dead) {
      return false;
    }

    Tile newTile = maze.getTile(x, y);
    if (newTile.isTraversable(this)) {
      newTile.addActor(this);
      currentTile.removeActor();
      currentTile = maze.getTile(x, y);
      xposPrev = xpos;
      yposPrev = ypos;
      xpos = x;
      ypos = y;
      return true;
    }
    return false;
  }

  /**
   * Make this actor use the next frame in its animation.
   */
  public void nextFrame() {
    if (dead) {
      // Do not repeat death animation, only play it once
      if (frame < (isOn(Water.class) ? 2 : 3)) {
        frame++;
      }
    } else {
      frame = (frame + 1) % 4;
    }
  }

  /**
   * Is this actor standing on the type of Tile provided.
   *
   * @param tileClass the type of tile to check
   * @return whether this actor is standing on the type of tile provided
   */
  public boolean isOn(Class<? extends Tile> tileClass) {
    return tileClass.isInstance(currentTile);
  }

  /**
   * Get this actor's image.
   *
   * @param moving is this actor currently moving
   * @return a BufferedImage of this actor
   * @throws IOException thrown if the file cannot be found for the actor
   */
  public abstract BufferedImage getImage(boolean moving) throws IOException;

  /**
   * Load image from file and act as a virtual proxy -
   * storing images loaded for first time in map so they can be loaded faster.
   *
   * @param filepath path to the image, starting inside "assets/actors/"
   * @return the loaded image
   * @throws IOException thrown if the file cannot be found
   */
  protected BufferedImage getImageProxy(String filepath) throws IOException {
    Preconditions.checkNotNull(filepath, "Actor image is being loaded with a null string");
    Preconditions.checkArgument(filepath.length() > 0,
            "Actor image is being loaded with an empty string");

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

  /**
   * Set this actor as dead.
   */
  public void die() {
    dead = true;
    frame = 0;
  }

  /**
   * Is this actor dead.
   *
   * @return whether the actor is dead
   */
  public boolean isDead() {
    return dead;
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

  /**
   * The maze that this player is in.
   *
   * @return the maze that this player is in
   */
  public Maze getMaze() {
    return maze;
  }

  /**
   * Is this actor a player.
   *
   * @return whether this actor is a player
   */
  public boolean isPlayer() {
    return this instanceof Player;
  }

  @Override
  public abstract String toString();
}
