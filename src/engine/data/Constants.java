package engine.data;

import java.math.BigInteger;

public class Constants {
    // Earth values
    public static final double GRAVITY = 9.80066;
    public static final double EARTH_RADIUS = 6.371;
    public static final BigInteger EARTH_MASS = new BigInteger("5972000000000000000000000");
    // Engines default values
    public static final int DEFAULT_THRUST_RATIO = 65;
    public static final int DEFAULT_ISP = 380;
    // Propellant default values
    public static final double DEFAULT_PROPELLANT_DENSITY = 1.0;
    public static final double DEFAULT_SOLID_PROPELLANT_DENSITY = 1.65;
    public static final int DEFAULT_PROPELLANT_TEMPERATURE = 15;
}
