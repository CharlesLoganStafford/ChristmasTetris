package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * The ControlsPanel class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public class ControlsPanel extends JPanel {
    
    /**
     * A generated serialVersionUID for the ControlsPanel class.
     */
    private static final long serialVersionUID = -7324217877526763689L;

    /**
     * A Dimension object representing the panel size of the Controls panel.
     */
    private static final Dimension SCORE_PANEL_SIZE = new Dimension(150, 150);
    
    /**
     * A Color object to be used as the background color of the Controls panel.
     */
    private static final Color SCORE_PANEL_BG_COLOR = new Color(60, 141, 13);
        
    /**
     * The controlsPanel constructor.
     */
    public ControlsPanel() {
        super();
        
        /* Setting some properties of the panel. */
        setPreferredSize(SCORE_PANEL_SIZE);
        setBackground(SCORE_PANEL_BG_COLOR);
        
        /* Calling a helper method to create and display the panel. */
        printControls();
    }

    /**
     * The printControls method of the ControlsPanel class.
     */
    private void printControls() {
        
        /* Creating a Box object to use as a space for the drawing. */
        final Box controlsPanel = Box.createVerticalBox();
        
        /* Creating and setting a centered, titled border for the panel. */
        final TitledBorder border = new TitledBorder(new LineBorder(Color.RED),
                                                     "Game Controls:",
                                                     TitledBorder.CENTER,
                                                     TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Verdana", Font.PLAIN, 10));
        setBorder(border);
        
        /* Creating some labels to represent each control. */
        final JLabel leftLabel = new JLabel("Move Left: A ");
        final JLabel rightLabel = new JLabel("Move Right: D ");
        final JLabel rotateLabel = new JLabel("Rotate: W ");
        final JLabel downLabel = new JLabel("Down: S ");
        final JLabel dropLabel = new JLabel("Drop: SPACE");
        final JLabel pauseLabel = new JLabel("Pause: CTRL+P ");
        
        /* Set the text color of these labels to white. */
        leftLabel.setForeground(Color.WHITE);
        rightLabel.setForeground(Color.WHITE);
        rotateLabel.setForeground(Color.WHITE);
        downLabel.setForeground(Color.WHITE);
        dropLabel.setForeground(Color.WHITE);
        pauseLabel.setForeground(Color.WHITE);
        
        /* Set the text font and size to 10pt Verdana. */
        leftLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
        rightLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
        rotateLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
        downLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
        dropLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
        pauseLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
        
        /* Centering components on the box. */
        leftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        downLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pauseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        /* Adding the created components to the controlsPanel Box object. */
        controlsPanel.add(leftLabel);
        controlsPanel.add(rightLabel);
        controlsPanel.add(rotateLabel);
        controlsPanel.add(downLabel);
        controlsPanel.add(dropLabel);
        controlsPanel.add(pauseLabel);
        
        /* Adding the created box with all elements to this object. */
        add(controlsPanel);
    }
    
}