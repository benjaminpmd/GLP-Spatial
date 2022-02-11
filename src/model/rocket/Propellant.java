package model.rocket;

/**
 * Class that contains informations about the propellant
 * @author Alice M, Benjamin P
 * @since 11.02.22
 * @version 22.02.11 - thrust me (1.0.0)
 */
public class Propellant {

    // C degrees
    private int temperature;
    // Kg/L
    private double density;

    /**
     * Constructor of the Propellant object
     * @param density in Kg/L
     * @param temperature in Celsius degrees
     */
    public Propellant(double density, int temperature) {
        this.density = density;
        this.temperature = temperature;
    }

    /**
     * Constructor to be used if the propellant is solid, default values will be used.
     * You can still create a custom solid propellant, for reminder, the average solid prop' density is about 1.65 and support ambiant temperature (15 Celcius degrees)
     * @param isSolid if the propellant is solid
     */
    public Propellant(boolean isSolid) {
            this(1.65, 15);
    }

    /**
     * Constructor to use if the propellant can support ambiant temperature
     * @param density in Kg/L
     */
    public Propellant(double density) {
        this(density, 15);
    }

    /**
     * Constructor to use for default values
     */
    public Propellant() {
        this(1, 15);
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
