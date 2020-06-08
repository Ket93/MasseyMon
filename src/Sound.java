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
    public Sound(String filePath, int volumeLevel){ // sound class, takes in a file path and a volume level for the sound
        this.filePath = filePath; // assigning the path
        try {
            clip = AudioSystem.getClip(); // getting the clip
        }
        catch (LineUnavailableException e) { // if the clip isn't there print error
            e.printStackTrace();
        }
        loadClip(); // loading the clip
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // declaring volume
        setVolume(volumeLevel); // setting volume
        madeSounds.add(this); // add the sound to the sound array list
    }
    private void loadClip(){ // method to load the clip
        try{
            inputStream = AudioSystem.getAudioInputStream(new File(filePath)); // get the clip at the file path
            clip.open(inputStream); // open the clip
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) { // otherwise catch the error
            System.out.println("Sound error");
            e.printStackTrace();
        }
    }
    public void play(){ // method to play the clip
        clip.setMicrosecondPosition(0); // setting the clip position
        clip.start(); // start the clip
    }
    public void resume(){
        clip.start();
    } // method to resume  the clip if it was stopped
    public void stop(){
        clip.stop();
    } // method to stop the clip
    public void closeSound(){ // method to close the clip
        clip.close(); // close the clip
        madeSounds.remove(this); // remove the sound from the array list
    }
    public boolean hasStarted(){
        return clip.isOpen();
    } // method to check if the clip is open
    public boolean isPlaying(){
        return clip.isActive();
    } // method to check if the clip is currently playing
    public void setVolume(int volumeLevel){ // method to set the volume of the clip
        float range = volume.getMaximum() - volume.getMinimum(); // getting the range between the min and max volume
        float gain = (float) (range * (volumeLevel/100.0)) + volume.getMinimum(); // getting the gain in volume
        volume.setValue(gain); // setting the volume gain
    }
}
