package GameLogic;

import Data.Configuration;
import UserInterface.Windows.DeathWindow;
import UserInterface.Windows.Window;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents the snake the user controls.
 */
public class Snake {

    private Boolean alive = true;
    private final LinkedList<Square> structure = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private Boolean directionCooldown = false;
    private Boolean animationPlaying = false;

    /**
     * Sets the cooldown to true or false.
     * @param cooldown true/false
     */
    public void setDirectionCooldown(Boolean cooldown){
        directionCooldown = cooldown;
    }

    /**
     * Checks if the Snake is alive.
     * @return true/false
     */
    public Boolean isAlive(){
        return alive;
    }

    /**
     * Kills the snake.
     */
    public void kill(){
        alive = false;
    }

    /**
     * A getter for the LinkedList of all Squares the Snake occupies.
     * @return LinkedList of Squares
     */
    public LinkedList<Square> getStructure(){
        return structure;
    }

    /**
     * A getter for the Snake's Direction.
     * @return The Snake's Direction
     */
    public Direction getDirection(){
        return direction;
    }

    /**
     * Makes the Snake grow by one Square.
     * @param newHead The new head of the Snake
     */
    public void growSnake(Square newHead){
        structure.addFirst(newHead);
    }

    /**
     * Sets the Direction, but only if it's allowed (it can't change to the opposite of the current direction or the direction it already is).
     * @param direction The new Direction
     */
    public void setDirection(Direction direction){
        if(this.direction != direction && direction != Configuration.getOppositeDirection(this.direction) && !directionCooldown){
            this.direction = direction;
            directionCooldown = true;
        }
    }

    /**
     * Displays a death animation where one of the Snake's Squares disappears every period until it's completely gone.
     * A DeathWindow is created and opened and the GameWindow is closed.
     * @param frame The GameWindow's JFrame meant to close
     * @param menu The Window opened when the animation ends
     */

    public void deathAnimation(JFrame frame, Window menu){

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

                            frame.setVisible(false);
                            DeathWindow deathWindow = new DeathWindow(menu);
                            deathWindow.show();
                        }
                    } else {
                        timer.cancel();
                        timer.purge();
                    }
                }
            }, 100,100);
        }
    }


}