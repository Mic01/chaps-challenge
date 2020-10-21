package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.Timer;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.IcePotion;
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
 * Renderer class for displaying the board,
 * Draws the visible tiles, performs animations.
 */
public class Board extends JPanel implements ActionListener {

  // Constant Variables
  private static final int visionRange = 9;
  private static final int reach = visionRange / 2;
  private static final int tileSize = 70;
  private static final int sleepTime = 300; //Time in ms before each draw

  //Static Rendering Variables
  private final Tile[][] lastVision;
  private final Tile[][] vision;
  private Player player;
  private final Maze maze;
  private ArrayList<AutoActor> autoActors = new ArrayList<>();

  //Dynamic Rendering Variables
  private boolean halfFrame;
  private boolean inAnimation;
  private Timer timer = new Timer(sleepTime, this); //Redraws from timer in ActionListener

  //Animation Variables
  private boolean playerMoved;
  public final HashMap<String, SoundEffect> loadedSounds = new HashMap<>();
  private int inventorySize;
  private int taskSize;
  public AudioPlayer audioPlayer = new AudioPlayer();
  private int deathTick;
  private final Set<Tile> walkedOnDoors = new HashSet<>();

  //Method Enums
  private enum SoundEffects {
    metalWalk_0, metalWalk_1, waterSwim_0, waterSwim_1, slide,
    conveyor_slide, pickup_item, finish_level, death, airlock
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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Reconstructs values for a new level,
   * done with a new Maze objection with level info.
   *
   * @param maze object for getting variables
   */
  public void updateLevel(Maze maze) {
    player = maze.getPlayer();
    autoActors = maze.getAutoActors();
    setVision();
  }

  /**
   * Based on Players position, return a new 2D Array
   * of all tiles visible on board to draw,
   * Also updating the last visible tiles for transitions.
   */
  private void setVision() {
    for (int x = player.getX() - reach, xcount = 0; x <= player.getX() + reach; x++, xcount++) {
      for (int y = player.getY() - reach, ycount = 0; y <= player.getY() + reach; y++, ycount++) {
        lastVision[xcount][ycount] = vision[xcount][ycount];

        //Adding tiles only if they in range
        if ((x > 0 && y > 0) && (x < maze.getWidth() && y < maze.getHeight())) {
          vision[xcount][ycount] = maze.getTile(x, y);
        } else {
          vision[xcount][ycount] = new NullTile();
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //Player not dead
    if (!player.isDead()) {
      if (halfFrame) {
        halfFrame = false;
      } else {
        playerMoved = false;
        inAnimation = false;
        timer.stop();
      }
      //Draw all death animation frames
    } else if (deathTick < 3) {
      deathTick++;
    } else {
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
    if (playerMove) {
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
      int offsetX = 0;
      int offsetY = 0;

      //Get offsets if currently drawing halfFrame
      if (halfFrame) {
        offsetX = getOffsetX();
        offsetY = getOffsetY();
        drawTiles(g2d, -offsetX, -offsetY, lastVision);
      }

      //Draw full frame
      drawTiles(g2d, offsetX, offsetY, vision);
      drawEntities(g2d, offsetX, offsetY);
      g2d.dispose();
      
      if (halfFrame || player.isDead()) {
        timer.start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * First step of draw method,
   * draws all tiles in players current vision.
   *
   * @param g graphics object to allow drawing images together
   * @param offsetX offset in x direction
   * @param offsetY offset in y direction
   * @param drawArray array of tiles to draw
   * @throws IOException if file not found
   */
  private void drawTiles(Graphics g, int offsetX, int offsetY, Tile[][] drawArray)
          throws IOException {
    for (int x = 0; x <= visionRange - 1; x++) {
      for (int y = 0; y <= visionRange - 1; y++) {
        g.drawImage(drawArray[x][y].getImage(),
                (x * tileSize) + offsetX, (y * tileSize) + offsetY, this);

        //Check if tile has item, draw tile with item
        if (drawArray[x][y] instanceof FreeTile && ((FreeTile) drawArray[x][y]).hasItem()) {
          g.drawImage(((FreeTile) drawArray[x][y]).getItem().getImage(),
                  (x * tileSize) + offsetX, (y * tileSize) + offsetY, this);
        }
      }
    }
  }

  /**
   * Second step of draw method,
   * draws a new frame of every actor that has moved this round.
   *
   * @param g graphics object to allow drawing images together
   * @param offsetX offset in x direction
   * @param offsetY offset in y direction
   * @throws IOException if file not found
   */
  private void drawEntities(Graphics g, int offsetX, int offsetY) throws IOException {
    //Draw Player
    g.drawImage(player.getImage(playerMoved),
            (getVisionX(player.getX()) * tileSize),
            (getVisionY(player.getY()) * tileSize), this);

    //Draw Auto actors
    if (!player.isDead()) {
      for (AutoActor actor : autoActors) {
        if (actorInVision(actor)) {
          g.drawImage(actor.getImage(true),
                  (getVisionX(actor.getX()) * tileSize) + offsetX,
                  (getVisionY(actor.getY()) * tileSize) + offsetY, this);
        }
      }
    }

    //Play sound effect based on players position and variables
    try {
      if (player.isDead()) {
        playSound("death", 4);
      } else if (playerMoved) {
        if (player.isOn(Exit.class)) {
          playSound("finish_level", 4);
        } else if ((player.isOn(LockedDoor.class) || player.isOn(ExitLock.class))
                && !walkedOnDoors.contains(maze.getTile(player.getX(), player.getY()))) {
          walkedOnDoors.add(maze.getTile(player.getX(), player.getY()));
          playSound("airlock", 3);
        } else if (player.isOn(Conveyor.class)) {
          playSound("conveyor_slide", 2);
        } else if (player.isOn(Ice.class) && !player.isHolding(new IcePotion())) {
          playSound("slide", 2);
        } else if (player.getInventory().size() > inventorySize) {
          inventorySize++;
          playSound("pickup_item", 3);
        } else if (player.treasuresCollected() > taskSize) {
          taskSize++;
          playSound("pickup_item", 3);
        } else if (player.isOn(Water.class)) {
          playSound("waterSwim_" + new Random().nextInt(2), 1);
        } else {
          playSound("metalWalk_" + new Random().nextInt(2), 0);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Plays a sound from assets folder.
   *
   * @param sound name of sound
   * @param priority of sound, to prioritize certain sounds over others
   */
  private void playSound(String sound, int priority) {
    if (deathTick == 0) {
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
   *
   * @return x offset
   */
  public int getOffsetX() {
    return (player.getX() - player.getPrevX()) * (tileSize / 2);
  }

  /**
   * Find offset for drawing half frames,
   * by checking direction they moved from.
   *
   * @return y offset
   */
  public int getOffsetY() {
    return (player.getY() - player.getPrevY()) * (tileSize / 2);
  }

  /**
   * Helper method to check if actor is in
   * current vision, to draw them.
   *
   * @param actor to check if in frame
   * @return true if in frame, else false
   */
  public boolean actorInVision(AutoActor actor) {
    return (getVisionX(actor.getX()) >= 0
            && getVisionX(actor.getX()) < visionRange
            && getVisionY(actor.getY()) >= 0
            && getVisionY(actor.getY()) < visionRange);
  }

  /**
   * Checks whether in current animation,
   * to stop tick based draw calls overriding animations.
   *
   * @return true if in animation, false otherwise
   */
  public boolean isAnimating() {
    return inAnimation;
  }

  /**
   * Changes delay between drawing, used for replaying levels
   * to speed up or slow down animations.
   *
   * @param speed multiplier to delay time (ie 2x or 0.5x)
   */
  public void setAnimateSpeed(double speed) {
    timer = new Timer((int) (sleepTime / speed), this);
  }

  @Override
  public String toString() {
    return "Board{"
            + "lastVision=" + Arrays.toString(lastVision)
            + ", vision=" + Arrays.toString(vision)
            + ", player=" + player
            + ", maze=" + maze
            + ", autoActors=" + autoActors
            + ", halfFrame=" + halfFrame
            + ", inAnimation=" + inAnimation
            + ", timer=" + timer
            + ", playerMoved=" + playerMoved
            + ", loadedSounds=" + loadedSounds
            + ", inventorySize=" + inventorySize
            + ", taskSize=" + taskSize
            + ", audioPlayer=" + audioPlayer
            + ", deathTick=" + deathTick
            + ", walkedOnDoors=" + walkedOnDoors
            + '}';
  }
}
