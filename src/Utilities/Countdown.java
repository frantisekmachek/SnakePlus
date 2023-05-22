package Utilities;

import Data.Configuration;
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
    private final Timer timer = new Timer();

    /**
     * Starts the countdown in a Game instance.
     * @param game An instance of Game
     */
    public void start(Game game){

        timer.schedule(new TimerTask() {
            int i = 4;
            @Override
            public void run() {
                    i--;
                    label.setText(Integer.toString(i));
                    if(i == 0){
                        label.setText("GO!");
                        game.startGame();
                    } else if (i == -1){
                        label.setText(null);
                        stop();
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
        label.setForeground(Configuration.BORDER_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBounds(0,0, Configuration.getGridWidth(),Configuration.getGridWidth());

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
