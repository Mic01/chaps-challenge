package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.io.File;
import org.junit.jupiter.api.Test;

public class TestCases {
  @Test
  public void testSave01() {
    Replay rep = new Replay("assets/Level1.json");
    try {
      rep.addAction("moveLeft", "player");
      Thread.sleep(1000);
      rep.addAction("moveRight", "player");
      Thread.sleep(1056);
      rep.addAction("moveUp", "actor1");
      Thread.sleep(2000);
      rep.addAction("moveLeft", "actor1");
      rep.levelUp("Level2");
      rep.addAction("moveRight", "player");
      Thread.sleep(1000);
      rep.addAction("moveUp", "actor2");
      Thread.sleep(5000);
      rep.addAction("moveUp", "actor2");
      Thread.sleep(6325);
      rep.levelUp("Level3");
      rep.addAction("moveUp", "player");
      Thread.sleep(1000);
      rep.addAction("moveUp", "actor2");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    rep.saveReplay(new File("src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Replays/testSave.json"));
  }

  @Test
  public void testParse() {
    Playback playback = new Playback();
    playback.load("src/nz.ac.vuw.ecs.swen225.gp20/recnplay/Replays/save.json");
    System.out.println("Completed parsing");
  }
}
