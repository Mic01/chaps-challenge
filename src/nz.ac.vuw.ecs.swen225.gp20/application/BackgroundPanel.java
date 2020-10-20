package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
  private final Image background;

  public BackgroundPanel(Image image, LayoutManager layout, boolean buffered) {
    super(layout, buffered);
    this.background = image;
  }

  /**
   * Paints on the JPanel.
   *
   * @param g - Graphics object.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(),
            0, 0, background.getWidth(this), background.getHeight(this),
            this);
  }
}
