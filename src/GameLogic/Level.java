package GameLogic;
import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Level class allows the user to add walls into the map which will kill them upon collision.
 */
public class Level implements Serializable, Comparable<Level> {

    private final int number;

    /**
     * A constructor for the Level class.
     * @param number Index of the level.
     */
    public Level(int number) {
        this.number = number;
    }

    /**
     * A getter for the level number (index).
     * @return Index of the level.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Checks if a level exists in the Levels folder and loads it via loadLevelFromFile().
     * @return An ArrayList of Squares representing the walls of the grid.
     */
    public HashSet<Square> getWalls(){
        File file = new File("res\\Levels\\Level" + number + ".txt");

        if(file.exists()){

            try {
                return loadLevelFromFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return new HashSet<>();
            }

        } else {
            System.out.println(file.getName() + " doesn't exist.");
            return new HashSet<>();
        }
    }

    /**
     * Loads a level from a file if the contents follow the template.
     * @param file The file we'll be loading the level from.
     * @return An ArrayList of Squares representing the walls of the grid.
     */
    public HashSet<Square> loadLevelFromFile(File file) throws IOException {

        HashSet<Square> walls = new HashSet<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";

        while((line = reader.readLine()) != null){

            Pattern pattern = Pattern.compile("^([0-9]+),([0-9]+)$");
            Matcher matcher = pattern.matcher(line);

            if(matcher.find()){

                int xCoordinate = Integer.parseInt(matcher.group(1));
                int yCoordinate = Integer.parseInt(matcher.group(2));

                if(!(xCoordinate <= 2 && yCoordinate == 0)){
                    walls.add(new Square(xCoordinate, yCoordinate));
                }

            }
        }
        System.out.println("Level " + number + " loaded.");
        reader.close();
        return walls;
    }

    @Override
    public int compareTo(Level level) {
        return Integer.compare(number, level.getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return number == level.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
