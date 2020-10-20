package nz.ac.vuw.ecs.swen225.gp20.recnplay.Threads;

/**
 * This class is used to wait until other threads have finished
 * and alert the application class upon completion.
 */
public class WaitThread extends Dispatch {
  Dispatch dispatchThread;

  public WaitThread(Dispatch dispatchThread) {
    this.dispatchThread = dispatchThread;
  }

  @Override
  public void run() {
    while (!dispatchThread.isComplete()){}

    System.out.println("Dispatch thread complete.");
  }
}
