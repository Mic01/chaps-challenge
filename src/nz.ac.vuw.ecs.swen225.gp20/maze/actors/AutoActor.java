package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

public abstract class AutoActor extends Actor {

  /**
   * A creature that is moving automatically, typically an enemy creature.
   *
   * @param xpos the xpos to start this actor at
   * @param ypos the ypos to start this actor at
   * @param maze the maze this actor is being made on
   */
  public AutoActor(int xpos, int ypos, Maze maze, Direction direction) {
    super(xpos, ypos, maze);
    this.currentDirection = direction;
    maze.addAutoActor(this);
  }

  /**
   * Make this actor follow the next step in its automatic movement.
   */
  public abstract void autoMove();

  /**
   * Turn this AutoActor left once.
   */
  protected void turnLeft() {
    int intDirection = (currentDirection.ordinal() + 3) % 4;
    currentDirection = Direction.values()[intDirection];
  }

  /**
   * Turn this AutoActor right once.
   */
  protected void turnRight() {
    int intDirection = (currentDirection.ordinal() + 1) % 4;
    currentDirection = Direction.values()[intDirection];
  }

  /**
   * Make this AutoActor face the opposite direction.
   */
  protected void turnAround() {
    int intDirection = (currentDirection.ordinal() + 2) % 4;
    currentDirection = Direction.values()[intDirection];
  }

  /**
   * Get the tile adjacent this AutoActor, in the provided direction.
   *
   * @return the Tile next to this AutoActor
   */
  protected Tile getAdjacentTile(Direction direction) {
    Preconditions.checkNotNull(direction,
            "AutoActor getAdjacentTile is being given a null direction");

    switch (direction) {
      case up:
        return maze.getTile(xpos, ypos - 1);
      case right:
        return maze.getTile(xpos + 1, ypos);
      case down:
        return maze.getTile(xpos, ypos + 1);
      case left:
        return maze.getTile(xpos - 1, ypos);
      default:
        return currentTile;
    }
  }

  /**
   * Move in the direction that this AutoActor is currently facing.
   */
  protected void moveForward() {
    switch (currentDirection) {
      case left:
        moveLeft();
        break;
      case right:
        moveRight();
        break;
      case down:
        moveDown();
        break;
      case up:
        moveUp();
        break;
      default:
    }
  }

  /**
   * Get the direction that this AutoActor is currently facing.
   *
   * @return the direction that this AutoActor is currently facing
   */
  public Direction getCurrentDirection() {
    return currentDirection;
  }

  @Override
  public abstract BufferedImage getImage(boolean moving) throws IOException;
}
