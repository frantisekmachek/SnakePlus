package UserInterface.Windows;

import Data.Configuration;
import Data.Item;
import UserInterface.Buttons.PurchaseButton;
import UserInterface.Buttons.WindowButton;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The Window where the user can purchase snake colors.
 */
public class ShopWindow extends ScrollableMenu{

    private final CreditPanel creditPanel = new CreditPanel();
    public ShopWindow(Menu menu){
        super(menu, "res\\Decals\\default_menu.png");
        load();
    }

    @Override
    void loadButtons() {
        addComponent(creditPanel);
        addComponent(new WindowButton(this,menu,"BACK"));

        HashMap<String,Item> snakeColors = Item.getSnakeColors();
        ArrayList<Item> snakeColorList = new ArrayList<>();

        for (Map.Entry<String, Item> set : snakeColors.entrySet()) {
            Item item = set.getValue();
            snakeColorList.add(item);
        }

        Collections.sort(snakeColorList);

        for (Item item : snakeColorList) {
            addButton(new PurchaseButton(this, item));
        }

    }

    /**
     * Updates all the buttons. That means that the button of the chosen color will have a black border and all the text will match the current state of the item
     * (for example if it's purchased already, the cost isn't shown).
     */
    public void update(){
        creditPanel.update();
        for(JButton button : buttons){
            ((PurchaseButton)button).reloadText();
        }
    }

    @Override
    public void open(){
        frame.setVisible(true);
        update();
    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
        loadScrollPane();
        loadTitle("SHOP");
        loadButtons();
        update();
    }

    /**
     * A JPanel that tells the current amount of credits.
     */
    private class CreditPanel extends JPanel{
        private final JLabel label = new JLabel();
        public CreditPanel(){

            this.setLayout(new FlowLayout());
            this.setOpaque(false);

            label.setForeground(Configuration.SECONDARY_UI_COLOR);
            label.setFont(new Font("Arial", Font.BOLD, 30));
            label.setAlignmentX(CENTER_ALIGNMENT);
            this.add(label);

            update();
        }

        /**
         * Changes the text to the user's amount of credits.
         */
        public void update(){
            label.setText("CREDITS: " + Configuration.getUser().getCredits());
        }

    }
}
