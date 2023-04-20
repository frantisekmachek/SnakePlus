package UnitTesting;

import Data.Configuration;
import GameLogic.Direction;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @org.junit.jupiter.api.Test
    void getOppositeDirectionTest() {

        Direction up = Direction.UP;
        Direction down = Configuration.getOppositeDirection(up);

        assertEquals(down, Direction.DOWN);

    }
}