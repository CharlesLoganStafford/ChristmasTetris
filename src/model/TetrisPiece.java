package model;

import java.util.Random;

/**
 * The TetrisPiece class, which enumerates the different types of Tetris pieces that exist.
 * Each TetrisPiece consists of a height and width, and enumerable type "Block" which 
 * represents what letter the TetrisPiece corresponds to (J or S, for example), and a coordinate
 * matrix of Points.
 * 
 * This system of "Point" objects (functionally very similar to "java.awt.Point") are used 
 * to represent each letter/piece. These points model a 4x3 matrix, with positions starting 
 * at (0,0) at the bottom-left, and ranging to a maximum value of (3,2) at the top-right part 
 * of the matrix.
 * 
 * DONE
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public enum TetrisPiece {

    /** The 'I' TetrisPiece. */
    
    I (4, 1, Block.I, new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)),

    /** The 'J' TetrisPiece. */
    
    J (3, 2, Block.J, new Point(0, 2), new Point(0, 1), new Point(1, 1), new Point(2, 1)),

    /** The 'L' TetrisPiece. */
    
    L (3, 2, Block.L, new Point(2, 2), new Point(0, 1), new Point(1, 1), new Point(2, 1)),

    /** The 'O' TetrisPiece. */
    
    O (2, 2, Block.O, new Point(1, 2), new Point(2, 2), new Point(1, 1), new Point(2, 1)),

    /** The 'S' TetrisPiece. */
    
    S (3, 2, Block.S, new Point(1, 2), new Point(2, 2), new Point(0, 1), new Point(1, 1)),

    /** The 'T' TetrisPiece. */
    
    T (3, 2, Block.T, new Point(1, 2), new Point(0, 1), new Point(1, 1), new Point(2, 1)),

    /** The 'Z' TetrisPiece. */
    
    Z (3, 2, Block.Z, new Point(0, 2), new Point(1, 2), new Point(1, 1), new Point(2, 1));

    /**
     * A Random object, which is used in generating a random piece
     */
    private static final Random RANDOM = new Random();

    /**
     * An integer representing the width of the TetrisPiece.
     */
    private final int myWidth;

    /**
     * An integer representing the height of the TetrisPiece.
     */
    private final int myHeight;

    /**
     * The 4 "points" of the TetrisPiece.
     */
    private final Point[] myPoints;

    /**
     * The "block" type of the TetrisPiece - see the "Block" class in the current package
     * for more information.
     */
    private final Block myBlock;

    /**
     * The sole TetrisPiece constructor.
     * 
     * @param theWidth The desired width of the TetrisPiece.
     * @param theHeight The desired height of the TetrisPiece.
     * @param theBlock The desired Block type of the TetrisPiece.
     * @param thePoints The initial position of the blocks of the TetrisPiece.
     */
    TetrisPiece(final int theWidth, final int theHeight,
                final Block theBlock, final Point... thePoints) {
        myWidth = theWidth;
        myHeight = theHeight;
        myBlock = theBlock;
        myPoints = thePoints.clone();
    }

    /**
     * The getWidth method. This returns the width of the TetrisPiece.
     * 
     * @return The width of the TetrisPiece.
     */
    protected int getWidth() {
        return myWidth;
    }

    /**
     * The getWidth method. This returns the height of the TetrisPiece.
     * 
     * @return The height of the TetrisPiece.
     */
    protected int getHeight() {
        return myHeight;
    }

    /**
     * The getBlock method. This returns the Block type of the TetrisPiece.
     * 
     * @return The Block type of the TetrisPiece.
     */
    protected Block getBlock() {
        return myBlock;
    }
    
    /**
     * The getPoints method. This returns the "points" of the TetrisPiece.
     * 
     * @return An array containing the "points" of the TetrisPiece.
     */
    protected Point[] getPoints() {
        return myPoints.clone();
    }

    /**
     * Get a random TetrisPiece.
     * 
     * @return A random TetrisPiece object.
     */
    protected static TetrisPiece getRandomPiece() {
        return values()[RANDOM.nextInt(values().length)];
    }
}