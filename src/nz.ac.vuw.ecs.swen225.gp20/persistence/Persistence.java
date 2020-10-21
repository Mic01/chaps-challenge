package nz.ac.vuw.ecs.swen225.gp20.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import levels.EnemyOne;
import levels.EnemyThree;
import levels.EnemyTwo;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.IcePotion;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Treasure;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.WaterPotion;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Conveyor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Exit;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.ExitLock;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Ice;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InfoTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.LockedDoor;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.NullTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Vent;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Wall;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Water;

public class Persistence {

  /**
   * reads json file, makes maze array.
   *
   * @param fileName = json file name String.
   * @param mazeObject = current level maze.
   * @return Tile[][] = level 2D tile array.
   */
  public static Tile[][] loadLevel(String fileName, Maze mazeObject) {
    //creates empty maze
    Tile[][] maze = new Tile[80][80];
    for (int i = 0; i < 80; i++) {
      for (int j = 0; j < 80; j++) {
        maze[j][i] = new NullTile();
      }
    }

    //adds tiles from file
    try {
      final JsonParser parser = Json.createParser(new FileInputStream(fileName));
      //repeats until it has gone through every tile object in the json file
      while (parser.hasNext()) {
        final Event event = parser.next();

        //each tile is an object in the json file
        if (event == Event.START_OBJECT) {
          JsonObject obj = parser.getObject();
          final String tile = obj.getString("tile");
          int x;
          int y;

          //tile switch
          switch (tile) {
            case "Inside":
              for (int i = obj.getInt("y1"); i < obj.getInt("y2"); i++) {
                for (int j = obj.getInt("x1"); j < obj.getInt("x2"); j++) {
                  maze[j][i] = new FreeTile();
                }
              }
              break;
            case "Wall":
              maze[obj.getInt("x")][obj.getInt("y")] = new Wall();
              break;
            case "Floor":

              final String slot = obj.getString("slot");
              //Floor tile can have items and actors on it
              switch (slot) {
                case "Item":
                  maze[obj.getInt("x")][obj.getInt("y")] = new FreeTile(loadItem(obj));
                  break;

                case "Player":
                  x = obj.getInt("x");
                  y = obj.getInt("y");
                  Player player = new Player(x, y, mazeObject);
                  maze[x][y] = new FreeTile(player);
                  JsonArray inventory = obj.getJsonArray("inventory");
                  if (!inventory.isEmpty()) {
                    for (JsonValue items : inventory) {
                      JsonObject item = (JsonObject) items;
                      player.pickup(loadItem(item));
                    }
                  }
                  break;
                case "Enemy":
                  x = obj.getInt("x");
                  y = obj.getInt("y");
                  String direction = obj.getString("direction");
                  final String type = obj.getString("type");
                  //currently three different enemy movement types
                  switch (type) {
                    case "One":
                      maze[x][y] = new FreeTile(new EnemyOne(
                              x, y, mazeObject, Actor.Direction.valueOf(direction)));
                      break;
                    case "Two":
                      maze[x][y] = new FreeTile(new EnemyTwo(
                              x, y, mazeObject, Actor.Direction.valueOf(direction)));
                      break;
                    case "Three":
                      maze[x][y] = new FreeTile(new EnemyThree(
                              x, y, mazeObject, Actor.Direction.valueOf(direction)));
                      break;
                    default:
                      break;
                  }
                  break;
                case "Null":
                  maze[obj.getInt("x")][obj.getInt("y")] = new FreeTile();
                  break;
                default:
                  break;
              }
              break;
            case "Door":
              maze[obj.getInt("x")][obj.getInt("y")] = new LockedDoor(
                      obj.getString("colour"), obj.getBoolean("vertical"), obj.getBoolean("open"));
              break;
            case "End":
              maze[obj.getInt("x")][obj.getInt("y")] = new Exit();
              mazeObject.setTimeLimit(obj.getInt("time"));
              break;
            case "Lock":
              maze[obj.getInt("x")][obj.getInt("y")] = new ExitLock(
                      obj.getInt("chips"), obj.getBoolean("vertical"), obj.getBoolean("open"));
              mazeObject.setTreasuresLeft(obj.getInt("chips"));
              break;
            case "Info":
              maze[obj.getInt("x")][obj.getInt("y")] = new InfoTile(obj.getString("text"));
              break;
            case "Conveyor":
              maze[obj.getInt("x")][obj.getInt("y")] = new Conveyor(
                      Actor.Direction.valueOf(obj.getString("direction")));
              break;
            case "Ice":
              maze[obj.getInt("x")][obj.getInt("y")] = new Ice();
              break;
            case "Water":
              maze[obj.getInt("x")][obj.getInt("y")] = new Water();
              break;
            case "Vent":
              maze[obj.getInt("x")][obj.getInt("y")] = new Vent(obj.getInt("x1"), obj.getInt("y1"));
              break;
            default:
              break;
          }
        }
      }
      parser.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return maze;
  }

  /**
   * returns Item on the give floor item object.
   *
   * @param obj Json floor tile with an item on it
   * @return Item
   */
  public static Item loadItem(JsonObject obj) {
    final String type = obj.getString("type");

    //finds correct item
    switch (type) {
      case "Key":
        return new Key(obj.getString("colour"));
      case "Chip":
        return new Treasure();
      case "Shoe":
        final String element = obj.getString("element");
        switch (element) {
          case "Water":
            return new WaterPotion();
          case "Ice":
            return new IcePotion();
          default:
            break;
        }
        break;
      default:
        break;
    }
    return null;
  }



  /**
   *  coverts a 2D of Tiles into json and saves it.
   *
   * @param maze current level to be saved.
   * @param fileName name of the file to be saved.
   * @param mazeObject = current level maze.
   */
  public static void saveLevel(Tile[][] maze, String fileName, Maze mazeObject) {

    StringBuilder output = new StringBuilder("[");

    //iterate through array
    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[0].length; j++) {
        Tile currentTile = maze[i][j];
        JsonObjectBuilder object = Json.createObjectBuilder();

        //dont save NullTiles (empty untraversable space tiles)
        if (currentTile instanceof NullTile) {
          continue;
        }

        //all tile objects in json level file have a tile name
        object.add("tile", currentTile.toString());

        if (currentTile instanceof ExitLock) {
          object.add("chips", mazeObject.getTreasuresLeft());
          object.add("vertical", ((ExitLock) currentTile).isVertical());
          object.add("open", ((ExitLock) currentTile).isOpen());
        } else if (currentTile instanceof LockedDoor) {
          object.add("colour", ((LockedDoor) currentTile).getColour());
          object.add("vertical", ((LockedDoor) currentTile).isVertical());
          object.add("open", ((LockedDoor) currentTile).isOpen());
        } else if (currentTile instanceof FreeTile) {
          //free tiles could have items and actors that need saving
          if ((currentTile).hasActor()) {
            Actor actor = ((FreeTile) currentTile).getActor();
            object.add("slot", actor.toString());
            if (actor instanceof Player) {
              JsonArrayBuilder array = Json.createArrayBuilder();
              for (Item item : ((Player) actor).getInventory()) {
                JsonObjectBuilder itemObject = Json.createObjectBuilder();
                saveItem(itemObject, item);
                array.add(itemObject);
              }
              object.add("inventory", array);
            } else {
              object.add("direction", (((AutoActor) actor).getCurrentDirection()).toString());

              if (actor instanceof EnemyOne) {
                object.add("type", "One");
              } else if (actor instanceof EnemyTwo) {
                object.add("type", "Two");
              } else if (actor instanceof EnemyThree) {
                object.add("type", "Three");
              }
            }
          } else if (((FreeTile) currentTile).hasItem()) {
            saveItem(object, ((FreeTile) currentTile).getItem());
          } else {
            object.add("slot", "Null");

          }
        } else if (currentTile instanceof InfoTile) {
          object.add("text", ((InfoTile) currentTile).getInfo());

        } else if (currentTile instanceof Vent) {
          object.add("x1", ((Vent) currentTile).getTargetX());
          object.add("y1", ((Vent) currentTile).getTargetY());

        } else if (currentTile instanceof Conveyor) {
          object.add("direction", ((Conveyor) currentTile).getDirection().toString());
        } else if (currentTile instanceof Exit) {
          object.add("time", mazeObject.getTimeLimit());
        }

        //add tiles saved have and x and a y
        object.add("x", i);
        object.add("y", j);
        JsonObject builtObject = object.build();
        output.append(builtObject.toString());
        output.append(",");
      }
    }

    output.deleteCharAt(output.length() - 1);
    output.append("]");
    File file = new File(fileName);
    OutputStreamWriter writer;
    try {
      writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
      writer.write(output.toString());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  /**
   * adds item on floor tile into a json object.
   *
   * @param object current object being created in json.
   * @param item = item to be added into json object.
   */
  public static void saveItem(JsonObjectBuilder object, Item item) {

    object.add("slot", "Item");
    object.add("type", item.toString());
    if (item instanceof Key) {
      object.add("colour", ((Key) item).getColour());
    } else if (item instanceof IcePotion) {
      object.add("element", "Ice");
    } else if (item instanceof WaterPotion) {
      object.add("element", "Water");
    }
  }
}

