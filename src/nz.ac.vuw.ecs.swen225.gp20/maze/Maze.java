package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.ArrayList;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;

public class Maze {
  private final Tile[][] tiles;
  private final ArrayList<Actor> actors = new ArrayList<>();
  private final ArrayList<AutoActor> autoActors = new ArrayList<>();
  private Player player;
  private final int width;
  private final int height;
  private String displayText = "";
  private boolean finished = false;
  private int treasuresLeft = 0;
  private int timeLimit;

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

  /**
   * Save this Maze using Persistence save method.
   *
   * @param fileName name of the file to save the Maze to
   */
  public void save(String fileName) {
    Persistence.saveLevel(tiles, fileName);
  }

  /**
   * Get the tile at the provided coordinates.
   *
   * @param x x-coordinate of the tile
   * @param y y-coordinate of the tile
   * @return the tile at the provided coordinates
   */
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
  public ArrayList<Actor> getActors() {
    return actors;
  }

  /**
   * Get the list of automatic actors in this maze.
   *
   * @return list of non player actors
   */
  public ArrayList<AutoActor> getAutoActors() {
    return autoActors;
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
  public void addAutoActor(AutoActor actor) {
    autoActors.add(actor);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getDisplayText() {
    return displayText;
  }

  public void setDisplayText(String displayText) {
    this.displayText = displayText;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished() {
    this.finished = true;
  }

  public void setTreasuresLeft(int treasuresLeft) {
    this.treasuresLeft = treasuresLeft;
  }

  public int getTreasuresLeft() {
    return treasuresLeft;
  }

  public void reduceTreasuresLeft() {
    treasuresLeft--;
  }

  public void setTimeLimit(int timeLimit) {
    this.timeLimit = timeLimit;
  }

  public int getTimeLimit() {
    return timeLimit;
  }
}