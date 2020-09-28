package nz.ac.vuw.ecs.swen225.gp20.application;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.render.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ApplicationView {
    private Main game;
    private Maze maze;
    private Board viewport;
    private JFrame window;
    private JPanel windowContents;
    private JMenuBar saveLoad;
    private JMenu save = new JMenu("Save");
    private JMenu load = new JMenu("Load");
    private JMenuItem saveGame = new JMenuItem("Save Game");
    private JMenuItem loadGame = new JMenuItem("Load Game");


    public ApplicationView(Main game){
        this.game = game;
        this.maze = new Maze(this.game.levelPath);
        this.viewport = new Board(this.maze);
        this.makeWindow();
    }

    private void makeWindow(){
        this.window = new JFrame("Chap's Challenge");
        this.window.setLayout(new BorderLayout());
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        this.addToWindow();
        this.window.pack();
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
    }

    private void addToWindow() {
        this.saveLoad = new JMenuBar();
        this.save.add(this.saveGame);
        this.load.add(this.loadGame);
        this.saveLoad.add(this.save);
        this.saveLoad.add(this.load);
        this.window.setJMenuBar(this.saveLoad);
        this.windowContents = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        GridBagConstraints sideConstraints = new GridBagConstraints();

        JPanel mainWindow = viewport;
        mainWindow.setMinimumSize(new Dimension(700, 700));
        mainWindow.setPreferredSize(new Dimension(700, 700));
        mainWindow.setBackground(Color.RED);

        JPanel sideWindow = new JPanel(new GridBagLayout());
        sideWindow.setMinimumSize(new Dimension(150, 100));
        sideWindow.setPreferredSize(new Dimension(150, 100));
        sideWindow.setBackground(Color.BLUE);

        JLabel score = new JLabel("Score:");
        JLabel scoreCount = new JLabel("PLACEHOLDER");
        JLabel time = new JLabel("Time Remaining:");
        JLabel timeCount = new JLabel("PLACEHOLDER");


        JButton left = new JButton("\uD83E\uDC50");
        left.addActionListener(actionEvent -> maze.getPlayer().moveLeft());
        left.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        left.getActionMap().put("moveLeft", new AbstractAction("moveLeft") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                maze.getPlayer().moveLeft();
            }
        });

        JButton up = new JButton("\uD83E\uDC51");
        up.addActionListener(actionEvent -> maze.getPlayer().moveUp());
        up.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
        up.getActionMap().put("moveUp", new AbstractAction("moveUp") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                maze.getPlayer().moveUp();
            }
        });

        JButton down = new JButton("\uD83E\uDC53");
        down.addActionListener(actionEvent -> maze.getPlayer().moveDown());
        down.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        down.getActionMap().put("moveDown", new AbstractAction("moveDown") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                maze.getPlayer().moveDown();
            }
        });

        JButton right = new JButton("\uD83E\uDC52");
        right.addActionListener(actionEvent -> maze.getPlayer().moveRight());
        right.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        right.getActionMap().put("moveRight", new AbstractAction("moveRight") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                maze.getPlayer().moveRight();
            }
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
        lowerWindow.setBackground(Color.GREEN);

        JButton quitGame = new JButton("Quit Game");
        quitGame.addActionListener(actionEvent -> System.exit(0));
        quitGame.setPreferredSize(new Dimension(125, 25));
        lowerWindow.add(quitGame);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        this.windowContents.add(mainWindow, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0;
        constraints.weighty = 1;
        this.windowContents.add(lowerWindow, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.weightx = 1;
        constraints.weighty = 0;
        this.windowContents.add(sideWindow, constraints);

        this.window.setContentPane(this.windowContents);
    }
}
