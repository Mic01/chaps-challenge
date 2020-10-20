package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Conveyor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Exit;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.ExitLock;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.LockedDoor;
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
  private Tile[][] lastVision;
  private Tile[][] vision;
  private Player player;
  private Maze maze;
  private ArrayList<AutoActor> autoActors = new ArrayList<>();

  //Dynamic Rendering Variables
  private boolean halfFrame;
  private boolean inAnimation;
  private Timer timer = new Timer(sleepTime, this); //Redraws from timer in ActionListener

  //Animation Variables
  private boolean playerMoved;
  public static HashMap<String, SoundEffect> loadedSounds = new HashMap<>();
  private int inventorySize;
  private int taskSize;
  public AudioPlayer audioPlayer = new AudioPlayer();
  private int deathTick;

  //Method Enums
  private enum SoundEffects {
    metalWalk_0, metalWalk_1, waterSwim_0, waterSwim_1, slide, conveyor_slide, pickup_item, finish_level, death, airlock
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
    inventorySize = 0;
    vision = new Tile[visionRange][visionRange];
    lastVision = new Tile[visionRange][visionRange];
    updateLevel(maze);
    deathTick = 0;

    //Preloading all Sound files
    try {
      for (SoundEffects s : SoundEffects.values()) {
        loadedSounds.put(s.toString(), new SoundEffect(s.toString()));
      }
    }catch(Exception e){ e.printStackTrace();}
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
    //Player not dead
    if(!player.isDead()) {
      if (halfFrame) {
        halfFrame = false;
      } else {
        playerMoved = false;
        inAnimation = false;
        timer.stop();
      }
      //Draw all death animation frames
    }else if(deathTick < 3){
      deathTick++;
    }else{
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
    playerMoved = playerMove;

    //If player is currently moving, draw a half frame
    if(playerMove){
      halfFrame = true;
      inAnimation = true;
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
      
      if(halfFrame || player.isDead()){
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
    //Draw Player
    g.drawImage(player.getImage(playerMoved),
            (getVisionX(player.getX()) * tileSize),
            (getVisionY(player.getY()) * tileSize), this);

    //Draw Auto actors
    if (!player.isDead()) {
      for (AutoActor actor : autoActors) {
        if (actorInVision(actor)) {
          g.drawImage(actor.getImage(true),
                  (getVisionX(actor.getX()) * tileSize) + xOffset,
                  (getVisionY(actor.getY()) * tileSize) + yOffset, this);
        }
      }
    }

    //Play Sound Effects
    try {
      if(player.isDead()){
        playSound("death",3);
      } else if(playerMoved) {
        if(player.isOn(Exit.class)){
          playSound("finish_level",3);
        } else if(player.isOn(LockedDoor.class) || player.isOn(ExitLock.class)){
          playSound("airlock",2);
        }else if (player.isOn(Conveyor.class)) {
          playSound("conveyor_slide",2);
        }else if (player.isOn(Ice.class)) {
          playSound("slide",2);
        } else if (player.getInventory().size() > inventorySize) {
          inventorySize++;
          playSound("pickup_item",2);
        } else if(player.treasuresCollected() > taskSize){
          taskSize++;
          playSound("pickup_item",2);
        } else if (player.isOn(Water.class)) {
          playSound("waterSwim_0",1);
        } else {
          playSound("metalWalk_0",1);
        }
     }
    }catch(Exception e){ e.printStackTrace(); }
  }

  /**
   * Plays a sound from assets folder.
   *
   * @param sound name of animation.
   */
  private void playSound(String sound, int priority) {
    if(deathTick == 0) {
      audioPlayer.playAudio(priority, loadedSounds.get(sound));
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

  /**
   * Helper method to check if actor is in
   * current vision, to draw them.
   *
   * @param actor to check if in frame
   * @return true if in frame, else false
   */
  public boolean actorInVision(AutoActor actor){
    return (getVisionX(actor.getX()) >= 0 &&
            getVisionX(actor.getX()) < visionRange &&
            getVisionY(actor.getY()) >= 0 &&
            getVisionY(actor.getY()) < visionRange);
  }

  /**
   * Checks whether in current animation,
   * to stop tick based draw calls overriding animations.
   *
   * @return true if in animation, false otherwise
   */
  public boolean isAnimating(){
    return inAnimation;
  }

  /**
   * Changes delay between drawing, used for replaying levels
   * to speed up or slow down animations.
   *
   * @param speed multiplier to delay time (ie 2x or 0.5x)
   */
  public void setAnimateSpeed(int speed){
    timer = new Timer(sleepTime*speed, this);
  }

  @Override
  public String toString() {
    return "Board{" +
            "visionRange=" + visionRange +
            ", tileSize=" + tileSize +
            ", sleepTime=" + sleepTime +
            ", lastVision=" + Arrays.toString(lastVision) +
            ", vision=" + Arrays.toString(vision) +
            ", player=" + player +
            ", maze=" + maze +
            ", actors=" + autoActors +
            '}';
  }
}
