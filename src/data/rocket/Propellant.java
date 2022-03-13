package data.rocket;

import config.Constants;

/**
 * Class that contains information about the propellant.
 *
 * @author Alice M, Benjamin P
 * @version 22.03.06 (1.0.0)
 * @since 11.02.22
 */
public class Propellant {

    // in C degrees
    private int temperature;
    // in kg.L^-1
    private double density;

    /**
     * Constructor of the propellant.
     */
    public Propellant() {
        density = Constants.DEFAULT_PROPELLANT_DENSITY;
        temperature = Constants.DEFAULT_PROPELLANT_TEMPERATURE;
    }

    /**
     * Constructor of the Propellant.
     */
    public Propellant(double density, int temperature) {
        this.density = density;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Propellant{" +
                "temperature=" + temperature +
                ", density=" + density +
                '}';
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }
}
