package UserInterface.Buttons;

import java.awt.event.ActionEvent;
import UserInterface.Windows.Window;

/**
 * The WindowButton class is a JButton which closes one Window and opens another when clicked.
 */
public class WindowButton extends Button {
    protected Window currentWindow;
    protected Window newWindow;

    public WindowButton(Window currentWindow, Window newWindow, String text){
        super();
        this.currentWindow = currentWindow;
        this.newWindow = newWindow;
        load(text);
    }

    /**
     * Opens one window and closes another.
     */
    public void closeAndOpen(){
        currentWindow.close();
        newWindow.open();
    }

    /**
     * An overridden actionPerformed() method, closes the current Window and opens the new one.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        closeAndOpen();
    }
}
