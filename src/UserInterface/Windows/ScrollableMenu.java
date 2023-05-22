package UserInterface.Windows;

import Data.Configuration;
import Utilities.DecalLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ScrollableMenu extends Menu{

    protected final Menu menu;
    protected BufferedImage backgroundImage;

    public ScrollableMenu(Menu menu, String backgroundFileName){
        this.menu = menu;
        backgroundImage = DecalLoader.loadDecal(backgroundFileName);
    }

    @Override
    void loadPanel() {
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                loadBackground(g);
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);
    }

    /**
     * Loads the background which scales with the frame. The frame is scrollable, so the image takes up the whole background.
     * @param g An instance of Graphics
     */
    public void loadBackground(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        for(int i = 0; i < panel.getHeight(); i = i + Configuration.getWindowWidth()){ // Draws the background image as many times as needed with scaling height
            if(backgroundImage != null){
                g2d.drawImage(backgroundImage, 0,i, Configuration.getWindowWidth(),Configuration.getWindowWidth(), null);
            } else {
                g2d.setColor(Configuration.PRIMARY_UI_COLOR);
                g2d.fillRect(0,0,panel.getWidth(), panel.getHeight());
            }
        }
    }

    /**
     * Loads the ScrollPane for the panel.
     */
    public void loadScrollPane(){
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane);
    }

}
