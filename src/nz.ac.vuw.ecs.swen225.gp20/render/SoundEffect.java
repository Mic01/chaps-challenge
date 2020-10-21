package nz.ac.vuw.ecs.swen225.gp20.render;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * SoundEffect class to construct clips
 * for sound playing in the Audio player.
 */
public class SoundEffect {
  private Clip clip;
  private final String name;

  /**
   * Generates clip from Audio stream by loading the file.
   *
   * @param fileName of the sound file
   */
  public SoundEffect(String fileName) {
    name = fileName;
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(
              new File("assets/soundeffects/" + fileName + ".wav"));
      AudioFormat format = audioStream.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, format);
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(audioStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns clip of the SoundEffect.
   *
   * @return clip
   */
  public Clip getClip() {
    return clip;
  }

  /**
   * Returns the name of the SoundEffect.
   *
   * @return name
   */
  public String getName() {
    return name;
  }
}
