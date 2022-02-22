package engine.data;

/**
 * Class containing all scientific constants for calculations.
 *
 * @author Benjamin P
 * @version 22.02.13 - I like maths (1.0.0)
 * @since 11.02.22
 */
public class Constants {
    public static final double GRAVITATIONAL_CONST = 0.000000000006674184;
    // Earth values
    public static final double GRAVITY = 9.80066;
    public static final int EARTH_RADIUS = 6371000;
    public static final double EARTH_MASS = 5972000000000000000000000D;
    // Engines default values
    public static final int DEFAULT_THRUST_RATIO = 65;
    public static final int DEFAULT_ISP = 380;
    // Propellant default values
    public static final double DEFAULT_PROPELLANT_DENSITY = 1.0;
    public static final double DEFAULT_SOLID_PROPELLANT_DENSITY = 1.65;
    public static final int DEFAULT_PROPELLANT_TEMPERATURE = 15;
}
