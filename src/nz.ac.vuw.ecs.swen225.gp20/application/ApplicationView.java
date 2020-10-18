package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.AutoActor;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.render.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplicationView {

    private final Main game;
    private final Maze maze;
    private final Board viewport;
    private final Replay log;
    private JFrame window;
    private final JMenu save = new JMenu("Save");
    private final JMenu load = new JMenu("Load");
    private final JMenuItem saveReplay = new JMenuItem("Save Replay");
    private final JMenuItem loadGame = new JMenuItem("Load Game");
    private boolean gameOver = false;


    public ApplicationView(Main game) {
        this.maze = new Maze(game.levelPath);
        this.viewport = new Board(this.maze);
        this.game = game;
        this.log = new Replay(this.getLevelPath());
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
        this.saveReplay.addActionListener(actionEvent -> this.log.saveReplay());
        this.save.add(this.saveReplay);
        this.load.add(this.loadGame);
        saveLoad.add(this.save);
        saveLoad.add(this.load);
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

        JLabel score = new JLabel("Score:");
        score.setForeground(Color.LIGHT_GRAY);
        JLabel scoreCount = new JLabel("PLACEHOLDER");
        scoreCount.setForeground(Color.LIGHT_GRAY);
        JLabel time = new JLabel("Time Remaining:");
        time.setForeground(Color.LIGHT_GRAY);
        JLabel timeCount = new JLabel("60 seconds");
        timeCount.setForeground(Color.LIGHT_GRAY);

        ActionListener countdown = new ActionListener() {
            int timeLeft = 59;
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                timeCount.setText(timeLeft + " seconds");
                if(timeLeft <= 0){
                    //doGameOverWindow
                    ((Timer)actionEvent.getSource()).stop();
                }
                timeLeft--;
            }
        };
        javax.swing.Timer countdownTimer = new javax.swing.Timer(1000, countdown);
        countdownTimer.start();

        ActionListener npcMovement = actionEvent -> {
            if (gameOver) {
                ((Timer) actionEvent.getSource()).stop();
            }
            for(AutoActor a : maze.getAutoActors()){
                a.autoMove();
            }
        };
        javax.swing.Timer npcMovementTimer = new javax.swing.Timer(250, npcMovement);
        npcMovementTimer.start();

        JButton left = new JButton("\uD83E\uDC50");
        left.addActionListener(actionEvent -> playerMovement(3, false));
        left.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "moveLeft");
        left.getActionMap().put("moveLeft", new AbstractAction("moveLeft") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { playerMovement(3, false); }
        });

        JButton up = new JButton("\uD83E\uDC51");
        up.addActionListener(actionEvent -> playerMovement(1, false));
        up.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "moveUp");
        up.getActionMap().put("moveUp", new AbstractAction("moveUp") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { playerMovement(1, false); }
        });

        JButton down = new JButton("\uD83E\uDC53");
        down.addActionListener(actionEvent -> playerMovement(2, false));
        down.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "moveDown");
        down.getActionMap().put("moveDown", new AbstractAction("moveDown") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { playerMovement(2, false); }
        });

        JButton right = new JButton("\uD83E\uDC52");
        right.addActionListener(actionEvent -> playerMovement(4, false));
        right.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "moveRight");
        right.getActionMap().put("moveRight", new AbstractAction("moveRight") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { playerMovement(4, false); }
        });

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

        JPanel lowerWindow = new JPanel();
        lowerWindow.setMinimumSize(new Dimension(100, 150));
        lowerWindow.setPreferredSize(new Dimension(100, 150));
        lowerWindow.setBackground(Color.BLACK);

        JButton quitGame = new JButton("Quit Game");
        quitGame.addActionListener(actionEvent -> System.exit(0));
        quitGame.setPreferredSize(new Dimension(125, 25));
        lowerWindow.add(quitGame);

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
                    ArrayList<Actor> toMove = new ArrayList<>();
                    toMove.add(maze.getPlayer());
                    viewport.draw(toMove);
                }
                if (!isFromLog) {
                    log.addAction("moveUp", "player");
                }
                break;
            case 2:
                if(maze.getPlayer().moveDown()) {
                    ArrayList<Actor> toMove = new ArrayList<>();
                    toMove.add(maze.getPlayer());
                    viewport.draw(toMove);
                }
                if (!isFromLog) {
                    log.addAction("moveUp", "player");
                }
                break;
            case 3:
                if(maze.getPlayer().moveLeft()) {
                    ArrayList<Actor> toMove = new ArrayList<>();
                    toMove.add(maze.getPlayer());
                    viewport.draw(toMove);
                }
                if (!isFromLog) {
                    log.addAction("moveUp", "player");
                }
                break;
            case 4:
                if(maze.getPlayer().moveRight()) {
                    ArrayList<Actor> toMove = new ArrayList<>();
                    toMove.add(maze.getPlayer());
                    viewport.draw(toMove);
                }
                if (!isFromLog) {
                    log.addAction("moveUp", "player");
                }
                break;
            default:
        }
    }
}
