package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.ArrayList;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;

public class Maze {
  private final Tile[][] tiles;
  private final ArrayList<Actor> actors = new ArrayList<>();
  private Player player;

  public Maze(String levelFile) {
    tiles = Persistence.loadLevel(levelFile, this);
  }

  public void setTile(int x, int y, Tile newTile) {
    tiles[x][y] = newTile;
  }

  public Tile getTile(int x, int y) {
    return tiles[x][y];
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  /**
   * Get the player actor for this maze.
   *
   * @return the player actor for this maze
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Set the player for this maze.
   *
   * @param player the new player for this maze
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Get the list of actors in this maze.
   *
   * @return list of non player actors
   */
  public ArrayList<Actor> getActors() {
    return actors;
  }

  /**
   * Add an actor to this maze.
   *
   * @param actor the actor to add to this maze
   */
  public void addActor(Actor actor) {
    actors.add(actor);
  }
}