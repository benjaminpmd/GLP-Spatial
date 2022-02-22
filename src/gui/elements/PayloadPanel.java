package gui.elements;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.SimConfig;
import engine.process.factories.RocketFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A panel with pre-made buttons. Allows the user to choose the type of payload to send into space.
 * Used in @see PreLaunchGUI
 * 
 * @author Alice Mabille
 *
 */
public class PayloadPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private RocketFactory rocketFactory;
	private JTextField weightField;
	private JButton obsButton;
	private JButton geoButton;
	private JButton cubsatButton;
	private JButton interplanetButton;


	public PayloadPanel(RocketFactory rocketFactory) {
		this.rocketFactory = rocketFactory;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//top panel : buttons for the type of payload
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		
		obsButton = new JButton("Observation");
		obsButton.addActionListener(new ButtonListener());

		topPanel.add(obsButton);
		
		geoButton = new JButton("Geostationary");
		geoButton.addActionListener(new ButtonListener());
		topPanel.add(geoButton);
		
		cubsatButton = new JButton("Cubsat");
		cubsatButton.addActionListener(new ButtonListener());
		topPanel.add(cubsatButton);
		
		interplanetButton = new JButton("Interplanetary");
		interplanetButton.addActionListener(new ButtonListener());
		topPanel.add(interplanetButton);
		
		
		add(topPanel);
		
		//bottom panel : custom weight and validate button
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.LINE_AXIS));
		
		JLabel weightLabel = new JLabel("Custom payload weight (kg) :");
		botPanel.add(weightLabel);
		weightField = new JTextField();
		botPanel.add(weightField);
		weightLabel.setVisible(!SimConfig.beginnerMode);
		weightField.setVisible(!SimConfig.beginnerMode);
		
		JButton validateButton = new JButton("Apply");
		botPanel.add(validateButton);
		
		add(botPanel);
	}

	private class ApplyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			rocketFactory.createPayload(Integer.valueOf(weightField.getText()));
		}
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (((JButton) e.getSource()).getText()) {
				case "Observation":
					weightField.setText("3000");
					break;
				case "Geostationary":
					weightField.setText("7500");
					break;
				case "Cubsat":
					weightField.setText("40");
					break;
				case "Interplanetary":
					weightField.setText("1500");
					break;
				default: weightField.setText("0");
			}
		}
	}
}
