package UserInterface.Buttons;

import Data.Configuration;
import Utilities.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Button extends JButton implements ActionListener {

    protected Font font = new Font("Arial", Font.BOLD, 40);
    /**
     * Sets the button's properties such as Font, Text, Background etc.
     * @param text describes which window the button will open. Example: "SHOP"
     */
    public void load(String text){
        setText(text);
        setFont(font);
        setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));

        setForeground(Configuration.PRIMARY_UI_COLOR);
        setBackground(Configuration.SECONDARY_UI_COLOR);

        setFocusPainted(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension size = new Dimension(300,80);
        setPreferredSize(size);
        setMaximumSize(size);
        setSize(size);

        addActionListener(this);
    }

    public abstract void actionPerformed(ActionEvent e);

    public void playSound(){
        final SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("Sounds\\click.wav");
    }
}
