package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;

public class InventoryPanel extends JPanel {
  private final Maze maze;
  private final Image background;

  public InventoryPanel(Maze maze, Image background) {
    this.maze = maze;
    this.background = background;
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
    g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(),
            0, 0, background.getWidth(this), background.getHeight(this),
            this);
    int x = 25;
    int y = 10;
    for (Item i : this.maze.getPlayer().getInventory()) {
      g.drawImage(i.getImage(), x, y, this);
      x += i.getImage().getWidth(this);
    }
  }
}
