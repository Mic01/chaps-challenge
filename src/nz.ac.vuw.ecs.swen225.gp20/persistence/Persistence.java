package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Treasure;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import java.io.FileWriter;
import java.io.IOException;

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

                    switch (tile) {

                        case "Inside":
                            for (int i = obj.getInt("y1"); i < obj.getInt("y2"); i++) {
                                for (int j = obj.getInt("x1"); j < obj.getInt("x2"); j++) {
                                    maze[j][i] = new FreeTile();
                                }
                            }

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
                                case "Player":
                                    int x = obj.getInt("x");
                                    int y = obj.getInt("y");
                                    maze[x][y] = new FreeTile(new Player(x, y, mazeObject));
                                    break;
                                case "Null":
                                    maze[x][y] = new FreeTile();
                                    break;
                            }
                            break;

                        case "Door":
                            maze[obj.getInt("x")][obj.getInt("y")] = new LockedDoor(obj.getString("colour"), obj.getBoolean("vertical"));
                            break;

                        case "End":
                            maze[obj.getInt("x")][obj.getInt("y")] = new Exit();
                            break;

                        case "Lock":
                            maze[obj.getInt("x")][obj.getInt("y")] = new ExitLock(obj.getInt("chips"));
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
                } else if (currentTile instanceof Exit) {
                    object.add("tile", "End");
                } else if (currentTile instanceof ExitLock) {
                    object.add("tile", "Lock");
                    object.add("chips", ((ExitLock) save[i][j]).getTreasuresNeeded());
                } else if (currentTile instanceof Ice) {
                    object.add("tile", "Ice");
                } else if (currentTile instanceof InfoTile) {
                    object.add("tile", "InfoTile");
                } else if (currentTile instanceof LockedDoor) {
                    object.add("tile", "Door");
                    object.add("colour", ((LockedDoor) save[i][j]).getColour());
                    object.add("vertical", ((LockedDoor) save[i][j]).isVertical());
                } else if (currentTile instanceof Wall) {
                    object.add("tile", "Wall");
                } else if (currentTile instanceof Water) {
                    object.add("tile", "Water");
                } else if (currentTile instanceof FreeTile) {
                    object.add("tile", "Floor");
                    if(((FreeTile) save[i][j]).hasActor()){
                        object.add("slot", "Player");

                    }
                    if(((FreeTile) save[i][j]).hasItem()){
                        object.add("slot", "Key");
                        object.add("colour", ((FreeTile) save[i][j]).getKeyColour());
                    }
                }
                object.add("x", i);
                object.add("y", j);

                JsonObject builtObject = object.build();
                output.append(builtObject.toString());

                if (!(i == maze.length - 1 && i == maze[0].length - 1)) {
                    output.append(",");
                }
            }
        }
        output.append("}");

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

