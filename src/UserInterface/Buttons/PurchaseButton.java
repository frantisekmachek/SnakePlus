package UserInterface.Buttons;

import Data.Configuration;
import Data.Item;
import Data.User;
import UserInterface.Windows.ShopWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Used in the Shop and triggers the purchase of an Item.
 */
public class PurchaseButton extends Button {
    private final Item item;
    private final ShopWindow shopWindow;
    public PurchaseButton(ShopWindow shopWindow, Item item){
        this.item = item;
        this.shopWindow = shopWindow;
        load(item.getName());

        setForeground(item.getColor());
    }

    /**
     * When clicked, triggers an Item purchase if the User hasn't unlocked it yet. If they have, the User's chosen Snake color is set to the Item's color.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        User user = Configuration.getUser();
        if(!user.isUnlocked(item)){
            user.purchaseItem(item);
        } else {
            user.setChosenItem(item);
        }
        shopWindow.update();
    }

    /**
     * Used when a purchase is made or a new Item is chosen. Changes the text to match the Item's state. If it is the one the User chose, the border's color is set to black. If the Item
     * hasn't been unlocked yet, the button displays the cost as well. If another Item has been chosen, the border is set back to the default one (gray).
     */
    public void reloadText(){

        User user = Configuration.getUser();

        if(user.isUnlocked(item)){
            setText(item.getName());
        } else {
            setText(item.getName() + ": " + item.getCost());
        }

        if(user.getSnakeColor().equals(item.getColor())){
            setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        } else {
            setBorder(BorderFactory.createLineBorder(Configuration.BORDER_COLOR,2));
        }
    }
}
