package UserInterface.Windows;

import Data.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * This abstract class represents a menu window which contains a frame, panel, title and buttons.
 * Can be used for creating windows such as shops, options etc.
 */
public abstract class Menu extends Window{

    protected JLabel title;
    protected HashSet<JButton> buttons = new HashSet<>();

    @Override
    void loadFrame(){
        frame = new JFrame();
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setSize(Configuration.getWindowWidth(),Configuration.getWindowWidth());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        moveToCenter();
        loadDefaultIconAndTitle();
    }

    /**
     * Creates the title and sets up the visuals. Each class extending Menu can implement this differently, but it has a default load as well.
     */
    void loadTitle(String name){
        title = new JLabel(name,SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 100));
        title.setForeground(Configuration.SECONDARY_UI_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        addComponent(title);
    }
    /**
     * Loads the different buttons with special purposes and sets up the visuals. Each class extending Menu can implement this differently.
     */
    abstract void loadButtons();

    /**
     * Loads all the Menu's components.
     */
    abstract void load();

    /**
     * Adds a JComponent to the menu's panel and creates a rigid area to space out the components.
     * @param component Any object extending JComponent.
     */
    void addComponent(JComponent component){
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(component);
    }

    /**
     * Adds a JButton to the Menu.
     * @param button The JButton added
     */
    void addButton(JButton button){
        addComponent(button);
        buttons.add(button);
    }


}
