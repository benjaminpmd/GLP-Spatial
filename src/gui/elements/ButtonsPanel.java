package gui.elements;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * This class is only responsible for the tempo monitoring buttons printing.
 * 
 * There is no algorithmic processing in this class.
 * 
 * @see gui.SimulationGUI
 * 
 * @author Lucas Lenaert
 *
 */
public class ButtonsPanel extends JPanel {

	private JButton delayButton = new JButton("DELAY START");
	private JButton playButton = new JButton("PLAY");
	private JButton speedUpButton = new JButton("SPEED UP");
	private JButton slowDownButton = new JButton("SLOW DOWN");
	
	public ButtonsPanel() {
	
		setLayout(new GridBagLayout());
		GridBagConstraints timeConstraints = new GridBagConstraints();
		
		JLabel actionsLabel = new JLabel("Actions", SwingConstants.CENTER);
		timeConstraints.gridwidth = 1;
		timeConstraints.gridheight = 1;
		timeConstraints.gridx = 0;
		timeConstraints.gridy = 0;
		add(actionsLabel, timeConstraints);
		
		timeConstraints.gridy = 1;
		timeConstraints.insets = new Insets(10, 10, 10, 10);
		add(delayButton, timeConstraints);
		
		timeConstraints.gridy = 2;
		add(playButton, timeConstraints);
		
		timeConstraints.gridy = 3;
		add(speedUpButton, timeConstraints);
		
		timeConstraints.gridy = 3;
		timeConstraints.insets.set(10, 10, 200, 10);
		add(slowDownButton, timeConstraints);
	}
}
