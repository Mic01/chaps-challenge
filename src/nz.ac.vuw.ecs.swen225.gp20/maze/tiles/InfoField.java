package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

public class InfoField extends Tile {
    private final String info;

    InfoField(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public boolean isTraversable(Actor actor) {
        return true;
    }
}
