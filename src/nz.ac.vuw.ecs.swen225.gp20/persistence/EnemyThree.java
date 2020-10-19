package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Wall;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyThree extends AutoActor {
  /**
   * A creature that is moving automatically, typically an enemy creature.
   *
   * @param xpos      the xpos to start this actor at
   * @param ypos      the ypos to start this actor at
   * @param maze      the maze this actor is being made on
   * @param direction
   */
  public EnemyThree(int xpos, int ypos, Maze maze, Direction direction) {
    super(xpos, ypos, maze, direction);
  }

  @Override
  public void autoMove() {
    if(getAdjacentTile(currentDirection) instanceof Wall){
      turnAround();
    }
    moveForward();
  }

  @Override
  public BufferedImage getImage(boolean moving) throws IOException {
    String type;
    if (moving) {
      type = "walk";
    } else {
      type = "stand";
      frame = 0;
    }
    String path = "enemy/" + type + "_" + (moving ? (frame + "_") : "") + currentDirection;
    return getImageProxy(path);

  }

  @Override
  public String toString() {
    return "Enemy1";
  }
}
