package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;

public class LockedDoor extends Tile {
    private final String colour;

    LockedDoor(String colour) {
        this.colour = colour;
    }

    @Override
    public boolean isTraversable(Actor actor) {
        if (actor instanceof Player) {
            Player player = (Player) actor;
            return player.isHolding(new Key(colour));
        }
        return false;
    }
}
