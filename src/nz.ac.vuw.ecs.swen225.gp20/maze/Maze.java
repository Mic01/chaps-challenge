package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

public class Maze {
  private int levelNum;
  private Tile[][] tiles;

  public Maze(int levelNum) {
    this.levelNum = levelNum;
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
}