package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Renderer class for displaying the board.
 */
public class Board {
  // Constant Variables
  public int visionRange = 9;
  public int reach = visionRange/2;
  public int tileSize = 80; //Maybe change this

  //Potential use
  public boolean actorsPresent;
  public int sleepTime = 200; //Time in ms before each draw
  public enum Soundeffects{
    metalWalk, waterWalk, iceWalk, fireWalk, slide,
    pickup_item, finish_level, death, flickSwitch_openDoor
  }
  public enum Animations{
    walkLeft, walkUp, walkRight, walkDown, explosion, death
  }

  //Rendering Variables
  public Dimension dimension = new Dimension(tileSize*visionRange, tileSize*visionRange);
  public Tile[][] level;
  public Tile[][] vision;
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
    this.player = player;
    this.actors = actors;
    this.vision = new Tile[visionRange][visionRange];
    if(actors.size() != 0){ actorsPresent = true; }
  }

  /**
   * Based on Players position, return a new 2D Array
   * of all tiles visible on board to draw.
   */
  public void setVision(){
    int yCount=0;
    int xCount=0;

    for(int xAxis=player.getX()-reach; xAxis<player.getX()+reach; xAxis++){
      xCount++;
      for(int yAxis=player.getY()-reach; yAxis<player.getY()+reach; yAxis++){
        yCount++;
        //todo add catch for outofbounds exception
        vision[xCount%visionRange][yCount%visionRange] = level[xAxis][yAxis];
      }
    }
  }

  /**
   * Draws the visible board and all entities on-top of tiles.
   *
   * @param sound Play sound passed.
   * @param animations Play a frame of each animation
   */
  public void draw(String sound, ArrayList<Actor> animations){
    setVision();
    drawTiles();
    drawEntities();
    playSound(sound);
    drawAnimations(animations);
  }

  /**
   * First step of draw method,
   * draws all tiles in players current vision.
   */
  public void drawTiles(){
    for(int xAxis=0; xAxis<visionRange; xAxis++){
      for(int yAxis=0; yAxis<visionRange; yAxis++){
        //todo draw with passed canvas
        //todo or by drawing onto positions of static canvas in maze
      }
    }
  }

  /**
   * Second step of draw method,
   * draws entities on-top of tiles (Actors, keys, etc...).
   */
  public void drawEntities(){
    for(int xAxis=0; xAxis<visionRange; xAxis++) {
      for(int yAxis=0; yAxis<visionRange; yAxis++){
        //todo draw with passed canvas
        //todo or by drawing onto positions of static canvas in maze
      }
    }
  }

  /**
   * Third step of draw method,
   * Plays a sound from enum by fetching from Maze hashmap.
   *
   * @param sound of animation.
   */
  public void playSound(String sound){
    //todo play sound files from michaiahs classes
    switch(Soundeffects.valueOf(sound)){
      case metalWalk:
        break;

      case waterWalk:
        break;

      case iceWalk:
        break;

      case fireWalk:
        break;

      case slide:
        break;

      case pickup_item:
        break;

      case finish_level:
        break;

      case death:
        break;

      case flickSwitch_openDoor:
        break;

      default:
        throw new IllegalStateException("Unexpected value: " + Soundeffects.valueOf(sound));
    }
  }

  /**
   * forth step of draw method,
   * Animation of actors/player and soundeffects.
   */
  public void drawAnimations(ArrayList<Actor> animations){
    for(Actor actor : animations){
      //todo call playAnimations method
      //todo base off of change in x/y
    }
  }

  public void playAnimations(String animation){
    //todo get images from michaiahs classes
    //todo this is still very wishy washy im not sure how its gonna work yet
    switch(Animations.valueOf(animation)){
      case walkLeft:
        break;

      case walkUp:
        break;

      case walkRight:
        break;

      case walkDown:
        break;

      case explosion:
        break;

      case death:
        break;

      default:
        throw new IllegalStateException("Unexpected value: " + Animations.valueOf(animation));
    }
  }
}
