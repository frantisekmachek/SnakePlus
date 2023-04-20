package UserInterface.Windows;

import Data.Configuration;
import Utilities.DecalLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ScrollableMenu extends Menu{

    protected final Menu menu;
    protected final String backgroundFileName;

    public ScrollableMenu(Menu menu, String backgroundFileName){
        this.menu = menu;
        this.backgroundFileName = backgroundFileName;
    }

    @Override
    void loadPanel() {
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                loadBackground(g, backgroundFileName);
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);
    }

    public void loadBackground(Graphics g, String fileName){
        BufferedImage backgroundImage = DecalLoader.loadDecal(fileName);
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

    public void loadScrollPane(){
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane);
    }

}
