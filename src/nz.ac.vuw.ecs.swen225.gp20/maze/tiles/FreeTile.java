package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Treasure;

public class FreeTile extends Tile {
  private Item item;
  private Actor actor;

  public FreeTile() {
    item = null;
  }

  public FreeTile(Item item) {
    this.item = item;
  }

  public FreeTile(Actor actor) {
    this.actor = actor;
  }

  /**
   * Add an actor to this tile.
   *
   * @param actor the actor to add to this tile
   */
  public void addActor(Actor actor) {
    //todo: deal with another actor on this tile already

    this.actor = actor;

    if (item != null && actor instanceof Player) {
      Player player = (Player) actor;
      if (item instanceof Treasure) {
        player.pickupTreasure();
      } else {
        player.pickup(item);
      }
      item = null;
    }
  }

  /**
   * Remove the actor from this tile.
   */
  public void removeActor() {
    actor = null;
  }

  @Override
  public boolean isTraversable(Actor actor) {
    return actor instanceof Player || item == null;
  }
}
