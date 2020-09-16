package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

public class Maze {
    private int levelNum;
    private Tile[][] tiles;

    Maze(int levelNum) {
        this.levelNum = levelNum;
    }
}
