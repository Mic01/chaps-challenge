package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

public abstract class Actor {
  private int xpos;
  private int ypos;
  private final Maze maze;
  private FreeTile currentTile;

  public Actor(int xpos, int ypos, Maze maze) {
    this.xpos = xpos;
    this.ypos = ypos;
    this.maze = maze;
    currentTile = (FreeTile) maze.getTile(xpos, ypos);
  }

  /**
   * Attempt to move this actor up.
   *
   * @return whether this actor successfully moved up or not
   */
  public boolean moveUp() {
    if (moveTo(xpos, ypos - 1)) {
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
    if (moveTo(xpos, ypos + 1)) {
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
    if (moveTo(xpos - 1, ypos)) {
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
    if (moveTo(xpos + 1, ypos)) {
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
}
