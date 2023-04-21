package UserInterface.Windows;
import Utilities.DecalLoader;

/**
 * The Window displayed when the Snake dies.
 */
public class DeathWindow extends EndingWindow{
    public DeathWindow(Window menu){
        super(menu, DecalLoader.loadDecal("Decals\\death_screen.png"));
    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
    }
}
