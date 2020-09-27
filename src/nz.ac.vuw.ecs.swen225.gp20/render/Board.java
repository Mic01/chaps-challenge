package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.NullTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;

/**
 * Renderer class for displaying the board.
 */
public class Board extends JPanel {

  // Constant Variables
  private final int visionRange = 9;
  private final int tileSize = 70;
  private final int sleepTime = 100; //Time in ms before each draw (ill be adding half frames)

  //Method Enums
  private enum Soundeffects {
    metalWalk, waterSwim, iceWalk, lavaSwim, slide, pickup_item, finish_level, death, openDoor
  }

  private enum Animations {
    doorOpen, death
  }

  //Rendering Variable
  private Tile[][] level;
  private Tile[][] lastVision;
  private Tile[][] vision;
  private Player player;
  private Maze maze;
  private ArrayList<Actor> moving = new ArrayList<>();
  private ArrayList<Actor> actors = new ArrayList<>();
  private String animation;

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
    level = maze.getTiles();
    actors = maze.getActors();
    setVision();
  }

  /**
   * Based on Players position, return a new 2D Array
   * of all tiles visible on board to draw.
   */
  private void setVision() {
    lastVision = vision;
    int ycount = 0;
    int xcount = 0;
    int reach = visionRange / 2;

    System.out.println("Level overall size: x:"+level.length+", y: "+level[0].length);
    for (int x = player.getX() - reach; x < player.getX() + reach; x++) {
      for (int y = player.getY() - reach; y < player.getY() + reach; y++) {
        System.out.println(xcount+", "+ycount);

        //Adding tiles only if they in range
        if ((x > 0 && y > 0) && (x < level.length && y < level[0].length)) {
          vision[xcount][ycount] = level[x][y];
          xcount++;
        } else {
          vision[xcount][ycount] = new NullTile();
        }

        //end of line, reset counting variables
        if (xcount >= visionRange) {
          xcount = 0;
          ycount++;
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
  public void draw(ArrayList<Actor> moving, String animation) {
    setVision();
    this.moving = moving;
    this.animation = animation;

    this.repaint();
    this.revalidate();
  }

  @Override
  protected void paintComponent(Graphics g) {
    try {
      drawTiles(g, 0);
      drawEntities(g, 0);
      drawAnimations(g);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * First step of draw method,
   * draws all tiles in players current vision.
   */
  private void drawTiles(Graphics g, int offset) throws IOException {
    for (int x = 0; x < visionRange; x++) {
      for (int y = 0; y < visionRange; y++) {
        g.drawImage(vision[x][y].getImage(),
                (x * tileSize) + offset, (y * tileSize) + offset, this);
      }
    }
  }

  /**
   * Second step of draw method,
   * draws a new frame of every actor that has moved this round.
   */
  private void drawEntities(Graphics g, int offset) throws IOException {
    for (Actor actor : moving) {
      g.drawImage(actor.getImage(true),
              (actor.getX() * tileSize) + offset, (actor.getY() * tileSize) + offset, this);

      if (actor.getCurrentTile() instanceof Ice) {
        playSound("slide");
      } else if (actor.getCurrentTile() instanceof Water) {
        playSound("waterWalk");
      } else {
        playSound("metalWalk");
      }
    }
  }

  /**
   * Third step of draw method,
   * Loops through unique (non-walk) animations and draws them.
   */
  private void drawAnimations(Graphics g) {
    playAnimations(animation, g);
  }

  /**
   * Switch statement to draw a frame of an animation
   * or create a new animation.
   *
   * @param animation enum value of animation
   * @param g passed canvas to draw on
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
}
