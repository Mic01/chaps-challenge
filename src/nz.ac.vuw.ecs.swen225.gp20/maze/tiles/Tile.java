package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public abstract class Tile {

  public abstract boolean isTraversable(Actor actor);
}
