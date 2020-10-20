package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.common.base.Preconditions;
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
  private int maxTreasures = 0;
  private int timeLimit;

  /**
   * Maze object that holds the tiles that represent the state of the game.
   *
   * @param levelFile the file of the level to load for this maze
   */
  public Maze(String levelFile) {
    Preconditions.checkNotNull(levelFile, "Maze passed a null levelFile name");
    tiles = Persistence.loadLevel(levelFile, this);

    width = tiles.length;
    assert width > 0;

    height = tiles[0].length;
    assert height > 0;

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
    Persistence.saveLevel(tiles, fileName, this);
  }

  /**
   * Get the tile at the provided coordinates.
   *
   * @param x x-coordinate of the tile
   * @param y y-coordinate of the tile
   * @return the tile at the provided coordinates
   */
  public Tile getTile(int x, int y) {
    Preconditions.checkElementIndex(x, getWidth(),
            x + " is not within the width of the Maze - " + getWidth() + ": getTile() in Maze");
    Preconditions.checkElementIndex(y, getHeight(),
            y + " is not within the height of the Maze - " + getHeight() + ": getTile() in Maze");

    assert tiles[x][y] != null;

    return tiles[x][y];
  }

  /**
   * Set the player for this maze.
   *
   * @param player the new player for this maze
   */
  public void setPlayer(Player player) {
    Preconditions.checkNotNull(player, "Maze Player is being set to null");
    this.player = player;
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
   * Add an actor to this maze.
   *
   * @param actor the actor to add to this maze
   */
  public void addActor(Actor actor) {
    Preconditions.checkNotNull(actor, "A null Actor is being added to Maze");
    actors.add(actor);
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
  public void addAutoActor(AutoActor actor) {
    Preconditions.checkNotNull(actor, "A null AutoActor is being added to Maze");
    autoActors.add(actor);
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
   * Get the width of this Maze.
   *
   * @return the width of this Maze
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of this Maze.
   *
   * @return the height of this Maze
   */
  public int getHeight() {
    return height;
  }

  /**
   * Set the text that should be displayed,
   * used for specific events and Info tiles.
   *
   * @param displayText String to be displayed
   */
  public void setDisplayText(String displayText) {
    Preconditions.checkNotNull(displayText, "Maze displayText is being set to null");
    this.displayText = displayText;
  }

  /**
   * Get the text that should be displayed,
   * used for specific events and Info tiles.
   *
   * @return String to be displayed
   */
  public String getDisplayText() {
    return displayText;
  }

  /**
   * Set this Maze as finished.
   */
  public void setFinished() {
    this.finished = true;
  }

  /**
   * Has this Maze been finished?.
   *
   * @return whether this Maze has been finished
   */
  public boolean isFinished() {
    return finished;
  }

  /**
   * Set the number of treasures left on the level.
   *
   * @param treasuresLeft the number of treasures left on the level
   */
  public void setTreasuresLeft(int treasuresLeft) {
    Preconditions.checkArgument(treasuresLeft >= 0,
            "Maze TreasuresLeft is being set to a negative value");
    this.treasuresLeft = treasuresLeft;
    if (treasuresLeft > maxTreasures) {
      maxTreasures = treasuresLeft;
    }
    //assert treasuresLeft + player.treasuresCollected() == maxTreasures;
  }

  /**
   * Get the number of treasures left on the level.
   *
   * @return the number of treasures left on the level
   */
  public int getTreasuresLeft() {
    return treasuresLeft;
  }

  /**
   * Reduce the number of treasures left by 1.
   */
  public void reduceTreasuresLeft() {
    treasuresLeft--;
    assert treasuresLeft >= 0;
    assert treasuresLeft + player.treasuresCollected() == maxTreasures;
  }

  /**
   * Set the time limit for this maze (in seconds).
   *
   * @param timeLimit the time limit for this maze (in seconds)
   */
  public void setTimeLimit(int timeLimit) {
    Preconditions.checkArgument(timeLimit >= 0,
            "Maze TimeLimit is being set to a negative value");
    this.timeLimit = timeLimit;
  }

  /**
   * Get the time limit for this maze (in seconds).
   *
   * @return  the time limit for this maze (in seconds)
   */
  public int getTimeLimit() {
    return timeLimit;
  }
}