package UserInterface.Windows;

import Data.Configuration;

import java.awt.*;

/**
 * An interface containing only the drawLines method with a default implementation.
 */
public interface DrawLines {

    /**
     * Draws lines at the edges of the grid and on every square.
     * @param g An instance of Graphics
     * @param color The color of the lines
     */
    default void drawLines(Graphics g, Color color){

        g.setColor(color);

        int edge = Configuration.getGridWidth() - 1;

        g.drawLine(0,edge,edge,edge);
        g.drawLine(edge,0,edge,edge);

        for(int xCoordinate = 0; xCoordinate < Configuration.getRowsAndColumns(); xCoordinate++){
            for(int yCoordinate = 0; yCoordinate < Configuration.getRowsAndColumns(); yCoordinate++){
                g.drawLine(xCoordinate*Configuration.getSquareSize(),0,xCoordinate*Configuration.getSquareSize(),500);
                g.drawLine(0,yCoordinate*Configuration.getSquareSize(),500,yCoordinate*Configuration.getSquareSize());
            }
        }
    }

}
