package UserInterface.Windows;

import Data.Configuration;
import GameLogic.Direction;
import GameLogic.Game;
import GameLogic.Snake;
import GameLogic.Square;
import UserInterface.Buttons.WindowButton;
import Utilities.DecalLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import static java.awt.event.KeyEvent.*;

/**
 * Displays everything happening in the game. It is dependent on a Game and Menu instance, Game being the game displayed and Menu being the Menu opened when stopping the game.
 */
public class GameWindow extends Window{
    private final Game game;
    private JPanel sidePanel;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private final Menu menu;

    public GameWindow(Game game, Menu menu){

        this.game = game;
        this.menu = menu;

        load();

        frame.setVisible(true);
    }

    public Window getMenu(){
        return menu;
    }
    public JPanel getGrid(){
        return panel;
    }

    @Override
    void loadFrame() {
        frame = new JFrame();
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setSize(950,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(null);
        frame.addKeyListener(new MyKeyListener());
        moveToCenter();
        loadDefaultIconAndTitle();
    }

    @Override
    void loadPanel() {
        panel = new Grid();
        panel.setBounds(50,50, Configuration.getGridWidth(),Configuration.getGridWidth());
        panel.setBorder(null);
        panel.setLayout(null);
        panel.setVisible(true);
        frame.add(panel);
    }

    /**
     * Loads a side panel where the score and highscore is shown. There is also a "STOP" button. Clicking on it stops the game and returns the user to the menu.
     * @param menu The Menu opened upon clicking on the stop button
     */
    public void loadSidePanel(Menu menu){
        sidePanel = new JPanel();
        sidePanel.setBounds(600,50,300,500);
        sidePanel.setLayout(null);
        sidePanel.setBackground(Configuration.PRIMARY_UI_COLOR);
        frame.add(sidePanel);

        WindowButton stopButton = new WindowButton(this,menu,"STOP"){
            @Override
            public void actionPerformed(ActionEvent e){
                game.stopGame();
                closeAndOpen();
                playSound();
            }
        };
        stopButton.setBounds(20,420,sidePanel.getWidth() - 40,60);
        sidePanel.add(stopButton);

        scoreLabel = createScoreLabel(20);
        highScoreLabel = createScoreLabel(60);
        updateScoreLabel();
        updateHighScoreLabel();
    }
    @Override
    void load() {
        loadFrame();
        loadPanel();
        loadSidePanel(menu);
    }

    /**
     * Creates a label which shows scores or highscores.
     * @param yCoordinate The y-axis coordinate of the label
     * @return A JLabel (the new score label)
     */
    public JLabel createScoreLabel(int yCoordinate){
        JLabel label = new JLabel();
        label.setBounds(20,yCoordinate,sidePanel.getWidth() - 40,60);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Configuration.SECONDARY_UI_COLOR);
        sidePanel.add(label);
        return label;
    }

    /**
     * Updates the score label, setting its text to the current score.
     */
    public void updateScoreLabel(){
        scoreLabel.setText("SCORE: " + game.getScore());
    }
    /**
     * Updates the high score label, setting its text to the current high score.
     */
    public void updateHighScoreLabel(){
        highScoreLabel.setText("HIGHSCORE: " + Configuration.getUser().getLevelHighScore(game.getLevel().getNumber()));
    }

    /**
     * A KeyAdapter made for GameWindow. It allows us to get the user's keyboard input and control the Snake.
     */
    private class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent evt) {
            Snake snake = game.getSnake();
            if(game.isPlaying()){
                if (evt.getKeyChar() == 'w' | evt.getKeyChar() == VK_UP) {
                    snake.setDirection(Direction.UP);
                } else if (evt.getKeyChar() == 's' | evt.getKeyChar() == VK_DOWN){
                    snake.setDirection(Direction.DOWN);
                } else if (evt.getKeyChar() == 'a'| evt.getKeyChar() == VK_LEFT) {
                    snake.setDirection(Direction.LEFT);
                } else if (evt.getKeyChar() == 'd'| evt.getKeyChar() == VK_RIGHT) {
                    snake.setDirection(Direction.RIGHT);
                }
            }
        }
    }

    /**
     * A grid on the GameWindow where the Squares are drawn.
     */
    public class Grid extends JPanel {
        private final HashSet<Square> walls = game.getWalls();
        private final Snake snake = game.getSnake();
        private final BufferedImage up, down, left, right;

        public Grid(){
            String colorName = Configuration.getUser().getChosenItem().getName();

            up = DecalLoader.loadDecal("Decals\\" + colorName + "_snake_up.png");
            down = DecalLoader.loadDecal("Decals\\" + colorName + "_snake_down.png");
            left = DecalLoader.loadDecal("Decals\\" + colorName + "_snake_left.png");
            right = DecalLoader.loadDecal("Decals\\" + colorName + "_snake_right.png");
        }

        /**
         * An overridden paintComponent() method which draws everything on the Grid. It calls all the draw methods inside the GameWindow class.
         * @param g the <code>Graphics</code> object to protect
         */
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            g.fillRect(0,0,500,500);

            drawLines(g);
            drawSnakeSquares(g);
            drawWalls(g);
            drawHead(g);

            drawApple(g);

        }

        /**
         * Draws the Snake's body except for its head. That is handled by the drawHead() method.
         * @param g An instance of Graphics used to paint on a Grid
         */
        public void drawSnakeSquares(Graphics g){
            for (int i = 1; i < snake.getStructure().size(); i++) {
                Square square = snake.getStructure().get(i);
                square.drawSnake(g);
            }
        }

        /**
         * Draws an apple if there is one.
         * @param g An instance of Graphics used to paint on a Grid
         */
        public void drawApple(Graphics g){
            Square apple = game.getApple();
            if(apple != null){
                apple.drawApple(g);
            }
        }

        /**
         * Draws the walls of a Level.
         * @param g An instance of Graphics used to paint on a Grid
         */
        public void drawWalls(Graphics g){
            for (Square square : walls) {
                square.drawEmpty(g);
            }
        }

        /**
         * Draws the Snake's head depending on its Direction.
         * @param g An instance of Graphics used to paint on a Grid
         */
        public void drawHead(Graphics g){
            if(snake.getStructure().size() > 0){
                Square head = snake.getStructure().getFirst();
                Direction direction = snake.getDirection();
                BufferedImage image = null;
                switch(direction){
                    case UP -> image = up;
                    case DOWN -> image = down;
                    case LEFT -> image = left;
                    case RIGHT -> image = right;
                }
                head.drawHead(g, image);
            }
        }

        /**
         * Draws lines on the Grid to paint squares.
         * @param g An instance of Graphics used to paint on a Grid
         */
        public void drawLines(Graphics g){
            for(int xCoordinate = 0; xCoordinate < Configuration.getRowsAndColumns(); xCoordinate++){
                for(int yCoordinate = 0; yCoordinate < Configuration.getRowsAndColumns(); yCoordinate++){
                    g.setColor(Configuration.SECONDARY_UI_COLOR);
                    g.drawLine(xCoordinate*Configuration.getSquareSize(),0,xCoordinate*Configuration.getSquareSize(),500);
                    g.drawLine(0,yCoordinate*Configuration.getSquareSize(),500,yCoordinate*Configuration.getSquareSize());
                }
            }
        }

    }
}
