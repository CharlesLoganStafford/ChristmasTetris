/* 
 * TCSS 305 – Autumn 2016
 * Assignment 6 - Tetris 
 */

package view;

import java.awt.EventQueue;

/**
 * The TetrisMain driver class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 12/9/2016
 */
public final class TetrisMain {
    
    /**
     * Dummy constructor used to prevent instantiation.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    
    /**
     * The main method of the TetrisMain class.
     * 
     * @param theArgs Command line arguments.
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