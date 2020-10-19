package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class InventoryPanel extends JPanel {
    private Maze maze;
    public InventoryPanel(Maze maze){
        this.maze = maze;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            drawInventory(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawInventory(Graphics g) throws IOException {
        int x = 10;
        int y = 10;
        for(Item i : this.maze.getPlayer().getInventory()){
            g.drawImage(i.getImage(), x, y, this);
            x += i.getImage().getWidth(this);
        }
    }
}
