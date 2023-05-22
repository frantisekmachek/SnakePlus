package UserInterface.Windows;

import Data.Configuration;
import GameLogic.Square;
import Utilities.GameGenerator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * The grid displayed on the level creation window.
 */
public class CreatorGrid extends JPanel implements DrawLines {

    private final Square[][] squares = GameGenerator.generateSquares();
    private final HashSet<Square> selectedSquares = new HashSet<>();
    private final HashSet<Square> blockedSquares = new HashSet<>();
    private final JPanel panel;
    private Square selectedSquare;
    private int squareX;
    private int squareY;

    public CreatorGrid(JPanel panel){
        this.panel = panel;
        setSize(500,500);
        setLocation(0,0);
        setBackground(Configuration.BORDER_COLOR);
        addMouseMotionListener(new MouseMotion());
        addMouseListener(new MouseClick());

        blockedSquares.add(squares[0][0]);
        blockedSquares.add(squares[1][0]);
        blockedSquares.add(squares[2][0]);
    }

    /**
     * A getter for the selectedSquares HashSet.
     * @return a HashSet of Squares
     */
    public HashSet<Square> getSelectedSquares(){
        return selectedSquares;
    }

    /**
     * An overridden paintComponent() method that displays all squares according to different factors. The Square currently hovered over is
     * painted the secondary UI color, the selected Squares are painted the border color and the Squares not selected are painted the
     * primary UI color. The first 3 Squares are painted the snake color
     * (the Snake starts at the first 3 Squares, so they can't be occupied by walls). At the end, the lines are drawn.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g){
        if(selectedSquare != null){
            selectedSquare.draw(Configuration.SECONDARY_UI_COLOR, g);
        }
        for(Square square : selectedSquares){
            if(!square.equals(selectedSquare)){
                square.draw(Configuration.BORDER_COLOR, g);
            } else {
                square.draw(Configuration.PRIMARY_UI_COLOR, g);
            }
        }
        for(Square square : blockedSquares){
            square.draw(Configuration.getUser().getSnakeColor(), g);
        }
        drawLines(g, Configuration.BORDER_COLOR);
    }

    private class MouseMotion implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            //
        }

        /**
         * When the mouse is moved, its coordinates are processed and squareX and squareY are calculated (coordinates of the Square
         * the mouse is hovering over). The selectedSquare (the Square hovered over) is changed, but only if it isn't forbidden or isn't already
         * the selectedSquare.
         * @param e the event to be processed
         */
        @Override
        public void mouseMoved(MouseEvent e) {

            squareX = Math.round(e.getX()/Configuration.getSquareSize());
            squareY = Math.round(e.getY()/Configuration.getSquareSize());

            Square square = squares[squareX][squareY];
            if(!square.equals(selectedSquare) && !blockedSquares.contains(square)){
                selectedSquare = square;
                panel.repaint();
            }
        }
    }
    private class MouseClick implements MouseListener {
        /**
         * When the mouse is clicked, the selectedSquare is added or removed from the selectedSquares HashSet.
         * @param e the event to be processed
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            Square square = squares[squareX][squareY];
            if(!selectedSquares.contains(square)){
                selectedSquares.add(square);
            } else {
                selectedSquares.remove(square);
            }
            selectedSquare = null;
            panel.repaint();
        }
        @Override
        public void mousePressed(MouseEvent e) {
            //
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            //
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            //
        }

        /**
         * When the mouse exits the grid, selectedSquare is set to null.
         * @param e the event to be processed
         */
        @Override
        public void mouseExited(MouseEvent e) {
            selectedSquare = null;
            panel.repaint();
        }
    }

    /**
     * Opens a JFileChooser. The user is asked to choose a .txt file for the Level they created. The Level is saved only if it isn't called
     * "Level0.txt", because Level 0 cannot be overwritten. It is the default Level with no walls.
     */
    public void openFileChooser(){

        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setCurrentDirectory(new File("res\\Levels"));

        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
        fc.addChoosableFileFilter(filter);

        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file.getName().equals("Level0.txt")){
                System.out.println("Cannot overwrite default level (Level 0).");
                return;
            }
            if(!file.getName().matches("^(.+)(\\.txt)$")){
                file = new File(file.toString() + ".txt");
            }
            saveLevel(file);
        }
    }

    /**
     * Saves the created Level in a text file.
     * @param file The file where the Level will be saved
     */
    public void saveLevel(File file){
        try {
            String fileName = file.getName();

            if(!fileName.equals("Level0.txt")){
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fileWriter);

                String text = "";

                for(Square square : selectedSquares){
                    text = text + square.getxCoordinate() + "," + square.getyCoordinate() + "\n";
                }
                bw.write(text);
                bw.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
