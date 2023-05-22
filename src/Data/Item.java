package Data;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Item implements Comparable<Item>, Serializable {

    private final Color color;
    private final int cost;
    private final String name;
    private static final HashMap<String,Item> SNAKE_COLORS = loadSnakeColors();

    /**
     * A constructor for the Item class.
     * @param color The color assigned to the Item
     * @param cost The amount of credits the Item costs
     * @param name The name of the Item (preferably the same name as the color)
     */
    public Item(Color color, int cost, String name){
        this.color = color;
        this.cost = cost;
        this.name = name;
    }

    /**
     * Loads a static HashMap with String keys and Item values. Each Item found in the shop is put in the HashMap. The key is the name
     * (for example GREEN) and the value is the Item, preferably with a Color of the same name.
     * @return HashMap containing all shop items
     */
    private static HashMap<String,Item> loadSnakeColors(){
        HashMap<String, Item> snakeColors = new HashMap<>();

        snakeColors.put("GREEN", new Item(Color.GREEN, 10, "GREEN"));
        snakeColors.put("BLUE", new Item(Color.BLUE, 100, "BLUE"));
        snakeColors.put("BLACK", new Item(Color.BLACK, 500, "BLACK"));
        snakeColors.put("RED", new Item(Color.RED, 100, "RED"));
        snakeColors.put("YELLOW", new Item(Color.YELLOW, 200, "YELLOW"));
        snakeColors.put("CYAN", new Item(Color.CYAN, 200, "CYAN"));

        return snakeColors;
    }

    /**
     * A getter for the cost.
     * @return cost of the Item (integer)
     */
    public int getCost(){
        return this.cost;
    }
    /**
     * A getter for the snakeColors HashMap.
     * @return HashMap containing all shop items
     */
    public static HashMap<String, Item> getSnakeColors(){
        return SNAKE_COLORS;
    }
    /**
     * A getter for the name.
     * @return the name of the Item
     */
    public String getName(){
        return name;
    }
    /**
     * A getter for the color.
     * @return The color assigned to the Item
     */
    public Color getColor(){
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return cost == item.cost && Objects.equals(color, item.color) && Objects.equals(name, item.name);
    }

    @Override
    public int compareTo(Item item) {
        if(this.cost < item.getCost()){
            return -1;
        } else if (this.cost > item.getCost()){
            return 1;
        } else {
            return name.compareTo(item.getName());
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(color, cost, name);
    }
}
