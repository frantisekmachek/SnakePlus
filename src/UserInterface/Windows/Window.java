package UserInterface.Windows;

import Utilities.DecalLoader;

import javax.swing.*;
import java.awt.*;

/**
 * A window consisting of a JFrame and JPanel.
 */
public abstract class Window {

    protected JFrame frame;
    protected JPanel panel;

    /**
     * Creates the frame and sets up its visuals. Each class extending Menu can implement this differently.
     */
    abstract void loadFrame();

    /**
     * Creates the panel and sets up its visuals. Each class extending Menu can implement this differently.
     */
    abstract void loadPanel();

    /**
     * Moves the frame to the center of the screen.
     */
    void moveToCenter(){
        Toolkit toolKit = frame.getToolkit();
        Dimension size = toolKit.getScreenSize();
        frame.setLocation(size.width/2 - frame.getWidth()/2, (int)(size.getHeight()/2 - frame.getHeight()/2));
    }

    /**
     * This method should call all the load methods such as loadFrame() and loadPanel(). Each class extending Window can implement this in its own way.
     */
    void load(){
        loadFrame();
        loadPanel();
    }

    /**
     * @return The main frame.
     */
    public JFrame getFrame(){
        return this.frame;
    }

    /**
     * Opens the Window's JFrame.
     */
    public void open(){
        frame.setVisible(true);
    }

    /**
     * Closes the Window's JFrame.
     */
    public void close(){
        frame.dispose();
    }

    /**
     * Loads the default icon and title of the JFrame.
     */
    public void loadDefaultIconAndTitle(){
        Image image = DecalLoader.loadDecal("res\\Decals\\icon.png");
        frame.setIconImage(image);
        frame.setTitle("Snake+");
    }
}
