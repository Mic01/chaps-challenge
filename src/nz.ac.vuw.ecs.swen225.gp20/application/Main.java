package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;

public class Main {
    private static Main gameInstance;
    private String levelPath;

    private Main() {
    }

    public static void main(String... args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        gameInstance = new Main();
        gameInstance.setup();
    }

    private void setup(){
        new SetupView(this);
    }
}
