package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * The Board class. This class represents a Tetris board with all
 * corresponding properties and actions.
 * 
 * @author Logan Stafford
 * @version 1.5
 * @date October 11th, 2020
 */
@SuppressWarnings("deprecation")
public class Board extends Observable {
    
    /**
     * An integer representing the default width of a board.
     */
    private static final int DEFAULT_BOARD_WIDTH = 10;

    /**
     * An integer representing the default height of a board.
     */
    private static final int DEFAULT_BOARD_HEIGHT = 20;
    
    /**
     * An integer representing the current width of a board.
     */
    private int myCurrentBoardWidth;

    /**
     * An integer representing the current height of a board.
     */
    private int myCurrentBoardHeight;
    
    /**
     * A List containing all the frozen blocks on a board.
     */
    private final List<Block[]> myFrozenBlocks;
    
    /**
     * A boolean value of the current game status (Think: Is the game over?)
     */
    private boolean myGameOver;

    /**
     * A List containing a non-random sequence of TetrisPieces to loop through.
     */
    private List<TetrisPiece> myNonRandomPieces;

    /**
     * An integer representing current index in the non-random piece list "myNonRandomPieces".
     */
    private int mySequenceIndex;
    
    /**
     * A TetrisPiece object that is next to be dropped.
     */
    private TetrisPiece myNextPiece;
    
    /**
     * A MoveableTetrisPiece object that is currently movable (the current piece).
     */
    private MovableTetrisPiece myCurrentPiece;

    /**
     * A boolean value to indicate when moving a piece down is part of a drop operation -
     * used to prevent the Board from notifying observers for each incremental
     * down movement in the drop.
     */
    private boolean myDropStatus;

    /**
     * The Board constructor, used for default-sized boards.
     */
    public Board() {
        this(DEFAULT_BOARD_WIDTH, DEFAULT_BOARD_HEIGHT);
    }

    /**
     * The Board constructor, used for non-default sized boards.
     * 
     * @param theWidth The desired width of the new board.
     * @param theHeight The desired height of the new board.
     */
    public Board(final int theWidth, final int theHeight) {
        super();
        
        myCurrentBoardWidth = theWidth;
        myCurrentBoardHeight = theHeight;
        
        myFrozenBlocks = new LinkedList<Block[]>();        
        myNonRandomPieces = new ArrayList<TetrisPiece>();
        
        /* Setting the index of the non-random pieces list to 0 (the first element/piece). */
        mySequenceIndex = 0;
    }
    
    /**
     * The setSize method, which sets the values of the myBoardWidth and myBoardHeight
     * to the input integers.
     * 
     * @param theWidth The new board width.
     * @param theHeight The new board height.
     */
    public void setSize(final int theWidth, final int theHeight) {
        myCurrentBoardWidth = theWidth;
        myCurrentBoardHeight = theHeight;        
    }
    
    /**
     * The getWidth method, which simply returns the current width of the board.
     * 
     * @return The current width of the board.
     */
    public int getWidth() {
        return myCurrentBoardWidth;
    }

    /**
     * The getHeight method, which simply returns the current Height of the board.
     * 
     * @return The current Height of the board.
     */
    public int getHeight() {
        return myCurrentBoardHeight;
    }

    /**
     * The newGame method. This resets the board for a new game. 
     * This method must be called before every new game.
     */
    public void newGame() {
        
        /* Resets the index of the non-random piece list back to the beginning of the list. */
        mySequenceIndex = 0;
        
       /* Resets the list of currently frozen blocks and re-populates the list.*/
        myFrozenBlocks.clear();
        for (int h = 0; h < myCurrentBoardHeight; h++) {
            myFrozenBlocks.add(new Block[myCurrentBoardWidth]);
        }

        /* Resetting the "status" of the game. */
        myGameOver = false;
        myCurrentPiece = nextMovablePiece(true);
        myDropStatus = false;
        
        /* Notifying all observer classes that the object data has changed. */
        setChanged();
        notifyObservers(toString());
    }

    /**
     * Sets a non random sequence of pieces to loop through.
     * 
     * @param thePieces the List of non random TetrisPieces.
     */
    public void setPieceSequence(final List<TetrisPiece> thePieces) {
        myNonRandomPieces = new ArrayList<TetrisPiece>(thePieces);
        mySequenceIndex = 0;
        myCurrentPiece = nextMovablePiece(true);
    }
    
    /**
     * Try to move the movable piece down.
     * Freeze the Piece in position if down tries to move into an illegal state.
     * Clear full lines.
     */
    public void down() {
        if (!move(myCurrentPiece.down())) {
            // the piece froze, so clear lines and update current piece
            addPieceToBoardData(myFrozenBlocks, myCurrentPiece);
            checkRows();
            if (!myGameOver) {
                myCurrentPiece = nextMovablePiece(false);
            }
            setChanged();
            notifyObservers(toString());
        }
    }

    /**
     * Move the current piece left.
     */
    public void left() {
        if (myCurrentPiece != null) {
            move(myCurrentPiece.left());
        }
    }

    /**
     * Move the current piece right.
     */
    public void right() {
        if (myCurrentPiece != null) {
            move(myCurrentPiece.right());
        }
    }

    /**
     * Try to rotate the movable piece in the clockwise direction.
     */
    public void rotate() {
        if (myCurrentPiece != null) {           
            if (myCurrentPiece.getTetrisPiece() == TetrisPiece.O) {
                move(myCurrentPiece.rotate());
            } else {
                final MovableTetrisPiece cwPiece = myCurrentPiece.rotate();
                final Point[] offsets = WallKick.getkicks(cwPiece.getTetrisPiece(),
                                                    myCurrentPiece.getRotation(),
                                                    cwPiece.getRotation());
                for (final Point p : offsets) {
                    final Point offsetLocation = cwPiece.getPosition().transform(p);
                    final MovableTetrisPiece temp = cwPiece.setPosition(offsetLocation);
                    if (move(temp)) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Drop the piece down until the piece freezes in place.
     */
    public void drop() {
        if (!myGameOver) {
            myDropStatus = true;
            while (isPieceLegal(myCurrentPiece.down())) {
                down();  // move down as far as possible
            }
            myDropStatus = false;
            down();  // move down one more time to freeze in place
        }
    }

    @Override
    public String toString() {
        final List<Block[]> board = getBoard();
        board.add(new Block[myCurrentBoardWidth]);
        board.add(new Block[myCurrentBoardWidth]);
        board.add(new Block[myCurrentBoardWidth]);
        board.add(new Block[myCurrentBoardWidth]);
        
        if (myCurrentPiece != null) {
            addPieceToBoardData(board, myCurrentPiece);
        }
        
        final StringBuilder sb = new StringBuilder();
        for (int i = board.size() - 1; i >= 0; i--) {
            final Block[] row = board.get(i);
            sb.append('|');
            
            for (final Block c : row) {
                if (c == null) {
                    sb.append(' ');
                } else {
                    sb.append(c);
                }
            }
            
            sb.append("|\n");
            
            if (i == this.myCurrentBoardHeight) {
                sb.append(' ');
                for (int j = 0; j < this.myCurrentBoardWidth; j++) {
                    sb.append('-');
                }
                sb.append('\n');
            }
        }
        sb.append('|');
        for (int w = 0; w < myCurrentBoardWidth; w++) {
            sb.append('-');
        }
        sb.append('|');
        return sb.toString();
    }
    
    /**
     * Helper function to check if the current piece can be shifted to the
     * specified position.
     * 
     * @param theMovedPiece the position to attempt to shift the current piece
     * @return True if the move succeeded
     */
    private boolean move(final MovableTetrisPiece theMovedPiece) {
        
        boolean result = false;
        if (isPieceLegal(theMovedPiece)) {
            myCurrentPiece = theMovedPiece;
            result = true;
            if (!myDropStatus) {
                setChanged();
            }
            notifyObservers(toString());
        }
        return result;
    }

    /**
     * Helper function to test if the piece is in a legal state.
     * 
     * Illegal states:
     * - points of the piece exceed the bounds of the board
     * - points of the piece collide with frozen blocks on the board
     * 
     * @param thePiece MovableTetrisPiece to test.
     * @return Returns true if the piece is in a legal state; false otherwise
     */
    private boolean isPieceLegal(final MovableTetrisPiece thePiece) {
        boolean result = true;
        
        for (final Point p : thePiece.getBoardPoints()) {
            if (p.getX() < 0 || p.getX() >= myCurrentBoardWidth) {
                result = false;
            }
            if (p.getY() < 0) {
                result = false;
            }
        }
        return result && !hasCollided(thePiece);      
    }

    /**
     * Adds a movable Tetris piece into a list of board data.
     * 
     * Allows a single data structure to represent the current piece
     * and the frozen blocks.
     * 
     * @param theFrozenBlocks Board to set the piece on.
     * @param thePiece Piece to set on the board.
     */
    private void addPieceToBoardData(final List<Block[]> theFrozenBlocks,
                                     final MovableTetrisPiece thePiece) {
        for (final Point p : thePiece.getBoardPoints()) {
            setPoint(theFrozenBlocks, p, thePiece.getTetrisPiece().getBlock());
        }
    }

    /**
     * Checks the board for complete rows.
     */
    private void checkRows() {
        final List<Integer> completeRows = new ArrayList<>();
        for (final Block[] row : myFrozenBlocks) {
            boolean complete = true;
            for (final Block b : row) {
                if (b == null) {
                    complete = false;
                    break;
                }
            }
            if (complete) {
                completeRows.add(myFrozenBlocks.indexOf(row));
                setChanged();
            }
        }
        // Loop through list backwards removing items by index
        if (!completeRows.isEmpty()) {
            for (int i = completeRows.size() - 1; i >= 0; i--) {
                final Block[] row = myFrozenBlocks.get(completeRows.get(i));
                myFrozenBlocks.remove(row);
                myFrozenBlocks.add(new Block[myCurrentBoardWidth]);
            }
        }
        notifyObservers(completeRows.toArray(new Integer[completeRows.size()]));
    }
    
    /**
     * Helper function to copy the board.
     * 
     * @return A new copy of the board.
     */
    private List<Block[]> getBoard() {
        final List<Block[]> board = new ArrayList<Block[]>();
        for (final Block[] row : myFrozenBlocks) {
            board.add(row.clone());
        }
        return board;
    }

    /**
     * Determines if a point is on the game board.
     * 
     * @param theBoard Board to test.
     * @param thePoint Point to test.
     * @return True if the point is on the board otherwise false.
     */
    private boolean isPointOnBoard(final List<Block[]> theBoard, final Point thePoint) {
        return thePoint.getX() >= 0 && thePoint.getX() < myCurrentBoardWidth && thePoint.getY() >= 0
               && thePoint.getY() < theBoard.size();
    }

    /**
     * Sets a block at a board point.
     * 
     * @param theBoard Board to set the point on.
     * @param thePoint Board point.
     * @param theBlock Block to set at board point.
     */
    private void setPoint(final List<Block[]> theBoard, final Point thePoint,
                          final Block theBlock) {        
        if (isPointOnBoard(theBoard, thePoint)) { 
            final Block[] row = theBoard.get(thePoint.getY());
            row[thePoint.getX()] = theBlock;
        } else if (!myGameOver) {
            myGameOver = true;
            setChanged();
            notifyObservers(myGameOver);
        }
    }

    /**
     * Returns the block at a specific board point.
     * 
     * @param thePoint the specific Point to check
     * @return the Block type at point or null if no block exists.
     */
    private Block getPoint(final Point thePoint) {
        Block b = null;
        if (isPointOnBoard(myFrozenBlocks, thePoint)) {
            b = myFrozenBlocks.get(thePoint.getY())[thePoint.getX()];
        }
        return b;
    }

    /**
     * Helper function to determine of a movable block has collided with set
     * blocks.
     * 
     * @param theTest movable TetrisPiece to test for collision.
     * @return Returns true if any of the blocks has collided with a set board
     *         block.
     */
    private boolean hasCollided(final MovableTetrisPiece theTest) {
        boolean hasPieceCollided = false;
        for (final Point p : theTest.getBoardPoints()) {
            if (getPoint(p) != null) {
        	hasPieceCollided = true;
            }
        }
        return hasPieceCollided;
    }

    /**
     * Gets the next MovableTetrisPiece.
     * 
     * @param theRestart Restart the non random cycle.
     * @return A new MovableTetrisPiece.
     */
    private MovableTetrisPiece nextMovablePiece(final boolean theRestart) {
        if (myNextPiece == null || theRestart) {
            prepareNextMovablePiece();
        }
        
        final TetrisPiece next = myNextPiece;
        
        int startY = myCurrentBoardHeight - 1;
        if (myNextPiece == TetrisPiece.I) {
            startY--; 
        }
        
        prepareNextMovablePiece();
        
        final MovableTetrisPiece nextMovablePiece =
            new MovableTetrisPiece(next,
                                   new Point((myCurrentBoardWidth - myNextPiece.getWidth()) / 2, startY));
        
        if (!myGameOver) {
            setChanged();
            notifyObservers(new MovableTetrisPiece(myNextPiece,
                            new Point((myCurrentBoardWidth - myNextPiece.getWidth()) / 2, startY)));
        }
        
        return nextMovablePiece;
    }
    
    /**
     * Prepares the Next movable piece.
     */
    private void prepareNextMovablePiece() {
        if (myNonRandomPieces == null || myNonRandomPieces.isEmpty()) {
            myNextPiece = TetrisPiece.getRandomPiece();
        } else {
            mySequenceIndex %= myNonRandomPieces.size();
            myNextPiece = myNonRandomPieces.get(mySequenceIndex++);
        }
    }   
}