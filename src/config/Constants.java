package config;

/**
 * Class containing all the scientific constants for calculations.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @since 22.02.11
 */
public class Constants {

    // universal constants
    public static final double GRAVITATIONAL_CONST = 6.67408 * Math.pow(10, -11);
    public static final double ABSOLUTE_ZERO = -273.15;

    // Earth values
    public static final double GRAVITY = 9.80066;
    public static final double AIR_PRESSURE_SEA_LEVEL = 1013.25;
    public static final int AIR_TEMPERATURE_SEA_LEVEL = 15;
    public static final double AIR_CONSTANT = 287.058;
    public static final int EARTH_RADIUS = (int) (6.371 * Math.pow(10, 6));
    public static final double EARTH_MASS = 5.9722 * Math.pow(10, 24);

    // rocket values
    public static final double DEFAULT_DRAG_COEFFICIENT = 0.75;
    public static final int DEFAULT_ROCKET_SURFACE = 16;

    public static final int PARKING_ORBIT = 500000;
}
