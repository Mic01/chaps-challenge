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

public class ReplayDoneView {
  private final JFrame owner;
  private final ApplicationView game;

  /**
   * Creates the borderless dialog informing the player that they have won.
   *
   * @param owner - The main game frame.
   * @param game - The main game state.
   */
  public ReplayDoneView(JFrame owner, ApplicationView game) {
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
    dialog.setLocation((this.owner.getLocationOnScreen().x) + (this.owner.getWidth() / 5),
            (this.owner.getLocationOnScreen().y)+ ((this.owner.getHeight()) / 2));
    dialog.setVisible(true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  private void addToDialog(Container pane) {
    Image background = Toolkit.getDefaultToolkit().createImage("assets/backgrounds/background.png");
    JPanel panel = new BackgroundPanel(background, new GridBagLayout(), true);

    GridBagConstraints replayEndConstraints = new GridBagConstraints();

    replayEndConstraints.anchor = GridBagConstraints.CENTER;
    replayEndConstraints.fill = GridBagConstraints.HORIZONTAL;
    replayEndConstraints.gridx = 1;
    replayEndConstraints.gridy = 0;
    replayEndConstraints.insets = new Insets(5, 0, 5, 0);
    JLabel titleText = new JLabel("Replay complete! Load another?");
    titleText.setFont(this.game.getMain().deface.deriveFont(20f));
    titleText.setForeground(Color.LIGHT_GRAY);
    titleText.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(titleText, replayEndConstraints);

    replayEndConstraints.anchor = GridBagConstraints.LINE_START;
    replayEndConstraints.fill = GridBagConstraints.NONE;
    replayEndConstraints.gridx = 0;
    replayEndConstraints.gridy = 2;
    replayEndConstraints.insets = new Insets(20, 20, 10, 0);
    JButton newReplay = new JButton();
    newReplay.setBorder(null);
    Image newReplayIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/load_replay.png");
    newReplay.setIcon(new ImageIcon(newReplayIcon));
    newReplay.addActionListener(actionEvent -> game.loadReplay());
    panel.add(newReplay, replayEndConstraints);

    replayEndConstraints.anchor = GridBagConstraints.LINE_END;
    replayEndConstraints.fill = GridBagConstraints.NONE;
    replayEndConstraints.gridx = 2;
    replayEndConstraints.gridy = 2;
    replayEndConstraints.insets = new Insets(20, 0, 10, 20);
    JButton exit = new JButton();
    exit.setBorder(null);
    Image exitIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/quit_game.png");
    exit.setIcon(new ImageIcon(exitIcon));
    exit.addActionListener(actionEvent -> System.exit(0));
    panel.add(exit, replayEndConstraints);
    panel.setBackground(Color.BLACK);
    pane.add(panel);
  }
}
