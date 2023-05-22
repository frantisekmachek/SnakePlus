package UserInterface.Buttons;

import GameLogic.Game;
import UserInterface.Windows.Menu;
import UserInterface.Windows.Window;

/**
 * The PlayButton is a WindowButton which starts the game when clicked. It has 2 constructors - one for a basic
 * "PLAY" button (automatically sets the level number to 0 which is the default level with no walls) and one for
 * special levels with walls. An instance of this class should only be created inside Menu instances.
 */
public class PlayButton extends WindowButton{
    private final int levelNumber;

    /**
     * A constructor for the default play button.
     * @param menu The menu we'll be closing.
     * @param newWindow The window we'll be opening.
     */
    public PlayButton(Menu menu, Window newWindow) {
        super(menu, newWindow, "PLAY");
        this.levelNumber = 0;
    }

    /**
     * A constructor which also demands a level number.
     * @param menu The menu we'll be closing.
     * @param newWindow The window we'll be opening.
     * @param levelNumber Number of the level we'll be attempting to load.
     */
    public PlayButton(Menu menu, Window newWindow, int levelNumber) {
        super(menu, newWindow, ("LEVEL " + levelNumber));
        this.levelNumber = levelNumber;
    }

    /**
     * Closes the currently opened Window and opens a new game.
     */
    @Override
    public void closeAndOpen(){
        currentWindow.close();
        new Game(levelNumber, (Menu)currentWindow);
    }
}
