package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * The MusicPanel class for the Tetris program.
 * 
 * @author Logan Stafford
 * @version 12/9/2016
 */
public class MusicPanel extends JPanel {

    /**
     * A generated serialVersionUID for the MusicPanel class.
     */
    private static final long serialVersionUID = -6529856001700905098L;

    /**
     * A Dimension object representing the panel size of the Music panel.
     */
    private static final Dimension SCORE_PANEL_SIZE = new Dimension(150, 150);
    
    /**
     * A Color object to be used as the background color of the Music panel.
     */
    private static final Color SCORE_PANEL_BG_COLOR = new Color(144, 23, 23);
    
    /**
     * The MusicPanel constructor.
     */
    public MusicPanel() {
        super();
        
        /* Setting some properties of the panel. */
        setPreferredSize(SCORE_PANEL_SIZE);
        setBackground(SCORE_PANEL_BG_COLOR);      
        
        /* Calling a helper method to create and display the panel. */
        printMusic();
    }

    /**
     * The printMusic method of the MusicPanel class.
     */
    private void printMusic() {
        /* Creating a Box object to use as a space for the drawing. */
        final Box musicPanel = Box.createVerticalBox();
        
        /* Creating and setting a centered, titled border for the panel. */
        final TitledBorder border = new TitledBorder(new LineBorder(Color.GREEN),
                                                     "Music:",
                                                     TitledBorder.CENTER,
                                                     TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.WHITE);
        setBorder(border);

        /* Creating some JLabels to display the music info. */
        final JLabel songArtistLabel = new JLabel("Andy Williams -");
        final JLabel songTitleLabel = new JLabel("\"It's The Most Wonderful");
        final JLabel songTitleLabel2 = new JLabel("Time Of The Year\" (1963)");
        final JLabel treeLabel = new JLabel(new ImageIcon("./media/tree.png"));
        
        /* Set the text color of these labels to white. */
        songArtistLabel.setForeground(Color.WHITE);
        songTitleLabel.setForeground(Color.WHITE);
        songTitleLabel2.setForeground(Color.WHITE);
        
        /* Adding the created components to the scorePanel Box object. */
        musicPanel.add(songArtistLabel);
        musicPanel.add(songTitleLabel);
        musicPanel.add(songTitleLabel2);
        musicPanel.add(treeLabel);
        
        /* Centering components on the box.  */
        songArtistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        songTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        songTitleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        treeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        /* Adding the created box with all elements to this object. */
        add(musicPanel);
    }

}