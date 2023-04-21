package GameLogic;

import Data.Configuration;
import Data.User;
import UserInterface.Windows.GameWindow;
import UserInterface.Windows.Menu;
import UserInterface.Windows.WinWindow;
import Utilities.Countdown;
import Utilities.GameGenerator;
import Utilities.SoundPlayer;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

/**
 * This class handles the game logic and functionality.
 */
public class Game {

    /**
     * A HashSet of Squares, representing the walls included in a Level.
     */
    private final HashSet<Square> walls;
    /**
     * An array of Squares, contains all the Squares on the Grid.
     */
    private final Square[][] squares;
    /**
     * The Snake the player controls.
     */
    private final Snake snake;
    /**
     * The User playing the game. Used for score.
     */
    private User user;
    /**
     * The GameWindow displaying what's happening in the game.
     */
    private GameWindow gameWindow;
    /**
     * Score are points the player acquires through collecting apples. One apple = 1 score.
     */
    private int score;
    /**
     * One Square assigned to be an apple. Can be any apple inside the squares array.
     */
    private Square apple;
    /**
     * The Level chosen by the player. If it's the default game with no walls, it is set to Level 0.
     */
    private Level level;
    /**
     * A Timer instance used for timing the update sequence.
     */
    private Timer timer = new Timer();
    /**
     * A Boolean which is true if the game is still running and false if not.
     */
    private Boolean playing = false;
    /**
     * A SoundPlayer instance used for playing music when the game starts.
     */
    private final SoundPlayer musicPlayer = new SoundPlayer();
    /**
     * An instance of Countdown which is used at the beginning of the game to give the player
     * a bit of time before starting.
     */
    private final Countdown countdown = new Countdown();

    /**
     * A constructor for the Game class which loads all essential objects. It creates a GameWindow and starts a Cooldown.
     * @param levelNumber The number of the Level
     * @param menu The Menu the player will return to after stopping the game or dying
     */
    public Game(int levelNumber, Menu menu){
        this.level = new Level(levelNumber);
        this.user = Configuration.getUser();

        this.walls = level.getWalls();
        this.squares = GameGenerator.generateSquares();
        this.snake = GameGenerator.generateSnake(squares);

        gameWindow = new GameWindow(this, menu);

        JLabel countdownLabel = countdown.setupLabel();
        gameWindow.getGrid().add(countdownLabel);
        countdown.start(this);
    }


    /**
     * A constructor for testing purposes.
     */
    public Game(){
        this.level = new Level(0);

        this.walls = level.getWalls();
        this.squares = GameGenerator.generateSquares();
        this.snake = GameGenerator.generateSnake(squares);
    }

    public int getScore(){
        return this.score;
    }
    public HashSet<Square> getWalls() {
        return walls;
    }
    public Snake getSnake() {
        return snake;
    }
    public Square getApple(){
        return apple;
    }
    public Level getLevel(){
        return level;
    }
    public Boolean isPlaying(){
        return playing;
    }
    public SoundPlayer getMusicPlayer(){
        return musicPlayer;
    }

    /**
     * Stops the game and the countdown if it's running. The Snake dies, but no death animation plays.
     */
    public void stopGame(){
        stop();
        snake.kill();
    }

    /**
     * Stops the game and the countdown if it's running. The snake dies and a death animation plays.
     */
    public void endGame(){
        stop();
        if(snake.isAlive()){
            snake.kill();
            if(gameWindow != null){
                snake.deathAnimation(gameWindow.getFrame(), gameWindow.getMenu());
            }
        }
    }

    /**
     * Stops all ongoing processes inside the Game instance.
     */
    public void stop(){
        countdown.stop();
        playing = false;
        stopTimer();
        musicPlayer.stopSound();
    }

    /**
     * When the player wins, the game is stopped and a WinWindow is opened.
     */
    public void win(){
        stop();
        gameWindow.close();
        WinWindow winWindow = new WinWindow(gameWindow.getMenu());
        winWindow.show();
    }

    /**
     * Checks if the next Square in the Snake's Direction is out of bounds or not. It also checks if it's a wall or the
     * snake itself, in that case, the Snake dies. However, it also checks if the next Square happens to be
     * the Snake's tail. Tail collision can be ignored, because it will move out of the way and the head will replace it.
     * @param x The x-axis coordinate of the next Square
     * @param y The y-axis coordinate of the next Square
     * @param squares The Square 2D array
     * @param structure The Snake's LinkedList
     * @param walls The Level's HashSet of Squares
     * @return A Boolean, either true (collided) or false (didn't collide)
     */
    public Boolean checkCollision(int x, int y, Square[][] squares, LinkedList<Square> structure, HashSet<Square> walls){

        if(checkXCoordinate(x, squares.length) && checkYCoordinate(y, squares.length)){
            Square square = squares[x][y];

            if(structure.contains(square) | walls.contains(square)) {
                if(checkCollisionWithTail(square)){
                    return false;
                } else {
                    endGame();
                    return true;
                }
            } else {
                return false;
            }

        } else {
            endGame();
            return true;
        }
    }

    /**
     * Checks if the Snake is about to collide with its tail. This collision can be ignored, because the
     * head will replace it.
     * @param square The checked Square
     * @return A Boolean, true = collided with the tail, false = the Square isn't the tail
     */
    public Boolean checkCollisionWithTail(Square square){
        // We can ignore collision on the tail because the tail is going to move out of the way anyway
        return square.equals(snake.getStructure().getLast());
    }

    /**
     * Checks if an x-axis coordinate is out of bounds.
     * @param x The x-axis coordinate
     * @param arrayLength The length of the array
     * @return A boolean, true = valid, false = out of bounds
     */
    public static Boolean checkXCoordinate(int x, int arrayLength){
        return x >= 0 && x < arrayLength;
    }

    /**
     * Checks if a y-axis coordinate is out of bounds.
     * @param y The y-axis coordinate
     * @param arrayLength The length of the array
     * @return A boolean, true = valid, false = out of bounds
     */
    public static Boolean checkYCoordinate(int y, int arrayLength){
        return y >= 0 && y < arrayLength;
    }

    /**
     * Moves the Snake. Creates a temporary Snake to help with moving every Square by one.
     * First it checks if the Snake can move by one Square. If it did, all the other Squares move as well.
     */
    public void moveSnake(){

        LinkedList<Square> tempSnake = new LinkedList<>(snake.getStructure());

        if(moveSnakeByOneSquare()){
            for(int i = 0; i < snake.getStructure().size(); i++){
                if(i + 1 < snake.getStructure().size()){
                    Square square = tempSnake.get(i);
                    snake.getStructure().set(i + 1, square);
                }
            }
        }
    }

    /**
     * Moves the Snake's head by one Square, depending on its Direction. Each Direction has its own offset (UP - one Square up, so yOffset is -1).
     * If the Square is empty and the Snake can move there, it becomes the new head - the head has been
     * moved by one Square. If the Square happens to be the apple, the Snake doesn't move and instead grows
     * where the apple used to be.
     * @return A Boolean, true = has moved, false = hasn't moved
     */
    public Boolean moveSnakeByOneSquare() {
        Square head = snake.getStructure().getFirst();
        int xOffset = 0;
        int yOffset = 0;

        switch(snake.getDirection()){
            case UP -> yOffset = -1;
            case DOWN -> yOffset = 1;
            case LEFT -> xOffset = -1;
            case RIGHT -> xOffset = 1;
        }
        if(!checkCollision(head.getxCoordinate()+xOffset,head.getyCoordinate()+yOffset, squares, snake.getStructure(), walls)){
            Square square = squares[head.getxCoordinate() + xOffset][head.getyCoordinate() + yOffset];
            if(square.equals(apple)){
                eatApple();
                return false;
            } else {
                snake.getStructure().set(0, square);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Starts the game and runs while the user is playing. Once every period, the Snake moves and
     * the GameWindow repaints.
     */
    public void startGame(){
        playing = true;
        apple = spawnApple();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(checkIfPlayerWon(snake.getStructure(), walls, squares)){
                    win();
                }
                update();
            }
        }, Configuration.getMovePeriod(), Configuration.getMovePeriod());
    }

    /**
     * Updates the game (one movement of the Snake happens).
     */
    public void update(){
        if(playing){
            moveSnake();
            snake.setDirectionCooldown(false);
            gameWindow.getFrame().repaint();
        }
    }

    /**
     * Stops the Game's Timer.
     */
    public void stopTimer(){
        timer.cancel();
        timer.purge();
    }

    /**
     * Spawns an apple on an unoccupied Square.
     */
    public Square spawnApple(){
        boolean appleChanged = false;
        Square apple = null;

        Random random = new Random();
        while(!appleChanged){
            int randomX = random.nextInt(squares.length);
            int randomY = random.nextInt(squares.length);
            Square square = squares[randomX][randomY];

            if(!snake.getStructure().contains(square) && !walls.contains(square)){
                apple = squares[randomX][randomY];
                appleChanged = true;
            }
        }

        return apple;
    }

    /**
     * When the apple is "eaten", the Snake grows by one Square, the User gets 1 credit and 1 score is added. A new apple is spawned.
     */
    public void eatApple(){

        SoundPlayer appleBitePlayer = new SoundPlayer();
        appleBitePlayer.playSound("Sounds\\bite.wav");

        snake.growSnake(apple);
        user.addCredits(1);
        addScore();

        if(!checkIfPlayerWon(snake.getStructure(), walls, squares)){
            apple = spawnApple();
        } else {
            apple = null;
        }
    }

    /**
     * Calculates if all Squares are occupied. If so, the player wins.
     * @return Boolean (true if the player has won, false if not)
     */
    public Boolean checkIfPlayerWon(LinkedList<Square> structure, HashSet<Square> walls, Square[][] squares){
        return (structure.size() + walls.size()) == squares.length * squares.length;
    }

    /**
     * Adds one score and updates the score label to match it. If the score is higher than the User's highscore, the highscore is updated.
     */
    public void addScore(){
        score++;
        gameWindow.updateScoreLabel();
        int highscore = user.getLevelHighScore(level.getNumber());
        if(score > highscore){
            highscore = score;
            user.setLevelHighScore(level.getNumber(), highscore);
            gameWindow.updateHighScoreLabel();
        }
    }
}
