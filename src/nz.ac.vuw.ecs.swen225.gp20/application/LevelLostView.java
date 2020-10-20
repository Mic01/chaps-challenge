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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LevelLostView {
  private final JFrame owner;
  private final ApplicationView game;
  private final boolean timeOut;

  /**
   * Creates the borderless dialog informing the player that they have lost.
   *
   * @param owner - The main game frame.
   * @param game - The main game state.
   */
  public LevelLostView(JFrame owner, ApplicationView game, boolean timeOut) {
    this.owner = owner;
    this.game = game;
    this.timeOut = timeOut;
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
    dialog.setVisible(true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  private void addToDialog(Container pane) {
    Image background = Toolkit.getDefaultToolkit().createImage("assets/backgrounds/background.png");
    JPanel panel = new BackgroundPanel(background, new GridBagLayout(), true);
    GridBagConstraints winConstraints = new GridBagConstraints();

    winConstraints.anchor = GridBagConstraints.CENTER;
    winConstraints.fill = GridBagConstraints.HORIZONTAL;
    winConstraints.gridx = 1;
    winConstraints.gridy = 0;
    winConstraints.insets = new Insets(5, 0, 5, 0);
    JLabel titleText;
    if (this.timeOut) {
      titleText = new JLabel("You ran out of time...");
    } else {
      titleText = new JLabel("You have been killed...");
    }
    titleText.setFont(this.game.getMain().deface.deriveFont(20f));
    titleText.setForeground(Color.LIGHT_GRAY);
    titleText.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(titleText, winConstraints);

    winConstraints.anchor = GridBagConstraints.LINE_START;
    winConstraints.fill = GridBagConstraints.NONE;
    winConstraints.gridx = 0;
    winConstraints.gridy = 2;
    winConstraints.insets = new Insets(20, 20, 10, 0);
    JButton restartGame = new JButton();
    restartGame.setBorder(null);
    Image restartGameIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/restart_level.png");
    restartGame.setIcon(new ImageIcon(restartGameIcon));
    restartGame.addActionListener(actionEvent -> game.restartLevel());
    panel.add(restartGame, winConstraints);

    winConstraints.anchor = GridBagConstraints.LINE_END;
    winConstraints.fill = GridBagConstraints.NONE;
    winConstraints.gridx = 2;
    winConstraints.gridy = 2;
    winConstraints.insets = new Insets(20, 0, 10, 20);
    JButton exit = new JButton();
    exit.setBorder(null);
    Image exitIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/quit_game.png");
    exit.setIcon(new ImageIcon(exitIcon));
    exit.addActionListener(actionEvent -> System.exit(0));
    panel.add(exit, winConstraints);
    panel.setBackground(Color.BLACK);
    pane.add(panel);
  }
}