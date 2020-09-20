package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

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
    public static Tile[][] loadLevel(String fileName){

      Tile[][] maze;

        try {
            final JsonParser parser = Json.createParser(new FileReader("Assets/Level1.json"));
            while (parser.hasNext()) {
                final Event event = parser.next();
                if (event == Event.START_OBJECT) {
                    JsonObject obj = (JsonObject) parser.getObject();
                    final String tile = obj.getString("tile");
                    switch (tile) {
                        case "Wall":
                            //make wall tile at obj.getInt("x") obj.getInt("y")
                            break;

                        case "Floor":
                            //make floor tile at obj.getInt("x") obj.getInt("y")
                            //
                            break;

                        case "End":

                            break;

                        case "Lock":

                            break;
                    }

                    //  System.out.println(obj.getString("tile"));
                    System.out.println(obj.getInt("x"));
                    System.out.println(obj.getInt("y"));
                }
            }
            parser.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}