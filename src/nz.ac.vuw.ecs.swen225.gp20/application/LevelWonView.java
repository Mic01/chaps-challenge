package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
    dialog.setVisible(true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  private void addToDialog(Container pane) {
    JPanel panel = new JPanel(new GridBagLayout());

    GridBagConstraints winConstraints = new GridBagConstraints();

    winConstraints.anchor = GridBagConstraints.CENTER;
    winConstraints.fill = GridBagConstraints.HORIZONTAL;
    winConstraints.gridx = 1;
    winConstraints.gridy = 0;
    winConstraints.insets = new Insets(5, 0, 5, 0);
    JLabel titleText = new JLabel("Level Complete! Congratulations!");
    titleText.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(titleText, winConstraints);

    winConstraints.anchor = GridBagConstraints.LINE_START;
    winConstraints.fill = GridBagConstraints.NONE;
    winConstraints.gridx = 0;
    winConstraints.gridy = 2;
    winConstraints.insets = new Insets(20, 20, 10, 0);
    JButton startGame = new JButton("Next Level");
    startGame.addActionListener(actionEvent -> game.changeLevel());
    panel.add(startGame, winConstraints);

    winConstraints.anchor = GridBagConstraints.LINE_END;
    winConstraints.fill = GridBagConstraints.NONE;
    winConstraints.gridx = 2;
    winConstraints.gridy = 2;
    winConstraints.insets = new Insets(20, 0, 10, 20);
    winConstraints.ipadx = 20;
    JButton exit = new JButton("Exit Game");
    exit.addActionListener(actionEvent -> System.exit(0));
    panel.add(exit, winConstraints);


    pane.add(panel);
  }
}
