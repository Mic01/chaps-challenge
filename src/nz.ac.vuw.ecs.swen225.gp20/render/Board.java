package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Renderer class for displaying the board.
 */
public class Board extends JPanel {

  // Constant Variables
  public int visionRange = 9;
  public int reach = visionRange/2;
  public int tileSize = 70;
  public Dimension dimension = new Dimension(tileSize*visionRange, tileSize*visionRange);

  //Potential use
  public int sleepTime = 100; //Time in ms before each draw (ill be adding half frames)
  public enum Soundeffects{
    metalWalk, waterSwim, iceWalk, lavaSwim, slide, pickup_item, finish_level, death, openDoor
  }
  public enum Animations{
    doorOpen, death
  }

  //Rendering Variable
  public Tile[][] level;
  public Tile[][] lastVision;
  public Tile[][] vision;
  public Player player;
  public Maze maze;
  public ArrayList<Actor> moving = new ArrayList<>();
  public String animation;

  /**
   * Construct a new Board when a new level is loaded.
   *
   * @param maze Maze object, fetching values of current level
   * @param player current player
   */
  public Board(Player player, Maze maze){
    //JPanel Variables
    this.setPreferredSize(dimension);
    this.setBackground(new Color(255, 0, 255)); //for debugging
    this.setLayout(new FlowLayout(FlowLayout.LEFT));

    //Setting Variables
    this.maze = maze;
    this.player = player;
    this.vision = new Tile[visionRange][visionRange];
    this.lastVision = new Tile[visionRange][visionRange];
    updateLevel();
  }

  /**
   * Fetches a new level from maze
   * Called after a level is completed.
   */
  public void updateLevel(){
    level = maze.getTiles();
    setVision();
  }

  /**
   * Based on Players position, return a new 2D Array
   * of all tiles visible on board to draw.
   */
  public void setVision(){
    lastVision = vision;
    int yCount=0;
    int xCount=0;

    for(int xAxis=player.getX()-reach; xAxis<player.getX()+reach; xAxis++){
      xCount++;
      for(int yAxis=player.getY()-reach; yAxis<player.getY()+reach; yAxis++){
        yCount++;

        //Adding tiles only if they in range
        if((xAxis > 0 && yAxis > 0) && (xAxis < level.length && yAxis < level[0].length)) {
          vision[xCount % visionRange][yCount % visionRange] = level[xAxis][yAxis];
        }else{
          vision[xCount % visionRange][yCount % visionRange] = new EmptyTile();
        }
      }
    }
  }

  /**
   * Draws the visible board and all entities on-top of tiles,
   * Calls the pre-built paint function of the JPanel and draws with graphics.
   *
   * @param moving All moving chars, Draw a frame of each animation
   * @param animation if non-moving animation is happening
   */
  public void draw(ArrayList<Actor> moving, String animation){
    setVision();
    this.moving = moving;
    this.animation = animation;

    this.repaint();
    this.revalidate();
  }

  @Override
  protected void paintComponent(Graphics g){
    try {
      drawTiles(g, 0);
      drawEntities(g, 0);
      drawAnimations(g);
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  /**
   * First step of draw method,
   * draws all tiles in players current vision.
   */
  private void drawTiles(Graphics g, int offset) throws IOException{
    for(int xAxis=0; xAxis<visionRange; xAxis++){
      for(int yAxis=0; yAxis<visionRange; yAxis++){
        g.drawImage(vision[xAxis][yAxis].getImage(),
                (xAxis*tileSize)+offset, (yAxis*tileSize)+offset, this);
      }
    }
  }

  /**
   * Second step of draw method,
   * draws a new frame of every actor that has moved this round.
   */
  private void drawEntities(Graphics g, int offset) throws IOException {
    for(Actor actor : moving){
      g.drawImage(actor.getImage(),
              (actor.getX()*tileSize)+offset, (actor.getY()*tileSize)+offset, this);

      if(actor.getCurrentTile() instanceof Ice)
        playSound("slide");
      else if(actor.getCurrentTile() instanceof Water)
        playSound("waterWalk");
      else
        playSound("metalWalk");
    }
  }

  /**
   * Third step of draw method,
   * Loops through unique (non-walk) animations and draws them.
   */
  private void drawAnimations(Graphics g){
    playAnimations(animation, player, g);
  }

  /**
   * Switch statement to draw a frame of an animation
   * or create a new animation
   *
   * @param animation
   */
  public void playAnimations(String animation, Actor actor, Graphics g){
    //todo create doorOpen and death myself
    switch(Animations.valueOf(animation)){
      case doorOpen:
        playSound("openDoor");
        break;

      case death:
        playSound("death");
        break;

      default:
        throw new IllegalStateException("Unexpected value: " + Animations.valueOf(animation));
    }
  }

  /**
   * Plays a sound from assets folder
   *
   * @param sound of animation.
   */
  public void playSound(String sound){
    //todo make sound files
    //todo play them from here
    switch(Soundeffects.valueOf(sound)){
      case metalWalk:
        break;

      case waterSwim:
        break;

      case iceWalk:
        break;

      case lavaSwim:
        break;

      case slide:
        break;

      case pickup_item:
        break;

      case finish_level:
        break;

      case death:
        break;

      case openDoor:
        break;

      default:
        throw new IllegalStateException("Unexpected value: " + Soundeffects.valueOf(sound));
    }
  }
}
