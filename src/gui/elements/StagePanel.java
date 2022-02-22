/**
 * 
 */
package gui.elements;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import config.SimConfig;
import engine.process.factories.RocketFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel that displays every characteristic of the Stage you need the user to choose so the rocket can fly.
 * @author Alice Mabille
 */
public class StagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int stage;
	private int maxEngines;
	private RocketFactory rocketFactory;
	// fields
	private JTextField capacityField;
	private JTextField densityField;
	private JTextField thrustToWeightField;
	private JTextField engineThrustField;
	private JTextField ispField;
	private JTextField propelTemperatureField;
	private JSlider engNumSlider;
	private String defaultCapacity;
	private String defaultDensity;
	private String defaultTWRatio;
	private String defaultThrust;
	private String defaultISP;
	private String defaultPropelTemp;
	private String defaultEngNb;


	/**
	 * Builds a JPanel that displays every characteristic of the Stage you need the user to choose so the rocket can fly.
	 * @param stage either 1 for the 1st stage or 2 for the 2nd stage
	 * @param maxEngines the maximum number of engines
	 * @param rocketFactory {@link RocketFactory}
	 */
	public StagePanel(int stage, int maxEngines, RocketFactory rocketFactory) {
		this.stage = stage;
		this.maxEngines = maxEngines;
		this.rocketFactory = rocketFactory;
		init();

	}
	
	/**
	 * Adds every label, text field, slider and button necessary.
	 */
	private void init() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel titleLabel = new JLabel("Stage " + stage);
		add(titleLabel);
		
		JLabel capacityLabel = new JLabel("Propellant Volume (L)");
		add(capacityLabel);
		capacityField = new JTextField();
		add(capacityField);
		
		JLabel densityLabel = new JLabel("Propellant density (kg/L)");
		add(densityLabel);
		densityField = new JTextField();
		add(densityField);
		densityLabel.setVisible(!SimConfig.beginnerMode);
		densityField.setVisible(!SimConfig.beginnerMode);
		
		JLabel propelTemperatureLabel = new JLabel("Propellant temperature");
		add(propelTemperatureLabel);
		propelTemperatureField = new JTextField();
		add(propelTemperatureField);
		
		JLabel engNumLabel = new JLabel("Number of engines");
		add(engNumLabel);
		engNumSlider = new JSlider(1, maxEngines);
		engNumSlider.setMajorTickSpacing(10);
		engNumSlider.setMinorTickSpacing(1);
		engNumSlider.setPaintTicks(true);
		engNumSlider.setPaintLabels(true);
		add(engNumSlider);
		
		
		JLabel thrustToWeightLabel = new JLabel("Thrust To Weight");
		add(thrustToWeightLabel);
		thrustToWeightField = new JTextField();
		add(thrustToWeightField);
		//fuelFlowLabel.setVisible(!SimConfig.beginnerMode);
		//fuelFlowField.setVisible(!SimConfig.beginnerMode);
		
		JLabel engineThrustLabel = new JLabel("Engine Thrust (kg.m.s^-2)");
		add(engineThrustLabel);
		engineThrustField = new JTextField();
		add(engineThrustField);
		
		JLabel ispLabel = new JLabel("ISP (s)");
		add(ispLabel);
		ispField = new JTextField();
		add(ispField);
		//ispLabel.setVisible(!SimConfig.beginnerMode);
		//ispField.setVisible(!SimConfig.beginnerMode);
		
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ApplyListener());
		add(applyButton);
		
		setDefaultValues();
	}
	
	/**
	 * Sets the texts in the text fields based on Falcon 9.
	 */
	public void setDefaultValues() {
		if(stage == 1) {
			defaultCapacity = "400000";
			defaultDensity = "0.72";
			defaultThrust = "482000";
			defaultEngNb = "9";
			defaultISP = "311";
			defaultPropelTemp = "-200";
			defaultTWRatio = "70.8";
		}
		else if (stage == 2) {
			defaultCapacity = "22000";
			defaultDensity = "0.8";
			defaultThrust = "626000";
			defaultEngNb = "1";
			defaultISP = "342";
			defaultPropelTemp = "-200";
			defaultTWRatio = "78.2";
		}
		capacityField.setText(defaultCapacity);
		densityField.setText(defaultDensity);
		thrustToWeightField.setText(defaultTWRatio);
		engineThrustField.setText(defaultThrust);
		ispField.setText(defaultISP);
		propelTemperatureField.setText(defaultPropelTemp);
	}
	
	/**
	 * Gets the text from the text fields and sends them to {@link RocketFactory} to create a stage.
	 * @author Benjamin Paumard
	 */
	private class ApplyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int capacity = Integer.valueOf(capacityField.getText());
			double density = Double.valueOf(densityField.getText());
			int propellantTemperature = 15;
			int engineNb = engNumSlider.getValue();
			double engineThrust = Double.valueOf(engineThrustField.getText());
			double ratio = Double.valueOf(thrustToWeightField.getText());
			int isp = Integer.valueOf(ispField.getText());
			rocketFactory.createStage(capacity, density, propellantTemperature, engineNb, engineThrust, ratio, isp, stage);
			System.out.println(rocketFactory.getBuiltRocket());
		}
	}


	
}
