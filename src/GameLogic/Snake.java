package GameLogic;

import Data.Configuration;
import UserInterface.Windows.DeathWindow;
import UserInterface.Windows.Window;
import Utilities.SoundPlayer;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents the snake the user controls.
 */
public class Snake {

    private Boolean alive = true;
    private LinkedList<Square> structure = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private Boolean directionCooldown = false;
    private Boolean animationPlaying = false;

    public void setDirectionCooldown(Boolean cooldown){
        directionCooldown = cooldown;
    }

    public Boolean isAlive(){
        return alive;
    }
    public void kill(){
        System.out.println("The snake has died.");
        alive = false;
    }
    public LinkedList<Square> getStructure(){
        return structure;
    }

    public void growSnake(Square newHead){
        structure.addFirst(newHead);
    }

    public void setDirection(Direction direction){
        if(this.direction != direction && direction != Configuration.getOppositeDirection(this.direction) && !directionCooldown){
            this.direction = direction;
            directionCooldown = true;
        }
    }
    public Direction getDirection(){
        return direction;
    }

    /**
     * Displays a death animation where one of the Snake's Squares disappears every period until it's completely gone.
     * Also plays a sound. A DeathWindow is created and opened, the GameWindow is closed.
     * @param frame The GameWindow's JFrame meant to close
     * @param menu The Window (MainMenu) meant to open
     */

    public void deathAnimation(JFrame frame, Window menu){

        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("Sounds\\death_sound.wav");
        soundPlayer.loopSound();

        if(!animationPlaying){
            animationPlaying = true;
            Timer timer = new Timer();

            timer.schedule(new TimerTask(){
                @Override
                public void run() {
                    if(frame.isVisible()){
                        structure.removeLast();
                        frame.repaint();

                        if(structure.size() == 0){
                            timer.cancel();
                            timer.purge();
                            soundPlayer.stopSound();

                            frame.setVisible(false);
                            DeathWindow deathWindow = new DeathWindow(menu);
                            deathWindow.show();
                        }
                    } else {
                        timer.cancel();
                        timer.purge();
                        soundPlayer.stopSound();
                    }
                }
            }, 100,100);
        }
    }


}