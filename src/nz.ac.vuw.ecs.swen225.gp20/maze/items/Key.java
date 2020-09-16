package nz.ac.vuw.ecs.swen225.gp20.maze.items;

public class Key extends Item {
    private final String colour;

    public Key(String colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key) {
            Key otherKey = (Key) obj;
            return this.colour.equals(otherKey.colour);
        }
        return false;
    }
}
