import Data.Configuration;
import Data.User;
import UserInterface.Windows.MainMenu;

public class Main {
    public static void main(String[] args) {

        User user = new User();
        Configuration.load(user);

        MainMenu mainMenu = new MainMenu();
        mainMenu.getFrame().setVisible(true);

    }
}