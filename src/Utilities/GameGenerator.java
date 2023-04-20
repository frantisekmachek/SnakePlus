package Utilities;

import Data.Configuration;
import GameLogic.Snake;
import GameLogic.Square;

/**
 * This class is used for generating important parts of the game's logic such as an array of Squares or a Snake instance.
 * All the methods can be called from a static context.
 */
public abstract class GameGenerator {

    /**
     * Generates a square 2D array of Squares (the amount of rows and columns is the same).
     * It uses the settings inside the Configuration class.
     * @return 2D array of Squares
     */
    public static Square[][] generateSquares(){
        int rowsAndColumns = Configuration.getRowsAndColumns();
        Square[][] squares = new Square[rowsAndColumns][rowsAndColumns];

        for(int x = 0; x < rowsAndColumns; x++){
            for(int y = 0; y < rowsAndColumns; y++){
                Square square = new Square(x, y);
                squares[x][y] = square;
            }
        }

        return squares;
    }

    /**
     * Generates a new Snake in the top left corner. Its default Direction is RIGHT.
     * @param squares The 2D Square array
     * @return A generated Snake
     */
    public static Snake generateSnake(Square[][] squares){ // Creating a new snake

        Snake snake = new Snake();

        for(int i = 2; i >= 0; i--){

            Square square = squares[i][0];
            snake.getStructure().add(square);

        }

        return snake;

    }

}
