package render;

import maze.Tile;

import java.awt.*;

/**
 * Renderer class for displaying the board.
 */
public class Board {
  // Constant Variables
  public int visionRange = 9;
  public int tileSize = 80; //Maybe change this

  //Potential use
  public boolean actorsPresent;
  public int sleepTime = 200; //Time in ms before each draw

  //Rendering Variables
  public Dimension dimension = new Dimension(tileSize*visionRange, tileSize*visionRange);
  public Tile[][] level;
  public Tile[][] vision = new Tile[visionRange][visionRange];
  public Player player;
  public ArrayList<Actor> actors;

  /**
   * Construct a new Board when a new level is loaded.
   *
   * @param level 2d array of full level
   * @param player current player
   * @param actors Any actors on this level
   */
  public Board(Tile[][] level, Player player, ArrayList<Actor> actors){
    this.level = level;
    this.Player = player;
    this.actors = actors;
    if(actors.size() != 0){ actorsPresent = true; }
  }

  /**
   * Based on Players position, return a new 2D Array
   * of all tiles visible on board to draw.
   */
  public void setVision(int x, int y){

  }

  /**
   * Draws the visible board and all entities on-top of tiles.
   */
  public void draw(){

  }

  /**
   * First step of draw method,
   * draws all tiles in players current vision.
   */
  public void drawTiles(){

  }

  /**
   * Second step of draw method,
   * draws entities on-top of tiles (Actors, keys, etc...).
   */
  public void drawEntities(){

  }

  /**
   * third step of draw method,
   * Animation of actors/player and soundeffects.
   */
  public void drawAnimations(){
    //this might change, not too sure how i implement it yet

  }
}
