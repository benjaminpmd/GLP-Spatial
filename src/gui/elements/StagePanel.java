package gui.elements;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Class containing all the required elements to create a JPanel that displays every characteristic of the Stage which the user has to choose, making the rocket able to take off.
 * Used in {@link gui.MainGUI}.
 *
 * @author Alice M
 * @version 22.04.28 (1.0.0)
 * @see gui.MainGUI
 * @since 22.02.11
 */
public class StagePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    // Core attributes
    private final int stage;
    private final int maxEngines;

    // JTextField used
    private final JTextField capacityField = new JTextField();
    private final JTextField densityField = new JTextField();
    private final JTextField thrustToWeightField = new JTextField();
    private final JTextField engineThrustField = new JTextField();
    private final JTextField ispField = new JTextField();
    private final JTextField exhaustVelocityField = new JTextField();

    // JLabel used
    private JLabel titleLabel;
    private final JLabel capacityLabel = new JLabel("Propellant Volume (L)");
    private final JLabel densityLabel = new JLabel("Propellant density (kg.L^-1)");
    private final JLabel exhaustVelocityLabel = new JLabel("Exhaust Velocity (m.s^-1)");
    private final JLabel engNumLabel = new JLabel("Number of engines");
    private final JLabel thrustToWeightLabel = new JLabel("Thrust To Weight");
    private final JLabel engineThrustLabel = new JLabel("Engine Thrust (kg.m.s^-2)");
    private final JLabel ispLabel = new JLabel("ISP (s)");

    //JSlider
    private JSlider engNumSlider;

    // Default values
    private String defaultCapacity;
    private String defaultDensity;
    private String defaultTWRatio;
    private String defaultThrust;
    private String defaultISP;
    private String defaultExhaustVelocity;
    private int defaultEngNb;


    /**
     * Builds a JPanel that displays every characteristic of the Stage which the user has to choose, making the rocket able to take off.
     *
     * @param stage      either 1 for the 1st stage or 2 for the 2nd stage
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

        engNumSlider = new JSlider(1, maxEngines);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        titleLabel = new JLabel("Stage " + stage);
        add(titleLabel);


        add(capacityLabel);
        add(capacityField);

        add(densityLabel);
        add(densityField);

        add(engNumLabel);
        engNumSlider.setMajorTickSpacing(10);
        engNumSlider.setMinorTickSpacing(1);
        engNumSlider.setPaintTicks(true);
        engNumSlider.setPaintLabels(true);
        add(engNumSlider);

        add(thrustToWeightLabel);
        add(thrustToWeightField);

        add(engineThrustLabel);
        add(engineThrustField);

        add(exhaustVelocityLabel);
        add(exhaustVelocityField);

        add(ispLabel);
        add(ispField);

        setDefaultValues();
    }

    /**
     * Sets up the background color of the elements.
     *
     * @param c the color of the elements
     */
    public void setElementsBackground(Color c) {
        capacityField.setBackground(c);
        densityField.setBackground(c);
        engineThrustField.setBackground(c);
        engNumSlider.setBackground(c);
        thrustToWeightField.setBackground(c);
        exhaustVelocityField.setBackground(c);
        ispField.setBackground(c);
    }

    /**
     * Sets up the foreground color of the elements.
     *
     * @param c the color of the elements
     */
    public void setElementsForeground(Color c) {
        capacityField.setForeground(c);
        densityField.setForeground(c);
        thrustToWeightField.setForeground(c);
        engNumSlider.setForeground(c);
        engineThrustField.setForeground(c);
        exhaustVelocityField.setForeground(c);
        ispField.setForeground(c);
        titleLabel.setForeground(c);
        capacityLabel.setForeground(c);
        densityLabel.setForeground(c);
        exhaustVelocityLabel.setForeground(c);
        engNumLabel.setForeground(c);
        thrustToWeightLabel.setForeground(c);
        engineThrustLabel.setForeground(c);
        ispLabel.setForeground(c);
    }

    /**
     * Sets the texts in the text fields based on Falcon 9.
     */
    public void setDefaultValues() {
        if (stage == 1) {
            defaultCapacity = "400000";
            defaultDensity = "0.72";
            defaultThrust = "482000";
            defaultEngNb = 9;
            defaultISP = "311";
            defaultExhaustVelocity = "4150";
            defaultTWRatio = "70.8";
        } else if (stage == 2) {
            defaultCapacity = "22000";
            defaultDensity = "0.8";
            defaultThrust = "626000";
            defaultEngNb = 1;
            defaultISP = "342";
            defaultExhaustVelocity = "4150";
            defaultTWRatio = "78.2";
        }
        capacityField.setText(defaultCapacity);
        densityField.setText(defaultDensity);
        thrustToWeightField.setText(defaultTWRatio);
        engineThrustField.setText(defaultThrust);
        ispField.setText(defaultISP);
        exhaustVelocityField.setText(defaultExhaustVelocity);
        engNumSlider.setValue(defaultEngNb);
    }

    /**
     * Gets the text from the text fields and slider. Used in {@link gui.MainGUI}.
     */
    public HashMap<String, String> getValues() {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("capacity", capacityField.getText());
        values.put("density", densityField.getText());
        values.put("engineNb", String.valueOf(engNumSlider.getValue()));
        values.put("thrust", engineThrustField.getText());
        values.put("exhaustVelocity", exhaustVelocityField.getText());
        values.put("thrustRatio", thrustToWeightField.getText());
        values.put("isp", ispField.getText());
        return values;
    }


}