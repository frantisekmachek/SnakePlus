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
    private Game game;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private final Menu menu;
    private Grid grid;

    /**
     * A constructor taking in a Game instance (the Game this GameWindow is portraying) and a Menu instance (the Menu where the GameWindow was opened).
     * @param game the Game this GameWindow is portraying
     * @param menu the Menu where the GameWindow was opened
     */
    public GameWindow(Game game, Menu menu){

        this.game = game;
        this.menu = menu;

        load();
        open();
    }

    /**
     * A getter for the Menu where the GameWindow was opened.
     * @return Menu
     */
    public Window getMenu(){
        return menu;
    }

    /**
     * A getter for the Grid.
     * @return Grid
     */
    public JPanel getGrid(){
        return grid;
    }

    @Override
    void loadFrame() {
        frame = new JFrame();
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setSize(510,550);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(null);
        frame.addKeyListener(new MyKeyListener());
        moveToCenter();
        loadDefaultIconAndTitle();
    }

    @Override
    void loadPanel() {
        panel = new JPanel();
        panel.setBounds(0,0, frame.getWidth(),frame.getHeight());
        panel.setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        panel.setLayout(null);
        panel.setBackground(Configuration.SECONDARY_UI_COLOR);
        frame.add(panel);
        WindowButton stopButton = new WindowButton(this,menu,"STOP"){
            @Override
            public void actionPerformed(ActionEvent e){
                game.stopGame();
                game = null;
                closeAndOpen();
            }
        };
        stopButton.setBounds(355,5,150,35);
        stopButton.setFont(new Font("Arial", Font.BOLD, 30));
        panel.add(stopButton);

        scoreLabel = createScoreLabel(5);
        highScoreLabel = createScoreLabel(22);
        updateScoreLabel();
        updateHighScoreLabel();
    }

    /**
     * Loads the Grid where the squares are displayed.
     */
    private void loadGrid(){
        grid = new Grid();
        grid.setBounds(5,45, Configuration.getGridWidth(),Configuration.getGridWidth());
        grid.setBorder(null);
        grid.setLayout(null);
        grid.setVisible(true);
        panel.add(grid);

    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
        loadGrid();
    }

    /**
     * Creates a label which shows scores or highscores.
     * @param yCoordinate The y-axis coordinate of the label
     * @return A JLabel (the new score label)
     */
    public JLabel createScoreLabel(int yCoordinate){
        JLabel label = new JLabel();
        label.setBounds(5,yCoordinate,200,17);
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setForeground(Configuration.PRIMARY_UI_COLOR);
        panel.add(label);
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
    public class Grid extends JPanel implements DrawLines{
        private final HashSet<Square> walls = game.getWalls();
        private final Snake snake = game.getSnake();
        private final BufferedImage up, down, left, right;

        public Grid(){
            String colorName = Configuration.getUser().getChosenItem().getName();

            up = DecalLoader.loadDecal("res\\Decals\\" + colorName + "_snake_up.png");
            down = DecalLoader.loadDecal("res\\Decals\\" + colorName + "_snake_down.png");
            left = DecalLoader.loadDecal("res\\Decals\\" + colorName + "_snake_left.png");
            right = DecalLoader.loadDecal("res\\Decals\\" + colorName + "_snake_right.png");
        }

        /**
         * An overridden paintComponent() method which draws everything on the Grid. It calls all the draw methods inside the GameWindow class.
         * @param g the <code>Graphics</code> object to protect
         */
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Configuration.PRIMARY_UI_COLOR);
            g.fillRect(0,0,500,500);

            drawLines(g, Configuration.BORDER_COLOR);
            drawSnakeSquares(g);
            drawWalls(g);
            drawHead(g);
            drawApple(g);

        }

        /**
         * Draws the Snake's body except for its head. That is handled by the drawHead() method.
         * @param g An instance of Graphics used to paint on a Grid
         */
        private void drawSnakeSquares(Graphics g){
            for (int i = 1; i < snake.getStructure().size(); i++) {
                Square square = snake.getStructure().get(i);
                square.drawSnake(g);
            }
        }

        /**
         * Draws an apple if there is one.
         * @param g An instance of Graphics used to paint on a Grid
         */
        private void drawApple(Graphics g){
            Square apple = game.getApple();
            if(apple != null){
                apple.drawApple(g);
            }
        }

        /**
         * Draws the walls of a Level.
         * @param g An instance of Graphics used to paint on a Grid
         */
        private void drawWalls(Graphics g){
            for (Square square : walls) {
                square.drawEmpty(g);
            }
        }

        /**
         * Draws the Snake's head depending on its Direction.
         * @param g An instance of Graphics used to paint on a Grid
         */
        private void drawHead(Graphics g){
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

    }
}
