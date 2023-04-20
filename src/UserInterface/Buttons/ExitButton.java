package UserInterface.Buttons;
import UserInterface.Windows.Window;

import java.awt.event.ActionEvent;

public class ExitButton extends WindowButton{
    public ExitButton(Window menu){
        super(menu, null, "EXIT");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        playSound();
        System.exit(0);
    }

}
