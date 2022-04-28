package gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Class containing a panel with pre-made buttons. Allows the user to choose the type of payload which is associated with the rocket.
 * Used in {@link MainGUI}.
 *
 * @see gui.MainGUI
 * @author Alice Mabille
 * @version 22.04.28 (1.0.0)
 * @since 22.02.11
 */
public class PayloadPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Top part of the JPanel
	private JPanel topPanel = new JPanel();
	private ButtonGroup radioButtonGroup = new ButtonGroup();
	private JRadioButton obsButton = new JRadioButton("Observation");
	private JRadioButton geoButton = new JRadioButton("Geostationary");
	private JRadioButton cubsatButton = new JRadioButton("Cubsat");
	private JRadioButton interplanetButton = new JRadioButton("Interplanetary");
	
	// Bottom part of the JPanel
	private JPanel bottomPanel = new JPanel();
	private JLabel massLabel = new JLabel("Custom payload mass (kg) :");
	private JTextField massField = new JTextField();
	
	/**
	 * Builds a JPanel that shows all the types of payload available for the user to be chosen.
	 */
	public PayloadPanel() {

		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);

		//top panel : buttons for the type of payload
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.setBounds(50, 50, 50, 50);

		obsButton.addActionListener(new PayloadTypeChosenAction());
		geoButton.addActionListener(new PayloadTypeChosenAction());
		cubsatButton.addActionListener(new PayloadTypeChosenAction());
		interplanetButton.addActionListener(new PayloadTypeChosenAction());

		radioButtonGroup.add(obsButton);
		radioButtonGroup.add(geoButton);
		radioButtonGroup.add(interplanetButton);
		radioButtonGroup.add(cubsatButton);

		topPanel.add(cubsatButton);
		topPanel.add(geoButton);
		topPanel.add(interplanetButton);
		topPanel.add(obsButton);

		//adds a blank space on top of the topPanel just to make it look nicer
		add(Box.createRigidArea(new Dimension(0, 50)));
		add(topPanel);
		//same but on bottom : between the two panels
		add(Box.createRigidArea(new Dimension(0, 50)));

		//bottom panel : custom mass and validate button
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));

		massField.setMaximumSize(new Dimension(200,40));
		bottomPanel.add(massLabel);
		bottomPanel.add(massField);

		add(bottomPanel);
	}
	
	/**
	 * Sets up the background color of the elements.
	 * @param c the color of the elements
	 */
	public void setElementsBackground(Color c) {
		obsButton.setBackground(c);
		geoButton.setBackground(c);
		cubsatButton.setBackground(c);
		interplanetButton.setBackground(c);
		massField.setBackground(c);
		massLabel.setBackground(c);
		bottomPanel.setBackground(c);
	}

	/**
	 * Sets up the foreground color of the elements.
	 * @param c the color of the elements
	 */
	public void setElementsForeground(Color c) {
		obsButton.setForeground(c);
		geoButton.setForeground(c);
		cubsatButton.setForeground(c);
		interplanetButton.setForeground(c);
		massField.setForeground(c);
		massLabel.setForeground(c);
	}

	public String getMassInput() {
		return massField.getText();
	}

	/**
	 * Sets up all the default values of the payload in function of its type, when the user clicks on its associated JRadioButton.
	 */
	private void setTypicalPayloadWeight() {
		if (obsButton.isSelected()) {
			massField.setText("1500");
		}
		else if (geoButton.isSelected()) {
			massField.setText("8000");
		}
		else if (cubsatButton.isSelected()) {
			massField.setText("40");
		}
		else if (interplanetButton.isSelected()) {
			massField.setText("2000");
		}
	}

	private class PayloadTypeChosenAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setTypicalPayloadWeight();
		}
	}
}