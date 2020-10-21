package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Audio Player class that acts like a "boombox"
 * Plays one audio at a time dependant on their priority rating.
 */
public class AudioPlayer implements LineListener {
  private Clip currentClip;
  private boolean finishedPlay = true;
  private int currentPriority;

  /**
   * Checks whether the passed audio can play,
   * based on if theres no audio playing or its priority,
   * plays audio if able to.
   *
   * @param priority of clip, to override other sounds
   * @param soundEffect object to retrieve clip
   */
  public void playAudio(int priority, SoundEffect soundEffect) {
    //New audio should be played, stop current audio
    if (priority > currentPriority) {
      finishedPlay = true;
      if (currentClip != null) {
        currentClip.stop();
      }
      currentPriority = priority;
    }

    //Audio finished playing, play new audio
    if (finishedPlay) {
      currentClip = soundEffect.getClip();
      currentClip.addLineListener(this);
      currentClip.setFramePosition(0);
      currentClip.start();
    }

    //Allows pickup item to override other pickup items
    if (soundEffect.getName().equals("pickup_item")) {
      currentPriority = 2;
    }
  }

  @Override
  public void update(LineEvent event) {
    LineEvent.Type type = event.getType();

    if (type == LineEvent.Type.START) {
      finishedPlay = false;
    } else if (type == LineEvent.Type.STOP) {
      finishedPlay = true;
      currentPriority = 1;
      currentClip.stop();
    }
  }
}
