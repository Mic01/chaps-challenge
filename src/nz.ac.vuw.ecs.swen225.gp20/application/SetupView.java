package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SetupView {
    private Main game;
    private JDialog dialog;
    private final CardLayout panes = new CardLayout();
    JPanel mainPanel;
    private int i = 0;

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
        titleConstraints.insets = new Insets(-10, 0, 20, 0);
        JLabel titleText = new JLabel("Team ____ presents:");
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        titleScreen.add(titleText, titleConstraints);

        titleConstraints.anchor = GridBagConstraints.CENTER;
        titleConstraints.fill = GridBagConstraints.HORIZONTAL;
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 1;
        titleConstraints.insets = new Insets(0, 0, 0, 0);
        JLabel title = new JLabel(new ImageIcon(ClassLoader.getSystemResource("assets/Logo.png")));
        titleScreen.add(title, titleConstraints);

        titleConstraints.anchor = GridBagConstraints.LINE_START;
        titleConstraints.fill = GridBagConstraints.NONE;
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 2;
        titleConstraints.insets = new Insets(40, 20, 0, 0);
        JButton startGame = new JButton("Begin Game");
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panes.next(mainPanel);
            }
        });
        titleScreen.add(startGame, titleConstraints);

        titleConstraints.anchor = GridBagConstraints.LINE_END;
        titleConstraints.fill = GridBagConstraints.NONE;
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 2;
        titleConstraints.insets = new Insets(40, 0, 0, 20);
        titleConstraints.ipadx = 55;
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        titleScreen.add(exit, titleConstraints);
        /**
        //Panel for selecting number of players
        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(-148, 10, 20, 10);
        panel1.add(new JLabel("Please input the number of players (minimum 3, maximum 6)."), constraints);

        //JSpinner numOfPlayers = new JSpinner(this.playerLimit);
        JTextField numOfPlayers = new JTextField(3);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(2, 100, 10, 10);
        panel1.add(numOfPlayers, constraints);

        JLabel errorMessage = new JLabel();
        constraints.anchor = GridBagConstraints.CENTER;
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, -150, 10);
        panel1.add(errorMessage, constraints);

        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int playerCount;
                try {
                    playerCount = Integer.parseInt(numOfPlayers.getText().trim());
                    if (playerCount < 3 || playerCount > 6) throw new ArrayIndexOutOfBoundsException("Out of range");
                    panes.next(mainPanel);
                } catch (NumberFormatException e) {
                    errorMessage.setText("Please input a number.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    errorMessage.setText("This number is invalid. Please try again.");
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 0, 10, 100);
        panel1.add(next, constraints);
        **/
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
        next2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ButtonModel model = levels.getSelection();
                if (model == null) return;
                switch (model.getActionCommand()) {
                    case "1":
                        //load level 1
                        break;
                    case "2":
                        //load level 2
                        break;
                    //etc
                    default:
                }
                dialog.dispose();
                i++;
            }
        });
        constraints2.gridx = 1;
        constraints2.gridy = 6;
        constraints2.fill = GridBagConstraints.NONE;
        constraints2.anchor = GridBagConstraints.LINE_END;
        constraints2.insets = new Insets(0, 0, 10, 30);
        panel2.add(next2, constraints2);

        mainPanel.add(titleScreen);
        //mainPanel.add(panel1);
        mainPanel.add(panel2);

        pane.add(mainPanel);
    }

}
