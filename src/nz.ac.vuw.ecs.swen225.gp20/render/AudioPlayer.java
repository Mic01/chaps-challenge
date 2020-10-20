package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioPlayer implements LineListener {
  public static Clip currentClip;
  private static boolean finishedPlay = true;
  private static int currentPriority;

  public void playAudio(int priority, SoundEffect soundEffect) {
    if(priority > currentPriority){
      finishedPlay = true;
      if(currentClip != null) currentClip.stop();
      currentPriority = priority;
    }

    if(finishedPlay) {
      currentClip = soundEffect.getClip();
      currentClip.addLineListener(this);
      currentClip.setFramePosition(0);
      currentClip.start();
    }
  }

  @Override
  public void update(LineEvent event) {
    LineEvent.Type type = event.getType();

    if(type == LineEvent.Type.START){
      finishedPlay = false;
    } else if(type == LineEvent.Type.STOP){
      finishedPlay = true;
      currentClip.stop();
    }
  }
}
