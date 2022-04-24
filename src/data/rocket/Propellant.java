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

    // in kg.L^-1
    private double density;

    /**
     * Constructor of the Propellant.
     */
    public Propellant(double density) {
        this.density = density;
    }

    @Override
    public String toString() {
        return "Propellant{" +
                "density=" + density +
                '}';
    }
    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }
}
