package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.CardLayout;
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
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class SetupView {
  private final Main game;
  private JDialog dialog;
  private final CardLayout panes = new CardLayout();
  private JPanel mainPanel;

  public SetupView(Main game) {
    this.game = game;
    setupWindow();
  }

  private void setupWindow() {

    dialog = new JDialog();
    dialog.setResizable(false);
    dialog.setSize(400, 330);
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
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  private void addToDialog(Container pane) {
    mainPanel = new JPanel(this.panes);

    //Title Screen Panel
    GridBagConstraints titleConstraints = new GridBagConstraints();

    titleConstraints.anchor = GridBagConstraints.CENTER;
    titleConstraints.fill = GridBagConstraints.NONE;
    titleConstraints.gridx = 0;
    titleConstraints.gridy = 0;
    titleConstraints.insets = new Insets(5, 0, 5, 0);
    JLabel titleText = new JLabel("The Indecisive Lads present:");
    titleText.setFont(this.game.deface.deriveFont(20f));
    titleText.setForeground(Color.LIGHT_GRAY);
    titleText.setOpaque(true);
    titleText.setBackground(Color.BLACK);
    titleText.setHorizontalAlignment(SwingConstants.CENTER);
    Image background = Toolkit.getDefaultToolkit().createImage("assets/backgrounds/startBackground.png");
    JPanel titleScreen = new BackgroundPanel(background, new GridBagLayout(), true);
    titleScreen.add(titleText, titleConstraints);

    titleConstraints.anchor = GridBagConstraints.CENTER;
    titleConstraints.fill = GridBagConstraints.HORIZONTAL;
    titleConstraints.gridx = 0;
    titleConstraints.gridy = 1;
    titleConstraints.insets = new Insets(0, 0, 0, 0);
    JLabel title = new JLabel(new ImageIcon("assets/Logo.png"));
    titleScreen.add(title, titleConstraints);

    titleConstraints.anchor = GridBagConstraints.LINE_START;
    titleConstraints.fill = GridBagConstraints.NONE;
    titleConstraints.gridx = 0;
    titleConstraints.gridy = 2;
    titleConstraints.insets = new Insets(20, 20, 10, 0);
    JButton startGame = new JButton();
    startGame.setBorder(null);
    Image startGameIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/begin_game.png");
    startGame.setIcon(new ImageIcon(startGameIcon));
    startGame.addActionListener(actionEvent -> panes.next(mainPanel));
    titleScreen.add(startGame, titleConstraints);

    titleConstraints.anchor = GridBagConstraints.LINE_END;
    titleConstraints.fill = GridBagConstraints.NONE;
    titleConstraints.gridx = 0;
    titleConstraints.gridy = 2;
    titleConstraints.insets = new Insets(20, 0, 10, 20);
    JButton exit = new JButton();
    exit.setBorder(null);
    Image exitIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/quit_game.png");
    exit.setIcon(new ImageIcon(exitIcon));
    exit.addActionListener(actionEvent -> System.exit(0));
    titleScreen.add(exit, titleConstraints);

    //Panel for level select
    GridBagConstraints constraints2 = new GridBagConstraints();

    constraints2.anchor = GridBagConstraints.LINE_START;
    constraints2.fill = GridBagConstraints.HORIZONTAL;
    constraints2.gridx = 0;
    constraints2.gridy = 0;
    constraints2.insets = new Insets(10, 25, 20, 20);
    Image levelSelBackground = Toolkit.getDefaultToolkit().createImage("assets/backgrounds/levelselBackground.png");
    JPanel panel2 = new BackgroundPanel(levelSelBackground, new GridBagLayout(), true);
    JLabel levelSelect = new JLabel("Please select a level.");
    levelSelect.setFont(this.game.deface.deriveFont(20f));
    levelSelect.setForeground(Color.LIGHT_GRAY);
    levelSelect.setOpaque(true);
    levelSelect.setBackground(Color.BLACK);
    levelSelect.setHorizontalAlignment(SwingConstants.CENTER);
    panel2.add(levelSelect, constraints2);


    JRadioButton l1 = new JRadioButton("Level 1");
    l1.setActionCommand("1");
    l1.setFont(this.game.deface.deriveFont(16f));
    l1.setForeground(Color.LIGHT_GRAY);
    l1.setBackground(Color.BLACK);

    JRadioButton l2 = new JRadioButton("Level 2");
    l2.setActionCommand("2");
    l2.setFont(this.game.deface.deriveFont(16f));
    l2.setForeground(Color.LIGHT_GRAY);
    l2.setBackground(Color.BLACK);

    ButtonGroup levels = new ButtonGroup();
    levels.add(l1);
    levels.add(l2);

    constraints2.insets = new Insets(0, 30, 10, 0);
    constraints2.fill = GridBagConstraints.HORIZONTAL;
    constraints2.gridx = 0;
    constraints2.gridy = 1;
    panel2.add(l1, constraints2);

    constraints2.gridx = 0;
    constraints2.gridy = 2;
    panel2.add(l2, constraints2);


    JButton next = new JButton();
    next.setBorder(null);
    Image nextIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/next.png");
    next.setIcon(new ImageIcon(nextIcon));
    next.addActionListener(actionEvent -> {
      ButtonModel model = levels.getSelection();
      if (model == null) {
        return;
      }
      switch (model.getActionCommand()) {
        case "1":
          this.game.levelPath = "levels/Level1.json";
          this.game.currLevel = 1;
          break;
        case "2":
          this.game.levelPath = "levels/Level2.json";
          this.game.currLevel = 2;
          break;
        //etc
        default:
      }
      dialog.dispose();
    });
    constraints2.gridx = 1;
    constraints2.gridy = 6;
    constraints2.fill = GridBagConstraints.NONE;
    constraints2.anchor = GridBagConstraints.LINE_END;
    constraints2.insets = new Insets(0, 0, 10, 30);
    panel2.add(next, constraints2);

    mainPanel.add(titleScreen);
    mainPanel.add(panel2);

    pane.add(mainPanel);
  }

}
