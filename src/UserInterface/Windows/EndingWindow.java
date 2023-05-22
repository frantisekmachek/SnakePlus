package UserInterface.Windows;

import Data.Configuration;
import UserInterface.Buttons.WindowButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Window shown when the game ends.
 */
public abstract class EndingWindow extends Window{
    protected Timer timer = new Timer();
    protected WindowButton exitButton;
    protected Window menu;
    protected BufferedImage background;

    /**
     * A constructor for the EndingWindow class. Takes in a Window instance (the Window opened when closing this one) and a BufferedImage (the background). The elements are loaded.
     * @param menu the Window opened when closing the ending window
     * @param background the background image (BufferedImage)
     */
    public EndingWindow(Window menu, BufferedImage background){
        this.menu = menu;
        this.background = background;
        load();
        loadExitButton();
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
                drawScreen(background, g);
            }
        };
        panel.setBackground(Configuration.PRIMARY_UI_COLOR);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        frame.add(panel);

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

    /**
     * Closes the ending window and opens the menu where the Level was opened.
     */
    public void openMenu(){
        close();
        menu.open();
    }

    /**
     * Opens the ending window. It's closed after 5 seconds if it's still open.
     */
    public void show(){
        open();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(frame.isVisible()){
                    openMenu();
                }
            }
        }, 5000);
    }

    /**
     * Loads the exit button on the frame which takes you to the menu.
     */
    public void loadExitButton(){
        exitButton = new WindowButton(this, menu, "EXIT");
        panel.add(exitButton);
        exitButton.setBounds(200,450, 200,75);
    }

    /**
     * Draws the background image of the ending window.
     * @param image The background image
     * @param g An instance of Graphics
     */
    public void drawScreen(BufferedImage image, Graphics g){
        if(image != null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(image,0,0, Configuration.getWindowWidth(), Configuration.getWindowWidth(), null);
        }
    }
}
