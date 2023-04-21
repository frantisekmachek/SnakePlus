package Data;

import Utilities.Serializer;
import Utilities.SoundPlayer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class contains the player's data such as high scores or unlocked items. A User can be saved and loaded through serialization.
 */
public class User implements Serializable {

    private int credits = 1010101010;
    private final HashMap<Integer,Integer> levelHighScores = new HashMap<>();
    private final HashSet<Item> unlockedItems = new HashSet<>();
    private Item chosenItem;

    public User(){
        HashMap<String,Item> snakeColors = Item.getSnakeColors();
        Item item = snakeColors.get("GREEN");
        unlockedItems.add(item);
        chosenItem = item;
    }

    /**
     * Adds a desired amount to the User's credits.
     * @param amount The added amount
     */
    public void addCredits(int amount){
        credits = credits + amount;
        saveData();
    }

    /**
     * Finds the User's high score on a provided Level. If the User has no high score,
     * a new one is created and set to 0.
     * @param levelNumber The number of the Level
     * @return High score on the provided Level
     */
    public int getLevelHighScore(int levelNumber){

        if(levelHighScores.containsKey(levelNumber)){
            return levelHighScores.get(levelNumber);
        } else {
            setLevelHighScore(levelNumber, 0);
            saveData();
            return 0;
        }
    }

    public void setLevelHighScore(int levelNumber, int highScore){
        levelHighScores.put(levelNumber, highScore);
        saveData();
    }
    public void setChosenItem(Item item){
        chosenItem = item;
        saveData();
    }
    public Color getSnakeColor(){
        return chosenItem.getColor();
    }
    public Item getChosenItem(){
        return chosenItem;
    }
    public int getCredits(){
        return credits;
    }

    /**
     * Triggers the purchase of an Item. The Item's cost is subtracted from the user's credits. A sound is played too.
     * @param item The Item to be purchased
     */
    public void purchaseItem(Item item){
        if(canAffordItem(item) && !unlockedItems.contains(item)){

            credits = credits - item.getCost();
            unlockItem(item);

            SoundPlayer purchaseSoundPlayer = new SoundPlayer();
            purchaseSoundPlayer.playSound("Sounds\\purchase.wav");

        }
    }

    /**
     * Unlocks an Item.
     * @param item an Item instance
     */
    public void unlockItem(Item item){
        unlockedItems.add(item);
        System.out.println("Item called " + item.getName() + " has been unlocked.");
        saveData();
    }

    /**
     * Checks if the user can afford an Item.
     * @param item an Item instance
     * @return true/false
     */
    public Boolean canAffordItem(Item item){
        if(credits >= item.getCost()){
            System.out.println("The user can afford the item.");
            return true;
        } else {
            System.out.println("The user cannot afford the item (the item costs " + item.getCost() + " credits and the user has " + credits + ").");
            return false;
        }
    }

    /**
     * Checks if the user has unlocked an Item.
     * @param item an Item instance
     * @return true/false
     */
    public Boolean isUnlocked(Item item){
        return unlockedItems.contains(item);
    }

    public void saveData(){
        Serializer<User> serializer = new Serializer<>();
        File dataDirectory = new File("Data");

        if (!dataDirectory.exists()){
            dataDirectory.mkdirs();
        }

        File file = new File("Data\\user.txt");
        try {
            serializer.saveObject(this, file);
            System.out.println("User data has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
