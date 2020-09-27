package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Treasure;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import java.math.BigDecimal;

public class Persistence {
    /**
     * reads json level file, makes maze array
     *
     * @param fileName json file name
     * @return level tile array
     */
    public static Tile[][] loadLevel(String fileName, Maze mazeObject){

        //creates empty maze
      Tile[][] maze = new Tile[80][80];
      for(int i=0;i<80;i++){
          for(int j=0;j<80;j++) {
            maze[j][i] = new FreeTile();
          }
      }

        try {
            final JsonParser parser = Json.createParser(new FileReader("assets/Level1.json"));
            while (parser.hasNext()) {
                final Event event = parser.next();
                if (event == Event.START_OBJECT) {

                    JsonObject obj = (JsonObject) parser.getObject();
                    final String tile = obj.getString("tile");

                    switch (tile) {

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
                                    int x=obj.getInt("x");
                                    int y=obj.getInt("y");
                                    maze[x][y] = new FreeTile(new Player(x,y,mazeObject));
                                    break;
                            }
                            break;

                        case "Door":
                            maze[obj.getInt("x")][obj.getInt("y")] = new LockedDoor(obj.getString("colour"));
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

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return maze;
    }
}