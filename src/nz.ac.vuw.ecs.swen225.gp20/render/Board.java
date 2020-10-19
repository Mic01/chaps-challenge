package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.NullTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;

/**
 * Renderer class for displaying the board.
 */
public class Board extends JPanel implements ActionListener {

  // Constant Variables
  private static final int visionRange = 9;
  private static final int reach = visionRange / 2;
  private static final int tileSize = 70;
  private static final int sleepTime = 300; //Time in ms before each draw

  //Static Rendering Variables
  private Tile[][] level;
  private Tile[][] lastVision;
  private Tile[][] vision;
  private Player player;
  private Maze maze;
  private ArrayList<AutoActor> autoActors = new ArrayList<>();

  //Dynamic Rendering Variables
  private ArrayList<Actor> moving = new ArrayList<>();
  private int animationState;
  private boolean halfFrame;
  private Timer timer = new Timer(sleepTime, this); //Redraws from timer in ActionListener
  private boolean playerMoved;

  //Method Enums
  private enum Soundeffects {
    metalWalk, waterSwim, iceWalk, lavaSwim, slide, pickup_item, finish_level, death, openDoor
  }
  private enum Animations {
    doorOpen, death
  }

  /**
   * Construct a new Board when a new level is loaded.
   *
   * @param maze Maze object, fetching values of current level
   */
  public Board(Maze maze) {
    //JPanel Variables
    Dimension dimension = new Dimension(tileSize * visionRange, tileSize * visionRange);
    setPreferredSize(dimension);

    setBackground(new Color(255, 0, 255)); //for debugging
    setLayout(new FlowLayout(FlowLayout.LEFT));

    //Setting Variables
    this.maze = maze;
    vision = new Tile[visionRange][visionRange];
    lastVision = new Tile[visionRange][visionRange];
    updateLevel(maze);
  }

  /**
   * Reconstructs values for a new level,
   * done with a new Maze objection with level info.
   */
  public void updateLevel(Maze maze) {
    player = maze.getPlayer();
    autoActors = maze.getAutoActors();
    setVision();
  }

  /**
   * Based on Players position, return a new 2D Array
   * of all tiles visible on board to draw.
   *
   * Also updating the last visible tiles for transitions.
   */
  private void setVision() {
    for (int x = player.getX() - reach, xcount = 0; x <= player.getX() + reach; x++, xcount++) {
      for (int y = player.getY() - reach, ycount = 0; y <= player.getY() + reach; y++, ycount++) {
        lastVision[xcount][ycount] = vision[xcount][ycount];

        //Adding tiles only if they in range
        if ((x > 0 && y > 0) && (x < maze.getWidth() && y < maze.getHeight())) {
          vision[xcount][ycount] = maze.getTile(x,y);
        } else {
          vision[xcount][ycount] = new NullTile();
        }

      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //If not animation
    if(halfFrame) {
      halfFrame = false;
    }else{
      this.moving = new ArrayList<>();
      timer.stop();
    }
    repaint();
    updateUI();
  }

  /**
   * Draws the visible board and all entities on-top of tiles,
   * Calls the pre-built paint function of the JPanel and draws with graphics.
   *
   * @param playerMove if player has moved
   */
  public void draw(boolean playerMove) {
    setVision();
    animationState = 0;
    playerMoved = playerMove;
    //If player is currently moving, draw a half frame
    if(playerMove){
      halfFrame = true;
    }

    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    try {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g.create();
      int xOffset = 0;
      int yOffset = 0;

      //Get offsets if currently drawing halfFrame
      if(halfFrame){
        xOffset = getOffsetX();
        yOffset = getOffsetY();
        drawTiles(g2d, -xOffset, -yOffset, lastVision);
      }

      //Draw full frame
      drawTiles(g2d, xOffset, yOffset, vision);
      drawEntities(g2d, xOffset, yOffset);
      g2d.dispose();
      
      if(halfFrame){
        timer.start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * First step of draw method,
   * draws all tiles in players current vision.
   */
  private void drawTiles(Graphics g, int xOffset, int yOffset, Tile[][] drawArray) throws IOException {
    for (int x = 0; x <= visionRange - 1; x++) {
      for (int y = 0; y <= visionRange - 1; y++) {
        g.drawImage(drawArray[x][y].getImage(),
                (x * tileSize) + xOffset, (y * tileSize) + yOffset, this);

        //Check if tile has item, draw tile with item
        if (drawArray[x][y] instanceof FreeTile && ((FreeTile) drawArray[x][y]).hasItem()) {
          g.drawImage(((FreeTile) drawArray[x][y]).getItem().getImage(),
                  (x * tileSize) + xOffset, (y * tileSize) + yOffset, this);
        }
      }
    }
  }

  /**
   * Second step of draw method,
   * draws a new frame of every actor that has moved this round.
   */
  private void drawEntities(Graphics g, int xOffset, int yOffset) throws IOException {
    g.drawImage(player.getImage(playerMoved),
            (getVisionX(player.getX()) * tileSize), (getVisionY(player.getY()) * tileSize), this);

    for(AutoActor actor : autoActors){
      g.drawImage(actor.getImage(true),
              (getVisionX(actor.getX()) * tileSize), (getVisionY(actor.getY()) * tileSize), this);
    }
      /*if(actor.equals(player)) {
        playerMoved = true;
        if (actor.getCurrentTile() instanceof Ice) {
          playSound("slide");
        } else if (actor.getCurrentTile() instanceof Water) {
          playSound("waterSwim");
        } else {
          playSound("metalWalk");
        }
      }*/
  }

  /**
   * Third step of draw method,
   * Loops through unique (non-walk) animations and draws them.
   */
  private void drawAnimations(Graphics g, String animation) {
    playAnimations(animation, g);
  }

  /**
   * Switch statement to draw a frame of an animation
   * or create a new animation.
   *
   * @param animation enum value of animation
   * @param g         passed canvas to draw on
   */
  private void playAnimations(String animation, Graphics g) {
    //todo create doorOpen and death myself
    switch (Animations.valueOf(animation)) {
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
   * Plays a sound from assets folder.
   *
   * @param sound of animation.
   */
  private void playSound(String sound) {
    //todo make sound files
    //todo play them from here
    switch (Soundeffects.valueOf(sound)) {
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

  /**
   * For drawing actors, convert their x coordinate to vision coordinate.
   *
   * @param x curr x coordinate
   * @return new x coordinate
   */
  private int getVisionX(int x) {
    return x - player.getX() + reach;
  }

  /**
   * For drawing actors, convert their y coordinate to vision coordinate.
   *
   * @param y curr y coordinate
   * @return new y coordinate
   */
  private int getVisionY(int y) {
    return y - player.getY() + reach;
  }

  /**
   * Find offset for drawing half frames,
   * by checking direction they moved from.
   * @return x offset
   */
  public int getOffsetX(){
    return (player.getX()-player.getPrevX())*35;
  }

  /**
   * Find offset for drawing half frames,
   * by checking direction they moved from.
   * @return y offset
   */
  public int getOffsetY(){
    return (player.getY()-player.getPrevY())*35;
  }

  @Override
  public String toString() {
    return "Board{" +
            "visionRange=" + visionRange +
            ", tileSize=" + tileSize +
            ", sleepTime=" + sleepTime +
            ", level=" + Arrays.toString(level) +
            ", lastVision=" + Arrays.toString(lastVision) +
            ", vision=" + Arrays.toString(vision) +
            ", player=" + player +
            ", maze=" + maze +
            ", moving=" + moving +
            ", actors=" + autoActors +
            '}';
  }
}
