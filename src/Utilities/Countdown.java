package Utilities;

import GameLogic.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Starts a countdown of 3 seconds to give the player a bit of time before starting.
 */
public class Countdown {

    private JLabel label;
    Timer timer = new Timer();

    /**
     * Starts the countdown in a Game instance.
     * @param game
     */
    public void start(Game game){

        timer.schedule(new TimerTask() {
            SoundPlayer soundPlayer = new SoundPlayer();
            int i = 4;
            @Override
            public void run() {
                    i--;
                    label.setText(Integer.toString(i));
                    if(i == 0){
                        label.setText("GO!");
                        game.startGame();
                        game.getMusicPlayer().playSound("Sounds\\music.wav");
                        game.getMusicPlayer().loopSound();
                    } else if (i == -1){
                        label.setText(null);
                        stop();
                    } else if (i > 0 && i < 4){
                        soundPlayer.playSound("Sounds\\tick_sound.wav");
                    }
            }
        }, 2000,1000);
    }

    /**
     * Sets up a JLabel displaying the countdown timer.
     */
    public JLabel setupLabel(){
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 250));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBounds(0,0,500,500);

        this.label = label;

        return label;
    }

    /**
     * Stops the countdown timer.
     */
    public void stop(){
        timer.cancel();
        timer.purge();
    }

}
