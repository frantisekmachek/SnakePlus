package UserInterface.Windows;

import Data.Configuration;
import UserInterface.Buttons.Button;
import UserInterface.Buttons.WindowButton;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The Window where Levels can be created.
 */
public class LevelCreationWindow extends Window{
    private final Menu menu;
    private CreatorGrid grid;

    public LevelCreationWindow(Menu menu){
        this.menu = menu;
        load();
    }

    @Override
    void loadFrame() {
        frame = new JFrame();
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setSize(700,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(null);
        moveToCenter();
        loadDefaultIconAndTitle();
    }

    @Override
    void loadPanel() {
        panel = new JPanel();
        panel.setBounds(0,0,700,500);
        panel.setBackground(Configuration.PRIMARY_UI_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        panel.setLayout(null);
        frame.add(panel);

        loadGrid();

        Button saveButton = new Button() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.openFileChooser();
            }
        };
        saveButton.load("SAVE");
        saveButton.setBounds(510,440,180,50);
        panel.add(saveButton);

        WindowButton menuButton = new WindowButton(this, menu, "EXIT");
        menuButton.setBounds(510,380,180,50);
        panel.add(menuButton);

        Button clearButton = new Button(){
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.getSelectedSquares().clear();
                panel.repaint();
            }
        };
        clearButton.load("CLEAR");
        clearButton.setBounds(510,320,180,50);
        panel.add(clearButton);

    }

    /**
     * Loads the grid of squares.
     */
    private void loadGrid(){
        grid = new CreatorGrid(panel);
        panel.add(grid);
    }

}
