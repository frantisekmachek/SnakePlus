package UserInterface.Windows;

import Data.Configuration;
import Utilities.DecalLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Window displayed when the Snake dies.
 */
public class DeathWindow extends EndingWindow{
    public DeathWindow(Window menu){
        super(menu);
    }

    @Override
    void loadPanel() {
        panel = new JPanel(){
            /**
             * An overridden paintComponent() method which draws an image on the JPanel if it can be found.
             * @param g the <code>Graphics</code> object to protect
             */
            @Override
            public void paintComponent(Graphics g){
                BufferedImage deathScreen = DecalLoader.loadDecal("Decals\\death_screen.png");
                drawScreen(deathScreen, g);
            }
        };
        panel.setBackground(Configuration.PRIMARY_UI_COLOR);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        frame.add(panel);

    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
    }
}
