package nz.ac.vuw.ecs.swen225.gp20.application;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.render.Board;

import javax.swing.*;
import java.awt.*;

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

        JPanel mainWindow = viewport;
        mainWindow.setMinimumSize(new Dimension(700, 700));
        mainWindow.setMaximumSize(new Dimension(700, 700));
        mainWindow.setPreferredSize(new Dimension(700, 700));
        mainWindow.setBackground(Color.RED);

        JPanel sideWindow = new JPanel();
        sideWindow.setMinimumSize(new Dimension(150, 100));
        sideWindow.setMaximumSize(new Dimension(150, 100));
        sideWindow.setPreferredSize(new Dimension(150, 100));
        sideWindow.setBackground(Color.BLUE);

        JPanel lowerWindow = new JPanel();
        lowerWindow.setMinimumSize(new Dimension(100, 150));
        lowerWindow.setMaximumSize(new Dimension(100, 150));
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
