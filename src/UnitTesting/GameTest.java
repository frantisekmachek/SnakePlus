package UnitTesting;

import GameLogic.Direction;
import GameLogic.Game;
import GameLogic.Snake;
import GameLogic.Square;
import Utilities.GameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /**
     * Checks if the created Snake collides with a wall in the Direction it's headed. The method should return true,
     * because the offset is one to the right which is where the wall is.
     */
    @Test
    void checkCollisionTest(){
        Game game = new Game();
        Square[][] squares = new Square[5][5];
        for(int x = 0; x < squares.length; x++){
            for(int y = 0; y < squares.length; y++){
                squares[x][y] = new Square(x,y);
            }
        }
        HashSet<Square> walls = new HashSet<>();
        walls.add(squares[3][0]);

        Snake snake = new Snake();

        snake.growSnake(squares[0][0]);
        snake.growSnake(squares[1][0]);
        snake.growSnake(squares[2][0]);
        for(int x = 0; x < 3; x++){
            squares[x][0] = new Square(x,0);
        }
        LinkedList<Square> structure = snake.getStructure();
        Square head = structure.getFirst();

        int x = head.getxCoordinate();
        int y = head.getyCoordinate();
        int xOffset = 1;
        int yOffset = 0;

        assertTrue(game.checkCollision(x + xOffset, y + yOffset, squares, structure, walls));
    }

    @Test
    void checkXCoordinateTest() {
        boolean valid = Game.checkXCoordinate(10,9);
        assertFalse(valid);
    }

    @Test
    void checkYCoordinateTest() {
        boolean valid = Game.checkYCoordinate(10,7);
        assertTrue(valid);
    }

    @Test
    void checkIfPlayerWonTest() {
        Game game = new Game();

        LinkedList<Square> structure = new LinkedList<>();
        for(int x = 0; x < 3; x++){ // Generates a Snake on the whole grid
            for(int y = 0; y < 3; y++){
                structure.add(new Square(x,y));
            }
        }

        HashSet<Square> walls = new HashSet<>();
        Square[][] squares = new Square[3][3];

        Boolean won = game.checkIfPlayerWon(structure,walls,squares);
        assertTrue(won);
    }

    @Test
    void moveSnakeTest(){
        Game game = new Game();

        Square[][] squares = GameGenerator.generateSquares();
        Snake snake = GameGenerator.generateSnake(squares);
        snake.setDirection(Direction.RIGHT);
        LinkedList<Square> structure = snake.getStructure();

        // The structure we're expecting (the new snake position)
        LinkedList<Square> correctStructure = new LinkedList<>();
        correctStructure.add(squares[3][0]);
        correctStructure.add(squares[2][0]);
        correctStructure.add(squares[1][0]);

        game.moveSnake(snake);

        assertEquals(structure, correctStructure);
    }

    @Test
    void getAvailableSquaresTest(){
        Game game = new Game();
        ArrayList<Square> availableSquares = new ArrayList<>();
        Square[][] squares = GameGenerator.generateSquares();

        for(Square[] squareArray : squares){ // Adds all squares that aren't part of the snake (there are no walls, so we can ignore them)
            for(Square square : squareArray){
                if(!game.getSnake().getStructure().contains(square)){
                    availableSquares.add(square);
                }
            }
        }
        assertEquals(game.getAvailableSquares(), availableSquares);
    }
}