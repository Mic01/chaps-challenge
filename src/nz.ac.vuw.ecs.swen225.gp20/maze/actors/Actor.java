package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

public abstract class Actor {
    protected int xPos, yPos;
    protected Tile[][] maze;

    Actor(int xPos, int yPos, Tile[][] maze) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.maze = maze;
    }

    public boolean moveUp() {
        //if (maze[xPos][yPos].isTraversable(this)) {

        //}
        return false;
    }
}
