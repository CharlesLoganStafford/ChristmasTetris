/* 
 * TCSS 305 – Autumn 2016
 * Assignment 6 - Tetris 
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Board;

/**
 * The ScorePanel class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 12/9/2016
 */
public class ScorePanel extends JPanel implements Observer {
    
    /**
     * A generated serialVersionUID for the ScorePanel class.
     */
    private static final long serialVersionUID = 8516737898944441944L;

    /**
     * A Dimension object representing the panel size of the Score panel.
     */
    private static final Dimension SCORE_PANEL_SIZE = new Dimension(150, 150);
    
    /**
     * A Color object to be used as the background color of the Score panel.
     */
    private static final Color SCORE_PANEL_BG_COLOR = new Color(144, 23, 23);
    
    /**
     * A String object used to space out components. Only being used temporarily.
     */
    private static final String BLANK_SPACE = "                                           ";
    
    /**
     * An integer representing the number of cleared lines between each level.
     */
    private static final int LINE_LEVEL_SEP = 5;
    
    /**
     * An integer representing the score multiplier.
     */
    private static final int SCORE_MULTIPLIER = 100;
    
    /**
     * The amount of time to decrement the timer by in ms.
     */
    private static final int TIMER_DECREMENT = 25;
    
    /**
     * The font used to draw the scores.
     */
    private static final Font SCORE_FONT = new Font("Arial", Font.BOLD, 12);
    
    /**
     * A integer used to center the text of the score values on the panel.
     */
    private static final int X_OFFSET = 110;
    
    /**
     * An integer representing the current level of the player.
     */
    private int myLevel;

    /**
     * An integer representing the number of lines cleared.
     */
    private int myNumberOfLinesCleared;
    
    /**
     * An integer representing the total score.
     */
    private int myScore;
    
    /**
     * An integer representing the number of lines until the next level is reached.
     */
    private int myLinesUntilNextLevel;
    
    /**
     * An integer representing the difficulty modifier; either 1, 2, or 3.
     */
    private int myDifficultyModifier;
    
    /**
     * An integer representing the high score.
     */
    private int myHighScore;    
    
    /**
     * A boolean value for representing if a new high score has been reached.
     */
    private boolean myNewHighScoreReached;

    /**
     * An integer array representing the score.
     */
    private Integer[] myScoreData;

    /**
     * The timer of the game.
     */
    private final Timer myGameTimer;

    /**
     * The speed to increment the timer by each level.
     */
    private int myGameTimerSpeed;

    /**
     * The ScorePanel constructor.
     * 
     * @param theTimer The timer of the current game.
     */
    public ScorePanel(final Timer theTimer) {
        super();
        
        /* Initializing some temporary variables to be used until I get the scoring working. */
        myLevel = 1;
        myLinesUntilNextLevel = LINE_LEVEL_SEP;
        myDifficultyModifier = 1;
        myGameTimer = theTimer;
        myGameTimerSpeed = myGameTimer.getDelay();
        
        /* Setting some properties of the panel. */
        setPreferredSize(SCORE_PANEL_SIZE);
        setBackground(SCORE_PANEL_BG_COLOR);
        
        /* Calling a helper method to create and display the panel. */
        printScores();        
    }
    
    /**
     * The setDifficulty method of the ScorePanel class.
     *
     *@param theDifficultyModifier The difficulty level of the game as an integer.
     */
    protected void setDifficulty(final int theDifficultyModifier) {
        myDifficultyModifier = theDifficultyModifier;
    }
    
    /**
     * The setTimerSpeed method of the ScorePanel class.
     *
     *@param theTimerSpeed The speed to set the timer to.
     */
    protected void setTimerSpeed(final int theTimerSpeed) {
        myGameTimerSpeed = theTimerSpeed;
    }

    /**
     * The printScores method of the ScorePanel class.
     */
    private void printScores() {
        /* Creating a Box object to use as a space for the drawing. */
        final Box scorePanel = Box.createVerticalBox();
        
        /* Creating and setting a centered, titled border for the panel. */
        final TitledBorder border = new TitledBorder(new LineBorder(Color.GREEN),
                                               "Score:",
                                               TitledBorder.CENTER,
                                               TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.WHITE);
        setBorder(border);
        
        /* Creating some JLabels to display the score board. */
        final JLabel linesLabel = new JLabel("Lines Cleared:");
        final JLabel totalScoreLabel = new JLabel("Total Score:");
        final JLabel levelLabel = new JLabel("Current Level:");
        final JLabel nextLevelLabel = new JLabel("Lines until Next:");
        final JLabel highScoreLabel = new JLabel("High Score:");
        
        /* Set the text color of these labels to white. */
        linesLabel.setForeground(Color.WHITE);
        totalScoreLabel.setForeground(Color.WHITE);
        levelLabel.setForeground(Color.WHITE);
        nextLevelLabel.setForeground(Color.WHITE);
        highScoreLabel.setForeground(Color.WHITE);
        
        /* Adding the created components to the scorePanel Box object. */
        scorePanel.add(new JLabel(BLANK_SPACE));
        scorePanel.add(linesLabel);
        scorePanel.add(totalScoreLabel);
        scorePanel.add(levelLabel);
        scorePanel.add(nextLevelLabel);
        scorePanel.add(highScoreLabel);
        
        /* Adding the created box with all elements to this object. */
        add(scorePanel);
    }
    
    /**
     * The overridden paintComponent method of the ScorePanel class.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        theGraphics.setColor(Color.WHITE);
        theGraphics.setFont(SCORE_FONT);
        
        final int linesYOffset = 55;
        final int scoreYOffset = 72;
        final int levelYOffset = 87;
        final int untilNextYOffset = 103;
        final int highScoreYOffset = 119;
        
        theGraphics.drawString(Integer.toString(myNumberOfLinesCleared), X_OFFSET, 
                               linesYOffset);
        theGraphics.drawString(Integer.toString(myScore), X_OFFSET, scoreYOffset);
        theGraphics.drawString(Integer.toString(myLevel), X_OFFSET, levelYOffset);
        theGraphics.drawString(Integer.toString(myLinesUntilNextLevel), X_OFFSET, 
                               untilNextYOffset);
        theGraphics.drawString(Integer.toString(myHighScore), X_OFFSET, highScoreYOffset);
    }

    /**
     * The overridden update method of the ScorePanel class.
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        /* If the object is a Board and the data is a Integer Array... */
        if (theObservable instanceof Board 
                        && theData instanceof Integer[]) {
            myScoreData = (Integer[]) theData;
            
            /* Calculate the current score... */
            for (int i = 0; i <= myScoreData.length; i++) {
                myScore = myScore + (myLevel * SCORE_MULTIPLIER * myDifficultyModifier);
            }
            
            /* ...and update class fields. */
            myNumberOfLinesCleared += myScoreData.length;
            myLinesUntilNextLevel--;
            
            if (myLinesUntilNextLevel == 0) {
                myLevel++;
                myLinesUntilNextLevel = LINE_LEVEL_SEP;
                myGameTimerSpeed -= TIMER_DECREMENT * myLevel;
                myGameTimer.setDelay(myGameTimerSpeed);
            }
            
            if (myScore > myHighScore) {
                myHighScore = myScore;
                myNewHighScoreReached = true;
            }
            
            repaint();
        } else if (theObservable instanceof Board 
                        && theData instanceof Boolean) {
            if (myNewHighScoreReached) {
                JOptionPane.showMessageDialog(null, 
                                              "Congratulations, you've reached a new "
                                              + "high score! Your score: " + myHighScore,
                                           "New High Score!", JOptionPane.INFORMATION_MESSAGE);
            }
            
            /* Else if the game is over and a boolean received... */
            final int defaultLinesUntilNextLevelValue = 5;
            myLevel = 0;
            myScore = 0;
            myNumberOfLinesCleared = 0;
            myLinesUntilNextLevel = defaultLinesUntilNextLevelValue;
            
            repaint();
        }             
    }
    
}