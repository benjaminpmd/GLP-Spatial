package data.rocket;

import engine.data.Constants;

/**
 * Class that contains information about the propellant.
 *
 * @author Alice M, Benjamin P
 * @version 22.02.11 - thrust me (1.0.0)
 * @since 11.02.22
 */
public class Propellant {

    // C degrees
    private int temperature;
    // Kg/L
    private double density;

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

    /**
     * Constructor to be used if the propellant is solid, default values will be used.
     * You can still create a custom solid propellant, for reminder, the average solid prop' density is about 1.65 and
     * support ambiant temperature (15 Celsius degrees)
     *
     * @param isSolid if the propellant is solid.
     */
    public Propellant(boolean isSolid) {
        this(Constants.DEFAULT_SOLID_PROPELLANT_DENSITY, Constants.DEFAULT_PROPELLANT_TEMPERATURE);
    }

    /**
     * Constructor to use if the propellant can support ambiant temperature.
     *
     * @param density in kg.L^-1.
     */
    public Propellant(double density) {
        this(density, Constants.DEFAULT_PROPELLANT_TEMPERATURE);
    }

    /**
     * Constructor to use for default values.
     */
    public Propellant() {
        this(Constants.DEFAULT_PROPELLANT_DENSITY, Constants.DEFAULT_PROPELLANT_TEMPERATURE);
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
