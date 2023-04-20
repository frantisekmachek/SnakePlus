package UnitTesting;

import GameLogic.Game;
import GameLogic.Snake;
import GameLogic.Square;
import org.junit.jupiter.api.Test;

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
        Game game = new Game();
        Boolean valid = game.checkXCoordinate(10,9);
        assertFalse(valid);
    }

    @Test
    void checkYCoordinateTest() {
        Game game = new Game();
        Boolean valid = game.checkYCoordinate(10,7);
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
}