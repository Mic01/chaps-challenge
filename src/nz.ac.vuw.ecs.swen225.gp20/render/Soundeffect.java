package nz.ac.vuw.ecs.swen225.gp20.render;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Soundeffect {
  public static HashMap<String, AudioInputStream> loadedSounds = new HashMap<>();

  public AudioInputStream getSoundProxy(String fileName) throws IOException, UnsupportedAudioFileException {
    //Sound has been loaded already, return stream
    if(loadedSounds.containsKey(fileName))
      return loadedSounds.get(fileName);

    //Otherwise load sound now
    AudioInputStream aStream = AudioSystem.getAudioInputStream(
            new File(fileName + ".wav").getAbsoluteFile());
    loadedSounds.put(fileName, aStream);
    return aStream;
  }
}
