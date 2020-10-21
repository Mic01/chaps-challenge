package levels;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Wall;

public class EnemyTwo extends AutoActor {
  /**
   * A creature that is moving automatically, typically an enemy creature.
   *
   * @param xpos      the xpos to start this actor at
   * @param ypos      the ypos to start this actor at
   * @param maze      the maze this actor is being made on
   * @param direction the direction of this actor
   */
  public EnemyTwo(int xpos, int ypos, Maze maze, Direction direction) {
    super(xpos, ypos, maze, direction);
  }

  @Override
  public void autoMove() {
    if (!getAdjacentTile(currentDirection).isTraversable(this)) {
      turnRight();
    }
    moveForward();
  }

  @Override
  public BufferedImage getImage(boolean moving) throws IOException {

    String path = "enemy/walk" + "_" + (frame % 2) + "_" + currentDirection;
    return getImageProxy(path);

  }

  @Override
  public String toString() {
    return "Enemy2";
  }
}
