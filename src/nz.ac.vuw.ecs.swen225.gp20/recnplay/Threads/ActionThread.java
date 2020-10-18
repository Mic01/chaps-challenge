package nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;

public class ActionThread extends Dispatch{
  int move;
  public ActionThread(ApplicationView application, int move) {
    this.move = move;
    this.application = application;
  }

  @Override
  public synchronized void start() {
    application.playerMovement(move, true);
  }
}
