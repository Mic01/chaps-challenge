package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The panel that should appear when a level is won.
 *
 * @author Nicholas Woolf-Ben-Avraham
 */
public class LevelWonView {
  private final JFrame owner;
  private final ApplicationView game;

  /**
   * Creates the borderless dialog informing the player that they have won.
   *
   * @param owner - The main game frame.
   * @param game - The main game state.
   */
  public LevelWonView(JFrame owner, ApplicationView game) {
    this.owner = owner;
    this.game = game;
    setupWindow();
  }

  private void setupWindow() {

    JDialog dialog = new JDialog(this.owner);
    dialog.setResizable(false);
    dialog.setSize(400, 330);
    dialog.setUndecorated(true);
    dialog.setModal(true);
    dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    addToDialog(dialog.getContentPane());
    dialog.pack();
    dialog.setLocationRelativeTo(this.owner);
    dialog.setLocation((this.owner.getLocationOnScreen().x) + (this.owner.getWidth() / 8),
            (this.owner.getLocationOnScreen().y) + ((this.owner.getHeight()) / 2));
    dialog.setVisible(true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  private void addToDialog(Container pane) {
    int toSave;
    if (game.getMain().currLevel > 2) {
      toSave = 2;
    } else {
      toSave = game.getMain().currLevel + 1;
    }
    try {
      SmallSave.saveFile(toSave);
    } catch (IOException e) {
      e.printStackTrace();
    }

    GridBagConstraints winConstraints = new GridBagConstraints();

    winConstraints.anchor = GridBagConstraints.CENTER;
    winConstraints.fill = GridBagConstraints.HORIZONTAL;
    winConstraints.gridx = 1;
    winConstraints.gridy = 0;
    winConstraints.insets = new Insets(5, 0, 5, 0);
    JLabel titleText = new JLabel("Level Complete! Congratulations!");
    titleText.setFont(this.game.getMain().deface.deriveFont(20f));
    titleText.setForeground(Color.LIGHT_GRAY);
    titleText.setHorizontalAlignment(SwingConstants.CENTER);

    Image background = Toolkit.getDefaultToolkit().createImage("assets/backgrounds/background.png");
    JPanel panel = new BackgroundPanel(background, new GridBagLayout(), true);
    panel.add(titleText, winConstraints);

    winConstraints.anchor = GridBagConstraints.LINE_START;
    winConstraints.fill = GridBagConstraints.NONE;
    winConstraints.gridx = 0;
    winConstraints.gridy = 2;
    winConstraints.insets = new Insets(20, 20, 10, 0);
    JButton nextLevel = new JButton();
    nextLevel.setBorder(null);
    Image nextLevelIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/next_level.png");
    nextLevel.setIcon(new ImageIcon(nextLevelIcon));
    nextLevel.addActionListener(actionEvent -> game.changeLevel());
    panel.add(nextLevel, winConstraints);

    winConstraints.anchor = GridBagConstraints.LINE_END;
    winConstraints.fill = GridBagConstraints.NONE;
    winConstraints.gridx = 2;
    winConstraints.gridy = 2;
    winConstraints.insets = new Insets(20, 0, 10, 20);
    JButton exit = new JButton();
    exit.setBorder(null);
    Image exitIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/save_and_exit.png");
    exit.setIcon(new ImageIcon(exitIcon));
    exit.addActionListener(actionEvent -> System.exit(0));
    panel.add(exit, winConstraints);
    panel.setBackground(Color.BLACK);
    pane.add(panel);
  }
}
