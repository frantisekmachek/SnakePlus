package UserInterface.Buttons;
import UserInterface.Windows.Window;

import java.awt.event.ActionEvent;

/**
 * A Button that allows the user to exit the game.
 */
public class ExitButton extends WindowButton{
    public ExitButton(Window menu){
        super(menu, null, "EXIT");
    }

    /**
     * Stops the program when clicked.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
