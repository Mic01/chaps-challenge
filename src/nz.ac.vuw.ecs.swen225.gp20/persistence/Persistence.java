package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;


import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Persistence {
    /**
     * reads json level file, makes maze array.
     *
     * @param fileName = json file name String.
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

        try {
            final JsonParser parser = Json.createParser(new FileReader(fileName));
            while (parser.hasNext()) {
                final Event event = parser.next();
                if (event == Event.START_OBJECT) {

                    JsonObject obj = parser.getObject();
                    final String tile = obj.getString("tile");
                    int x;
                    int y;
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
                            switch (slot) {
                                case "Key":
                                    maze[obj.getInt("x")][obj.getInt("y")] = new FreeTile(new Key(obj.getString("colour")));
                                    break;
                                case "Chip":
                                    maze[obj.getInt("x")][obj.getInt("y")] = new FreeTile(new Treasure());
                                    break;
                                case "Shoe":
                                    final String element = obj.getString("type");
                                    x = obj.getInt("x");
                                    y = obj.getInt("y");
                                    System.out.println("Shoe");
                                    switch (element) {

                                        case "Water":
                                            maze[x][y] = new FreeTile(new WaterPotion());
                                        break;

                                        case "Ice":
                                            System.out.println("Ice");
                                            maze[x][y] = new FreeTile(new IcePotion());
                                        break;

                                    }
                                    break;
                                case "Player":

                                    x = obj.getInt("x");
                                    y = obj.getInt("y");
                                    maze[x][y] = new FreeTile(new Player(x, y, mazeObject));
                                    break;
                                case "Enemy":

                                    x = obj.getInt("x");
                                    y = obj.getInt("y");
                                    String direction = obj.getString("direction");
                                    final String type = obj.getString("type");
                                    switch (type) {
                                        case "One":
                                            maze[x][y] = new FreeTile(new EnemyOne(x, y, mazeObject, Actor.Direction.valueOf(direction)));

                                            break;

                                        case "Two":
                                            maze[x][y] = new FreeTile(new EnemyTwo(x, y, mazeObject,Actor.Direction.valueOf(direction)));
                                            break;

                                        case "Three":
                                            maze[x][y] = new FreeTile(new EnemyThree(x, y, mazeObject,Actor.Direction.valueOf(direction)));
                                            break;
                                    }
                                    break;

                                case "Null":
                                    maze[obj.getInt("x")][obj.getInt("y")] = new FreeTile();
                                    break;
                            }
                            break;

                        case "Door":
                            maze[obj.getInt("x")][obj.getInt("y")] = new LockedDoor(obj.getString("colour"), obj.getBoolean("vertical"), obj.getBoolean("open"));
                            break;

                        case "End":
                            maze[obj.getInt("x")][obj.getInt("y")] = new Exit();
                            break;

                        case "Lock":
                            maze[obj.getInt("x")][obj.getInt("y")] = new ExitLock(obj.getInt("chips"), obj.getBoolean("vertical"), obj.getBoolean("open"));
                            break;

                        case "Info":
                            maze[obj.getInt("x")][obj.getInt("y")] = new InfoTile(obj.getString("text"));
                            break;

                        case "Conveyor":

                            maze[obj.getInt("x")][obj.getInt("y")] = new Conveyor(Actor.Direction.valueOf(obj.getString("direction")));
                            break;

                        case "Ice":
                            maze[obj.getInt("x")][obj.getInt("y")] = new Ice();
                            break;

                        case "Water":
                            maze[obj.getInt("x")][obj.getInt("y")] = new Water();
                            break;

                        case "Vent":
                            maze[obj.getInt("x")][obj.getInt("y")] = new Vent(obj.getInt("x1"),obj.getInt("y1"));
                            break;
                    }
                }
            }
            parser.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //saveLevel(maze, "assets/SaveFile.json");

        return maze;
    }

    /**
     * coverts a 2D of Tiles into json and saves it
     *
     * @param maze current level to be saved
     * @param fileName name of the file to be saved
     */
    public static void saveLevel(Tile[][] maze, String fileName) {
        Tile[][] save = new Tile[80][80];
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                save[j][i] = new NullTile();
            }
        }

        StringBuilder output = new StringBuilder("[");

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                Tile currentTile = maze[i][j];
                JsonObjectBuilder object = Json.createObjectBuilder();



                if (currentTile instanceof NullTile) {
                    continue;
                }
                object.add("tile", currentTile.toString());

                if (currentTile instanceof ExitLock) {
                    object.add("chips", ((ExitLock) currentTile).getTreasuresNeeded());
                    object.add("vertical", ((ExitLock) currentTile).isVertical());
                    object.add("open", ((ExitLock) currentTile).isOpen());
                } else if (currentTile instanceof LockedDoor) {
                    object.add("colour", ((LockedDoor) currentTile).getColour());
                    object.add("vertical", ((LockedDoor) currentTile).isVertical());
                    object.add("open", ((LockedDoor) currentTile).isOpen());
                } else if (currentTile instanceof FreeTile) {
                    if (((FreeTile) currentTile).hasActor()) {
                        object.add("slot", "Player");
                       // object.add("inventory", ((FreeTile) currentTile).getActor().getInventory());

                    } else if (((FreeTile) currentTile).hasItem()) {
                        Item item = ((FreeTile) currentTile).getItem();
                        if (item instanceof Key) {
                            object.add("slot", "Key");
                            object.add("colour", ((Key) item).getColour());
                        } else if (item instanceof Treasure) {
                            object.add("slot", "Chip");
                        }
                    } else{
                        object.add("slot", "Null");

                    }
                }else if (currentTile instanceof InfoTile){
                    object.add("text", ((InfoTile)currentTile).getInfo());

                }

                object.add("x", i);
                object.add("y", j);

                JsonObject builtObject = object.build();
                output.append(builtObject.toString());

                output.append(",");
            }
        }

        output.deleteCharAt(output.length()-1);
        output.append("]");

        try {
            FileWriter file = new FileWriter(fileName);
            file.write(output.toString());
            file.flush();
            file.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}

