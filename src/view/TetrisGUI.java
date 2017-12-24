package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Board;

/**
 * The TetrisGUI class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public class TetrisGUI extends KeyAdapter implements Observer {
    
    /**
     * The Board object used by the GUI.
     */
    protected static Board myGameBoard;
    
    /**
     * The program's frame and pop-up menu icon.
     */
    private static final String ICON_IMAGE = "./media/icon.png";
    
    /**
     * A String representing the file location of the background image.
     */
    private static final String BACKGROUND_IMG = "./media/background.jpg";
    
    /**
     * The background Color used for the game panel.
     */
    private static final Color BACKGROUND_COLOR =  new Color(144, 23, 23);
    
    /**
     * The timer speed of the game on the "Easy" setting.
     */
    private static final int EASY_TIMER_SPEED = 500;   
    
    /** 
     * A Dimension object representing the frame size when sized "Default".
     */
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(800, 800);
    
    /**
     * The key binding for moving the piece left.
     */
    private String myMoveLeftControl;
    
    /**
     * The key binding for moving the piece right.
     */
    private String myMoveRightControl;
    
    /**
     * The key binding for rotating the piece.
     */
    private String myRotateControl;
    
    /**
     * The key binding for moving the piece down.
     */
    private String myDownControl;
    
    /**
     * The key binding for dropping the piece.
     */
    private String myDropControl;
    
    /**
     * The Timer used by the GUI.
     */
    private Timer myGameTimer;
    
    /**
     * The GamePanel object used to draw and represent the game.
     */
    private GamePanel myGamePanel;
    
    /**
     * The scorePanel of the game information display.
     */
    private ScorePanel myScorePanel;
    
    /**
     * The nextPiecePanel of the game information display.
     */ 
    private NextPiecePanel myNextPiecePanel;
    
    /**
     * The line cleared WAV file represented as a Clip object.
     */
    private Clip myLineClearedSound;
    
    /**
     * The game over WAV file represented as a Clip object.
     */
    private Clip myGameOverSound; 
    
    /**
     * The start() method of the TetrisGUI class.
     */
    public void start() {
        setUpFrame();
    }
    
    /**
     * The setUpFrame() method of the TetrisGUI class.
     */
    private void setUpFrame() {        
        /* Initializing some class fields */
        myGameBoard = new Board();
        myGameTimer = new Timer(EASY_TIMER_SPEED, new TimerListener()); 
        myMoveLeftControl = "A";
        myMoveRightControl = "D";
        myRotateControl = "W";
        myDownControl = "S";
        myDropControl = "Space";
        
        /* Initializing the GUI's JFrame. */
        final JFrame frame = new JFrame("Tetris - Merry Christmas!");        
        frame.setIconImage(new ImageIcon(ICON_IMAGE).getImage());        
        
        /* Setting the background image onto the frame. */
        final JLabel background = new JLabel(new ImageIcon(BACKGROUND_IMG));
        frame.add(background, BorderLayout.CENTER);       
        
        /* Adding the GUI elements to the frame. */        
        frame.add(createGameDisplayPanel(), BorderLayout.WEST);
        frame.add(createGameInfoPanel(), BorderLayout.EAST);
        
        /* Tidying up the properties of the frame. */
        final TetrisMenuBar menuBar = new TetrisMenuBar(frame, myGameTimer, 
                                                  myGamePanel, myScorePanel, myNextPiecePanel);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULT_FRAME_SIZE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        /* Adding the KeyListener to the GUI. */
        frame.addKeyListener(this);
        
        /* Adding the GUI to the game board's list of observers. */
        myGameBoard.addObserver(this);
        myGameBoard.addObserver(menuBar);
        
        /* Adding the background music to the game. */
        try {           
            final AudioInputStream lnClear = AudioSystem.getAudioInputStream(
                                     new File("./media/line_cleared.wav").getAbsoluteFile());
            myLineClearedSound = AudioSystem.getClip();
            myLineClearedSound.open(lnClear);
            
            final AudioInputStream gameOver = AudioSystem.getAudioInputStream(
                                        new File("./media/game_over.wav").getAbsoluteFile());
            myGameOverSound = AudioSystem.getClip();
            myGameOverSound.open(gameOver);
            
        } catch (final FileNotFoundException ex) {
            /* Catching exceptions required by Eclipse */
            JOptionPane.showMessageDialog(null, 
                                          "Sound File(s) Not Found!",
                                        "File Not Found", JOptionPane.INFORMATION_MESSAGE,
                                        new ImageIcon(ICON_IMAGE));
        } catch (final IOException ex) {
            JOptionPane.showMessageDialog(null, 
                                          "Sound File(s) I/O Problem!",
                                        "File IO Exception", JOptionPane.INFORMATION_MESSAGE,
                                        new ImageIcon(ICON_IMAGE));            
        } catch (final UnsupportedAudioFileException ex) {
            JOptionPane.showMessageDialog(null, 
                                          "Unsupported Sound File(s)!",
                                        "Unsupported Sound", JOptionPane.INFORMATION_MESSAGE,
                                        new ImageIcon(ICON_IMAGE));        
        } catch (final LineUnavailableException ex) {
            JOptionPane.showMessageDialog(null, 
                                          "Line Unavailable!",
                                        "Line Unavailable Exception", 
                                        JOptionPane.INFORMATION_MESSAGE,
                                        new ImageIcon(ICON_IMAGE));
        }
    }
    
    /**
     * The createGameDisplayPanel() method of the TetrisGUI class.
     * 
     * @return The current game panel.
     */
    private JPanel createGameDisplayPanel() {
        /* Creating a JPanel to hold our GamePanel object.*/
        final JPanel gameDisplayPanel = new JPanel();
        
        /* Creating and setting a centered, titled border for the panel. */
        final TitledBorder border = new TitledBorder(new LineBorder(Color.GREEN),
                                                     "Tetris",
                                                     TitledBorder.CENTER,
                                                     TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.WHITE);
        gameDisplayPanel.setBorder(border);
        
        /* Initializing the GamePanel. */
        final int defaultBlockSize = 25;
        myGamePanel = new GamePanel(defaultBlockSize);
        
        /* Adding the GamePanel to the list of the board's observers. */
        myGameBoard.addObserver(myGamePanel);
        
        /* Adding the game panel to the Game Display Panel and setting properties. */
        gameDisplayPanel.setLayout(new GridBagLayout());
        gameDisplayPanel.add(myGamePanel); 
        gameDisplayPanel.setBackground(BACKGROUND_COLOR);
        
        return gameDisplayPanel;
    }
    
    /**
     * The createGameInfoPanel() method of the TetrisGUI class.
     * 
     * @return The current game information panel.
     */
    private Box createGameInfoPanel() {        
        /* Creating a Box object to store individual game info panels in. */
        final Box gameInfoPanel = new Box(BoxLayout.PAGE_AXIS);
        
        /* Creating and instantiating the individual info panels. */
        myNextPiecePanel = new NextPiecePanel();
        myScorePanel = new ScorePanel(myGameTimer);
        final ControlsPanel controlsPanel = new ControlsPanel();
        final MusicPanel musicPanel = new MusicPanel();
        
        /* Adding the "Next Piece"/"Score" panels to the list of the game board's observers.*/
        myGameBoard.addObserver(myNextPiecePanel);
        myGameBoard.addObserver(myScorePanel);
        
        /* Adding the panels to the gameInfoPanel box object. */
        gameInfoPanel.add(myNextPiecePanel);
        gameInfoPanel.add(myScorePanel);
        gameInfoPanel.add(controlsPanel);
        gameInfoPanel.add(musicPanel);      
        
        return gameInfoPanel;
    }
    
    /**
     * The drawGameOver method of the TetrisMenuBar class.
     */
    protected void drawGameOver() {
        /*Play the "Game Over" sound. */
        myGameOverSound.start();
        
        /* Display a pop-up dialog saying that the current game is over. */
        JOptionPane.showMessageDialog(null, 
                                      "Bah Humbug! Game Over!\n"
                                      + "Better luck next time!",
                                    "Game Over!", JOptionPane.INFORMATION_MESSAGE,
                                    new ImageIcon(ICON_IMAGE));
        
        /* Then, "reset" the sound so it can be primed for starting again. */
        myGameOverSound.setFramePosition(0);
    }
    
    /**
     * The overridden update method of the TetrisGUI class.
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        /* Listens for the game to be over and plays "Game Over" sound. */
        /* And, listens for a line to be cleared and plays "Line Cleared" sound. */
        if (theObservable instanceof Board && theData instanceof Boolean) {
            myGameTimer.stop();
            drawGameOver();            
        } else if (theObservable instanceof Board && theData instanceof Integer[]) {           
            myLineClearedSound.start();
            myLineClearedSound.setFramePosition(0);
            
        }
    }
    
    /**
     * The overridden keyPressed method of the TetrisGUI class.
     */
    @Override
    public void keyPressed(final KeyEvent theEvent) {
        super.keyPressed(theEvent);
        
        /* Create a String out of the KeyEvent received. */
        final String keyText = KeyEvent.getKeyText(theEvent.getKeyCode());
        
        /* Check to see if the game timer is running before executing method calls. */
        if (myGameTimer.isRunning()) {
            if (keyText.equals(myMoveLeftControl)) {
                myGameBoard.left();            
            } else if (keyText.equals(myMoveRightControl)) {
                myGameBoard.right();
            } else if (keyText.equals(myRotateControl)) {
                myGameBoard.rotate();
            } else if (keyText.equals(myDownControl)) {
                myGameBoard.down();
            } else if (keyText.equals(myDropControl)) {
                myGameBoard.drop();
            }
        }        
    }
    
    /**
     * The TimerListener inner class which listens to the timer and
     * manipulates the GUI elements.
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            /* Calls the down method for each increment of the timer. */
            myGameBoard.down();            
        }
    }
    
}