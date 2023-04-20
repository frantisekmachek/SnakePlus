package Data;

import GameLogic.Direction;

import java.awt.*;
import java.util.HashMap;

/**
 * Contains some settings used in the game such as window width and the User.
 */
public abstract class Configuration {
    private static User USER;
    private static final int WINDOW_WIDTH = 600;
    private static final int ROWS_AND_COLUMNS = 20;
    private static final int GRID_WIDTH = 500;
    private static int MOVE_PERIOD = 150;
    private static final HashMap<Direction, Direction> OPPOSITE_DIRECTIONS = loadOppositeDirections();
    public static Color PRIMARY_UI_COLOR = Color.GRAY;
    public static Color SECONDARY_UI_COLOR = Color.WHITE;
    public static Color BORDER_COLOR = Color.LIGHT_GRAY;

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
    public static int getWindowWidth(){
        return WINDOW_WIDTH;
    }
    public static int getRowsAndColumns(){
        return ROWS_AND_COLUMNS;
    }
    public static int getGridWidth(){
        return GRID_WIDTH;
    }
    public static int getSquareSize(){
        return GRID_WIDTH/ROWS_AND_COLUMNS;
    }
    public static int getMovePeriod(){
        return MOVE_PERIOD;
    }

    public static User getUser(){
        if(USER == null){
            load(new User());
        }
        return USER;
    }

    /**
     * Called at the very start of the program. Loads all necessary information used later on.
     * @param user The User loaded from a file
     */
    public static void load(User user){

        if(user != null){
            USER = user;
        } else {
            USER = new User();
        }

        loadOppositeDirections();
    }
}
