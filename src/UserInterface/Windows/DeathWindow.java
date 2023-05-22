package UserInterface.Windows;
import Utilities.DecalLoader;

/**
 * The Window displayed when the Snake dies.
 */
public class DeathWindow extends EndingWindow{
    /**
     * A constructor taking in a window which will open when leaving the end screen window. Probably something like the main menu or level menu.
     * @param window the window which will open when leaving this window
     */
    public DeathWindow(Window window){
        super(window, DecalLoader.loadDecal("res\\Decals\\death_screen.png"));
    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
    }
}
