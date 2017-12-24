package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Board;
import model.MovableTetrisPiece;

/**
 * The NextPiecePanel class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public class NextPiecePanel extends JPanel implements Observer {
    
    /**
     * A generated serialVersionUID for the NextPiecePanel class.
     */
    private static final long serialVersionUID = 8788708798809554749L;

    /**
     * A Dimension object representing the panel size of the Next Piece panel.
     */
    private static final Dimension SCORE_PANEL_SIZE = new Dimension(150, 150);
    
    /**
     * A Color object to be used as the background color of the Next Piece panel.
     */
    private static final Color SCORE_PANEL_BG_COLOR = new Color(60, 141, 13);
    
    /**
     * A Color object to be used as the grid color of the Next Piece panel.
     */
    private static final Color GRID_COLOR = new Color(60, 141, 13);

    /**
     * An integer used for the size of the piece blocks.
     */
    private static final int BLOCK_SIZE = 25;
    
    /**
     * An integer used to offset the X-axis when drawing of the grid and pieces.
     */
    private static final int X_OFFSET = 25;
    
    /**
     * An integer used to offset the X-axis when drawing of the grid and pieces.
     */
    private static final int Y_OFFSET = 40;
    
    /**
     * An integer used to size the drawing correctly.
     */
    private static final int PANEL_SIZE = 100;

    /**
     * A internal MovableTetrisPiece object used for drawing the next piece.
     */
    private MovableTetrisPiece myNextPiece;
    
    /**
     * A String representation of the myNextPiece field.
     */
    private String myNextPieceString;

    /**
     * A flag to determine if it is okay to draw the next piece.
     */
    private boolean myNextPieceFlag;

    /**
     * The option to draw a grid on the GamePanel as a boolean.
     */
    private boolean myCanDrawGameGrid;
    
    /**
     * The NextPiecePanel constructor.
     */
    public NextPiecePanel() {
        super();
        
        myNextPieceString = "";
        myCanDrawGameGrid = true;
        
        /* Setting some properties of the panel. */
        setPreferredSize(SCORE_PANEL_SIZE);
        setBackground(SCORE_PANEL_BG_COLOR);
        
        /* Calling a helper method to create and display the panel. */
        printNextPiecePanel();
    }
    
    /** 
     * The overridden paintComponent method of the NextPiecePanel class.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        /* Creating and setting properties of the Graphics2D object. */
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        /* Drawing a Dark Gray rectangle representing the panel.*/
        theGraphics.setColor(Color.DARK_GRAY);
        theGraphics.fillRect(X_OFFSET, Y_OFFSET, PANEL_SIZE, 
                             PANEL_SIZE);
        
        /* Calling methods to draw both the grid and the next piece. */
        if (myNextPieceFlag) {
            drawNextPiece(g2d);
        }
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
    public void enableGameGridOverlay(final boolean theOption) {
        myCanDrawGameGrid = theOption;
    }
    
    /**
     * The drawGrid method of the NextPiecePanel class.
     * 
     * @param theGraphics The Graphics2D object used to draw the grid.
     */
    private void drawGameGrid(final Graphics2D theGraphics) {        
        /* Drawing a green rectangular grid. */
        theGraphics.setColor(GRID_COLOR);
        for (int column = X_OFFSET; column < PANEL_SIZE + Y_OFFSET + 1; column += BLOCK_SIZE) {
            theGraphics.drawLine(column, Y_OFFSET, column, PANEL_SIZE + Y_OFFSET);
        }        
        for (int row = Y_OFFSET; row < PANEL_SIZE + X_OFFSET + 1; row += BLOCK_SIZE) {
            theGraphics.drawLine(X_OFFSET, row, PANEL_SIZE + X_OFFSET, row);
        }
    }
    
    /**
     * The drawNextPiece method of the NextPiecePanel class.
     * 
     * @param theGraphics The Graphics2D object used to draw the piece.
     */
    private void drawNextPiece(final Graphics2D theGraphics) {        
        /* Drawing the pieces onto the panel.*/
        /* Creating some loop variables representing coordinates. */
        int x = X_OFFSET;
        int y = Y_OFFSET;
        
        /* Iterating through the NextPiece string. */
        for (int i = 0; i < myNextPieceString.length(); i++) { 
            x += BLOCK_SIZE;
            
            /* If a newLine character is found, increment Y and reset X. */
            if (myNextPieceString.charAt(i) == '\n') {
                y += BLOCK_SIZE;
                x = X_OFFSET;
            }
            
            /* Drawing the individual pieces, colors chosen by Block type.*/
            if (myNextPieceString.charAt(i) == 'I' 
                            || myNextPieceString.charAt(i) == 'L') {                
                theGraphics.setColor(Color.RED);
                theGraphics.fill3DRect(x - BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE, true);
            } else if (myNextPieceString.charAt(i) == 'O' 
                            || myNextPieceString.charAt(i) == 'Z') {                
                theGraphics.setColor(Color.GREEN);
                theGraphics.fill3DRect(x - BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE, true);
            } else if (myNextPieceString.charAt(i) == 'S' 
                            || myNextPieceString.charAt(i) == 'J') {
                theGraphics.setColor(Color.BLUE);
                theGraphics.fill3DRect(x - BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE, true);
            } else if (myNextPieceString.charAt(i) == 'T') {
                theGraphics.setColor(Color.YELLOW);
                theGraphics.fill3DRect(x - BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE, true); 
            }           
        }        
    }
    
    /**
     * The printNextPiece method of the NextPiecePanel class.
     */
    private void printNextPiecePanel() {        
        /* Creating a Box object to use as a space for the drawing. */
        final Box piecePanel = Box.createVerticalBox();
        
        /* Creating and setting a centered, titled border for the panel. */
        final TitledBorder border = new TitledBorder(new LineBorder(Color.RED),
                                                     "Next Piece:",
                                                     TitledBorder.CENTER,
                                                     TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.WHITE);        
        setBorder(border); 
        
        /* Adding the box panel to this object. */
        add(piecePanel);        
    }

    /**
     * The overridden update method of the NextPiecePanel class.
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {        
        /* If the object is a Board and the data is a MovableTetris Piece... */
        if (theObservable instanceof Board 
                        && theData instanceof MovableTetrisPiece) { 
            
            /* Set the internal "myNextPiece" field to the input data object. */
            myNextPiece = (MovableTetrisPiece) theData;
            myNextPieceString = new String(myNextPiece.toString());
            
            /* Set the internal flag to true so the panel knows when to draw the piece. */
            myNextPieceFlag = true;
        }        
        repaint();        
    }
    
}