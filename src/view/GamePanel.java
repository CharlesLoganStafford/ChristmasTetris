package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Board;

/**
 * The GamePanel class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public class GamePanel extends JPanel implements Observer {

    /**
     * A generated serialVersionUID for the GamePanel class.
     */
    private static final long serialVersionUID = 5566629310868813534L;

    /**
     * The size of the game panel as a Dimension object.
     */
    private static final Dimension DEFAULT_SIZE = new Dimension(251, 501);
    
    /**
     * A Color object to be used as the grid color of the Game Panel.
     */
    private static final Color GRID_COLOR = new Color(60, 141, 13);

    /**
     * The size of each "block" as an integer.
     */
    private int myBlockSize;
    
    /**
     * The data received from the Board as a String.
     */
    private String myBoardData;

    /**
     * The option to draw a grid on the GamePanel as a boolean.
     */
    private boolean myCanDrawGameGrid;
  
    /**
     * The GamePanel constructor. 
     * 
     * @param theBlockSize The integer value to set the block size to.
     */
    public GamePanel(final int theBlockSize) {
        super();
        
        /* Initializing myBoardData as an ArrayList of Character objects. */
        myBoardData = "";
        myCanDrawGameGrid = true;
        myBlockSize = theBlockSize;
        
        /* Setting some qualities of the game panel. */
        setPreferredSize(DEFAULT_SIZE);
        setBackground(Color.DARK_GRAY);
    }
    
    /** 
     * The overridden paintComponent method of the GamePanel class.
     * 
     * @param theGraphics The Graphics2D object used to draw the board.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        /* Creating and setting properties of the Graphics2D object. */
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        /* Drawing a Dark Gray rectangle representing the board.*/
        theGraphics.setColor(Color.DARK_GRAY);
        theGraphics.fillRect(0, 0, getWidth(), getHeight());
        
        /* Calling methods to draw both the grid and the game board. */
        drawGameBoard(g2d); 
        if (myCanDrawGameGrid) {
            drawGameGrid(g2d);
        }
        repaint();      
    }
    
    /**
     * The enableGameGridOverlay method of the GamePanel class.
     * 
     * @param theOption The boolean option to draw the game grid or not.
     */
    protected void enableGameGridOverlay(final boolean theOption) {
        myCanDrawGameGrid = theOption;
    }
    
    /**
     * The setBlockSize method of the GamePanel class.
     * 
     * @param theBlockSize The integer to set the Block Size to.
     */
    protected void setBlockSize(final int theBlockSize) {
        myBlockSize = theBlockSize;
    }
    
    /**
     * The drawGrid method of the GamePanel class.
     * 
     * @param theGraphics The Graphics2D object used to draw the grid.
     */
    private void drawGameGrid(final Graphics2D theGraphics) {        
        /* Drawing a green rectangular grid. */
        theGraphics.setColor(GRID_COLOR);
        for (int i = 0; i < getWidth(); i += myBlockSize) {
            theGraphics.drawLine(i, 0, i, getHeight());
        }        
        for (int j = 0; j < getHeight(); j += myBlockSize) {
            theGraphics.drawLine(0, j, getWidth(), j);
        }
    }
    
    /**
     * The drawBoard method of the GamePanel class.
     * 
     * @param theGraphics The Graphics2D object used to draw the board.
     */
    private void drawGameBoard(final Graphics2D theGraphics) {        
        /* Drawing the pieces onto the panel.*/
        /* Creating some loop variables representing coordinates. */
        final int boardXOffset = 2;
        int x = -1 * boardXOffset * myBlockSize;
        int y = -1 * myBlockSize;
        
        /* Iterating through the myBoardData string. */
        for (int i = 0; i < myBoardData.length(); i++) { 
            x += myBlockSize;
            
            /* If a newLine character is found, increment Y and reset X. */
            if (myBoardData.charAt(i) == '\n') {
                y += myBlockSize;
                x = -1 * boardXOffset * myBlockSize;
            }
            
            /* Drawing the individual pieces, colors chosen by Block type.*/
            if (myBoardData.charAt(i) == 'I' 
                            || myBoardData.charAt(i) == 'L') {                
                theGraphics.setColor(Color.RED);
                theGraphics.fill3DRect(x, y, myBlockSize, myBlockSize, true);                
            } else if (myBoardData.charAt(i) == 'O' 
                            || myBoardData.charAt(i) == 'Z') {                
                theGraphics.setColor(Color.GREEN);
                theGraphics.fill3DRect(x, y, myBlockSize, myBlockSize, true);                
            } else if (myBoardData.charAt(i) == 'S' 
                            || myBoardData.charAt(i) == 'J') {
                theGraphics.setColor(Color.BLUE);
                theGraphics.fill3DRect(x, y, myBlockSize, myBlockSize, true);
            } else if (myBoardData.charAt(i) == 'T') {
                theGraphics.setColor(Color.YELLOW);
                theGraphics.fill3DRect(x, y, myBlockSize, myBlockSize, true); 
            }
        }        
    }
    
    /**
     * The overridden update method of the GamePanel class.
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {        
        if (theObservable instanceof Board && theData instanceof String) {            
            /* Add the contents of the data to my field. */
            myBoardData = new String((String) theData);
            
            final int belowGridIndex = myBoardData.indexOf('-') - 1;           
            myBoardData = new String(myBoardData.substring(belowGridIndex));
          
            repaint();            
        }
    }  
}