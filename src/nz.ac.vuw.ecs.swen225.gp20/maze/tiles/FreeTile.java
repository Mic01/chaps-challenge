package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;

public class FreeTile extends Tile {
    private Item item;
    private Actor actor;

    FreeTile() {
        item = null;
    }

    FreeTile(Item item) {
        this.item = item;
    }

    FreeTile(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean isTraversable(Actor actor) {
        return actor instanceof Player || item == null;
    }
}
