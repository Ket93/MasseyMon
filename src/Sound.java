//Dimitrios Christopoulos
//Sound.java
//Class for sound effects and game music. THis will create a Clip object and allow the clip to be paused, played, muted,
//and for the volume of the clip to be adjusted.

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sound { // Class for game sound and sound effects
    private static ArrayList<Sound> madeSounds = new ArrayList<>(); // array list of sounds
    private static boolean isMuted; // if the sound is muted
    private AudioInputStream inputStream; // audio input object
    private Clip clip; // clip object
    private String filePath; // file path of the clip
    FloatControl volume; // volume of clip
    private boolean wasPaused; // if the sound was paused
    public Sound(String filePath, int volumeLevel){
        this.filePath = filePath;
        try {
            clip = AudioSystem.getClip();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        loadClip();
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(volumeLevel);
        madeSounds.add(this);
    }
    private void loadClip(){
        try{
            inputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip.open(inputStream);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Sound error");
            e.printStackTrace();
        }
    }
    public void play(){
        clip.setMicrosecondPosition(0);
        clip.start();
    }
    public void resume(){
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
    public void closeSound(){
        clip.close();
        madeSounds.remove(this);
    }
    public boolean hasStarted(){
        return clip.isOpen();
    }
    public boolean isPlaying(){
        return clip.isActive();
    }
    public void setVolume(int volumeLevel){
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (float) (range * (volumeLevel/100.0)) + volume.getMinimum();
        volume.setValue(gain);
    }
    public void setGain(float gain){
        volume.setValue(gain);
    }
    public float getGain(){
        return volume.getValue();
    }

    // Static methods
    public static void pauseAll(){
        for(Sound sound: madeSounds){
            if(sound.isPlaying()){
                sound.stop();
                sound.wasPaused = true;
            }
        }
    }
    public static void resumeAll(){
        for(Sound sound: madeSounds){
            if(sound.wasPaused){
                sound.resume();
                sound.wasPaused = false;
            }
        }
    }
    public static boolean isMuted(){
        return isMuted;
    }
}
