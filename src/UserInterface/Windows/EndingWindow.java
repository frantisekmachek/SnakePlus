package UserInterface.Windows;

import Data.Configuration;
import UserInterface.Buttons.WindowButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public abstract class EndingWindow extends Window{
    protected Timer timer = new Timer();
    protected WindowButton exitButton;
    protected Window menu;

    public EndingWindow(Window menu){
        this.menu = menu;
        load();
        loadExitButton();
    }

    @Override
    void loadFrame() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setSize(Configuration.getWindowWidth(),Configuration.getWindowWidth());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        moveToCenter();
        loadDefaultIconAndTitle();
    }

    public void openMenu(){
        this.frame.setVisible(false);
        menu.open();
    }
    public void show(){
        frame.setVisible(true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(frame.isVisible()){
                    openMenu();
                }
            }
        }, 5000);
    }

    public void loadExitButton(){
        exitButton = new WindowButton(this, menu, "EXIT");
        panel.add(exitButton);
        exitButton.setBounds(200,450, 200,75);
    }

    public void drawScreen(BufferedImage image, Graphics g){
        if(image != null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(image,0,0, Configuration.getWindowWidth(), Configuration.getWindowWidth(), null);
        }
    }
}
