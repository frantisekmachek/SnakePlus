package UserInterface.Windows;

import Data.Configuration;
import UserInterface.Buttons.ExitButton;
import UserInterface.Buttons.PlayButton;
import UserInterface.Buttons.WindowButton;
import Utilities.DecalLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The MainMenu is what the player sees when they first boot up the game. Some other windows are also created here.
 */
public class MainMenu extends Menu {
    private final ShopWindow shopWindow = new ShopWindow(this);
    private final LevelWindow levelWindow = new LevelWindow(this);

    public MainMenu(){
        load();
    }

    @Override
    void loadPanel() {
        BufferedImage backgroundImage = DecalLoader.loadDecal("res\\Decals\\main_menu.png");
        if(backgroundImage != null){
            panel = new JPanel(){
                @Override
                public void paintComponent(Graphics g){
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.drawImage(backgroundImage, 0,0,Configuration.getWindowWidth(),Configuration.getWindowWidth(), null);
                }
            };
        } else {
            System.out.println("Main menu background image not found.");
            panel = new JPanel();
        }
        panel.setBackground(Configuration.PRIMARY_UI_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        frame.add(panel);
    }

    @Override
    void loadTitle(String name) {
        title = new JLabel(name,SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 100));
        title.setForeground(Configuration.SECONDARY_UI_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        addComponent(title);
    }

    @Override
    void loadButtons() {
        addButton(new PlayButton(this,this));
        addButton(new WindowButton(this,shopWindow,"SHOP"));
        addButton(new WindowButton(this,levelWindow,"LEVELS"));
        addButton(new ExitButton(this));
    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
        loadTitle("SNAKE+");
        loadButtons();
    }

}
