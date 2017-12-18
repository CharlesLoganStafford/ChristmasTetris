package view;

import java.awt.EventQueue;

/**
 * The TetrisMain driver class for the Christmas Tetris program. This class simply
 * has a main method which invokes a Swing-based GUI for the game.
 * 
 * DONE
 * 
 * @author Logan Stafford
 * @version 2.0
 */
public final class TetrisMain {
    
    /**
     * A dummy constructor used to prevent instantiation.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    
    /**
     * The main method of the TetrisMain class, which invokes
     * 
     * @param theArgs Command line arguments - not application for this program.
     */
    public static void main(final String[] theArgs) {                
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
}