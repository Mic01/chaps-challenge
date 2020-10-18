package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;

public class Maze {
  private final Tile[][] tiles;
  private final ArrayList<Actor> actors = new ArrayList<>();
  private final ArrayList<Actor> autoActors = new ArrayList<>();
  private Player player;
  private final int width;
  private final int height;

  /**
   * Maze object that holds the tiles that represent the state of the game.
   *
   * @param levelFile the file of the level to load for this maze
   */
  public Maze(String levelFile) {
    tiles = Persistence.loadLevel(levelFile, this);
    width = tiles.length;
    height = tiles[0].length;
    for (Actor actor : actors) {
      actor.setup();
    }
  }

  public void setTile(int x, int y, Tile newTile) {
    tiles[x][y] = newTile;
  }

  public Tile getTile(int x, int y) {
    return tiles[x][y];
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
  public List<Actor> getActors() {
    return Collections.unmodifiableList(actors);
  }

  /**
   * Get the list of automatic actors in this maze.
   *
   * @return list of non player actors
   */
  public List<Actor> getAutoActors() {
    return Collections.unmodifiableList(autoActors);
  }

  /**
   * Add an actor to this maze.
   *
   * @param actor the actor to add to this maze
   */
  public void addActor(Actor actor) {
    actors.add(actor);
  }

  /**
   * Add an actor to this maze.
   *
   * @param actor the actor to add to this maze
   */
  public void addAutoActor(Actor actor) {
    autoActors.add(actor);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}