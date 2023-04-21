package Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * A class used for playing sound files.
 */
public class SoundPlayer {

    private Clip clip;

    /**
     * Attempts to find a sound file. If it can be found, and it's supported, it is played.
     * @param fileName Name of the file
     */
    public void playSound(String fileName)  {


    }

    //File file = new File(fileName);
    //
    //        if(file.exists()){
    //            try {
    //                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
    //
    //                if(audioStream != null){
    //                    this.clip = AudioSystem.getClip();
    //                    this.clip.open(audioStream);
    //                    this.clip.start();
    //                }
    //            } catch (UnsupportedAudioFileException e) {
    //                System.out.println("File called " + fileName + " isn't a supported audio file.");
    //                e.printStackTrace();
    //            } catch (IOException | LineUnavailableException e) {
    //                e.printStackTrace();
    //            }
    //
    //        } else {
    //            System.out.println(file.getName() + " doesn't exist.");
    //        }

    /**
     * Stops the Clip if it's playing.
     */
    public void stopSound(){
        if(clip != null){
            if(clip.isActive()){
                clip.stop();
            }
        }
    }

    /**
     * Loops the Clip continuously.
     */
    public void loopSound(){
        if(clip != null){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

}
