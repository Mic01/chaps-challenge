package nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads;

import nz.ac.vuw.ecs.swen225.gp20.application.ApplicationView;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Elements.Node;

public class ReplayThread extends Dispatch {
  public ReplayThread(ApplicationView application, Node baseNode, double timeScale) {
    super(application, baseNode, timeScale);
  }

  @Override
  public synchronized void start() {
    baseNode.play(application, timeScale);
    complete = true;
  }
}
