package UserInterface.Windows;

import Data.Configuration;
import Data.Item;
import UserInterface.Buttons.PurchaseButton;
import UserInterface.Buttons.WindowButton;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ShopWindow extends ScrollableMenu{

    private final CreditPanel creditPanel = new CreditPanel();
    public ShopWindow(Menu menu){
        super(menu, "Decals\\default_menu.png");
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

    private class CreditPanel extends JPanel{
        private Font font = new Font("Arial", Font.BOLD, 30);
        private JLabel label = new JLabel();
        public CreditPanel(){

            this.setLayout(new FlowLayout());
            this.setOpaque(false);

            label.setForeground(Configuration.SECONDARY_UI_COLOR);
            label.setFont(font);
            label.setAlignmentX(CENTER_ALIGNMENT);
            this.add(label);

            update();
        }

        public void update(){
            label.setText("CREDITS: " + Configuration.getUser().getCredits());
        }

    }
}
