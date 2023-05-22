package GameLogic;

import Data.Configuration;
import Data.User;
import UserInterface.Windows.GameWindow;
import UserInterface.Windows.Menu;
import UserInterface.Windows.WinWindow;
import Utilities.Countdown;
import Utilities.GameGenerator;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

/**
 * This class handles the game logic and functionality.
 */
public class Game {

    private final HashSet<Square> walls;
    private final Square[][] squares;
    private final Snake snake;
    private User user;
    private GameWindow gameWindow;
    private int score;
    private Square apple;
    private final Level level;
    private final Timer timer = new Timer();
    private Boolean playing = false;
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

    /**
     * A getter for the current score.
     * @return the current score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * A getter for the Level's walls.
     * @return a HashSet of Squares
     */
    public HashSet<Square> getWalls() {
        return walls;
    }

    /**
     * A getter for the snake.
     * @return a Snake instance
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * A getter for the apple.
     * @return a Square instance
     */
    public Square getApple(){
        return apple;
    }

    /**
     * A getter for the current Level.
     * @return the current Level
     */
    public Level getLevel(){
        return level;
    }

    /**
     * Checks if the game is being played or not.
     * @return true/false
     */
    public Boolean isPlaying(){
        return playing;
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
        user.saveData();
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
    public void moveSnake(Snake snake){

        LinkedList<Square> tempSnake = new LinkedList<>(snake.getStructure());

        if(moveSnakeByOneSquare(snake)){
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
    public Boolean moveSnakeByOneSquare(Snake snake) {
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
                } else {
                    update();
                }
            }
        }, Configuration.getMovePeriod(), Configuration.getMovePeriod());
    }

    /**
     * Updates the game (one movement of the Snake happens).
     */
    public void update(){
        if(playing){
            moveSnake(snake);
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

        if((snake.getStructure().size() + walls.size()) != squares.length * squares.length){

            ArrayList<Square> availableSquares = getAvailableSquares();
            Random random = new Random();

            int index = random.nextInt(availableSquares.size());
            apple = availableSquares.get(index);

        } else {
            apple = null;
        }
        return apple;
    }

    /**
     * Finds all the empty Squares in the squares array.
     * @return an ArrayList of empty Squares
     */
    public ArrayList<Square> getAvailableSquares(){
        ArrayList<Square> list = new ArrayList<>();
        for(Square[] squareArray : squares){
            list.addAll(Arrays.asList(squareArray));
        }
        list.removeAll(walls);
        list.removeAll(snake.getStructure());

        return list;
    }

    /**
     * The Snake grows by one Square. The User gets an amount of credits based on the amount of walls and the Snake's size. A new apple is spawned.
     */
    public void eatApple(){
        snake.growSnake(apple);
        addScore();
        spawnApple();

        int creditMultiplier = (walls.size()/20);
        int credit;
        if(creditMultiplier == 0){
            credit = 1 + (snake.getStructure().size()/10);
        } else {
            credit = 1 + (snake.getStructure().size()/10) * creditMultiplier;
        }
        user.addCredits(credit);
        System.out.println("The user has gained " + credit + " credits.");
    }

    /**
     * Calculates if all Squares are occupied. If so, the player wins.
     * @return Boolean (true if the player has won, false if not)
     */
    public Boolean checkIfPlayerWon(LinkedList<Square> structure, HashSet<Square> walls, Square[][] squares){
        return (structure.size() + walls.size()) == squares.length * squares.length;
    }

    /**
     * Adds one score and updates the score label to match it. If the score is higher than the User's high score, the high score is updated.
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
