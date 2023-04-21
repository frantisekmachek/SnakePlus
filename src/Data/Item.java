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

    private static HashMap<String,Item> loadSnakeColors(){
        HashMap<String, Item> snakeColors = new HashMap<>();

        snakeColors.put("GREEN", new Item(Color.GREEN, 10, "GREEN"));
        snakeColors.put("BLUE", new Item(Color.BLUE, 20, "BLUE"));
        snakeColors.put("BLACK", new Item(Color.BLACK, 100, "BLACK"));
        snakeColors.put("RED", new Item(Color.RED, 20, "RED"));
        snakeColors.put("YELLOW", new Item(Color.YELLOW, 30, "YELLOW"));
        snakeColors.put("CYAN", new Item(Color.CYAN, 30, "CYAN"));

        return snakeColors;
    }

    public static HashMap<String, Item> getSnakeColors(){
        return SNAKE_COLORS;
    }
    public Item(Color color, int cost, String name){
        this.color = color;
        this.cost = cost;
        this.name = name;
    }


    public int getCost(){
        return this.cost;
    }
    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return cost == item.cost && Objects.equals(color, item.color) && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, cost, name);
    }

    public Color getColor(){
        return color;
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
}
