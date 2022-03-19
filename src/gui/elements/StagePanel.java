
package gui.elements;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import config.SimConfig;

import java.awt.*;
import java.util.HashMap;

/**
 * JPanel that displays every characteristic of the Stage you need the user to choose so the rocket can fly.
 * @author Alice Mabille
 */
public class StagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int stage;
	private int maxEngines;
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
	private int defaultEngNb;

	private final Color BACKGROUND_COLOR = Color.DARK_GRAY;


	/**
	 * Builds a JPanel that displays every characteristic of the Stage you need the user to choose so the rocket can fly.
	 * @param stage either 1 for the 1st stage or 2 for the 2nd stage
	 * @param maxEngines the maximum number of engines
	 */
	public StagePanel(int stage, int maxEngines) {
		this.stage = stage;
		this.maxEngines = maxEngines;
		init();

	}
	
	/**
	 * Adds every label, text field, slider and button necessary.
	 */
	private void init() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel titleLabel = new JLabel("Stage " + stage);
		titleLabel.setBackground(BACKGROUND_COLOR);
		add(titleLabel);
		
		JLabel capacityLabel = new JLabel("Propellant Volume (L)");
		add(capacityLabel);
		capacityField = new JTextField();
		add(capacityField);
		
		JLabel densityLabel = new JLabel("Propellant density (kg/L)");
		add(densityLabel);
		densityField = new JTextField();
		add(densityField);
		
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
			defaultEngNb = 9;
			defaultISP = "311";
			defaultPropelTemp = "-200";
			defaultTWRatio = "70.8";
		}
		else if (stage == 2) {
			defaultCapacity = "22000";
			defaultDensity = "0.8";
			defaultThrust = "626000";
			defaultEngNb = 1;
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
		engNumSlider.setValue(defaultEngNb);
	}
	
	/**
	 * Gets the text from the text fields and slider. Used in {@link gui.MainGUI}.
	 * @author Benjamin Paumard, Alice Mabille
	 */
	public HashMap<String,String> getValues() {
		HashMap<String,String> values = new HashMap<String,String>();
		values.put("tankCapacity", capacityField.getText());
		values.put("propellantDensity", densityField.getText());
		values.put("propellantTemperature", propelTemperatureField.getText());
		values.put("engineNb", String.valueOf(engNumSlider.getValue()));
		values.put("engineThrust", engineThrustField.getText());
		values.put("engineThrustRatio", thrustToWeightField.getText());
		values.put("isp", ispField.getText());
		return values;
	}


	
}
