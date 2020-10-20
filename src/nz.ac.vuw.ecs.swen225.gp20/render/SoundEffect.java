package nz.ac.vuw.ecs.swen225.gp20.render;

import java.io.File;

import javax.sound.sampled.*;

public class SoundEffect {
  private Clip clip;
  private String name;

  public SoundEffect(String fileName){
    name = fileName;
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(
              new File("assets/soundeffects/" + fileName + ".wav"));
      AudioFormat format = audioStream.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, format);
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(audioStream);
    }catch(Exception e){ e.printStackTrace(); }
  }

  public Clip getClip(){
    return clip;
  }

  public String getName(){ return name; }
}
