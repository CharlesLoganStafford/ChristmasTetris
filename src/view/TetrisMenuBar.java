/* 
 * TCSS 305 – Autumn 2016
 * Assignment 6 - Tetris 
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.Board;

/**
 * The TetrisMenuBar class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 12/9/2016
 */
public class TetrisMenuBar extends JMenuBar implements Observer {
    
    /**
     * A generated serialVersionUID for the TetrisMenuBar class.
     */
    private static final long serialVersionUID = -4061552447035646028L;

    /**
     * The program's frame and pop-up menu icon.
     */
    private static final String ICON_IMAGE = "./media/icon.png";
    
    /**
     * A String representing the small grid size.
     */
    private static final String SMALL_GRID_SIZE_STR = "Small (6x12)";   
    
    /**
     * A String representing the default grid size.
     */
    private static final String DEFAULT_GRID_SIZE_STR = "Medium (10x20)";
    
    /**
     * A String representing the large grid size.
     */
    private static final String LARGE_GRID_SIZE_STR = "Large (14x28)";   
    
    /**
     * The timer speed of the game on the "Easy" setting.
     */
    private static final int EASY_TIMER_SPEED = 500;

    /**
     * The timer speed of the game on the "Medium" setting.
     */
    private static final int MED_TIMER_SPEED = 300;
    
    /**
     * The timer speed of the game on the "Hard" setting.
     */
    private static final int HARD_TIMER_SPEED = 100;
    
    /**
     * A String representing the "Easy" difficulty name.
     */
    private static final String EASY_DIFF = "Reindeer";
    
    /**
     * A String representing the "Medium" difficulty name.
     */
    private static final String MED_DIFF = "Santa";
    
    /**
     * A String representing the "Hard" difficulty name.
     */
    private static final String HARD_DIFF = "Scrooge";
    
    /**
     * The number to multiply the score by on "Easy" difficulty.
     */
    private static final int EASY_DIFF_MULTIPLIER = 1;
    
    /**
     * The number to multiply the score by on "Medium" difficulty.
     */
    private static final int MED_DIFF_MULTIPLIER = 2;
    
    /**
     * The number to multiply the score by on "Hard" difficulty.
     */
    private static final int HARD_DIFF_MULTIPLIER = 3;

    /**
     * The frame of the GUI.
     */
    private final JFrame myFrame;
    
    /**
     * The "New Game" menu item.
     */
    private JMenuItem myNewGameButton;
    
    /**
     * The "End Game" menu item.
     */
    private JMenuItem myEndGameButton;
    
    /**
     * The "Pause Game" menu item.
     */
    private JMenuItem myPauseGameButton;
    
    /**
     * The Timer used by the GUI.
     */
    private final Timer myGameTimer;
    
    /**
     * The GamePanel object used to draw and represent the game.
     */
    private final GamePanel myGamePanel;
    
    /**
     * The scorePanel of the game information display.
     */
    private final ScorePanel myScorePanel;
    
    /**
     * The NextPiecePanel of the game.
     */
    private final NextPiecePanel myNextPiecePanel;
    
    /**
     * The background music WAV file represented as a Clip object.
     */
    private Clip myBackgroundMusic;
    
    /**
     * The "game_paused" WAV file represented as a Clip object.
     */
    private Clip myPauseSound;
    
    /**
     * The TetrisMenuBar constructor. 
     * 
     * @param theFrame The JFrame of the program.
     * @param theGameTimer The Timer of the program.
     * @param theGamePanel The GamePanel of the program.
     * @param theScorePanel The ScorePanel of the program.
     * @param theNextPiecePanel The NextPiecePanel of the program.
     */
    public TetrisMenuBar(final JFrame theFrame, final Timer theGameTimer, 
                         final GamePanel theGamePanel, 
                         final ScorePanel theScorePanel, 
                         final NextPiecePanel theNextPiecePanel) {
        super();
        
        /* Initializing fields */
        myFrame = theFrame;
        myGameTimer = theGameTimer;
        myGamePanel = theGamePanel;
        myScorePanel = theScorePanel;
        myNextPiecePanel = theNextPiecePanel;
        
        /* Calling helper method to create the menu bar. */
        createMenuBar();
    }
    
    /**
     * The createMenuBar() method of the class.
     */
    private void createMenuBar() {        
        /* Calling helper methods to create the menu bar. */
        add(fileMenu());
        add(optionsMenu());
        add(aboutMenu());
        
        /* Adding the background music to the game. */
        try {
            final AudioInputStream bgMusic = AudioSystem.getAudioInputStream(
                                     new File("./media/andywilliams.wav").getAbsoluteFile());
            myBackgroundMusic = AudioSystem.getClip();
            myBackgroundMusic.open(bgMusic);
            
            final AudioInputStream pause = AudioSystem.getAudioInputStream(
                                     new File("./media/game_paused.wav").getAbsoluteFile());
            myPauseSound = AudioSystem.getClip();
            myPauseSound.open(pause);
            
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
     * The fileMenu method of the TetrisGUI class.
     * 
     * @return The "File" JMenu used in the menu bar of the program.
     */
    private JMenu fileMenu() {
        /* Creating and instantiating the "File" JMenu object.*/
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);           
               
        /* Adds the components to the File menu using their helper methods..*/
        fileMenu.add(createNewGameButton());
        fileMenu.add(createEndGameButton());
        fileMenu.addSeparator();
        fileMenu.add(createPauseGameButton());
        fileMenu.addSeparator();
        fileMenu.add(createQuitButton());
        
        return fileMenu;
    }
    
    /**
     * The createNewGameButton method of the TetrisGUI class.
     * 
     * @return The "New Game" button used in the "File" menu.
     */
    private JMenuItem createNewGameButton() {
        /* Instantiating the "New Game" JMenuItem and setting its shortcuts. */
        myNewGameButton = new JMenuItem("New Game");
        myNewGameButton.setMnemonic(KeyEvent.VK_N);
        myNewGameButton.setAccelerator(KeyStroke.getKeyStroke('N',
                                        java.awt.event.InputEvent.CTRL_MASK));
         
        /* Creating an ActionListener for the "New Game" button. */
        myNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGameTimer.restart();                
                TetrisGUI.myGameBoard.newGame();                              
                myNewGameButton.setEnabled(false);
                myEndGameButton.setEnabled(true);
                myPauseGameButton.setEnabled(true);
                
            }
        });
        
        return myNewGameButton;
    }
    
    /**
     * The createEndGameButton method of the TetrisGUI class.
     * 
     * @return The "End Game" button used in the "File" menu.
     */
    private JMenuItem createEndGameButton() {
        /* Instantiating the "End Game" JMenuItem and setting its shortcuts. */
        myEndGameButton = new JMenuItem("End Game");
        myEndGameButton.setMnemonic(KeyEvent.VK_E);       
        myEndGameButton.setEnabled(false);        
        myEndGameButton.setAccelerator(KeyStroke.getKeyStroke('E',
                                       java.awt.event.InputEvent.CTRL_MASK));
        
        /* Creating an ActionListener for the "End Game" button. */
        myEndGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGameTimer.stop();                 
                myEndGameButton.setEnabled(false);
                myPauseGameButton.setEnabled(false);
                myNewGameButton.setEnabled(true);
            }
        });
        
        return myEndGameButton;
    }
    
    /**
     * The createPauseGameButton method of the TetrisGUI class.
     * 
     * @return The "Pause Game" button used in the "File" menu.
     */
    private JMenuItem createPauseGameButton() {
        /* Instantiating the "Pause Game" JMenuItem and setting its shortcuts. */
        myPauseGameButton = new JMenuItem("Pause/Resume");
        myPauseGameButton.setMnemonic(KeyEvent.VK_P);
        myPauseGameButton.setEnabled(false);
        myPauseGameButton.setAccelerator(KeyStroke.getKeyStroke('P',
                                          java.awt.event.InputEvent.CTRL_MASK));
        
        /* Creating an ActionListener for the "Pause Game" button. */
        myPauseGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {               
                if (myGameTimer.isRunning()) {
                    myGameTimer.stop();
                    drawPause();                    
                } else {
                    myGameTimer.start();                
                }
            }
        });
        
        return myPauseGameButton;
    }
    
    /**
     * The createQuitButton method of the TetrisMenuBar class.
     * 
     * @return The "Quit" button used in the "File" menu.
     */
    private JMenuItem createQuitButton() {
        /* Instantiating the "Quit" JMenuItem and setting its shortcuts. */
        final JMenuItem quitButton = new JMenuItem("Quit");
        quitButton.setMnemonic(KeyEvent.VK_Q);
        quitButton.setAccelerator(KeyStroke.getKeyStroke('Q',
                                  java.awt.event.InputEvent.CTRL_MASK));
        
        /* Creating an ActionListener for the "Quit" button. */
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGameTimer.stop();
                myFrame.dispose();
            }
        });
        
        return quitButton;
    }
    
    /**
     * The optionsMenu method of the TetrisMenuBar class.
     * 
     * @return The "Game" JMenu used in the menu bar of the program.
     */
    private JMenu optionsMenu() {        
        /* CCreating and instantiating the "Options" JMenu object. */
        final JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);        
        
        /* Adds the components to the "Options" menu using helper methods. */
        optionsMenu.add(createSoundToggleMenuItem());
        optionsMenu.add(createGridToggleMenuItem());
        optionsMenu.addSeparator();
        optionsMenu.add(createChangeGridSizeSubMenu());
        optionsMenu.addSeparator();
        optionsMenu.add(createDifficultySubMenu());
                
        return optionsMenu;
    }
    
    /**
     * The createSoundToggleMenuItem method of the TetrisMenuBar class.
     * 
     * @return The toggle sound check box of the Options menu.
     */
    private JCheckBoxMenuItem createSoundToggleMenuItem() {
        /* Creating and instantiating the sound check box object. */
        final JCheckBoxMenuItem soundToggleButton = new JCheckBoxMenuItem("Toggle "
                        + "Music On/Off");
        soundToggleButton.setIcon(new ImageIcon("./media/sound_icon.png"));
        soundToggleButton.setAccelerator(KeyStroke.getKeyStroke('S', 
                                                  java.awt.event.InputEvent.CTRL_MASK));
        
        /* Adding an ActionListener to the sound check box. */
        soundToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (soundToggleButton.isSelected()) {
                    enableMusic(true);
                } else {
                    enableMusic(false);
                }
            }
        });
        
        return soundToggleButton;
    }
    
    /**
     * The createGridToggleMenuItem method of the TetrisMenuBar class.
     * 
     * @return The toggle game grid check box of the Options menu.
     */
    private JCheckBoxMenuItem createGridToggleMenuItem() {
        /* Creating and instantiating the grid toggle check box object. */
        final JCheckBoxMenuItem gridToggleButton = new JCheckBoxMenuItem("Toggle Game"
                        + " Grid On/Off");
        gridToggleButton.setIcon(new ImageIcon("./media/grid_icon.png"));
        gridToggleButton.setAccelerator(KeyStroke.getKeyStroke('G',
                                                         java.awt.event.InputEvent.CTRL_MASK));
        gridToggleButton.setSelected(true);
       
        /* Adding an ActionListener to the grid check box. */
        gridToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGamePanel.enableGameGridOverlay(gridToggleButton.isSelected());
                myNextPiecePanel.enableGameGridOverlay(gridToggleButton.isSelected());
            } 
        });
        
        return gridToggleButton;
    }
    
    /**
     * The createChangeGridSizeMenuItem method of the TetrisMenuBar class.
     * 
     * @return The grid size changing dialog of the Options menu.
     */
    private JMenu createChangeGridSizeSubMenu() {
        /* Creating and instantiating the gridSize JMenu object. */
        final JMenu gridSizeSubMenu = new JMenu("Change Grid Size    ");
        gridSizeSubMenu.setIcon(new ImageIcon("./media/gridsize_icon.png"));
        gridSizeSubMenu.setMnemonic(KeyEvent.VK_G);     
        
        /* Adding radio buttons to the JMenu to select grid size. */
        final List<String> gridSizeList = new ArrayList<String>();
        gridSizeList.add(SMALL_GRID_SIZE_STR);
        gridSizeList.add(DEFAULT_GRID_SIZE_STR);
        gridSizeList.add(LARGE_GRID_SIZE_STR);
        
        final ButtonGroup gridGroup = new ButtonGroup();
        
        for (final String size : gridSizeList) {   
            final JRadioButtonMenuItem aSize = new JRadioButtonMenuItem(size);            
            gridGroup.add(aSize);
            gridSizeSubMenu.add(aSize);
            
            if (DEFAULT_GRID_SIZE_STR.equals(size)) {
                aSize.setSelected(true);
            }
            
            /* Creating an action listener for each JRadioButtonMenuItem. */
            aSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    setGridSize(size);
                }
            });
        }
        
        return gridSizeSubMenu;
    }
    
    /**
     * A helper method for the actionListener in the GridSize selector.
     * 
     * @param theSize A String to set the size against.
     */
    protected void setGridSize(final String theSize) {
        if (theSize.equals(DEFAULT_GRID_SIZE_STR)) {
            final int defaultBlockSize = 25;
            final int defWidth = 10;
            final int defHeight = 20;  
            myGamePanel.setBlockSize(defaultBlockSize);
            TetrisGUI.myGameBoard.setSize(defWidth, defHeight);
        } else if (theSize.equals(LARGE_GRID_SIZE_STR)) { 
            final int largeBlockSize = 18;
            final int largeWidth = 14;
            final int largeHeight = 28;    
            myGamePanel.setBlockSize(largeBlockSize);
            TetrisGUI.myGameBoard.setSize(largeWidth, largeHeight);                        
        } else if (theSize.equals(SMALL_GRID_SIZE_STR)) {
            final int smallBlockSize = 42;
            final int smallWidth = 6;
            final int smallHeight = 12;
            myGamePanel.setBlockSize(smallBlockSize);
            TetrisGUI.myGameBoard.setSize(smallWidth, smallHeight);
        }
        myGamePanel.repaint();        
    }

    /**
     * The createDifficultySubMenu method of the TetrisMenuBar class. 
     * 
     * @return The difficulty selection sub-menu of the Options menu.
     */
    private JMenu createDifficultySubMenu() {
        /* Creating and instantiating the setDifficulty JMenu object. */
        final JMenu difficultySubMenu = new JMenu("Set Difficulty  ");
        difficultySubMenu.setIcon(new ImageIcon("./media/diff_icon.png"));
        difficultySubMenu.setMnemonic(KeyEvent.VK_D);       
        
        /* Adding radio buttons to the JMenu to select grid size. */
        final List<String> diffList = new ArrayList<String>();
        diffList.add(EASY_DIFF);
        diffList.add(MED_DIFF);
        diffList.add(HARD_DIFF);
        
        final ButtonGroup diffGroup = new ButtonGroup();
        
        for (final String diff : diffList) {   
            final JRadioButtonMenuItem difficulty = new JRadioButtonMenuItem(diff);
            final String diffIcon = "./media/" + diff.toLowerCase(Locale.ENGLISH)
                                                                    + "_icon.png";
            difficulty.setIcon(new ImageIcon(diffIcon));
            diffGroup.add(difficulty);
            difficultySubMenu.add(difficulty);
            
            if (EASY_DIFF.equals(diff)) {
                difficulty.setSelected(true);
            }
            
            /* Creating an action listener for each JRadioButtonMenuItem. */
            difficulty.addActionListener(new ActionListener() {            
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    setDifficulty(diff);
                }
            });
        }
        
        return difficultySubMenu;
    }

    /**
     * The aboutMenu method of the TetrisMenuBar class.
     * 
     * @return The "About" JMenu used in the menu bar of the program.
     */
    private JMenu aboutMenu() {
        /* Creating and instantiating the "About" JMenu object. */
        final JMenu aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic(KeyEvent.VK_A);
        
        /* Adding the components to the "About" menu using their helper methods. */
        aboutMenu.add(createScoringMenu());
        aboutMenu.add(createCreditsMenu());
        
        return aboutMenu;
    }
    
    /**
     * The createScoringMenu method of the TetrisMenuBar class.
     * 
     * @return The "Scoring" menu item of the About menu.
     */
    private JMenuItem createScoringMenu() {
        /* Creating and instantiating the "Scoring" JMenuItem. */
        final JMenuItem scoring = new JMenuItem("Scoring...");
        scoring.setMnemonic(KeyEvent.VK_S);
        
        final String scoreString = "The game starts at Level 1. \n"
                        + "The level increases by 1 for every 5 cleared lines.\n"
                        + "For every line cleared, the score is "
                        + "increased by X points:\n\n"
                        + " X = (level * 100 * difficulty modifier)\n\n"
                        + "The difficulty modifier is:\n"
                        + "Easy = 1     Medium = 2     Hard = 3\n";
        
        /* Adding an ActionListener to the "Scoring" menu item.*/
        scoring.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(myFrame, 
                                 scoreString,
                               "Scoring Method", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        return scoring;
    }
    
    /**
     * The createCreditsMenu method of the TetrisMenuBar class.
     * 
     * @return The "Credits" menu item of the About menu.
     */
    private JMenuItem createCreditsMenu() {
        /* Creating and instantiating the "Credits" JMenuItem. */
        final JMenuItem credits = new JMenuItem("Credits...");
        credits.setMnemonic(KeyEvent.VK_C);
        
        final String creditsList = "TCSS 305 - Tetris (Autumn 2016) by Logan Stafford\n\n"
                        + "Credits:\nMany thanks to the Java API and Oracle Tutorials "
                        + "for various aspects of programming the GUI.\n\n"
                        + "Image Credits:\nProgram Icon: http://www.freeiconspng.com/"
                        + "\nSet Difficulty Menu Icon: https://www.shopify.com/\n"
                        + "Sound Toggle Icon: http://www.freeiconspng.com/\n"
                        + "Grid Toggle Icon: http://www.dataknet.com/\nChange "
                        + "Grid Size Icon: https://www.iconfinder.com/\n"
                        + "Window Background: http://www.pixelstalk.net/\nMusic "
                        + "Panel Image: http://www.icons.iconarchive.com/\n\n"
                        + "Sound Credits:\nBackground Music: Andy Williams - "
                        + "The Most Wonderful Time of the Year (1963)\n"
                        + "Line Cleared Sound: https://www.freesound.org/"
                        + "people/SFXsource.com/sounds/110315/\nGame Over"
                        + " Sound: https://www.freesound.org/people/noirenex/"
                        + "sounds/159408/\nGame Paused Sound: https://www.freesound."
                        + "org/people/Reitanna/sounds/241170/";
        
        /* Adding an ActionListener to the "Credits" menu item.*/
        credits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null,
                    creditsList, "Credits", JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(ICON_IMAGE));
            }
        });
        
        return credits;
    }
    
    /**
     * The drawPause method of the TetrisMenuBar class.
     */
    private void drawPause() {
        /* Play the "Pause" sound effect. */
        myPauseSound.start();
        
        /* Display a pop-up dialog saying that the current game is paused. */
        JOptionPane.showMessageDialog(null, 
                                      "Ho Ho Ho! Game Paused",
                                    "Paused", JOptionPane.INFORMATION_MESSAGE,
                                    new ImageIcon(ICON_IMAGE));
        /* Then, "reset" the sound so it can be primed for starting again. */
        myPauseSound.setFramePosition(0);
    }
    
    /**
     * The setDifficulty method of the TetrisMenuBar class.
     * 
     * @param theDiff The requested difficulty as a String.
     */
    private void setDifficulty(final String theDiff) {        
        /* Setting the speed of the timer based on what String is passed in. */
        if (EASY_DIFF.equals(theDiff)) {
            myGameTimer.setDelay(EASY_TIMER_SPEED);
            myScorePanel.setDifficulty(EASY_DIFF_MULTIPLIER);
            myScorePanel.setTimerSpeed(EASY_TIMER_SPEED);
        } else if (MED_DIFF.equals(theDiff)) {
            myGameTimer.setDelay(MED_TIMER_SPEED);
            myScorePanel.setDifficulty(MED_DIFF_MULTIPLIER);
            myScorePanel.setTimerSpeed(MED_TIMER_SPEED);
        } else if (HARD_DIFF.equals(theDiff)) {
            myGameTimer.setDelay(HARD_TIMER_SPEED);
            myScorePanel.setDifficulty(HARD_DIFF_MULTIPLIER);
            myScorePanel.setTimerSpeed(HARD_TIMER_SPEED);
        }        
    }
    
    /**
     * The enableMusic method of the TetrisMenuBar class.
     * 
     * @param theOption The boolean variable that disables/enables the music.
     */
    private void enableMusic(final boolean theOption) {
        final boolean musicEnabled = theOption;
        
        /* If true, loop music continuously. Else, stop the music. */
        if (musicEnabled) {
            myBackgroundMusic.start();
            myBackgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            myBackgroundMusic.stop();
        }        
    }

    /**
     * The overridden update method of the TetrisMenuBar class.
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        /* Only if theData is boolean and theObject is a Board, end the game. */
        if (theObservable instanceof Board && theData instanceof Boolean) {
            myPauseGameButton.setEnabled(false);
            myEndGameButton.setEnabled(false);
            myNewGameButton.setEnabled(true);
        }        
    }

}