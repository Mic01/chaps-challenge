package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class Wall extends Tile {

  @Override
  public boolean isTraversable(Actor actor) {
    return false;
  }

}