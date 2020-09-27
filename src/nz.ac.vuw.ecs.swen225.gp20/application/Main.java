package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;

public class Main {
    private static Main gameInstance;
    public String levelPath;


    private Main() {
    }

    public static void main(String... args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //JFrame.setDefaultLookAndFeelDecorated(true);
        //JDialog.setDefaultLookAndFeelDecorated(true);
        gameInstance = new Main();
        gameInstance.setup();
    }

    private void setup(){
        new SetupView(this);
        new ApplicationView(this);
    }
}
