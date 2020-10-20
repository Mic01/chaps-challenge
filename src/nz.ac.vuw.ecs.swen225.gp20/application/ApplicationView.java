package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Playback;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.render.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ApplicationView {

    private final Main game;
    private final Maze maze;
    private final Board viewport;
    private final Replay log;
    private JFrame window;
    private final JMenu save = new JMenu("Save");
    private final JMenu load = new JMenu("Load");
    private final JMenu replays = new JMenu("Replays");
    private final JMenuItem saveGame = new JMenuItem("Save Game");
    private final JMenuItem loadGame = new JMenuItem("Load Game");
    private final JMenuItem saveReplay = new JMenuItem("Save Replay");
    private final JMenuItem loadReplay = new JMenuItem("Load Replay");
    private boolean gameOver = false;
    private boolean isReplay;
    private JLabel scoreCount = new JLabel("0");
    private JPanel lowerWindow = new JPanel();
    private String replayPath = "";
    private Timer countdownTimer = null;


    public ApplicationView(Main game, boolean isReplay) {
        this.maze = new Maze(game.levelPath);
        this.viewport = new Board(this.maze);
        this.game = game;
        this.log = new Replay(this);
        this.isReplay = isReplay;
        this.makeWindow();
    }

    public ApplicationView(Main game, boolean isReplay, String replayPath) {
        this.maze = new Maze(game.levelPath);
        this.viewport = new Board(this.maze);
        this.game = game;
        this.log = new Replay(this);
        this.isReplay = isReplay;
        this.replayPath = replayPath;
        this.makeWindow();
    }

    /**
     * Constructs a JFrame within which the main game will be displayed.
     */
    private void makeWindow() {
        this.window = new JFrame("Chap's Challenge");
        this.window.setLayout(new BorderLayout());
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        this.window.getRootPane().getActionMap().put("close", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        this.window.getRootPane().getActionMap().put("saveAndClose", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke("control X"), "close");
        this.window.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke("control S"), "saveAndClose");
        this.addToWindow();
        this.window.pack();
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
    }

    /**
     * Adds contents to the constructed JFrame.
     */
    private void addToWindow() {

        JMenuBar saveLoad = new JMenuBar();
        this.saveGame.addActionListener(actionEvent -> saveGame());
        this.save.add(this.saveGame);
        this.loadGame.addActionListener(actionEvent -> loadSave());
        this.load.add(this.loadGame);
        this.saveReplay.addActionListener(actionEvent -> showSaveReplayDialogue());
        this.loadReplay.addActionListener(actionEvent -> showLoadReplayDialogue());
        this.replays.add(saveReplay);
        this.replays.add(loadReplay);
        saveLoad.add(this.save);
        saveLoad.add(this.load);
        saveLoad.add(this.replays);
        this.window.setJMenuBar(saveLoad);

        JPanel windowContents = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        GridBagConstraints sideConstraints = new GridBagConstraints();

        JPanel mainWindow = viewport;
        mainWindow.setMinimumSize(new Dimension(630, 630));
        mainWindow.setPreferredSize(new Dimension(630, 630));
        mainWindow.setBackground(Color.BLACK);

        JPanel sideWindow = new JPanel(new GridBagLayout());
        sideWindow.setMinimumSize(new Dimension(150, 100));
        sideWindow.setPreferredSize(new Dimension(150, 100));
        sideWindow.setBackground(Color.BLACK);

        JLabel score = new JLabel("Electronics Remaining:");
        score.setForeground(Color.LIGHT_GRAY);
        this.scoreCount = new JLabel("" + maze.getTreasuresLeft());
        this.scoreCount.setForeground(Color.LIGHT_GRAY);
        JLabel time = new JLabel("Time Remaining:");
        time.setForeground(Color.LIGHT_GRAY);
        JLabel timeCount;
        timeCount = new JLabel(this.maze.getTimeLimit() + " seconds");
        timeCount.setForeground(Color.LIGHT_GRAY);

        ApplicationView currentGame = this;
        ActionListener countdown;
        countdown = new ActionListener() {
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
                maze.setTimeLimit(timeLeft);
            }
        };
        countdownTimer = new javax.swing.Timer(1000, countdown);
        countdownTimer.start();

        ActionListener npcMovement = actionEvent -> {
            if (gameOver) {
                ((Timer) actionEvent.getSource()).stop();
            }
            else if(this.maze.getPlayer().isDead()){
                gameOver = true;
                new LevelLostView(window, currentGame, false);
            }
            else {
                for (AutoActor a : maze.getAutoActors()) {
                    a.autoMove();
                    if(!viewport.isAnimating()) {
                        viewport.draw(false);
                    }
                }
            }
        };
        javax.swing.Timer npcMovementTimer = new javax.swing.Timer(250, npcMovement);
        npcMovementTimer.start();

        JButton left = new JButton("\uD83E\uDC50");
        JButton up = new JButton("\uD83E\uDC51");
        JButton down = new JButton("\uD83E\uDC53");
        JButton right = new JButton("\uD83E\uDC52");

        if(!isReplay) {
            down.addActionListener(actionEvent -> playerMovement(2, false));
            down.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "moveDown");
            down.getActionMap().put("moveDown", new AbstractAction("moveDown") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    playerMovement(2, false);
                }
            });

            up.addActionListener(actionEvent -> playerMovement(1, false));
            up.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "moveUp");
            up.getActionMap().put("moveUp", new AbstractAction("moveUp") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    playerMovement(1, false);
                }
            });

            left.addActionListener(actionEvent -> playerMovement(3, false));
            left.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "moveLeft");
            left.getActionMap().put("moveLeft", new AbstractAction("moveLeft") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    playerMovement(3, false);
                }
            });

            right.addActionListener(actionEvent -> playerMovement(4, false));
            right.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "moveRight");
            right.getActionMap().put("moveRight", new AbstractAction("moveRight") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    playerMovement(4, false);
                }
            });
        }

        JButton quitGame = new JButton("Quit Game");
        quitGame.addActionListener(actionEvent -> System.exit(0));
        quitGame.setPreferredSize(new Dimension(125, 25));

        sideConstraints.gridx = 3;
        sideConstraints.gridy = 0;
        sideConstraints.fill = GridBagConstraints.HORIZONTAL;
        sideConstraints.anchor = GridBagConstraints.CENTER;
        sideConstraints.insets = new Insets(0, -125, 0, 0);
        sideWindow.add(score, sideConstraints);

        sideConstraints.gridx = 3;
        sideConstraints.gridy = 1;
        sideConstraints.insets = new Insets(0, -125, 50, 0);
        sideWindow.add(scoreCount, sideConstraints);

        sideConstraints.gridx = 3;
        sideConstraints.gridy = 2;
        sideConstraints.insets = new Insets(0, -125, 0, 0);
        sideWindow.add(time, sideConstraints);

        sideConstraints.gridx = 3;
        sideConstraints.gridy = 3;
        sideConstraints.insets = new Insets(0, -125, 300, 0);
        sideWindow.add(timeCount, sideConstraints);

        sideConstraints.gridx = 1;
        sideConstraints.gridy = 4;
        sideConstraints.fill = GridBagConstraints.NONE;
        sideConstraints.insets = new Insets(0, 0, 0, 0);
        sideWindow.add(up, sideConstraints);

        sideConstraints.gridx = 0;
        sideConstraints.gridy = 5;
        sideConstraints.insets = new Insets(0, 4, 0, 0);
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
        sideConstraints.insets = new Insets(100, -127, 0, 0);
        sideWindow.add(quitGame, sideConstraints);

        this.lowerWindow = new InventoryPanel(this.maze);
        this.lowerWindow.setMinimumSize(new Dimension(100, 150));
        this.lowerWindow.setPreferredSize(new Dimension(100, 150));
        this.lowerWindow.setBackground(Color.BLACK);

        JPanel replayWindow = new JPanel();
        if(isReplay) {
            replayWindow = new JPanel(new GridBagLayout());
            replayWindow.setMinimumSize(new Dimension(150, 50));
            replayWindow.setPreferredSize(new Dimension(150, 50));
            replayWindow.setBackground(Color.WHITE);

            GridBagConstraints replayConstraints = new GridBagConstraints();
            Playback replay = new Playback();
            replay.load(this.replayPath);

            ApplicationView currAppli = this;
            boolean replayHasFinished = false;

            JButton pause = new JButton("\u2016");
            JButton play = new JButton("⯈");
            JButton step = new JButton("\uD83E\uDC7A");

            play.addActionListener(actionEvent -> replay.play(currAppli, 1.0));
            pause.addActionListener(actionEvent -> replay.pause());

            replayConstraints.gridx = 0;
            replayConstraints.gridy = 0;
            replayConstraints.fill = GridBagConstraints.NONE;
            replayConstraints.anchor = GridBagConstraints.CENTER;
            replayConstraints.insets = new Insets(10, 0, 0, 0);
            replayWindow.add(play, replayConstraints);

            replayConstraints.gridx = 1;
            replayConstraints.insets = new Insets(10, 10, 0, 0);
            replayWindow.add(pause, replayConstraints);

            replayConstraints.gridx = 2;
            replayWindow.add(step, replayConstraints);
        }

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        windowContents.add(mainWindow, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0;
        constraints.weighty = 1;
        windowContents.add(lowerWindow, constraints);

        if(isReplay){
            constraints.gridy = 2;
            windowContents.add(replayWindow, constraints);
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

    public String getLevelPath(){
        return this.game.levelPath;
    }

    /**
     * Controls movement of the player.
     *
     * @param dir - direction player is moving
     * 1: move up
     * 2: move down
     * 3: move left
     * 4: move right
     */
    public void playerMovement(int dir, boolean isFromLog){
        switch (dir) {
            case 1:
                if(maze.getPlayer().moveUp()) {
                    viewport.draw(true);
                }
                if (!isFromLog) {
                    log.addAction("moveUp", "player");
                }
                break;
            case 2:
                if(maze.getPlayer().moveDown()) {
                    viewport.draw(true);
                }
                if (!isFromLog) {
                    log.addAction("moveDown", "player");
                }
                break;
            case 3:
                if(maze.getPlayer().moveLeft()) {
                    viewport.draw(true);
                }
                if (!isFromLog) {
                    log.addAction("moveLeft", "player");
                }
                break;
            case 4:
                if(maze.getPlayer().moveRight()) {
                    viewport.draw(true);
                }
                if (!isFromLog) {
                    log.addAction("moveRight", "player");
                }
                break;
            default:
        }
        this.scoreCount.setText("" + maze.getTreasuresLeft());
        this.lowerWindow.repaint();
        if(maze.isFinished()){
            gameOver = true;
            countdownTimer.stop();
            new LevelWonView(this.window, this);
        }
    }

    private void showLoadReplayDialogue() {
        JFileChooser c = new JFileChooser();
        int rVal = c.showOpenDialog(window);
        Label filename = new Label(), dir = new Label();
        if (rVal == JFileChooser.APPROVE_OPTION) {
            filename.setText(c.getSelectedFile().getName());
            dir.setText(c.getCurrentDirectory().toString());
            game.loadReplayLevel(dir.getText() + "/" + filename.getText(), this.game.currLevel);
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            filename.setText("");
            dir.setText("");
        }
    }

    private void showSaveReplayDialogue() {
        JFileChooser c = new JFileChooser();
        int rVal = c.showSaveDialog(window);
        Label filename = new Label();
        Label dir = new Label();
        if (rVal == JFileChooser.APPROVE_OPTION) {
            filename.setText(c.getSelectedFile().getName());
            dir.setText(c.getCurrentDirectory().toString());
            this.log.saveReplay(new File(dir.getText() + "/" + filename.getText()));
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            filename.setText("");
            dir.setText("");
        }
    }

    public void restartLevel(){
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.game.restartLevel(this.game.currLevel);
    }

    public void changeLevel(){
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.game.nextLevel(this.game.currLevel);
    }

    private void saveGame(){
        JFileChooser c = new JFileChooser();
        int rVal = c.showSaveDialog(window);
        Label filename = new Label();
        Label dir = new Label();
        if (rVal == JFileChooser.APPROVE_OPTION) {
            filename.setText(c.getSelectedFile().getName());
            dir.setText(c.getCurrentDirectory().toString());
            this.maze.save(dir.getText() + "/" + filename.getText() + this.game.currLevel + ".json");
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            filename.setText("");
            dir.setText("");
        }
    }

    private void loadSave(){
        JFileChooser c = new JFileChooser();
        int rVal = c.showOpenDialog(window);
        Label filename = new Label(), dir = new Label();
        if (rVal == JFileChooser.APPROVE_OPTION) {
            filename.setText(c.getSelectedFile().getName());
            dir.setText(c.getCurrentDirectory().toString());
            this.game.loadSave(dir.getText() + "/" + filename.getText());
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            filename.setText("");
            dir.setText("");
        }
    }

    public void disposeWindow(){
        this.window.dispose();
    }
}
