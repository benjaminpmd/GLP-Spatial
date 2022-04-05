package config;

/**
 * Class containing all scientific constants for calculations.
 *
 * @author Benjamin P
 * @version 22.02.13 - I like maths (1.0.0)
 * @since 11.02.22
 */
public class Constants {

    // universal constants
    public static final double GRAVITATIONAL_CONST = 6.67408 * Math.pow(10, -11);
    public static final double ABSOLUTE_ZERO = 273.15;

    // Earth values
    public static final double GRAVITY = 9.80066;
    public static final double AIR_PRESSURE_SEA_LEVEL = 1013.25;
    public static final double AIR_TEMPERATURE_SEA_LEVEL = 15;
    public static final double AIR_CONSTANT = 287.058;
    public static final int EARTH_RADIUS = (int) (6.371 * Math.pow(10, 6));
    public static final double EARTH_MASS = 5.9722 * Math.pow(10, 24);

    // Propellant default values
    public static final double DEFAULT_PROPELLANT_DENSITY = 1.0;
    public static final int DEFAULT_PROPELLANT_TEMPERATURE = 15;

    // rocket values
    public static final int DEFAULT_EXHAUST_VELOCITY = 4150;
    public static final double DEFAULT_DRAG_COEFFICIENT = 0.75;
    public static final int DEFAULT_ROCKET_SURFACE = 4;
}
