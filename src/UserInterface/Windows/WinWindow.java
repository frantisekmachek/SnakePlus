package UserInterface.Windows;
import Utilities.DecalLoader;

/**
 * The Window displayed when the user wins.
 */
public class WinWindow extends EndingWindow{
    public WinWindow(Window menu) {
        super(menu, DecalLoader.loadDecal("res\\Decals\\win_screen.png"));
    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
    }
}
