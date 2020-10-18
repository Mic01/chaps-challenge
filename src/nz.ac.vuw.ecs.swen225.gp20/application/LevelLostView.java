package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LevelLostView {
    private JDialog dialog;
    private JPanel panel;
    private JFrame owner;
    private ApplicationView game;
    private boolean timeOut;

    public LevelLostView(JFrame owner, ApplicationView game, boolean timeOut) {
        this.owner = owner;
        this.game = game;
        this.timeOut = timeOut;
        setupWindow();
    }

    private void setupWindow() {

        this.dialog = new JDialog(this.owner);
        this.dialog.setResizable(false);
        this.dialog.setSize(400, 330);
        this.dialog.setUndecorated(true);
        this.dialog.setModal(true);
        this.dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addToDialog(this.dialog.getContentPane());
        this.dialog.pack();
        this.dialog.setLocationRelativeTo(this.owner);
        this.dialog.setVisible(true);
        this.dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void addToDialog(Container pane) {
        this.panel = new JPanel(new GridBagLayout());

        GridBagConstraints winConstraints = new GridBagConstraints();

        winConstraints.anchor = GridBagConstraints.CENTER;
        winConstraints.fill = GridBagConstraints.HORIZONTAL;
        winConstraints.gridx = 1;
        winConstraints.gridy = 0;
        winConstraints.insets = new Insets(5, 0, 5, 0);
        JLabel titleText;
        if(this.timeOut) {
           titleText = new JLabel("You ran out of time...");
        }
        else{
            titleText = new JLabel("You have been killed...");
        }
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        this.panel.add(titleText, winConstraints);

        winConstraints.anchor = GridBagConstraints.LINE_START;
        winConstraints.fill = GridBagConstraints.NONE;
        winConstraints.gridx = 0;
        winConstraints.gridy = 2;
        winConstraints.insets = new Insets(20, 20, 10, 0);
        JButton startGame = new JButton("Restart Level");
        startGame.addActionListener(actionEvent -> game.restartLevel());
        this.panel.add(startGame, winConstraints);

        winConstraints.anchor = GridBagConstraints.LINE_END;
        winConstraints.fill = GridBagConstraints.NONE;
        winConstraints.gridx = 2;
        winConstraints.gridy = 2;
        winConstraints.insets = new Insets(20, 0, 10, 20);
        winConstraints.ipadx = 20;
        JButton exit = new JButton("Exit Game");
        exit.addActionListener(actionEvent -> System.exit(0));
        this.panel.add(exit, winConstraints);


        pane.add(this.panel);
    }
}