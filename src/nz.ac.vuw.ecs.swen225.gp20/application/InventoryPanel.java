package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;

public class InventoryPanel extends JPanel {
  private final Maze maze;

  public InventoryPanel(Maze maze) {
    this.maze = maze;
  }

  /**
   * Paints on the JPanel.
   *
   * @param g - Graphics object.
   */
  public void paintComponent(Graphics g) {
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
    for (Item i : this.maze.getPlayer().getInventory()) {
      g.drawImage(i.getImage(), x, y, this);
      x += i.getImage().getWidth(this);
    }
  }
}
