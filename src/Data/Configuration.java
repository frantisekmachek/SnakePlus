package Data;

import GameLogic.Direction;
import Utilities.Serializer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Contains some settings used in the game such as window width and the User.
 */
public abstract class Configuration {
    private final static User USER = loadUser();
    private final static int WINDOW_WIDTH = 600;
    private final static int ROWS_AND_COLUMNS = 20;
    private final static int GRID_WIDTH = 500;
    private final static int MOVE_PERIOD = 150;
    private final static HashMap<Direction, Direction> OPPOSITE_DIRECTIONS = loadOppositeDirections();
    public static Color PRIMARY_UI_COLOR = Color.GRAY;
    public static Color SECONDARY_UI_COLOR = Color.WHITE;
    public static Color BORDER_COLOR = Color.LIGHT_GRAY;

    // DEFAULT COLOR PALETTE:
    // public static Color PRIMARY_UI_COLOR = Color.GRAY;
    // public static Color SECONDARY_UI_COLOR = Color.WHITE;
    // public static Color BORDER_COLOR = Color.LIGHT_GRAY;

    /**
     * Loads the OPPOSITE_DIRECTIONS HashMap, assigning an opposite Direction to all values of Direction.
     */
    private static HashMap<Direction,Direction> loadOppositeDirections(){

        HashMap<Direction,Direction> oppositeDirections = new HashMap<>();

        oppositeDirections.put(Direction.UP, Direction.DOWN);
        oppositeDirections.put(Direction.DOWN, Direction.UP);
        oppositeDirections.put(Direction.LEFT, Direction.RIGHT);
        oppositeDirections.put(Direction.RIGHT, Direction.LEFT);

        return oppositeDirections;
    }

    /**
     * Finds a Direction's opposite.
     * @param direction The Direction we want to find the opposite for
     * @return The opposite Direction of the one provided
     */
    public static Direction getOppositeDirection(Direction direction){
        return OPPOSITE_DIRECTIONS.get(direction);
    }

    /**
     * A getter for the window width.
     * @return window width (integer)
     */
    public static int getWindowWidth(){
        return WINDOW_WIDTH;
    }

    /**
     * A getter for the row and column amount.
     * @return row and column amount (integer)
     */
    public static int getRowsAndColumns(){
        return ROWS_AND_COLUMNS;
    }

    /**
     * A getter for the grid width.
     * @return grid width (integer)
     */
    public static int getGridWidth(){
        return GRID_WIDTH;
    }

    /**
     * Calculates the size of individual squares.
     * @return square size (integer)
     */
    public static int getSquareSize(){
        return GRID_WIDTH/ROWS_AND_COLUMNS;
    }

    /**
     * A getter for the move period.
     * @return move period in milliseconds (integer)
     */
    public static int getMovePeriod(){
        return MOVE_PERIOD;
    }

    /**
     * A getter for the User.
     * @return The User currently loaded
     */
    public static User getUser(){
        return USER;
    }

    /**
     * Loads the User from a file. If it can't be found, a new User is created.
     * @return User
     */
    private static User loadUser(){
        final Serializer<User> serializer = new Serializer<>();
        File file = new File("Data\\user.txt");

        if(file.exists()){
            try {
                return serializer.loadObject(file);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new User();
            }
        } else {
            System.out.println("User data file not found.");
            return new User();
        }
    }
}
