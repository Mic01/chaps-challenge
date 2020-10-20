package nz.ac.vuw.ecs.swen225.gp20.render;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.*;

public class SoundEffect implements LineListener {
  public static HashMap<String, File> loadedSounds = new HashMap<>();
  private static boolean finishedPlay = true;
  private static DataLine.Info info = null;
  private static Clip clip = null;

  public static File getFileProxy(String fileName){
    //Sound has been loaded already, return file
    if(loadedSounds.containsKey(fileName))
      return loadedSounds.get(fileName);

    //Otherwise load file now
    File soundFile = new File("assets/soundeffects/"+fileName + ".wav");
    loadedSounds.put(fileName, soundFile);
    return soundFile;
  }

  public static AudioInputStream getAudioStream(String fileName) throws IOException, UnsupportedAudioFileException {
    return AudioSystem.getAudioInputStream(getFileProxy(fileName).getAbsoluteFile());
  }

  public void playAudio(AudioInputStream audioStream) throws IOException, LineUnavailableException {
    if(finishedPlay) {
      AudioFormat format = audioStream.getFormat();
      info = new DataLine.Info(Clip.class, format);
      clip = (Clip) AudioSystem.getLine(info);
      clip.addLineListener(this);
      clip.open(audioStream);
      clip.setFramePosition(0);
      clip.start();
    }
  }

  @Override
  public void update(LineEvent event) {
    LineEvent.Type type = event.getType();

    if(type == LineEvent.Type.START){
      finishedPlay = false;
      System.out.println("PlayBack Started.");
    } else if(type == LineEvent.Type.STOP){
      finishedPlay = true;
      System.out.println("Playback ended.");
      event.getLine().close();
    }
  }
}
