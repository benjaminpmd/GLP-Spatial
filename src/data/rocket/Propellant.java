package data.rocket;

import engine.data.Constants;

/**
 * Class that contains information about the propellant.
 *
 * @author Alice M, Benjamin P
 * @version 22.03.06
 * @since 11.02.22
 */
public class Propellant {

    // in C degrees
    private int temperature;
    // in kg.L^-1
    private double density;

    /**
     * Constructor of the propellant object.
     */
    public Propellant() {
        density = Constants.DEFAULT_PROPELLANT_DENSITY;
        temperature = Constants.DEFAULT_PROPELLANT_TEMPERATURE;
    }

    /**
     * Constructor of the Propellant object.
     *
     * @param density     in kg.L^-1.
     * @param temperature in C.
     */
    public Propellant(double density, int temperature) {
        this.density = density;
        this.temperature = temperature;
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
