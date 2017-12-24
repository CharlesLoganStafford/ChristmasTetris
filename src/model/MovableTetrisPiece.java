package model;

/**
 * The MoveableTetrisPiece class. This is an important component of the 
 * back-end logic that associates a TetrisPiece object with two important
 * aspects: a position and a rotation. This allows a TetrisPiece to move 
 * and rotate along the Board, and essentially ties together several classes
 * into a major functional component of the program. A MovableTetrisPiece is 
 * immutable.
 * 
 * DONE
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public final class MovableTetrisPiece {
    
    /**
     * The number of Points/Blocks in a TetrisPiece.
     */
    private static final int BLOCKS = 4;
    
    /**
     * The corresponding TetrisPiece.
     */
    private final TetrisPiece myTetrisPiece;
    
    /**
     * The board position of the TetrisPiece.
     */
    private final Point myPosition;

    /**
     * The rotation value of the TetrisPiece.
     */
    private final Rotation myRotation;
    
    /**
     * The MoveableTetrisPiece constructor. The initial rotation
     * is set to the default (START) Rotation.
     * 
     * @param theTetrisPiece The type of TetrisPiece to be created.
     * @param thePosition The position of the Piece on the Board.
     */
    public MovableTetrisPiece(final TetrisPiece theTetrisPiece, final Point thePosition) {        
        this(theTetrisPiece, thePosition, Rotation.START);
    }

    /**
     * The second MoveableTetrisPiece constructor. The initial rotation 
     * in this constructor is a method parameter, so a TetrisPiece can be given
     * any Rotation and Position.
     * 
     * @param theTetrisPiece The type of TetrisPiece to be created.
     * @param thePosition The position of the Piece on the Board.
     * @param theRotation The rotational state of the TetrisPiece.
     */
    public MovableTetrisPiece(final TetrisPiece theTetrisPiece,
                              final Point thePosition, 
                              final Rotation theRotation) {        
        myTetrisPiece = theTetrisPiece;
        myPosition = thePosition;
        myRotation = theRotation;
    }
  
    /**
     * Return the width of the MovableTetrisPiece.
     * 
     * @return The width of the MovableTetrisPiece.
     */
    public int getWidth() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (final Point block : getLocalPoints()) {
            min = Math.min(min, block.getX());
            max = Math.max(max, block.getX());
        }
        
        return max - min + 1;
    }

    /**
     * Return the height of the MovableTetrisPiece.
     * 
     * @return The height of the MovableTetrisPiece.
     */
    public int getHeight() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (final Point block : getLocalPoints()) {
            min = Math.min(min, block.getY());
            max = Math.max(max, block.getY());
        }
        
        return max - min + 1;
    }
    
    /**
     * The getBlock method. This returns the Block type
     * of the TetrisPiece.
     * 
     * @return The Block type of the TetrisPiece.
     */
    public Block getBlock() {
        return myTetrisPiece.getBlock();
    }
    
    /**
     * The overridden toString method. Generates a String representation
     * of a MovableTetrisPiece object.
     * 
     * @return A String representation of a MovableTetrisPiece.
     */
    @Override
    public String toString() {        
        final StringBuilder sb = new StringBuilder();
        final String[][] blocks = new String[BLOCKS][BLOCKS];
        
        for (int h = 0; h < BLOCKS; h++) {
            for (int w = 0; w < BLOCKS; w++) {
                blocks[w][h] = " ";
            }
        }       
        
        for (final Point block : getLocalPoints()) {
            blocks[block.getY()][block.getX()] =
                myTetrisPiece.getBlock().toString();
        }

        for (int h = BLOCKS - 1; h >= 0; h--) {
            for (int w = 0; w < BLOCKS; w++) {
                if (blocks[h][w] != null) {
                    sb.append(blocks[h][w]);
                }
            }
            sb.append('\n');
        }
        
        return sb.toString();
    }
 
    /**
     * The getTetrisPiece method. This method returns what type of
     * TetrisPiece is contained by a MoveableTetrisPiece.
     * 
     * @return The TetrisPiece describing this piece.
     */
    protected TetrisPiece getTetrisPiece() {
        return myTetrisPiece;
    }
    
    /**
     * The getPosition method. This method returns the current 
     * position of the TetrisPiece.
     * 
     * @return The position of the MovableTetrisPiece.
     */
    protected Point getPosition() {
        return myPosition;
    }

    /**
     * The getRotation method. This method returns the current 
     * rotation of the movable TetrisPiece.
     * 
     * @return The current rotation of the MovableTetrisPiece.
     */
    protected Rotation getRotation() {
        return myRotation;
    }

    /**
     * The getBoardPoints method. This method returns an array containing
     * the TetrisPiece points rotated and translated to board coordinates.
     * 
     * @return An array of Points representing the MovableTetrisPiece.
     */
    protected Point[] getBoardPoints() {
        return getPoints(myPosition);
    }
    
    /**
     * The rotate method. This rotates the TetrisPiece clockwise; more 
     * specifically, this method creates and returns an identical MovableTetrisPiece 
     * that has the same position but is rotated clockwise.
     * 
     * @return A new and rotated MovableTetrisPiece.
     */
    protected MovableTetrisPiece rotate() {
        return new MovableTetrisPiece(myTetrisPiece, myPosition, myRotation.clockwise());
    }

    /**
     * The left method. This moves the TetrisPiece to the left; more 
     * specifically, this method creates and returns an identical MovableTetrisPiece 
     * that has the same rotation but is moved to the left by one unit.
     * 
     * @return A new and left-moved MovableTetrisPiece.
     */
    protected MovableTetrisPiece left() {
        return new MovableTetrisPiece(myTetrisPiece, myPosition.transform(-1, 0), myRotation);
    }

    /**
     * The right method. This moves the TetrisPiece to the right; more 
     * specifically, this method creates and returns an identical MovableTetrisPiece 
     * that has the same rotation but is moved to the right by one unit.
     * 
     * @return A new and right-moved MovableTetrisPiece.
     */
    protected MovableTetrisPiece right() {
        return new MovableTetrisPiece(myTetrisPiece, myPosition.transform(1, 0), myRotation);
    }

    /**
     * The down method. This moves the TetrisPiece down; more 
     * specifically, this method creates and returns an identical MovableTetrisPiece 
     * that has the same rotation but is moved down by one unit.
     * 
     * @return A new and moved-down MovableTetrisPiece.
     */
    protected MovableTetrisPiece down() {
        return new MovableTetrisPiece(myTetrisPiece, myPosition.transform(0, -1), myRotation);
    }
    
    /**
     * The setPosition method. This method returns a new MovableTetrisPiece 
     * of the same TetrisPiece type and same Rotation at an input location.
     * 
     * @param thePosition The desired location for the new MovableTetrisPiece.
     * @return A new MovableTetrisPiece at the input location.
     */
    protected MovableTetrisPiece setPosition(final Point thePosition) {
        return new MovableTetrisPiece(myTetrisPiece, thePosition, myRotation);
    }

    /**
     * The getPoints method. This method returns an array containing
     * the block points of the MovableTetrisPiece transformed by x and y.
     * 
     * @param thePoint The point to transform the points around.
     * @return An array containing TetrisPiece block points.
     */
    private Point[] getPoints(final Point thePoint) {
        final Point[] blocks = myTetrisPiece.getPoints();
        
        for (int i = 0; i < blocks.length; i++) {
            final Point block = blocks[i];
            if (myTetrisPiece != TetrisPiece.O) {
                switch (myRotation) {
                    case QUARTER:
                        blocks[i] = new Point(block.getY(), myTetrisPiece.getWidth() - block.getX() - 1);                       
                        break;
                    case HALF:
                        blocks[i] = new Point(myTetrisPiece.getWidth() - block.getX() - 1, myTetrisPiece.getWidth() - block.getY() - 1);                        
                        break;
                    case THREEQUARTER:                 
                        blocks[i] = new Point(myTetrisPiece.getWidth() - block.getY() - 1, block.getX());                        
                        break;
                    default:
                }
            }
            
            if (thePoint != null) {
                blocks[i] = blocks[i].transform(thePoint);
            }
        }

        return blocks;
    }
    
    /**
     * The getLocalPoints method. This method returns the local Points of 
     * a MovableTetrisPiece.
     * 
     * @return An array containing TetrisPiece block points.
     */
    private Point[] getLocalPoints() {
        return getPoints(null);
    }

}