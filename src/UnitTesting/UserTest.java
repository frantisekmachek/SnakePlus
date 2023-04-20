package UnitTesting;

import Data.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getLevelHighScoreTest() {
        User user = new User();
        user.setLevelHighScore(1,10);
        user.setLevelHighScore(1,12);
        user.setLevelHighScore(2,15);

        assertEquals(user.getLevelHighScore(1), 12);
        assertEquals(user.getLevelHighScore(2), 15);
    }

}