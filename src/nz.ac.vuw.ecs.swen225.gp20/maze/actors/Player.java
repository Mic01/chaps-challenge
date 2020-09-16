package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player extends Actor {
    private final ArrayList<Item> inventory = new ArrayList<>();
    private int treasures = 0;

    Player(int xPos, int yPos, Tile[][] maze) {
        super(xPos, yPos, maze);
    }

    /**
     * Add an item to Player's inventory
     * @param item the item being picked up
     */
    public void pickup(Item item) {
        inventory.add(item);
    }

    /**
     * Is Player holding the provided item
     * @param item is Player holding this item
     * @return boolean response for Player holding the provided item
     */
    public boolean isHolding(Item item) {
        for (Item heldItem : inventory)
            if (heldItem.equals(item)) return true;
        return false;
    }

    /**
     * Increase the number of treasures Player has by 1
     */
    public void pickupTreasure(){
        treasures++;
    }

    /**
     * Get the number of treasures the player has collected
     * @return number of treasures the player has collected
     */
    public int treasuresCollected() {
        return treasures;
    }

    /**
     * Get a unmodifiable list of items in Player's inventory
     * @return unmodifiable list of items
     */
    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }
}
