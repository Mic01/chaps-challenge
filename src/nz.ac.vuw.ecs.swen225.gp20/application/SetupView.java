package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SetupView {
    private Main game;
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
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addToDialog(dialog.getContentPane());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void addToDialog(Container pane) {
        mainPanel = new JPanel(this.panes);

        //Title Screen Panel
        JPanel titleScreen = new JPanel(new GridBagLayout());
        GridBagConstraints titleConstraints = new GridBagConstraints();

        titleConstraints.anchor = GridBagConstraints.CENTER;
        titleConstraints.fill = GridBagConstraints.HORIZONTAL;
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.insets = new Insets(0, 0, 10, 0);
        JLabel titleText = new JLabel("Team ____ presents:");
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
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
        JButton startGame = new JButton("Begin Game");
        startGame.addActionListener(actionEvent -> panes.next(mainPanel));
        titleScreen.add(startGame, titleConstraints);

        titleConstraints.anchor = GridBagConstraints.LINE_END;
        titleConstraints.fill = GridBagConstraints.NONE;
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 2;
        titleConstraints.insets = new Insets(20, 0, 10, 20);
        titleConstraints.ipadx = 55;
        JButton exit = new JButton("Exit");
        exit.addActionListener(actionEvent -> System.exit(0));
        titleScreen.add(exit, titleConstraints);

        //Panel for level select
        JRadioButton l1, l2;
        ButtonGroup levels = new ButtonGroup();
        JPanel panel2 = new JPanel(new GridBagLayout());
        GridBagConstraints constraints2 = new GridBagConstraints();

        constraints2.anchor = GridBagConstraints.LINE_START;
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.gridx = 0;
        constraints2.gridy = 0;
        constraints2.insets = new Insets(10, 0, 20, 20);
        panel2.add(new JLabel("Please select a level."), constraints2);

        l1 = new JRadioButton("Level 1");
        l1.setActionCommand("1");
        l2 = new JRadioButton("Level 2");
        l2.setActionCommand("2");

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


        JButton next2 = new JButton("Next");
        next2.addActionListener(actionEvent -> {
            ButtonModel model = levels.getSelection();
            if (model == null) return;
            switch (model.getActionCommand()) {
                case "1":
                    this.game.levelPath = "assets/Level1.json";
                    break;
                case "2":
                    this.game.levelPath = "assets/Level2.json";
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
        panel2.add(next2, constraints2);

        mainPanel.add(titleScreen);
        mainPanel.add(panel2);

        pane.add(mainPanel);
    }

}
