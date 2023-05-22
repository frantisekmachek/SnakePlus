package GameLogic;

import Data.Configuration;
import Utilities.DecalLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * An instance of a Square represents one square on a grid, having coordinates on the x-axis and y-axis.
 * This class also includes methods which help paint the square on the grid to properly visualise it.
 * You can draw an empty square, a Snake square (a square which is part of the Snake) and an apple.
 */
public class Square {
    private final int xCoordinate;
    private final int yCoordinate;
    private final int squareSize = Configuration.getSquareSize();

    /**
     * A constructor which sets the x and y coordinates.
     * @param xCoordinate The coordinate on the x-axis.
     * @param yCoordinate The coordinate on the y-axis.
     */
    public Square(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * A getter for the x-axis coordinate.
     * @return Coordinate on the x-axis.
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * A getter for the y-axis coordinate.
     * @return Coordinate on the y-axis.
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Calls the draw() method and passes the color white to draw an empty square.
     * @param g A Graphics instance acquired from an overridden paintComponent() method.
     */
    public void drawEmpty(Graphics g){
        draw(Configuration.SECONDARY_UI_COLOR, g);
    }

    /**
     * Calls the draw() method and passes the chosen Snake color to draw a square which is part of the Snake.
     * @param g A Graphics instance acquired from an overridden paintComponent() method.
     */
    public void drawSnake(Graphics g){
        draw(Configuration.getUser().getSnakeColor(), g);
    }

    /**
     * Draws an apple on the Square's coordinates. If the image can't be found, a red oval is drawn instead.
     * @param g A Graphics instance acquired from an overridden paintComponent() method.
     */
    public void drawApple(Graphics g){
        BufferedImage appleImage = DecalLoader.loadDecal("res\\Decals\\apple.png");
        if(appleImage != null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(appleImage,xCoordinate*squareSize,yCoordinate*squareSize, squareSize, squareSize, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval(xCoordinate*squareSize, yCoordinate*squareSize, squareSize, squareSize);
        }
    }

    /**
     * Draws a square on the instance's coordinates with the chosen color.
     * @param color The color the drawn square will be.
     * @param g A Graphics instance acquired from an overridden paintComponent() method.
     */
    public void draw(Color color, Graphics g){
        g.setColor(color);
        g.fillRect(xCoordinate*squareSize,yCoordinate*squareSize,squareSize,squareSize);
    }

    /**
     * Draws a Snake's head on the Square's coordinates. If an image can't be found, a rectangle is filled instead.
     * @param g A Graphics instance acquired from an overridden paintComponent() method.
     * @param image The image of the head. Different for each color.
     */
    public void drawHead(Graphics g, Image image){
        if(image != null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(image,xCoordinate*squareSize,yCoordinate*squareSize, squareSize, squareSize, null);
        } else {
            draw(Configuration.getUser().getSnakeColor(), g);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return xCoordinate == square.xCoordinate && yCoordinate == square.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }
}
