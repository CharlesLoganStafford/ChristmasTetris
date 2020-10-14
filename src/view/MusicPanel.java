package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

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
 * @version 1.5
 * @date October 11th, 2020
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
     * A Font object to set a constant font for the text within the Music panel.
     */
    private static final Font SCORE_PANEL_FONT = new Font("Verdana", Font.PLAIN, 10);
    
    /**
     * The MusicPanel constructor.
     */
    public MusicPanel() {
        super();
        
        /* Setting some properties of the panel. */
        setPreferredSize(SCORE_PANEL_SIZE);
        setBackground(SCORE_PANEL_BG_COLOR);      
        
        /* Creating a Box object to use as a space for the drawing. */
        final Box musicPanel = Box.createVerticalBox();
        
        /* Creating and setting a centered, titled border for the panel. */
        final TitledBorder border = new TitledBorder(new LineBorder(Color.GREEN), "Music:", TitledBorder.CENTER, TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(SCORE_PANEL_FONT);
        setBorder(border);

        /* Creating JLabels to display the music credits and a display a festive picture. */        
        final JLabel songLabel = new JLabel("<html><center>Andy Williams -<br>It's The Most Wonderful<br>\"Time Of The Year\" (1963)</center></html>");
        final JLabel treeLabel = new JLabel(new ImageIcon("./media/tree.png"));
        
        /* Set the text color and font of these labels. */
        songLabel.setForeground(Color.WHITE);
        songLabel.setFont(SCORE_PANEL_FONT);
        
        /* Adding and centering components onto the scorePanel Box object. */
        musicPanel.add(songLabel);
        musicPanel.add(treeLabel);
        songLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        treeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        songLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        treeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        /* Adding the created box with all elements to this object. */
        add(musicPanel);
    }
}