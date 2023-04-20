package UnitTesting;

import GameLogic.Direction;
import org.junit.jupiter.api.Test;
import GameLogic.Snake;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    @Test
    void setDirectionTest() {
        Snake snake = new Snake();

        snake.setDirection(Direction.UP);
        snake.setDirection(Direction.DOWN);

        assertEquals(snake.getDirection(), Direction.UP);
    }
}