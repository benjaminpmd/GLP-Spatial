package gui.elements;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.SimConfig;

/**
 * A panel with pre-made buttons. Allows the user to choose the type of payload to send into space.
 * Used in @see MainGUI
 * 
 * @author Alice Mabille
 *
 */
public class PayloadPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField massField;

	public PayloadPanel() {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//top panel : buttons for the type of payload
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		
		JButton obsButton = new JButton("Observation");
		topPanel.add(obsButton);
		
		JButton geoButton = new JButton("Geostationary");
		topPanel.add(geoButton);
		
		JButton cubsatButton = new JButton("Cubsat");
		topPanel.add(cubsatButton);
		
		JButton interplanetButton = new JButton("Interplanetary");
		topPanel.add(interplanetButton);

		add(topPanel);
		
		//bottom panel : custom mass and validate button
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		
		JLabel massLabel = new JLabel("Custom payload mass (kg) :");
		bottomPanel.add(massLabel);
		massField = new JTextField();
		bottomPanel.add(massField);
		
		JButton validateButton = new JButton("Validate payload");
		bottomPanel.add(validateButton);
		
		add(bottomPanel);
	}


	public String getMassInput() {
		return massField.getText();
	}
}
