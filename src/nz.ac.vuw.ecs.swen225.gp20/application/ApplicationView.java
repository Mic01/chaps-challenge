package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.render.Board;

public class ApplicationView {

  private final Main game;
  private final Maze maze;
  private final Board viewport;
  private final Replay log;
  private JFrame window;
  private final JMenu save = new JMenu("Save");
  private final JMenu load = new JMenu("Load");
  private final JMenu replays = new JMenu("Replays");
  private final JMenu help = new JMenu("Help");
  private final JMenuItem saveGame = new JMenuItem("Save Game");
  private final JMenuItem loadGame = new JMenuItem("Load Game");
  private final JMenuItem saveReplay = new JMenuItem("Save Replay");
  private final JMenuItem loadReplay = new JMenuItem("Load Replay");
  private final JMenuItem viewHelp = new JMenuItem("View Help");
  private boolean gameOver = false;
  private final boolean isReplay;
  private boolean isPaused;
  private JLabel scoreCount = new JLabel("0");
  private JLabel helpWindow = new JLabel("");
  private JPanel lowerWindow = new JPanel();
  private JPanel mainWindow = new JPanel();
  private String replayPath = "";
  private Timer countdownTimer = null;
  private Timer npcMovementTimer = null;
  private double currSpeed = 1.0;
  private JLabel replaySpeed;
  private HelpView helpView;


  /**
   * Initializer for the Gameplay version of ApplicationView.
   *
   * @param game     - Main program
   * @param isReplay - True if the loaded file is a replay, false if it is not.
   */
  public ApplicationView(Main game, boolean isReplay) {
    this.maze = new Maze(game.levelPath);
    this.viewport = new Board(this.maze);
    this.game = game;
    this.log = new Replay(this);
    this.isReplay = isReplay;
    this.makeWindow();
    this.mainWindow.requestFocus();
  }

  /**
   * Initializer for the Replay version of ApplicationView.
   *
   * @param game       - Main program
   * @param isReplay   - True if the loaded file is a replay, false if it is not.
   * @param replayPath - Filepath for the replay.
   */
  public ApplicationView(Main game, boolean isReplay, String replayPath) {
    this.maze = new Maze(game.levelPath);
    this.viewport = new Board(this.maze);
    this.game = game;
    this.log = new Replay(this);
    this.isReplay = isReplay;
    this.replayPath = replayPath;
    this.makeWindow();
    this.mainWindow.requestFocus();
  }

  /**
   * Constructs a JFrame within which the main game will be displayed.
   */
  private void makeWindow() {
    try {
      SmallSave.saveFile(this.game.currLevel);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.window = new JFrame("Chips Among Us");
    this.window.setLayout(new BorderLayout());
    this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.window.setResizable(false);
    this.window.getRootPane().getActionMap().put("close", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        window.dispose();
      }
    });
    this.window.getRootPane().getActionMap().put("saveAndClose", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        saveGame();
        window.dispose();
      }
    });
    this.window.getRootPane().getActionMap().put("loadGame", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        loadSave();
      }
    });
    this.window.getRootPane().getActionMap()
            .put("startGameAtUnfinishedLevel", new AbstractAction() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                game.restartLevel(game.currLevel);
              }
            });
    this.window.getRootPane().getActionMap().put("startGameAtLevel1", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        game.restartLevel(1);
      }
    });
    this.window.getRootPane().getActionMap().put("pause", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (isPaused) {
          isPaused = false;
          countdownTimer.start();
          npcMovementTimer.start();
        } else {
          isPaused = true;
          countdownTimer.stop();
          npcMovementTimer.stop();
        }
      }
    });
    this.window.getRootPane().getActionMap().put("exitPause", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (isPaused) {
          isPaused = false;
          countdownTimer.start();
          npcMovementTimer.start();
        }
      }
    });
    this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke("control X"), "close");
    this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke("control S"), "saveAndClose");
    this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke("control R"), "loadGame");
    this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke("control P"), "startGameAtUnfinishedLevel");
    this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke("control 1"), "startGameAtLevel1");
    this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "pause");
    this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitPause");
    this.addToWindow();
    this.window.pack();
    this.window.setLocationRelativeTo(null);
    this.window.setVisible(true);
  }

  /**
   * Adds contents to the constructed JFrame.
   */
  private void addToWindow() {

    this.saveGame.addActionListener(actionEvent -> saveGame());
    this.save.add(this.saveGame);
    this.loadGame.addActionListener(actionEvent -> loadSave());
    this.load.add(this.loadGame);
    this.saveReplay.addActionListener(actionEvent -> showSaveReplayDialogue());
    this.loadReplay.addActionListener(actionEvent -> showLoadReplayDialogue());
    this.replays.add(saveReplay);
    this.replays.add(loadReplay);
    this.viewHelp.addActionListener(actionEvent -> showHelpView());
    this.help.add(viewHelp);
    JMenuBar saveLoad = new JMenuBar();
    saveLoad.add(this.save);
    saveLoad.add(this.load);
    saveLoad.add(this.replays);
    saveLoad.add(help);
    this.window.setJMenuBar(saveLoad);

    this.mainWindow = viewport;
    this.mainWindow.setMinimumSize(new Dimension(630, 630));
    this.mainWindow.setPreferredSize(new Dimension(630, 630));
    this.mainWindow.setBackground(Color.BLACK);
    Image sideBackground = Toolkit.getDefaultToolkit()
            .createImage("assets/backgrounds/sideBackground.png");
    JPanel sideWindow = new BackgroundPanel(sideBackground, new GridBagLayout(), true);
    sideWindow.setMinimumSize(new Dimension(150, 100));
    sideWindow.setPreferredSize(new Dimension(150, 100));
    sideWindow.setBackground(Color.BLACK);

    JLabel score = new JLabel("Boards Remaining:");
    score.setForeground(Color.LIGHT_GRAY);
    score.setFont(this.game.deface.deriveFont(14f));
    score.setOpaque(true);
    score.setBackground(Color.BLACK);
    this.scoreCount = new JLabel("" + maze.getTreasuresLeft());
    this.scoreCount.setFont(this.game.deface.deriveFont(14f));
    this.scoreCount.setForeground(Color.LIGHT_GRAY);
    this.scoreCount.setOpaque(true);
    this.scoreCount.setBackground(Color.BLACK);

    JLabel time = new JLabel("Time Remaining:");
    time.setFont(this.game.deface.deriveFont(14f));
    time.setForeground(Color.LIGHT_GRAY);
    time.setOpaque(true);
    time.setBackground(Color.BLACK);
    JLabel timeCount;
    timeCount = new JLabel(this.maze.getTimeLimit() + " seconds");
    timeCount.setFont(this.game.deface.deriveFont(14f));
    timeCount.setForeground(Color.LIGHT_GRAY);
    timeCount.setOpaque(true);
    timeCount.setBackground(Color.BLACK);

    this.helpWindow.setFont(this.game.deface.deriveFont(14f));
    this.helpWindow.setForeground(Color.LIGHT_GRAY);
    this.helpWindow.setOpaque(true);
    this.helpWindow.setBackground(Color.BLACK);

    ApplicationView currentGame = this;
    ActionListener countdown = new ActionListener() {
      int timeLeft = maze.getTimeLimit() - 1;

      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        timeCount.setText(timeLeft + " seconds");
        if (timeLeft <= 0) {
          gameOver = true;
          ((Timer) actionEvent.getSource()).stop();
          new LevelLostView(window, currentGame, true);
        }
        timeLeft--;
        if (timeLeft < 0) {
          timeLeft = 0;
        }
        maze.setTimeLimit(timeLeft);
      }
    };
    this.countdownTimer = new javax.swing.Timer(1000, countdown);
    if (!isReplay) {
      this.countdownTimer.start();
    }

    ActionListener npcMovement = actionEvent -> {
      if (gameOver) {
        viewport.draw(false);
        ((Timer) actionEvent.getSource()).stop();
      } else if (this.maze.getPlayer().isDead()) {
        viewport.draw(false);
        gameOver = true;
        ((Timer) actionEvent.getSource()).stop();
        countdownTimer.stop();
        new LevelLostView(window, currentGame, false);
      } else {
        for (AutoActor a : maze.getAutoActors()) {
          a.autoMove();
          if (!viewport.isAnimating()) {
            viewport.draw(false);
          }
        }
      }
    };
    this.npcMovementTimer = new Timer(250, npcMovement);
    if (!isReplay) {
      this.npcMovementTimer.start();
    }

    JButton left = new JButton();
    left.setBorder(null);
    Image leftIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/left.png");
    left.setIcon(new ImageIcon(leftIcon));

    JButton up = new JButton();
    up.setBorder(null);
    Image upIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/up.png");
    up.setIcon(new ImageIcon(upIcon));

    JButton down = new JButton();
    down.setBorder(null);
    Image downIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/down.png");
    down.setIcon(new ImageIcon(downIcon));

    JButton right = new JButton();
    right.setBorder(null);
    Image rightIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/right.png");
    right.setIcon(new ImageIcon(rightIcon));

    if (!isReplay) {
      down.addActionListener(actionEvent -> playerMovement(2, false));
      down.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
              .put(KeyStroke.getKeyStroke("released DOWN"), "moveDown");
      down.getActionMap().put("moveDown", new AbstractAction("moveDown") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          playerMovement(2, false);
        }
      });

      up.addActionListener(actionEvent -> playerMovement(1, false));
      up.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
              .put(KeyStroke.getKeyStroke("released UP"), "moveUp");
      up.getActionMap().put("moveUp", new AbstractAction("moveUp") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          playerMovement(1, false);
        }
      });

      left.addActionListener(actionEvent -> playerMovement(3, false));
      left.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
              .put(KeyStroke.getKeyStroke("released LEFT"), "moveLeft");
      left.getActionMap().put("moveLeft", new AbstractAction("moveLeft") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          playerMovement(3, false);
        }
      });

      right.addActionListener(actionEvent -> playerMovement(4, false));
      right.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
              .put(KeyStroke.getKeyStroke("released RIGHT"), "moveRight");
      right.getActionMap().put("moveRight", new AbstractAction("moveRight") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          playerMovement(4, false);
        }
      });
    }

    JButton removeSave = new JButton();
    removeSave.setBorder(null);
    Image removeSaveIcon = Toolkit.getDefaultToolkit()
            .createImage("assets/buttons/remove_save.png");
    removeSave.setIcon(new ImageIcon(removeSaveIcon));
    removeSave.addActionListener(actionEvent -> SmallSave.removeFile());

    JButton quitGame = new JButton();
    quitGame.setBorder(null);
    Image quitGameIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/quit_game.png");
    quitGame.setIcon(new ImageIcon(quitGameIcon));
    quitGame.addActionListener(actionEvent -> System.exit(0));

    GridBagConstraints sideConstraints = new GridBagConstraints();
    sideConstraints.gridx = 3;
    sideConstraints.gridy = 0;
    sideConstraints.fill = GridBagConstraints.HORIZONTAL;
    sideConstraints.anchor = GridBagConstraints.CENTER;
    sideConstraints.insets = new Insets(0, -110, 0, 0);
    sideWindow.add(score, sideConstraints);

    sideConstraints.gridy = 1;
    sideConstraints.insets = new Insets(0, -110, 50, 0);
    sideWindow.add(scoreCount, sideConstraints);

    sideConstraints.gridy = 2;
    sideConstraints.insets = new Insets(0, -110, 0, 0);
    sideWindow.add(time, sideConstraints);

    sideConstraints.gridy = 3;
    sideConstraints.insets = new Insets(0, -110, 50, 0);
    sideWindow.add(timeCount, sideConstraints);


    sideConstraints.gridx = 1;
    sideConstraints.gridy = 4;
    sideConstraints.fill = GridBagConstraints.NONE;
    sideConstraints.insets = new Insets(250, 0, 0, 0);
    sideWindow.add(up, sideConstraints);

    sideConstraints.gridx = 0;
    sideConstraints.gridy = 5;
    sideConstraints.insets = new Insets(0, 15, 0, 0);
    sideWindow.add(left, sideConstraints);

    sideConstraints.gridx = 1;
    sideConstraints.gridy = 5;
    sideConstraints.insets = new Insets(0, 0, 0, 0);
    sideWindow.add(down, sideConstraints);

    sideConstraints.gridx = 2;
    sideConstraints.gridy = 5;
    sideWindow.add(right, sideConstraints);

    sideConstraints.gridx = 3;
    sideConstraints.gridy = 6;
    sideConstraints.fill = GridBagConstraints.HORIZONTAL;
    sideConstraints.insets = new Insets(130, -113, 0, 0);
    sideWindow.add(removeSave, sideConstraints);

    sideConstraints.gridy = 7;
    sideConstraints.insets = new Insets(10, -113, 0, 0);
    sideWindow.add(quitGame, sideConstraints);

    sideConstraints.gridy = 8;
    sideConstraints.insets = new Insets(-1000, -110, 0, 0);
    sideWindow.add(helpWindow, sideConstraints);

    Image invBackground = Toolkit.getDefaultToolkit()
            .createImage("assets/backgrounds/invBackground.png");
    this.lowerWindow = new InventoryPanel(this.maze, invBackground);
    this.lowerWindow.setMinimumSize(new Dimension(100, 150));
    this.lowerWindow.setPreferredSize(new Dimension(100, 150));
    this.lowerWindow.setBackground(Color.BLACK);

    JPanel replayWindow = new JPanel();
    if (isReplay) {
      Image replayBackground = Toolkit.getDefaultToolkit()
              .createImage("assets/backgrounds/replayBackground.png");
      replayWindow = new BackgroundPanel(replayBackground, new GridBagLayout(), true);
      replayWindow.setMinimumSize(new Dimension(150, 40));
      replayWindow.setPreferredSize(new Dimension(150, 40));
      replayWindow.setBackground(Color.BLACK);

      Playback replay = new Playback();
      replay.load(this.replayPath);

      JButton pause = new JButton();
      pause.setBorder(null);
      Image pauseIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/pause.png");
      pause.setIcon(new ImageIcon(pauseIcon));

      JButton play = new JButton();
      play.setBorder(null);
      Image playIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/play.png");
      play.setIcon(new ImageIcon(playIcon));

      JButton step = new JButton();
      step.setBorder(null);
      Image stepIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/step.png");
      step.setIcon(new ImageIcon(stepIcon));

      ApplicationView currAppli = this;
      play.addActionListener(actionEvent -> {
        if (replay.isPaused()) {
          replay.resume(currAppli);
        } else {
          replay.play(currAppli);
        }
        startTimers();
      });
      pause.addActionListener(actionEvent -> {
        replay.pause();
        stopTimers();
      });
      step.addActionListener(actionEvent -> {
        startTimers();
        replay.step(true, currAppli);
        stopTimers();
      });

      JButton speedChange = new JButton();
      speedChange.setBorder(null);
      Image speedIcon = Toolkit.getDefaultToolkit().createImage("assets/buttons/speed.png");
      speedChange.setIcon(new ImageIcon(speedIcon));
      speedChange.addActionListener(actionEvent -> {
        if (replay.isPaused() || !replay.isRunning()) {
          currSpeed = changeReplaySpeed(currSpeed);
          viewport.setAnimateSpeed(currSpeed);
          replay.setReplaySpeed(currSpeed);
          double countTimerValue = (1000 * currSpeed);
          countdownTimer.setDelay((int) countTimerValue);
          double actorTimerValue = (250 * currSpeed);
          countdownTimer.setDelay((int) actorTimerValue);
          this.replaySpeed.setText("Current speed: " + this.currSpeed + "x");
        }
      });

      this.replaySpeed = new JLabel("Current speed: " + this.currSpeed + "x");
      this.replaySpeed.setForeground(Color.LIGHT_GRAY);
      this.replaySpeed.setFont(this.game.deface.deriveFont(14f));
      this.replaySpeed.setOpaque(true);
      this.replaySpeed.setBackground(Color.BLACK);

      GridBagConstraints replayConstraints = new GridBagConstraints();
      replayConstraints.gridx = 0;
      replayConstraints.gridy = 0;
      replayConstraints.fill = GridBagConstraints.NONE;
      replayConstraints.anchor = GridBagConstraints.CENTER;
      replayConstraints.insets = new Insets(0, 0, 0, 0);
      replayWindow.add(play, replayConstraints);

      replayConstraints.gridx = 1;
      replayConstraints.insets = new Insets(0, 10, 0, 0);
      replayWindow.add(pause, replayConstraints);

      replayConstraints.gridx = 2;
      replayWindow.add(step, replayConstraints);

      replayConstraints.gridx = 3;
      replayWindow.add(speedChange, replayConstraints);

      replayConstraints.gridx = 4;
      replayWindow.add(this.replaySpeed, replayConstraints);
    }

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.weighty = 1;
    JPanel windowContents = new JPanel(new GridBagLayout());
    windowContents.add(this.mainWindow, constraints);

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridheight = 1;
    constraints.gridwidth = 2;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = 0;
    constraints.weighty = 1;
    windowContents.add(this.lowerWindow, constraints);

    if (isReplay) {
      constraints.gridy = 2;
      windowContents.add(replayWindow, constraints);
      Image fillerBackground = Toolkit.getDefaultToolkit()
              .createImage("assets/backgrounds/fillerBackground.png");
      JPanel fillSpace = new BackgroundPanel(fillerBackground, new GridBagLayout(), true);
      fillSpace.setMinimumSize(new Dimension(150, 40));
      fillSpace.setPreferredSize(new Dimension(150, 40));
      fillSpace.setBackground(Color.BLACK);
      constraints.gridx = 1;
      windowContents.add(fillSpace, constraints);
    }

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.gridheight = 2;
    constraints.fill = GridBagConstraints.VERTICAL;
    constraints.weightx = 1;
    constraints.weighty = 0;
    windowContents.add(sideWindow, constraints);

    this.window.setContentPane(windowContents);
  }

  public String getLevelPath() {
    return this.game.levelPath;
  }

  /**
   * Controls movement of the player.
   *
   * @param dir - direction player is moving
   *            1: move up
   *            2: move down
   *            3: move left
   *            4: move right
   * @param isFromLog - True if the command is coming from a replay.
   */
  public void playerMovement(int dir, boolean isFromLog) {
    if (!isPaused) {
      switch (dir) {
        case 1:
          if (maze.getPlayer().moveUp()) {
            viewport.draw(true);
          }
          if (!isFromLog) {
            log.addAction("moveUp", "player");
          }
          break;
        case 2:
          if (maze.getPlayer().moveDown()) {
            viewport.draw(true);
          }
          if (!isFromLog) {
            log.addAction("moveDown", "player");
          }
          break;
        case 3:
          if (maze.getPlayer().moveLeft()) {
            viewport.draw(true);
          }
          if (!isFromLog) {
            log.addAction("moveLeft", "player");
          }
          break;
        case 4:
          if (maze.getPlayer().moveRight()) {
            viewport.draw(true);
          }
          if (!isFromLog) {
            log.addAction("moveRight", "player");
          }
          break;
        default:
      }
      this.scoreCount.setText("" + this.maze.getTreasuresLeft());
      this.helpWindow.setText("" + this.maze.getDisplayText());
      this.lowerWindow.repaint();
      if (this.maze.isFinished()) {
        this.gameOver = true;
        this.countdownTimer.stop();
        new LevelWonView(this.window, this);
      }
      this.mainWindow.requestFocus();
    }
  }

  private void showLoadReplayDialogue() {
    this.countdownTimer.stop();
    JFileChooser c = new JFileChooser();
    int windowDialog = c.showOpenDialog(window);
    Label filename = new Label();
    Label dir = new Label();
    if (windowDialog == JFileChooser.APPROVE_OPTION) {
      filename.setText(c.getSelectedFile().getName());
      dir.setText(c.getCurrentDirectory().toString());
      game.loadReplayLevel(dir.getText() + "/" + filename.getText());
    }
    if (windowDialog == JFileChooser.CANCEL_OPTION) {
      filename.setText("");
      dir.setText("");
      this.countdownTimer.start();
    }
  }

  private void showSaveReplayDialogue() {
    this.countdownTimer.stop();
    JFileChooser c = new JFileChooser();
    int windowDialog = c.showSaveDialog(window);
    Label filename = new Label();
    Label dir = new Label();
    if (windowDialog == JFileChooser.APPROVE_OPTION) {
      filename.setText(c.getSelectedFile().getName());
      dir.setText(c.getCurrentDirectory().toString());
      this.log.saveReplay(new File(dir.getText() + "/"
              + filename.getText() + this.game.currLevel + ".json"));
    }
    if (windowDialog == JFileChooser.CANCEL_OPTION) {
      filename.setText("");
      dir.setText("");
    }
    this.countdownTimer.start();
  }

  public void restartLevel() {
    this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.game.restartLevel(this.game.currLevel);
  }

  public void changeLevel() {
    this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.game.nextLevel(this.game.currLevel);
  }

  private void saveGame() {
    this.countdownTimer.stop();
    JFileChooser c = new JFileChooser();
    int windowDialog = c.showSaveDialog(window);
    Label filename = new Label();
    Label dir = new Label();
    if (windowDialog == JFileChooser.APPROVE_OPTION) {
      filename.setText(c.getSelectedFile().getName());
      dir.setText(c.getCurrentDirectory().toString());
      this.maze.save(dir.getText() + "/" + filename.getText() + this.game.currLevel + ".json");
    }
    if (windowDialog == JFileChooser.CANCEL_OPTION) {
      filename.setText("");
      dir.setText("");
    }
    this.countdownTimer.start();
  }

  private void loadSave() {
    this.countdownTimer.stop();
    JFileChooser c = new JFileChooser();
    int windowDialog = c.showOpenDialog(window);
    Label filename = new Label();
    Label dir = new Label();
    if (windowDialog == JFileChooser.APPROVE_OPTION) {
      filename.setText(c.getSelectedFile().getName());
      dir.setText(c.getCurrentDirectory().toString());
      this.game.loadSave(dir.getText() + "/" + filename.getText());
    }
    if (windowDialog == JFileChooser.CANCEL_OPTION) {
      filename.setText("");
      dir.setText("");
      this.countdownTimer.start();
    }
  }

  public void loadReplay() {
    showLoadReplayDialogue();
  }

  public void makeReplayDialog() {
    new ReplayDoneView(this.window, this);
  }

  private double changeReplaySpeed(double toChange) {
    double newSpeed;
    if (toChange < 2.0) {
      newSpeed = toChange + 0.5;
    } else {
      newSpeed = 0.5;
    }
    return newSpeed;
  }

  public void disposeWindow() {
    this.window.dispose();
  }

  public Main getMain() {
    return this.game;
  }

  public void stopTimers() {
    countdownTimer.stop();
    npcMovementTimer.stop();
  }

  public void startTimers() {
    countdownTimer.start();
    npcMovementTimer.start();
  }

  private void showHelpView() {
    stopTimers();
    this.isPaused = true;
    helpView = new HelpView(this.window);
    helpView.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
        isPaused = false;
        startTimers();
      }
    });
    this.helpView.setVisible(true);
  }
}
